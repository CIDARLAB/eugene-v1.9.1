package org.cidarlab.eugene.solver.jacop;

import java.util.Collection;
import java.util.List;

import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.cache.SymbolTables;
import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.solver.Solver;
import org.cidarlab.eugene.solver.stats.jacop.SolverStats;
import org.cidarlab.eugene.visual.graphviz.RuleGraphVisualizer;

import JaCoP.constraints.Constraint;
import JaCoP.core.Domain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainRandom;
import JaCoP.search.IndomainSimpleRandom;
import JaCoP.search.LargestDomain;
import JaCoP.search.MostConstrainedDynamic;
import JaCoP.search.Search;
import JaCoP.search.SelectChoicePoint;
import JaCoP.search.SimpleSelect;


public class JaCoPSolver 
	implements Solver {

	private Store store;
	private SolverStats stats;
	
	public JaCoPSolver() {
		this.store = new Store();
		this.stats = new SolverStats();
	}
	
    public IntVar[] model(Device device) 
    		throws EugeneException {
    	List<Component> lstDeviceComponents = device.getAllComponents();
    	
    	IntVar[] variables = new IntVar[lstDeviceComponents.size()];
    	int i=0;
    	for(Component component : lstDeviceComponents) {

    		variables[i] = new IntVar(store, component.getName());
    		
    		/*
    		 * 2. load all Domain values (i.e. the parts)
			 */
    		if(component instanceof PartType) {

    			Collection<Part> parts = SymbolTables.getParts((PartType)component);
    			for(Part part : parts) {
    				int partId = (int)SymbolTables.getId(part.getName());
    				variables[i].addDom(partId, partId);
    			}
        		
    		} else if(component instanceof Part) {
    			int id = (int)SymbolTables.getId(component.getName());
   				variables[i].addDom(id, id);    			
    		}
    		
    		i++;
    	}
    	
    	return variables;
    }

    @Override
    public GeneratedDeviceArray solveProduct(Device device, List<Rule> lstRules, int N) 
    		throws EugeneException {
    	
    	GeneratedDeviceArray gda = null;
    	
//    	double sum = 0.0;
//    	double avg = 0.0;
//    	int k=0;
//    	for(int i=1; i<=30; i++) {
//    		//System.out.println("i: "+i);
//    		this.store = new Store();
    		
	    	long T1 = System.nanoTime();
	    	
	
	    	/*
	    	 * create the variables of the constraint solving problem
	    	 * i.e. the parts
	    	 */
	    	IntVar[] variables = this.model(device);
	
	    	/*
	    	 * map the Eugene rules onto JaCoP constraints
	    	 */
	    	this.imposeConstraints(variables, device, lstRules);
	    	
	    	
	    	/*
	    	 * for testing: print the store's information
	    	 */
	    	//store.print();
	    	
	    	/*
	    	 * now, let's solve the problem
	    	 */
	    	Domain[][] solutions = this.solve(variables, N, device);
	    	
	    	/*
	    	 * finally, we return the solutions
	    	 */
	    	gda = (GeneratedDeviceArray)EugeneBuilder.buildDeviceArray(
														device,
														solutions);
	    	return gda;
    }

    
    public void imposeConstraints(IntVar[] variables, Device device, List<Rule> rules) {
    	StringBuilder sb = new StringBuilder();
    	for(Rule rule : rules) {
    		
    		if(sb.toString().isEmpty()) {
    			sb.append(rule.getName());
    		} else {
    			sb.append(" /\\ ").append(rule.getName());
    		}
    		
    		try {
	    		Constraint constraint = rule.getPredicate().toJaCoP(store, variables, device, device.getAllComponents());

	    		if(null != constraint) {
	    			store.impose(constraint);
	    		}
    		} catch(EugeneException ee) {
    			ee.printStackTrace();
    		}
    	}
		RuleGraphVisualizer.addVertex(sb.toString());
    }
    
    public Domain[][] solve(IntVar[] variables, int N, Device device) 
    		throws EugeneException {
    	Search<IntVar> search = new DepthFirstSearch<IntVar>(); 

    	// 
    	//IndomainSimpleRandom
        SelectChoicePoint<IntVar> select = null;
        if(N != (-1)) {
			select =  new SimpleSelect<IntVar>(
							variables, 
							new MostConstrainedDynamic<IntVar>(), 
							new IndomainRandom<IntVar>());  
			search.getSolutionListener().setSolutionLimit(N);
        } else {        	
        	select = new SimpleSelect<IntVar>(
    				variables, 
    				new LargestDomain<IntVar>(),
    				new IndomainSimpleRandom<IntVar>()); 
        	search.getSolutionListener().searchAll(true);   
        }

        search.setPrintInfo(false);
        search.getSolutionListener().recordSolutions(true);
                
        // SOLVE
		try {
			search.labeling(store, select);
		} catch(OutOfMemoryError oome) {
			throw new EugeneException("I'm sorry! This problem is currently too big for me to solve!");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return search.getSolutionListener().getSolutions();
    }

	@Override
	public GeneratedDeviceArray solvePermute(Device device, List<Rule> rules,
			int N) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
