package org.cidarlab.eugene.data.pigeon;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.collection.CollectionElement;
import org.cidarlab.eugene.dom.collection.EugeneCollection;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.relation.Interaction;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.fact.relation.Relation;

import com.rits.cloning.Cloner;


public class Pigeon {

	private static Map<String, Character> Eugene2PigeonMap;
	private static Map<String, Integer> ColoringMap;
		
	public static void draw(String name) 
			throws EugeneException {
		/*
		 * first, retrieve the object from the symbol tables
		 */
		NamedElement element = SymbolTables.get(name);
		if(null == element) {
			throw new EugeneException("I don't know "+name);
		}
		
		/*
		 * and then draw it
		 */
		draw(element);
	}
	
	
	public static void draw(NamedElement element) 
			throws EugeneException {
		
		if(Eugene2PigeonMap == null) {
			loadMap();
		}
		if(null == ColoringMap) {
			ColoringMap = new HashMap<String, Integer>();
		}
		
		try {
			if(element instanceof Component) {
				visualizeComponent((Component)element);
			} else if(element instanceof DeviceArray) {
				visualizeDeviceArray((DeviceArray)element);
			} else if(element instanceof EugeneCollection) {
				visualizeCollection((EugeneCollection)element);
			}
		} catch(EugeneException ee) {
			throw new EugeneException (ee.toString());
		}
	}

	private static void visualizeComponent(Component component) 
			throws EugeneException {
		if(component instanceof Device) {
			System.out.println(visualize((Device)component));
		}
	}
	
	private static void visualizeCollection(EugeneCollection collection) 
			throws EugeneException {
		
		StringBuilder sb = new StringBuilder();
		String NEWLINE = "\r\n";
		//String NEWLINE = System.getProperty("line.separator");
		
//		for(CollectionElement element : collection.getElements()) {
		Iterator<CollectionElement> it = collection.getElements().iterator();
		CollectionElement element = it.next();
			if(element instanceof Device) {
				System.out.println(Arrays.toString(((Device)element).getDirections()));
				sb.append(toPigeonNoArcs((Device)element)).append(NEWLINE);
			}
			
//		}
		
		sb.append("# Arcs");
		
//		System.out.println(sb);
		
		WeyekinPoster.setPigeonText(sb.toString());
		WeyekinPoster.postMyBird();
	}
	
	private static void visualizeDeviceArray(DeviceArray deviceArray) 
			throws EugeneException {

		int size = deviceArray.size();
		if(size == 0) {
			return;
		}
		
		Set<String> setArcs = new HashSet<String>();
		StringBuilder sb = new StringBuilder();
		String NEWLINE = "\r\n";
		//String NEWLINE = System.getProperty("line.separator");
		for(int i=0; i<size; i++) {
			Device device = (Device)deviceArray.get(i);

//			System.out.println("visualizing -> "+device);
			
			List<Component> lst = device.getComponents();
			if(null != lst && !lst.isEmpty()) {
				int pos=0;
				for(Component c : lst) {

					if(c instanceof Part) {

						String pigeon = new String();
						
						PropertyValue pv = ((Part)c).getPropertyValue("Pigeon");
						if(null != pv) {
							pigeon = pv.getValue();
						} else {
							pigeon = toPigeon((Part)c);
						}

						if(device.getDirections()[pos] == '-') {
							if(pigeon.startsWith(">")) {
								pigeon = "<" + pigeon.substring(1);
							} else {
								pigeon = "<" + pigeon;
							}
						}
						
						sb.append(pigeon).append(NEWLINE);
						
						/* check for any regulatory interactions */
						List<Interaction> interactions = SymbolTables.getInteractions(c);
						if(null != interactions && !interactions.isEmpty()) {
							for(Interaction interaction : interactions) {
								Component lhs = (Component)interaction.getLhs();
								Component rhs = (Component)interaction.getRhs();
								setArcs.add(lhs.getName()+" "+toPigeonRelation(interaction.getRelation())+" "+rhs.getName());
							}
						}
					} else if (c instanceof Device) {
						sb.append(toPigeonNoArcs((Device)c));
						setArcs.addAll(getPigeonArcs((Device)c));
					}
					
					pos++;
				}
			}
			
			if(i<size-1) {
				sb.append(NEWLINE);
			}
		}
		
		/* now, let's create the arcs */
		sb.append("# Arcs").append(NEWLINE);
		for(String arc : setArcs) {
			sb.append(arc).append(NEWLINE);
		}

//		System.out.println(sb);
		
		WeyekinPoster.setPigeonText(sb.toString());
		WeyekinPoster.postMyBird();
	}
	
	private static Set<String> getPigeonArcs(Device device) {
		Set<String> setArcs = new HashSet<String>();
		for(Component c : device.getAllComponents()) {
			List<Interaction> interactions = SymbolTables.getInteractions(c);
			if(null != interactions && !interactions.isEmpty()) {
				for(Interaction interaction : interactions) {
					Component lhs = (Component)interaction.getLhs();
					Component rhs = (Component)interaction.getRhs();
					setArcs.add(lhs.getName()+" "+toPigeonRelation(interaction.getRelation())+" "+rhs.getName());
				}
			}
		}
		return setArcs;
	}
	
	private static String toPigeonNoArcs(Device device) 
			throws EugeneException {
		StringBuilder sb = new StringBuilder();
		String NEWLINE = "\r\n";

//		System.out.println("visualizing -> "+device.getName());
		
		List<Component> lst = device.getComponents();
		if(null != lst && !lst.isEmpty()) {
			int pos=0;
			for(Component c:lst) {

				if(c instanceof Part) {
					String pigeon = new String();
					
					PropertyValue pv = ((Part)c).getPropertyValue("Pigeon");
					if(null != pv) {
						pigeon = pv.getValue();
					} else {
						pigeon = toPigeon((Part)c);
					}

					if(device.getDirections()[pos] == '-') {
						if(pigeon.startsWith(">")) {
							pigeon = "<" + pigeon.substring(1);
						} else {
							pigeon = "<" + pigeon;
						}
					}
					
					sb.append(pigeon).append(NEWLINE);
					
				} else if (c instanceof Device) {
					
//					sb.append(".").append(NEWLINE);
					if(device.getDirections()[pos] == '-') {
						/*
						 * reverse the elements of the device
						 */
						Device reversed = new Cloner().deepClone((Device)c);
						reversed.reverseComponents();
						sb.append(toPigeonNoArcs(reversed));
					} else {
						sb.append(toPigeonNoArcs((Device)c));
					}
//					sb.append(".").append(NEWLINE);
				}
				pos++;
			}

		}
		
		return sb.toString();
	}
	
	private static String toPigeon(Part part) {
		/*
		 * now, retrieve the appropriate pigeon character from 
		 * the hashmap
		 */
		char pigeonType = getPigeonType(part.getPartType().getName());
		
		//NamedElement ne = c.get("Pigeon");
		//PropertyValue pv = (PropertyValue)ne;
		int color;
		if(ColoringMap.containsKey(part.getName())) {
			color = ColoringMap.get(part.getName());
		} else {
			color = getRandomColor();
			ColoringMap.put(part.getName(), new Integer(color));
		}
		
		return pigeonType + " " + part.getName()+ " " + color;
	}
	
	public static URI visualize(Device device) 
			throws EugeneException {
		if(null != device) {
			String pigeon = toPigeon(device);
			WeyekinPoster.setPigeonText(pigeon);
			URI url = WeyekinPoster.getMyBirdsURL();
			return url;
		}
		
		return null;
	}
	
	//private static Set<String> setArcs;
	private static String toPigeon(Device device) 
			throws EugeneException {
		StringBuilder sb = new StringBuilder();
		String NEWLINE = "\r\n";

		sb.append(toPigeonNoArcs(device));
		sb.append("# Arcs").append(NEWLINE);

		Set<String> setArcs = getPigeonArcs(device);
		if(!setArcs.isEmpty()) {
			Iterator<String> it = setArcs.iterator();
			while(it.hasNext()) {
				sb.append(it.next());
				if(it.hasNext()) {
					sb.append(NEWLINE);
				}
			}
		}
		
		return sb.toString();
	}
	
	private static String toPigeonRelation(Relation relation) {
		if(Relation.REPRESSES.equals(relation)) {
			return "rep";
		} else if(Relation.INDUCES.equals(relation) ||
				Relation.BINDS.equals(relation)) {
			return "ind";
		}
		return null;
	}
	
	private static void loadMap() {
		Eugene2PigeonMap = new HashMap<String, Character>();
		
		/*
		 * key   ... Eugene type
		 * value ... Pigeon type 
		 */
		Eugene2PigeonMap.put("P", 'p');
		Eugene2PigeonMap.put("PROMOTER", 'p');
		Eugene2PigeonMap.put("INDUCIBLEPROMOTER", 'p');
		Eugene2PigeonMap.put("CONSTITUTIVEPROMOTER", 'p');
		Eugene2PigeonMap.put("REGULATEDPROMOTER", 'p');
		Eugene2PigeonMap.put("REPRESSIBLEPROMOTER", 'p');

		Eugene2PigeonMap.put("RPROMOTER", 'p');
		Eugene2PigeonMap.put("IPROMOTER", 'p');
		Eugene2PigeonMap.put("PROMOTER", 'p');
		
		Eugene2PigeonMap.put("R", 'r');
		Eugene2PigeonMap.put("RBS", 'r');
		
		Eugene2PigeonMap.put("C", 'c');
		Eugene2PigeonMap.put("RECOMBINASE", 'c');
		Eugene2PigeonMap.put("PACKAGINGSITE", 'c');
		Eugene2PigeonMap.put("LYTICREPLICON", 'c');		
		Eugene2PigeonMap.put("CODINGSEQUENCE", 'c');
		Eugene2PigeonMap.put("RIBOZYME", 'c');
		Eugene2PigeonMap.put("LEADER", 'c');
		Eugene2PigeonMap.put("CDS", 'c');
		Eugene2PigeonMap.put("REPRESSOR", 'c');
		Eugene2PigeonMap.put("REPORTER", 'c');
		Eugene2PigeonMap.put("INDUCER", 'c');
		Eugene2PigeonMap.put("GENE", 'c');
		
		Eugene2PigeonMap.put("T", 't');
		Eugene2PigeonMap.put("TERMINATOR", 't');

		Eugene2PigeonMap.put("MO", 'o');
		Eugene2PigeonMap.put("FUSIONSITE", 'o');
		Eugene2PigeonMap.put("RECSITE", '>');
		Eugene2PigeonMap.put("ANTIREPRESSOR", 'g');
		Eugene2PigeonMap.put("SPACER", 's');
	}
	
	private static char getPigeonType(String eugeneType) {
//		System.out.println("[getPigeonType] -> "+eugeneType);
		if(Eugene2PigeonMap.containsKey(eugeneType.toUpperCase())) {
			return (Eugene2PigeonMap.get(eugeneType.toUpperCase())).charValue();
		} else {
			
		}
		return '?';
	}
	
	private static final int COLOR_MIN = 1;
	private static final int COLOR_MAX = 13;
	
	private static int getRandomColor() {
		return COLOR_MIN + (int)(Math.random() * ((COLOR_MAX - COLOR_MIN) + 1));
	}
}
