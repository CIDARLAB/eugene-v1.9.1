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

package org.cidarlab.eugene;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.LogManager;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.cidarlab.eugene.data.json.Eugene2JSON;
import org.cidarlab.eugene.data.pigeon.Pigeon;
import org.cidarlab.eugene.dom.SavableElement;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.output.ResultSet;
import org.cidarlab.eugene.parser.EugeneLexer;
import org.cidarlab.eugene.parser.EugeneParser;
import org.cidarlab.eugene.util.EugeneUtil;
import org.json.JSONObject;


public class EugeneExecutor {

	// nOutput indicates how the devices should be printed
	// nReturn == 0 -> no return (default)
	// nReturn == 1 -> Strings
	// nReturn == 2 -> Eugene Components
	public static Object execute(String sScript, int nReturn)
			throws RecognitionException {

		LogManager.getLogManager().reset();

		EugeneLexer lexer = new EugeneLexer(new ANTLRStringStream(sScript));
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		EugeneParser parser = new EugeneParser(tokens);
		
		long t1 = System.nanoTime();
		parser.initSymbolTables();
		long t2 = System.nanoTime();
		parser.prog();
		long t3 = System.nanoTime();
		
		Object results = null;
		if (nReturn == 1 || nReturn == 2) {
			// return the components of the result set as strings
			ResultSet rs = parser.getResultSet();
			if (null != rs) {
				HashMap<String, SavableElement> hmComponents = rs.getResults();
				if (nReturn == 2) {
					results = new HashMap<String, SavableElement>();
					Iterator<String> it = hmComponents.keySet().iterator();
					while (it.hasNext()) {
						String sKey = it.next();
						((HashMap<String, SavableElement>) results).put(
								new String(sKey), hmComponents.get(sKey));
					}
				} else {
					results = new String[hmComponents.size()];
					Iterator<String> it = hmComponents.keySet().iterator();

					int i = 0;
					while (it.hasNext()) {
						SavableElement objElement = hmComponents.get(it.next());
						if (null != objElement) {
							((String[]) results)[i++] = objElement.toString();							
						}
					}
				}
			}
		} else if(nReturn == 3) {
			ResultSet rs = parser.getResultSet();
			results = new HashSet<JSONObject>();
			
			if (null != rs) {
				HashMap<String, SavableElement> hmComponents = rs.getResults();
				
				// pigeonize every device
				Iterator<String> it = hmComponents.keySet().iterator();

				while (it.hasNext()) {
					
					SavableElement se = hmComponents.get(it.next());
					if(se instanceof DeviceArray) {
						int nSize = ((DeviceArray)se).size();
						for(int i=0; i<nSize; i++) {
							try {
                                JSONObject json;
                                Device device = (Device)((DeviceArray)se).get(i);
                                json = Eugene2JSON.toJSON(device);
                                json.put("pigeon-uri", Pigeon.visualize(device));
                                ((HashSet<JSONObject>)results).add(json);
                                
                                System.out.println("pigeon-uri: "+ Pigeon.visualize(device));
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
				}				
			}			
		}
		
		long t4 = System.nanoTime();
		// clean up the symbol tables
		parser.cleanUpNoExit();

		long t5 = System.nanoTime();
		
//		System.out.println("start-up time: "+((t2-t1)*Math.pow(10, -9))+"sec");
//		System.out.println("execution time: "+((t3-t2)*Math.pow(10, -9))+"sec");
//		System.out.println("result processing time: "+((t4-t3)*Math.pow(10, -9))+"sec");
//		System.out.println("clean-up time: "+((t5-t4)*Math.pow(10, -9))+"sec");
		
		return results;
	}

	// nReturn == 0 -> no return
	// nReturn == 1 -> return components as Strings
	// nReturn == 2 -> return components in a hash map
	public static Object execute(File fScript, int nReturn)
			throws RecognitionException, IOException {
		// read the file's content
		return EugeneExecutor.execute(
				EugeneUtil.readFile(fScript), 
				nReturn);
	}
}
