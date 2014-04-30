package org.cidarlab.eugene.solver;

/*
 * this will be the ultimate (and hopefully very efficient) solver ...
 */

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.solver.jacop.EugeneSolutionListener;
import org.cidarlab.eugene.solver.jacop.Variables;
import org.cidarlab.eugene.solver.stats.jacop.SolverStats;
import org.cidarlab.eugene.solver.stats.jacop.Stats;

import JaCoP.constraints.Alldifferent;
import JaCoP.constraints.XeqC;
import JaCoP.core.Domain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMin;
import JaCoP.search.IndomainSimpleRandom;
import JaCoP.search.LargestDomain;
import JaCoP.search.MostConstrainedDynamic;
import JaCoP.search.Search;
import JaCoP.search.SelectChoicePoint;
import JaCoP.search.SimpleMatrixSelect;

public class EfficientSolver 
		implements Solver {
	
	private Store store;
	private SolverStats stats;
	
	public EfficientSolver() {
		this.store = new Store();
		this.stats = new SolverStats();
	}
	
	@Override
	public GeneratedDeviceArray solveProduct(
			Device device, List<Rule> rules, int N) 
			throws EugeneException {
		
		long t1 = System.nanoTime();
		
    	/*
    	 * create the variables of the constraint solving problem
    	 * i.e. the parts
    	 */
    	IntVar[][] variables = null;
    	try {
    		variables = modelProduct(device);
    		//System.out.println("# possible devices: "+this.NUMBER_OF_DEVICES);
    	} catch(EugeneException ee) {
    		throw new EugeneException(ee.toString());
    	}

    	long t2 = System.nanoTime();
    	
    	/*
    	 * map the Eugene rules onto JaCoP constraints
    	 */
    	//imposeConstraints(store, variables, rules, device);
    	
    	
    	/*
    	 * for testing: print the store's information
    	 */
    	store.print();
    	
    	/*
    	 * now, let's solve the problem
    	 */
    	Domain[][] solutions = solve(variables, N);
    	
    	long t3 = System.nanoTime();
    	
    	/*
    	 * print the stats
    	 */
    	//this.stats.print();
    	
    	Stats.set(3, "modeling time", (t2-t1)*Math.pow(10, -9));
    	Stats.set(4, "constraint solving time", (t3-t2)*Math.pow(10, -9));
    	
    	/*
    	 * finally, we return the solutions
    	 */
		return new GeneratedDeviceArray(
				device,
				solutions);
	}
	
 
	
    private IntVar[][] modelProduct(Device device) 
    		throws EugeneException {
 
    	this.partPositions = new HashMap<String, Integer[]>();
    	
    	/*
    	 * we flatten the device => getAllComponents
    	 */
    	List<Component> components = device.getAllComponents();
    	int N = components.size();
    	
    	IntVar[][] variables = new IntVar[4][N]; 

		/*
		 * for every ``element'' in the design
		 */
    	int i=1;
    	for(Component component : components) {

			/*
			 * Variables:
			 */

    		/*
    		 * Position
    		 */
    		variables[Variables.POSITION][i-1] = new IntVar(store, "X_"+i+"_1", 1, N);
//    		variables[POSITION][i-1] = new IntVar(store, "X_"+i+"_1", i, i);
    		
    		/*
    		 * Strand/Orientation
    		 */
    		variables[Variables.ORIENTATION][i-1] = new IntVar(store, "X_"+i+"_2", -1, 1);
    		
    		/*
    		 * Type
    		 */
    		variables[Variables.PARTTYPE][i-1] = new IntVar(store, "X_"+i+"_3");
    		int id = -1;
    		if(component instanceof PartType) {
    			id = (int)SymbolTables.getId(component.getName());
    		} else if(component instanceof Part) {
    			id = (int)SymbolTables.getId(((Part)component).getPartType().getName());
    		}

    		if(id != (-1)) {
    			variables[Variables.PARTTYPE][i-1].setDomain(id, id);
    		}
    		
    		/*
    		 * parts
    		 */
    		variables[Variables.PARTS][i-1] = new IntVar(store, "X_"+i+"_4");
    		if(component instanceof PartType) {
    			Collection<Part> parts = SymbolTables.getParts((PartType)component);
    			for(Part part : parts) {
    				int partId = (int)SymbolTables.getId(part.getName());
    				variables[Variables.PARTS][i-1].addDom(partId, partId);
    			}
    		} else if(component instanceof Part) {
				int partId = (int)SymbolTables.getId(component.getName());
				variables[Variables.PARTS][i-1].addDom(partId, partId);
    		}
    		
    		i++;
    	}
    	
    	/*
    	 * for permutations
    	 */
    	store.impose(new Alldifferent(variables[Variables.POSITION]));
//    	store.impose(new Alldifferent(variables[1]));
//    	store.impose(new Alldifferent(variables[2]));
    	
    	/*
    	 * the order of the parts/part types must adhere to the given device
    	 */
    	for(int pos=0; pos<components.size(); pos++) {
    		Component component = components.get(pos);

    		int componentId = (int)SymbolTables.getId(component.getName());
    		if(component instanceof PartType) {
        		store.impose(new XeqC(variables[Variables.PARTTYPE][pos], componentId));
    		} else if(component instanceof Part) {
        		store.impose(new XeqC(variables[Variables.PARTS][pos], componentId));
    		}
    	}
    		
    	
    	/*
    	 * for the stats
    	 */
//    	System.out.println(calculateNrOfDevices(variables));
    	//this.stats.add("number of devices", 1);
    	
    	return variables;
    }
    
    private BigInteger calculateNrOfDevices(IntVar[] variables) {
    	System.out.println("*****");
    	for(int i=0; i<variables.length; i++) {
    		System.out.println(variables[i].getSize());
    	}
    	return null;
    }
    
    private Map<String, Integer[]> partPositions;
    
    private void addPartPosition(IntVar iv, int i) {
		Integer[] positions = null;
		if(partPositions.containsKey(iv.id())) {
			positions = partPositions.get(iv.id());
			positions = ArrayUtils.add(positions, i);
		} else {
			positions = new Integer[1];
			positions[0] = i;
		}
		partPositions.put(iv.id(), positions);
    }

    public void imposeConstraints(Store store, IntVar[][] variables, List<Rule> rules, Device device) 
    		throws EugeneException {
    	
    	int N = device.getAllComponents().size();
    	for(int i=0; i<N; i++) {
    		
    	}
//    	for(Rule rule : rules) {
//    		try {
//	    		Constraint constraint = rule.getPredicate().toJaCoP(store, variables, device, device.getAllComponents());
//	    		if(null != constraint) {
//	    			store.impose(constraint);
//	    		}
//    		} catch(EugeneException ee) {
//    			ee.printStackTrace();
//    		}
//    	}

//    	int N = 1;    	
//    	String propertyName = "numProp";
//    	String operator = "=";
//    	String partTypeName = "X";
//    	
//    	System.out.println("imposing "+partTypeName+"."+propertyName+" "+operator+" "+N+"...");    	
//
//    	IntVar numPropVar = (IntVar)store.findVariable(propertyName);
////    	BooleanVar bVar = new BooleanVar(store);
////    	store.impose(new Reified(new XeqC(numPropVar, Eugene2JaCoP.toASCII(String.valueOf(N))), bVar));
//    	
//    	store.impose(new XgtC(numPropVar, Eugene2JaCoP.toASCII(String.valueOf(N))));
//    	/*
//    	 * for every part of part type X
//    	 */
//    	PartType pt = (PartType)SymbolTables.get(partTypeName);
//    	Collection<Part> lstParts = SymbolTables.getParts(pt);
//    	for(Part part : lstParts) {
//    		int partId = (int)SymbolTables.getId(part.getName());
//    		
//    		PrimitiveConstraint[] pc = null;
//
//    		for(int i=0; i<variables.length; i++) {
//	    		
//
//	    		IntDomain dom = variables[i].dom();
//	    		if(dom.contains(partId)) {
//	    			System.out.println("IF "+propertyName+" "+operator+" "+Eugene2JaCoP.toASCII(String.valueOf(N))+", THEN place "+part.getName()+" ("+partId+") into position "+i);
////	    			BooleanVar bVar = new BooleanVar(store);
//	    			if(null == pc) {
//		    			pc = new PrimitiveConstraint[1];
//		    			pc[0] = new XeqC(variables[i], partId);
//	    			} else {
//	    				pc = ArrayUtils.add(pc, new XeqC(variables[i], partId));
//	    			}
//	    		}
//	    	
//	    	}
//
//    		if(null != pc) {
//				store.impose(new Or(pc));
//			}
//    	}
    	
//    	store.impose(new XeqC(numPropVar, Eugene2JaCoP.toASCII(String.valueOf(N))));

//    	String txt = "txt4";
//    	System.out.println("imposing X.txtProp == "+txt+"...");    	
//    	IntVar txtPropVar = (IntVar)store.findVariable("txtProp");
//    	pc[1] = new XeqC(txtPropVar, Eugene2JaCoP.toASCII(String.valueOf(txt)));
//    	
//    	store.print();
    	
//    	for(Rule rule : rules) {
//    		try {
//
//	    		Constraint constraint = rule.getPredicate().toJaCoP(store, variables, device, device.getAllComponents());
//	    		
//	    		if(null != constraint) {
//	    			store.impose(constraint);
//	    		}
//    		} catch(EugeneException ee) {
//    			ee.printStackTrace();
//    		}
//    	}
    }

    public Domain[][] solve(IntVar[][] variables, int N) {
    	Search<IntVar> search = new DepthFirstSearch<IntVar>(); 
    	
        EugeneSolutionListener<IntVar> esl = new EugeneSolutionListener<IntVar>();
        search.setSolutionListener(esl);

        SelectChoicePoint<IntVar> select = null;
        if(N != (-1)) {
			select =  new SimpleMatrixSelect<IntVar>(
							variables, 
							new MostConstrainedDynamic<IntVar>(), 
							new IndomainSimpleRandom<IntVar>());  
			
			search.getSolutionListener().setSolutionLimit(N);
			   
        } else {        	
        	select = new SimpleMatrixSelect<IntVar>(
    				variables, 
    				new LargestDomain<IntVar>(),
    				new IndomainMin<IntVar>()); 
        	search.getSolutionListener().searchAll(true);   
        
        }
        
        search.setPrintInfo(true);
        search.getSolutionListener().recordSolutions(true);
                
//		long T1 = System.nanoTime();

        // SOLVE
		try {
//			store.print();
			search.labeling(store, select);
		} catch(Exception e) {
			e.printStackTrace();
		}

//		long T2 = System.nanoTime();
		
//		double PROCESSING_TIME = (T2 - T1) * Math.pow(10, -9);
//		System.out.println("processing time: "+PROCESSING_TIME+"sec");

//        search.getSolutionListener().printAllSolutions();

//		System.out.println(nProcessing);

//		search.printAllSolutions();
		
		return search.getSolutionListener().getSolutions();
    }
    
	@Override
	public GeneratedDeviceArray solvePermute(
			Device device, List<Rule> rules, int N) {
		// TODO Auto-generated method stub
		return null;
	}


}
