package org.cidarlab.eugene.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.StackElement;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.exception.EugeneException;

import com.rits.cloning.Cloner;


public class JCSPersistor {
	private JCS jcsCache;	
	private Cloner cloner;

	public JCSPersistor() {
		
		/*** JCS CACHE ***/
		try {
			jcsCache = JCS.getInstance("eugene");
		} catch (Exception e) {
			e.printStackTrace();
		}

		cloner = new Cloner();
	}
	
	public void put(String sName, NamedElement objElement)
			throws EugeneException {

//		System.out.println("[JCS.put] -> "+sName+", "+objElement+", "+getGroupName());
		if (null != sName && null != objElement) {
			try {
				if(objElement instanceof Component) {
//					System.out.println("[JCS.put] -> "+sName);
					jcsCache.putInGroup(sName, "MAIN",
						cloner.deepClone(objElement));
				} else {
//					System.out.println("[JCS.put] -> "+sName+", "+getGroupName());
					jcsCache.putInGroup(sName, getGroupName(),
						cloner.deepClone(objElement));
				}
			} catch(Exception e) {
				throw new EugeneException(e.toString());
			}
		}
	}

	public NamedElement get(String sName) {
		if (null != sName && null != jcsCache) {
			String sGroupName = getGroupName();
			
			NamedElement obj = (NamedElement) jcsCache.getFromGroup(sName, sGroupName);

			// TODO: go down the stack and check for the element
			if(null == obj && !"MAIN".equals(sGroupName)) {
				obj = (NamedElement) jcsCache.getFromGroup(sName, "MAIN");
			}
			return obj;
		}
		return (NamedElement) null;
	}

	private static String getGroupName() {
		String sGroupName = "MAIN";
		StackElement objStackElement = SymbolTables.peek();

		if (null != objStackElement) {
			sGroupName = String.valueOf(objStackElement.hashCode()+SymbolTables.stackSize());
		}
		//System.out.println("[JCS.getGroupName] -> "+sGroupName);
		return sGroupName;
	}


	/** CONTAINS **/
	public boolean contains(String sName) {
		
        if(null == jcsCache) {
        	return false;
        }

        if(null != sName) {
        	
        	/**
        	if(null != SymbolTables.peek()) {        		
        		StackElement se = SymbolTables.pop();
        		System.out.println(se);        		
        		SymbolTables.push(se);        		
        	}
        	**/
        	
        	return (null != jcsCache.getFromGroup(sName, getGroupName()));
        }
        return false;
	}

	/** REMOVE **/
	public void remove(String sName) {
		if(null != sName) {
			jcsCache.remove(sName, getGroupName());
		}
	}

	/** CLEANUP **/
	public void clear() {
		if (null != jcsCache) {
			try {
				jcsCache.clear();
			} catch (CacheException e) {
			}
			jcsCache = null;

			//System.out.println("XXXXX");
			//EugeneUtil.deleteDirectory(new File("./eugene.cache"));
			//System.out.println("XXXXX");
		}
	}

	public void clear(String sGroup) {

		if (null != sGroup && null != jcsCache) {
			try {
				Set<String> setKeys = this.jcsCache.getGroupKeys(sGroup);
				if (null != setKeys && !setKeys.isEmpty()) {
					Iterator<String> it = setKeys.iterator();
					while (it.hasNext()) {
						String s = it.next();
						jcsCache.remove(s, sGroup);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Collection<Part> getParts(PartType pt) {
		Collection<Part> colParts = new ArrayList<Part>();

		String sGroupName = getGroupName();
		if (null != sGroupName && null != jcsCache) {
			Set<String> setKeys = jcsCache.getGroupKeys(sGroupName);
			if (null != setKeys && !setKeys.isEmpty()) {
				Iterator<String> it = setKeys.iterator();
				while (it.hasNext()) {
					String s = it.next();
					NamedElement objElement = (NamedElement) jcsCache
							.getFromGroup(s, sGroupName);
					if (objElement instanceof Part) {
						Part objPart = (Part) objElement;
						if (objPart.getPartType().equals(pt)) {
							colParts.add((Part) objElement);
						}
					}
				}
			}
		}
		return colParts;
	}

}
