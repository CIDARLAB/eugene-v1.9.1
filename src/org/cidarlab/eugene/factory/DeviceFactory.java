/*
Copyright (c) 2012 Boston University.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL BOSTON UNIVERSITY BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
BOSTON UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

BOSTON UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND BOSTON UNIVERSITY HAS
NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.
 */

package org.cidarlab.eugene.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.rules.RuleEngine;


public class DeviceFactory {

	public static List<Device> generateDevices(String sFunction, 
				List<Component> lstComponents, Collection<Rule> colRules, int nCap, String sDeviceName)
			throws Exception {

		//System.out.println("[DeviceFactory.generateDevices] -> "+lstComponents);
		
		if (null == sFunction
				|| (!(EugeneConstants.PRODUCT.equals(sFunction)) && 
						!(EugeneConstants.PERMUTE.equals(sFunction)))) {
			throw new Exception(
					"Eugene's device generation capabilities do not support a "
							+ sFunction + " function!");
		}

		// first, check if the given device passes all rules
		// that are defined on the abstract device
/**
		if (null != colRules && EugeneConstants.PRODUCT.equals(sFunction)) {
			boolean b = true;
			Iterator<Rule> it = colRules.iterator();
			Rule r = (Rule) null;
			while (it.hasNext() && b) {
				r = it.next();
				b = b & RuleEngine.abstractEvaluate(r, objDevice);
			}

			if (!b) {
				throw new Exception("The " + objDevice.getName()
						+ " device violates the " + r.getName() + " rule!");
			}
		}
**/
		
		// do the permutations/productions
		Iterator<List<Component>> it = null;
		if (EugeneConstants.PERMUTE.equals(sFunction)) {
			it = DeviceFactory.permute(lstComponents).iterator();
		} else {
			it = DeviceFactory.product(prepareProduct(lstComponents))
					.iterator();
		}

		int n = 1;

		List<Device> lstDevices = (List<Device>) null;
		if (nCap > 0) {
			lstDevices = new ArrayList<Device>(nCap);
		} else {
			lstDevices = new ArrayList<Device>();
		}

		Device objGeneratedDevice = (Device) null;
		boolean bRules = colRules != null && !colRules.isEmpty();
		while (it.hasNext() && (nCap == -1 || n <= nCap)) {
			objGeneratedDevice = EugeneBuilder.buildDevice(sDeviceName + "_" + n, it.next(), (char[])null);
			objGeneratedDevice.setDeviceType(sDeviceName);

			if (null != objGeneratedDevice) {
				boolean b = true;
				if (bRules && b) {
					Iterator<Rule> itRules = colRules.iterator();
					while (itRules.hasNext() && b) {
						Rule objRule = itRules.next();
						b = b & RuleEngine.evaluate(
								objRule, objGeneratedDevice);
					}
				}

				if (b) {
					// the device satisfies all the rules
					// add the device to the result set, if the result set is
					// not full yet
					if ((nCap > 0 && n <= nCap) || nCap == (-1)) {

						SymbolTables.put(objGeneratedDevice);
						lstDevices.add(objGeneratedDevice);
						n++;
						
						if(n%6000 == 0) {
							System.gc();
						}
					}
				}
			}
		}
		return lstDevices;
	}

	private static ArrayList<Collection<Component>> prepareProduct(
			List<Component> lstDeviceComponents) {
		
		// then, create a arraylist of collections which will serve as
		// input to the cartesian product function
		ArrayList<Collection<Component>> lst = new ArrayList<Collection<Component>>();

		ArrayList<Component> lstComponents = null;

		for (Component objComponent : lstDeviceComponents) {

			lstComponents = new ArrayList<Component>();

			if (objComponent instanceof Part) {
				lstComponents.add((Part) objComponent);
			} else if (objComponent instanceof Device) {
				lstComponents.add((Device) objComponent);
			} else if (objComponent instanceof PartType) {

				ArrayList<Part> lstParts = new ArrayList<Part>(
						SymbolTables.getParts((PartType) objComponent));
				if (!lstParts.isEmpty()) {
					ArrayList<Component> lstTmp2 = new ArrayList<Component>();
					for (int k = 0; k < lstParts.size(); k++) {
						lstTmp2.add(lstParts.get(k));
					}
					lstComponents.addAll(lstTmp2);
				} else {
					// if no parts of the given part type exists,
					// then add the part type to the components list
					lstComponents.add(objComponent);
				}
			}
			lst.add(lstComponents);
		}

		return lst;
	}

	private static <T> Collection<List<T>> permute(Collection<T> input) {

		Collection<List<T>> output = new ArrayList<List<T>>();
		if (input.isEmpty()) {
			output.add(new ArrayList<T>());
			return output;
		}
		List<T> list = new ArrayList<T>(input);
		T head = list.get(0);
		List<T> rest = list.subList(1, list.size());
		for (List<T> permutations : permute(rest)) {
			List<List<T>> subLists = new ArrayList<List<T>>();
			for (int i = 0; i <= permutations.size(); i++) {
				List<T> subList = new ArrayList<T>();
				subList.addAll(permutations);
				subList.add(i, head);
				subLists.add(subList);
			}
			output.addAll(subLists);
		}
		return output;
	}

	/*
	 * example: buckets=[[A1,A2,..Aa],[B1,B2,..Bb],...,[X1,X2,..Xx]] the method
	 * will return an iterable that allows to iterate over all elements from
	 * Cartesian product [A1,A2,..Aa]x[B1,B2,..Bb]x[X1,X2,..Xx] that means it
	 * returns an iterator with all combinations:
	 * 
	 * [A1,B1,...X1] [A2,B1,...,X1] [A3,B1,...,X1] ... [A1,B2,...,X1]
	 * [A2,B2,...,X1] ... [Aa,Bb,...,Xx]
	 * 
	 * @param sets: ordered List of collections of <T> structures
	 * 
	 * @return: Iterable of List<T> with all elements of cartesian product
	 */
	private static <T> Iterable<List<T>> product(final List<Collection<T>> sets) {
		return new Iterable<List<T>>() {

			private long size = 1L;

			{
				for (Collection<T> set : sets) {
					size *= (long) set.size();
				}
			}

			@Override
			public Iterator<List<T>> iterator() {
				return new Iterator<List<T>>() {

					long counter = 0;
					ArrayList<T> currentValues = new ArrayList<T>(sets.size());
					ArrayList<Iterator<T>> iterators = new ArrayList<Iterator<T>>(
							sets.size());

					{
						for (Iterable<T> set : sets) {
							Iterator<T> it = set.iterator();
							iterators.add(it);
							if (it.hasNext()) {
								currentValues.add(it.next());
							}
						}
					}

					@Override
					public boolean hasNext() {
						return counter < size;
					}

					@Override
					public List<T> next() {
						List<T> result = new LinkedList<T>(currentValues);
						counter++;
						increment(0);
						return result;
					}

					private void increment(int i) {
						if (iterators.get(i).hasNext()) {
							currentValues.set(i, iterators.get(i).next());
						} else {
							iterators.set(i, sets.get(i).iterator());
							currentValues.set(i, iterators.get(i).next());
							if (i < iterators.size() - 1) {
								increment(i + 1);
							}
						}
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException(
								"impossible to change combination set");
					}
				};
			}
		};
	}
}
