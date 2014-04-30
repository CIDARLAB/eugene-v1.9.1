package org.cidarlab.eugene.cache;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.LogManager;

import org.apache.commons.lang3.ArrayUtils;
import org.cidarlab.eugene.builder.EugeneBuilder;
import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.PropertyValue;
import org.cidarlab.eugene.dom.arrays.DeviceArray;
import org.cidarlab.eugene.dom.arrays.GeneratedDeviceArray;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.Property;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.cidarlab.eugene.dom.relation.Interaction;
import org.cidarlab.eugene.dom.relation.Participant;
import org.cidarlab.eugene.dom.relation.RelationType;
import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.fact.BinaryFact;
import org.cidarlab.eugene.fact.Fact;
import org.cidarlab.eugene.fact.relation.Relation;
import org.cidarlab.eugene.rules.tree.predicate.LogicalAnd;
import org.cidarlab.eugene.rules.tree.predicate.BinaryPredicate;
import org.cidarlab.eugene.rules.tree.predicate.EndsWith;
import org.cidarlab.eugene.rules.tree.predicate.LogicalPredicate;
import org.cidarlab.eugene.rules.tree.predicate.LogicalNot;
import org.cidarlab.eugene.rules.tree.predicate.LogicalOr;
import org.cidarlab.eugene.rules.tree.predicate.Precedence;
import org.cidarlab.eugene.rules.tree.predicate.Predicate;
import org.cidarlab.eugene.rules.tree.predicate.RulePredicate;
import org.cidarlab.eugene.rules.tree.predicate.StartsWith;
import org.cidarlab.eugene.rules.tree.predicate.UnaryPredicate;
import org.cidarlab.eugene.rules.tree.predicate.LogicalXor;
import org.cidarlab.eugene.rules.tree.predicate.counting.Contains;
import org.cidarlab.eugene.rules.tree.predicate.positional.nextto.AllNextTo;
import org.cidarlab.eugene.rules.RuleEngine;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.ThisShouldNotHappenError;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;
import org.neo4j.kernel.impl.util.FileUtils;

import JaCoP.constraints.Constraint;
import JaCoP.constraints.IfThen;
import JaCoP.constraints.XeqC;
import JaCoP.core.BoundDomain;
import JaCoP.core.Domain;
import JaCoP.core.IntDomain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.ValueEnumeration;
import JaCoP.search.CreditCalculator;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMiddle;
import JaCoP.search.IndomainMin;
import JaCoP.search.IndomainSimpleRandom;
import JaCoP.search.InputOrderSelect;
import JaCoP.search.LargestDomain;
import JaCoP.search.LargestMin;
import JaCoP.search.MostConstrainedDynamic;
import JaCoP.search.MostConstrainedStatic;
import JaCoP.search.PrintOutListener;
import JaCoP.search.Search;
import JaCoP.search.SelectChoicePoint;
import JaCoP.search.SimpleMatrixSelect;
import JaCoP.search.SimpleSelect;
import JaCoP.search.SimpleSolutionListener;
import JaCoP.search.SmallestMax;
import JaCoP.search.SolutionListener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Neo4jPersistor {

	private static final String DB_NAME = "data/eugene";
    private GraphDatabaseService graphDb;
    private Transaction tx;
    private ExecutionEngine engine;
    private ObjectMapper mapper;
    
    public Neo4jPersistor(String dbName) {
    	
    	// first, delete the database
    	this.clearDb();
    	
    	//System.out.println("dbName: "+dbName);
   		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_NAME );
		this.engine = new ExecutionEngine( this.graphDb );		
   		this.mapper  = new ObjectMapper();

        registerShutdownHook( graphDb );
    }
    
    public void clearDb() {
    	if(null != graphDb) {
    		graphDb.shutdown();
    	}

        try {
            FileUtils.deleteRecursively( new File( DB_NAME ) );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }
    
    public void startTransaction() {
    	this.tx = graphDb.beginTx();
    }
        
    private static void registerShutdownHook( final GraphDatabaseService graphDb ) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread() {
            @Override
            public void run() {   
//            	System.out.println("*** shutting down neo4j ***");
            	if(null != graphDb) {
            		graphDb.shutdown();
            	}
            }
        } );
    }

    public void shutdown() {
    	if(null != graphDb) {
    		graphDb.shutdown();
    	}
    }

    public void finalize() {
//    	System.out.println("[finalize]");
    	if(null != this.tx) {
    		this.tx.success();
    		this.tx.finish();
    		this.tx = null;
    	}
    }
    
    public long put(String sName, Component component) 
    		throws Exception {
    	
		if(null != component) {
			Node node = null;
			if(component instanceof Part) {
				node = part2node((Part)component);
			} else if(component instanceof PartType) {
				node = parttype2node((PartType)component);
			} else if(component instanceof Device) {
				node = device2node((Device)component);
			}
			
			if(null != node) {
				this.tx.success();
				return Long.valueOf(node.getId());
			}
		}
		return -1;
	}
    
    public void put(Interaction interaction) 
    		throws EugeneException {
    	
    	//System.out.println("[neo4j.put] -> "+interaction.toString());
    	
    	Participant lhs = interaction.getLhs();
    	Participant rhs = interaction.getRhs();
    	
    	if(lhs instanceof Part || lhs instanceof PartType) {
    		
    	}
    	
    	//SymbolTables.getId(sName);
    }

    public List<Interaction> queryInteractions(String componentName) {
    	// first, we retrieve the component's node
    	Node node = this.queryNodeByName(componentName);
    	if(null != node) {
    		/*** REPRESSES relations ***/
    		List<Interaction> lst = this.queryRepressesInteractions(node);
    		
    		lst.addAll(this.queryInducesInteractions(node));

    		// now, we iterate over the query results
    		return lst;
    	}
    	return null;
    } 
    
    private List<Interaction> queryRepressesInteractions(Node node) {
    	
		StringBuilder sb = new StringBuilder();
		sb.append("START c=node(").append(node.getId()).append(") ");
		sb.append("MATCH c-[:").append(Relation.REPRESSES).append("]->r ");
		sb.append("RETURN c, r");
		
//		System.out.println("[Neo4j.queryInteractions] -> "+sb.toString());
		
		ExecutionResult result = this.engine.execute(sb.toString());
		List<Interaction> lst = new ArrayList<Interaction>();

		for ( Map<String, Object> row : result ) {
			Node[] pair = new Node[2];
			int j=0;
			for ( Entry<String, Object> column : row.entrySet() ) {
		        pair[j++] = (Node)column.getValue();
		    }
//			System.out.println("[Neo4j.queryInteractions] -> "+Arrays.toString(pair));
			
			Component lhs = this.node2part(pair[0]);
			Component rhs = this.node2part(pair[1]);
			lst.add(new Interaction(lhs, Relation.REPRESSES, rhs));
		}
		
		return lst;
    }

    private List<Interaction> queryInducesInteractions(Node node) {
    	
		StringBuilder sb = new StringBuilder();
		sb.append("START c=node(").append(node.getId()).append(") ");
		sb.append("MATCH r-[:").append(Relation.INDUCES).append("]->c ");
		sb.append("RETURN r, c");
		
//		System.out.println("[Neo4j.queryInteractions] -> "+sb.toString());
		
		ExecutionResult result = this.engine.execute(sb.toString());
		List<Interaction> lst = new ArrayList<Interaction>();

		for ( Map<String, Object> row : result ) {
			Node[] pair = new Node[2];
			int j=0;
			for ( Entry<String, Object> column : row.entrySet() ) {
		        pair[j++] = (Node)column.getValue();
		    }
			
//			System.out.println("[Neo4j.queryInteractions] -> "+Arrays.toString(pair));
			
			Component lhs = this.node2part(pair[1]);
			Component rhs = this.node2part(pair[0]);
			lst.add(new Interaction(lhs, Relation.INDUCES, rhs));
		}
		
		return lst;
    }

    
	public long[][] queryPairs(String relation, Component c1, Component c2) 
			throws EugeneException {
		
		/*
		 * START p1=node(*), p2=node(*)
		 * MATCH p1-[:REPRESSES]->p2
		 * RETURN p1, p2
		 */
		
		StringBuilder sb = new StringBuilder();
		sb.append("START c1=node(*), c2=node(*) ");
		sb.append("MATCH c1-[:").append(relation).append("]->c2 ");
		sb.append("WHERE ");
		boolean b = false;
		if(c1 instanceof PartType) {
			sb.append("HAS(c1.parttype) AND c1.parttype='").append(c1.getName()).append("' ");
			b = true;
		} else if(c1 instanceof Part) {
			sb.append("HAS(c1.COMPONENT_NAME) AND c1.COMPONENT_NAME='").append(c1.getName()).append("' ");
			b = true;
		}
		
		if(c2 != null) {
			if(c2 instanceof PartType) {
				if(b) {
					sb.append("AND ");
				}
				sb.append("HAS(c2.parttype) AND c2.parttype='").append(c2.getName()).append("' ");
			} else if(c1 instanceof Part) {
				if(b) {
					sb.append("AND ");
				}
				sb.append("HAS(c1.COMPONENT_NAME) AND c1.COMPONENT_NAME='").append(c1.getName()).append("' ");
			}
		}
		sb.append("RETURN c1, c2");

		ExecutionResult result = this.engine.execute(sb.toString());
		
		long[][] pairs = new long[1][2];

		for ( Map<String, Object> row : result ) {

			long[] pair = new long[2];
			int j=0;
			for ( Entry<String, Object> column : row.entrySet() ) {
		        pair[j++] = ((Node)column.getValue()).getId();
		    }

			pairs = ArrayUtils.add(pairs, pair);
		}
		pairs = ArrayUtils.remove(pairs, 0);

		return pairs;
	}
	
/*****	
    public long[] product(long nDeviceId, List<Rule> lstRules, int N) {
    	/*
    	 * 1. load all Variables (i.e. the device's components) 
    	 * 2. load all Domain values (i.e. the parts)
    	 * 3. load the constraints (i.e. by traversing the rule tree)
    	 *
    	long[] components = this.getOrderedDeviceComponents(nDeviceId);
    	
    	/*
    	 * 1. load all Variables (i.e. the device's components)
    	 * 
    	
    	Store store = new Store();
    	
    	List<Component> lstDeviceComponents = new ArrayList<Component>();
    	
    	IntVar[] variables = new IntVar[components.length];
    	int i=0;
    	for(long component : components) {
    		Node node = this.graphDb.getNodeById(component);
       	 	
    		// here, we need to deserialize the object using the JSON string
    		
    		/*
    		 * 2. load all Domain values (i.e. the parts)
			 *
    		if(PartType.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
    			
        		lstDeviceComponents.add(SymbolTables.getPartType((String)node.getProperty(EugeneConstants.NODE_NAME)));

    			// load all parts of the part type
    			long[] partIds = this.queryParts(node);    			
        		
    			IntVar iv = new IntVar(store, (String)node.getProperty(EugeneConstants.NODE_NAME));
        		for(int k=0; k<partIds.length; k++) {
        			iv.addDom((int)partIds[k], (int)partIds[k]);
        		}
        		
        		variables[i++] = iv;
        		
    		} else if(Part.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
    			
        		lstDeviceComponents.add(this.node2part(node));

        		variables[i++] = new IntVar(store, (String)node.getProperty(EugeneConstants.NODE_NAME), (int)node.getId(), (int)node.getId());
    			
    		}
    	}
    	
    	/*
    	 * 3. load the constraints (i.e. by traversing the rule tree)
    	 *
    	for(Rule rule : lstRules) {
    		//System.out.println(rule.getPredicate());
    		
    		Constraint constraint = rule.getPredicate().toJaCoP(store, lstDeviceComponents, variables);
   			store.impose(constraint);
    		
    	}

//    	System.out.println(store);
    	
		Search<IntVar> label = new DepthFirstSearch<IntVar>(); 
        SelectChoicePoint<IntVar> select = null;
        if(N != (-1)) {
			select = new InputOrderSelect<IntVar>(store, variables, new IndomainSimpleRandom<IntVar>()); 
        	label.getSolutionListener().setSolutionLimit(N);
        } else {
        	select = new InputOrderSelect<IntVar>(store, variables, new IndomainMin<IntVar>()); 
            label.getSolutionListener().searchAll(true);            
        }

        label.setPrintInfo(false);
        label.getSolutionListener().recordSolutions(true);
                
		long T1 = System.nanoTime();

        // SOLVE
        label.labeling(store, select);

		long T2 = System.nanoTime();		
		//System.out.println("processing time: "+(T2-T1)*Math.pow(10, -9)+"sec");

		//System.out.println(label.getSolutionListener().getSolutions().length+" solutions generated...");
		
//		for (i=1; i<=label.getSolutionListener().solutionsNo(); i++) { 
//			System.out.print("Solution " + i + ": "); 
//			for (int j=0; j<label.getSolution(i).length; j++) { 
//				System.out.print(
//						label.getSolution(i)[j]+", "); 
//			}
//			System.out.println(); 
//		}
        
        System.out.println("number of solutions: "+label.getSolutionListener().solutionsNo());

        Node device_node = this.graphDb.getNodeById(nDeviceId);
        Node solutions_node = this.graphDb.createNode();
        String sDeviceName = (String)device_node.getProperty(EugeneConstants.NODE_NAME);
        solutions_node.setProperty(EugeneConstants.NODE_NAME, sDeviceName+"_PRODUCT");
        solutions_node.createRelationshipTo(device_node, EugeneRelation.PRODUCT_OF);
        
		long[] solutionIds = new long[label.getSolutionListener().solutionsNo()];
		for (i=1; i<=label.getSolutionListener().solutionsNo(); i++) {
		
			Node solution_node = this.graphDb.createNode();
			solution_node.setProperty(EugeneConstants.NODE_NAME, sDeviceName+"_"+i);

			solutions_node.createRelationshipTo(solution_node, EugeneRelation.PRODUCT_OF);
//			System.out.print(solution_node.getProperty(EugeneConstants.NODE_NAME)+" -> [");
			solutionIds[i-1] = solution_node.getId();
			
			for (int j=0; j<label.getSolutionListener().getSolution(i).length; j++) {

				ValueEnumeration ve = label.getSolutionListener().getSolution(i)[j].valueEnumeration();
				
				while(ve.hasMoreElements()) {
					Node node = this.graphDb.getNodeById((long)ve.nextElement());
					Relationship rel = solution_node.createRelationshipTo(node, EugeneRelation.CONSISTS_OF);
					rel.setProperty("position", j);

//					System.out.print(node.getProperty(EugeneConstants.NODE_NAME));
				}

//				if(j<label.getSolutionListener().getSolution(i).length-1) {
//					System.out.print(", ");
//				}
			}

//			System.out.println("] ");
		}
//		
		return solutionIds;

//		return null;
    }
**/    
    public long[] createProduct(long nDeviceId, long N, List<Rule> lstRules) 
    		throws EugeneException {
    	//System.out.println("[neo4j.createProduct] -> Rules ");
    	for(Rule rule : lstRules) {
    		System.out.println(rule);
    	}
    	
    	Node deviceNode = this.graphDb.getNodeById(nDeviceId);
    	deviceName = (String)deviceNode.getProperty(EugeneConstants.NODE_NAME);
    	
    	
    	// do some preprocessing 
    	//preProcess(device_node, lstRules);
    	
    	//productTraverse(device_node);
    	return this.traverseDeviceInstances(deviceNode, N, lstRules);
    }
    

	private long[] traverseDeviceInstances(Node deviceNode, long N, List<Rule> lstRules) {
		
		try {
			long[] device_components = this.getOrderedDeviceComponents(deviceNode.getId());

			
			/* first, we create arc consistency */
			//buildConstrainGraph(deviceNode, device_components, lstRules);
			
			generatedDevices = new long[1];
			
			current_device = new long[device_components.length];
			counter = 0;
			if(N == -1) {
				MAX_DEVICES = new Long(Long.MAX_VALUE).longValue();
			} else {
				MAX_DEVICES = N;
			}
			
			traverseComponents(
					deviceNode,
					device_components,
					0,
					lstRules);

		} catch(Exception e) {
			//e.printStackTrace();
		}
				
		return ArrayUtils.remove(generatedDevices, 0);
	}
	
/***	
	public void buildConstrainGraph(Node deviceNode, long[] device_components, List<Rule> rules) {
		
		Node cgNode = this.graphDb.createNode();
		
		for(Rule rule : rules) {
			
			Node ruleNode = this.graphDb.createNode();
			ruleNode.setProperty(EugeneConstants.NODE_NAME, rule.getName());
			
			if(rule.getDeviceId() == deviceNode.getId()) {
				ruleNode.createRelationshipTo(deviceNode, EugeneRelation.ON);
			}
			
			ruleNode.createRelationshipTo(
					createConstraintGraph(rule.getPredicate()),
					EugeneRelation.CONSTRAINT);

			cgNode.createRelationshipTo(ruleNode, EugeneRelation.CONSTRAINT);
			
		}	
		
		this.tx.success();
	}
 ***/
	
/***	
	private Node createConstraintGraph(Predicate predicate) {
		if(predicate instanceof LogicalPredicate) {			
			if(predicate instanceof LogicalNot) {
				
				Node not = this.graphDb.createNode();
				not.setProperty(EugeneConstants.NODE_NAME, ((LogicalNot)predicate).getOperator());

				// setting appropriate properties?
				
				not.createRelationshipTo(
						createConstraintGraph(((LogicalNot)predicate).getPredicate()), 
								EugeneRelation.CONSTRAINT);
			
				return not;
			} else if(predicate instanceof Precedence) {

				Node prec = this.graphDb.createNode();
				prec.setProperty(EugeneConstants.NODE_NAME, ((Precedence)predicate).getOperator());

				// setting appropriate properties?
				
				prec.createRelationshipTo(
						createConstraintGraph(((Precedence)predicate).getPredicate()), 
								EugeneRelation.CONSTRAINT);
				
				return prec;
				
			} else if(predicate instanceof LogicalAnd) {

				Node and = this.graphDb.createNode();
				and.setProperty(EugeneConstants.NODE_NAME, ((LogicalAnd)predicate).getOperator());

				// setting appropriate properties?
				
				Relationship lhs = and.createRelationshipTo(
						createConstraintGraph(((LogicalAnd)predicate).getA()), 
								EugeneRelation.CONSTRAINT);
				lhs.setProperty("position", "LHS");

				Relationship rhs = and.createRelationshipTo(
						createConstraintGraph(((LogicalAnd)predicate).getB()), 
								EugeneRelation.CONSTRAINT);
				rhs.setProperty("position", "RHS");
				
				return and;
				
			} else if(predicate instanceof LogicalOr) {

				Node or = this.graphDb.createNode();
				or.setProperty(EugeneConstants.NODE_NAME, ((LogicalOr)predicate).getOperator());

				// setting appropriate properties?
				
				Relationship lhs = or.createRelationshipTo(
						createConstraintGraph(((LogicalOr)predicate).getA()), 
								EugeneRelation.CONSTRAINT);
				lhs.setProperty("position", "LHS");

				Relationship rhs = or.createRelationshipTo(
						createConstraintGraph(((LogicalOr)predicate).getB()), 
								EugeneRelation.CONSTRAINT);
				rhs.setProperty("position", "RHS");
				
				return or;
				
			} else if(predicate instanceof LogicalXor) {

				Node xor = this.graphDb.createNode();
				xor.setProperty(EugeneConstants.NODE_NAME, ((LogicalXor)predicate).getOperator());

				// setting appropriate properties?
				
				Relationship lhs = xor.createRelationshipTo(
						createConstraintGraph(((LogicalXor)predicate).getA()), 
								EugeneRelation.CONSTRAINT);
				lhs.setProperty("position", "LHS");

				Relationship rhs = xor.createRelationshipTo(
						createConstraintGraph(((LogicalXor)predicate).getB()), 
								EugeneRelation.CONSTRAINT);
				rhs.setProperty("position", "RHS");
				
				return xor;
			}
		} else if(predicate instanceof RulePredicate) {
			
			if(predicate instanceof UnaryPredicate) {
				
				if(predicate instanceof StartsWith) {
					// here, we create a node that reflects the rule predicate				
					Node startswith = this.graphDb.createNode();
					startswith.setProperty(EugeneConstants.NODE_NAME, ((StartsWith)predicate).getOperator());
					
					// next, we connect the new node with the rule's operands
					startswith.createRelationshipTo(
							this.graphDb.getNodeById(((StartsWith)predicate).getB()), 
							EugeneRelation.CONSTRAINT);
					
					return startswith;
					
				} else if(predicate instanceof EndsWith) {
					
					// here, we create a node that reflects the rule predicate				
					Node endswith = this.graphDb.createNode();
					endswith.setProperty(EugeneConstants.NODE_NAME, ((EndsWith)predicate).getOperator());
					
					// next, we connect the new node with the rule's operands
					endswith.createRelationshipTo(
							this.graphDb.getNodeById(((EndsWith)predicate).getB()), 
							EugeneRelation.CONSTRAINT);
						
					return endswith;
					
				} else if(predicate instanceof Contains) {
					// here, we create a node that reflects the rule predicate				
					Node contains = this.graphDb.createNode();
					contains.setProperty(EugeneConstants.NODE_NAME, ((Contains)predicate).getOperator());
					
					// next, we connect the new node with the rule's operands
					contains.createRelationshipTo(
							this.graphDb.getNodeById(((Contains)predicate).getB()), 
							EugeneRelation.CONSTRAINT);
					
					return contains;
				}
			} else if(predicate instanceof BinaryPredicate) {
				if(predicate instanceof NextTo) {
					
					Node nextto = this.graphDb.createNode();
					nextto.setProperty(EugeneConstants.NODE_NAME, ((NextTo)predicate).getOperator());
					
					Relationship lhs = nextto.createRelationshipTo(
								this.graphDb.getNodeById(((NextTo)predicate).getA()), 
								EugeneRelation.CONSTRAINT);
					lhs.setProperty("position", "LHS");
					
					Relationship rhs = nextto.createRelationshipTo(
							this.graphDb.getNodeById(((NextTo)predicate).getB()), 
							EugeneRelation.CONSTRAINT);
					rhs.setProperty("position", "RHS");
					
					return nextto;
				}				
			}
		} 
		return null;
	}
***/
	

//	private void createRelationsToParts(Node partType) {
//		StringBuilder sb = new StringBuilder();		
//		sb.append("START ").append("p=node(*)")
//			.append("WHERE HAS(p.parttype) AND p.parttype=\"").append(partType.getProperty(EugeneConstants.NODE_NAME)).append("\" ")
//			.append("RETURN p");
//		ExecutionResult result = this.engine.execute(sb.toString());
//		Iterator<Node> it = result.columnAs("p");
//		while(it.hasNext()) {
//			Node part = it.next();	
//			part.createRelationshipTo(partType, EugeneRelation.PRODUCT_OF);
//		}
//	}
    
	private static String deviceName;
    private static long[] generatedDevices;
    private static long[] current_device;
    private static long MAX_DEVICES;
    private static long counter;
    
    public void traverseComponents(Node device_node, long[] components, int n, List<Rule> lstRules) 
    		throws EugeneException {

    	if(counter > MAX_DEVICES) {
    		throw new EugeneException("");
    	}
    	
    	if(n < components.length) {
    		
    		// here, we need to query all parts of the part type
    		
//			TraversalDescription tdParts = Traversal.description()
//					.relationships(EugeneRelation.PRODUCT_OF, Direction.INCOMING)
//					.evaluator(Evaluators.excludeStartPosition());
			
			Node node = this.graphDb.getNodeById(components[n]);
			long[] part_ids = new long[1];
			if(Part.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				part_ids[0] = node.getId();
			} else if (PartType.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				
				// query all parts of this part type
				part_ids = this.queryParts(node);
			}

			//System.out.println(Arrays.toString(ArrayUtils.subarray(current_device, 0, n)));
			for(long part_id : part_ids) {

				current_device[n] = part_id;

				boolean b = true;
	    		if(!lstRules.isEmpty()) {
	    			b = RuleEngine.evaluateProduct(
	    					lstRules, 
	    					ArrayUtils.subarray(current_device, 0, n), 
	    					components.length);
	    		}
	    		
	    		if(b) {
	    			traverseComponents(device_node, components, n+1, lstRules);
	    		}
			}
    	} else {
    		
    		boolean b = true;
    		if(!lstRules.isEmpty()) {
    			b = RuleEngine.evaluate(lstRules, current_device);
    		}
    		
    		if(b) {
	    		// here, we need to create the device    		
	    		Node new_device = this.graphDb.createNode();
	    		new_device.setProperty(EugeneConstants.NODE_NAME, deviceName+"_"+ (++counter));
	    		for(int i=0; i<current_device.length; i++) {
	    			Relationship rel = new_device.createRelationshipTo(
	    					this.graphDb.getNodeById(current_device[i]), 
	    					EugeneRelation.CONSISTS_OF);
	    			rel.setProperty("position", i);
	    		}
	    		new_device.createRelationshipTo(device_node, EugeneRelation.PRODUCT_OF);
//	    		System.out.println(Arrays.toString(current_device));
	    		generatedDevices = ArrayUtils.add(generatedDevices, new_device.getId());	    		
    		}
    	}
    }
    
    public long[] queryParts(Node nodePartType) {
    	
    	//System.out.println("[queryParts] -> "+nodePartType);
    	
		StringBuilder sb = new StringBuilder();
		sb.append("START ").append("p=node(*) ")
			.append("WHERE HAS(p.parttype) AND p.parttype = '")
				.append(nodePartType.getProperty(EugeneConstants.NODE_NAME)).append("' ")
			.append("RETURN p");
		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("p");

		long[] part_ids = new long[1];
		while(it.hasNext()) {
			part_ids = ArrayUtils.add(part_ids, ((Node)it.next()).getId());
		}
		part_ids = ArrayUtils.remove(part_ids, 0);
		
		return part_ids;
    }
    
    public Device createDevice(long deviceId) { 
//    	System.out.println("[neo4j.createDevice] -> "+deviceId);
    	return this.node2device(this.graphDb.getNodeById(deviceId));
    }
        

//  private Set<Long> getBoundIds(Predicate predicate) {
//	Set<Long> setIds = new HashSet<Long>();
//	if(predicate instanceof BinaryPredicate) {
//		setIds.add(((BinaryPredicate)predicate).getA());
//		setIds.add(((BinaryPredicate)predicate).getB());
//	} else if (predicate instanceof UnaryPredicate) {
//		setIds.add(((UnaryPredicate)predicate).getB());
//	} else if (predicate instanceof LogicalPredicate) {
//		if(predicate instanceof Not) {
//			setIds.addAll(getBoundIds(((Not)predicate).getPredicate()));
//		} else if(predicate instanceof And) {
//			setIds.addAll(getBoundIds(((And)predicate).getA()));
//			setIds.addAll(getBoundIds(((And)predicate).getB()));
//		} else if(predicate instanceof Or) {
//			setIds.addAll(getBoundIds(((Or)predicate).getA()));
//			setIds.addAll(getBoundIds(((Or)predicate).getB()));
//		} else if(predicate instanceof Xor) {
//			setIds.addAll(getBoundIds(((Xor)predicate).getA()));
//			setIds.addAll(getBoundIds(((Xor)predicate).getB()));				
//		}
//	}
//	
//	return setIds;
//}

    public String device2String(long[] elements) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("[");
    	for(int i=0; i<elements.length; i++) {
    		sb.append(((Node)this.graphDb.getNodeById(elements[i])).getProperty(EugeneConstants.NODE_NAME));
    		if(i<elements.length-1) {
    			sb.append(", ");
    		}
    	}
    	sb.append("]");
    	return sb.toString();
    }
    
    public long[] permute(long nDeviceId) {
    	/*
    	 * START d=node(<nDeviceId>) 
		 * MATCH d-[:CONSISTS_OF]->x0, d-[:CONSISTS_OF]->x1, d-[:CONSISTS_OF]->x2, d-[:CONSISTS_OF]->x3, d-[:CONSISTS_OF]->x4
		 * CREATE UNIQUE n-[:INSTANCE_OF]->d
		 * CREATE n-[:STARTSWITH]->x0-[:BEFORE]->x1-[:BEFORE]->x2-[:BEFORE]->x3-[:BEFORE]->x4
		 * RETURN x0, x1, x2, x3
    	 */    
    	
    	
    	/** we can avoid this **/
    	long[] device_components = this.getDeviceConsistsOf(nDeviceId);
    	
    	
    	StringBuilder sbStart = new StringBuilder();
    	sbStart.append("START d=node(").append(nDeviceId).append(") ");
    	
    	StringBuilder sbMatch = new StringBuilder();
    	sbMatch.append("MATCH ");
    	
    	StringBuilder sbCreate1 = new StringBuilder();
    	sbCreate1.append("CREATE ");
    	
    	StringBuilder sbCreate2 = new StringBuilder();
    	sbCreate2.append("CREATE ");
    	
    	StringBuilder sbReturn = new StringBuilder();
    	sbReturn.append("RETURN n");
    	
    	for(int i=0; i<device_components.length; i++) {
    		
    		/* currently, we need to check if the current device component is 
    		 * another device, a part type, or a part
    		 * 
    		 * -> i.e. use a JSON-Object serializer (e.g. Jackson)
    		 */
    		
    		sbMatch.append("d-[:CONSISTS_OF]->").append("x").append(i);
    		if(i==0) {
        		sbCreate1.append("n-[:INSTANCE_OF]->d ");
        		sbCreate2.append("n-[:STARTSWITH]->");
    		} else {
    			sbCreate2.append("-[:BEFORE]->");
    		}
    		sbCreate2.append("x").append(i);
    		
//    		sbReturn.append("x").append(i);
    		
    		if(i<device_components.length-1) {
    			sbMatch.append(", ");
//    			sbReturn.append(", ");
    		}
    	}
    	
    	String s = sbStart.toString() + System.getProperty("line.separator") + 
    			sbMatch.toString() + System.getProperty("line.separator") + 
    			sbCreate1.toString() + System.getProperty("line.separator") + 
    			sbCreate2.toString() + System.getProperty("line.separator") + 
    			sbReturn.toString();

    	long nBefore = System.nanoTime();
		ExecutionResult result = this.engine.execute(s);
		Iterator<Node> it = result.columnAs("n");
		long[] new_node_ids = new long[1];
		while(it.hasNext()) {
			new_node_ids = ArrayUtils.add(new_node_ids, it.next().getId());
		}
		new_node_ids = ArrayUtils.remove(new_node_ids, 0);
		
		double nProcessing = (System.nanoTime() - nBefore) * Math.pow(10, -9);
		System.out.println(nProcessing+"sec");
		
		return new_node_ids;
    }
    
    public long[] getInstancesOf(long nDeviceId) {
		StringBuilder sb = new StringBuilder();
		sb.append("START ").append("X=node(").append(nDeviceId).append(") ")
			.append("MATCH ").append("X-[:INSTANCE_OF]->Y ")
			.append("RETURN Y");
		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("Y");
		
		long[] instance_ids = new long[1];
		while(it.hasNext()) {
			instance_ids = ArrayUtils.add(instance_ids, ((Node)it.next()).getId());
		}
		instance_ids = ArrayUtils.remove(instance_ids, 0);
		
		return instance_ids;
    }
    
    private void createGraph(ExecutionResult result, Node device_node) {
    	List<Node> lstNodes = new ArrayList<Node>();
    	
    	Iterator<Map<String, Object>> it = result.iterator();
    	while(it.hasNext()) {
    		Node new_device = this.graphDb.createNode();
    		
    		Map<String, Object> map = it.next();
    		for(String key : map.keySet()) {
    			Node part_node = (Node)map.get(key);
    			new_device.createRelationshipTo(part_node, EugeneRelation.CONSISTS_OF);
    		}
    		
    		new_device.createRelationshipTo(device_node, EugeneRelation.INSTANCE_OF);
    	}
    }
    
    public void createPermutation(long nDeviceId, String newDeviceName, long[] l) {
    	Node n = this.graphDb.createNode();
    	n.setProperty(EugeneConstants.NODE_NAME, newDeviceName);
    	
    	n.createRelationshipTo(this.graphDb.getNodeById(nDeviceId), EugeneRelation.PERMUTATION_OF);
    	
    	for(int i=0; i<l.length; i++) {
        	Relationship r = n.createRelationshipTo(this.graphDb.getNodeById(l[i]), EugeneRelation.CONSISTS_OF);
        	r.setProperty("position", (i+1));
    	}
    }
    
    private Node part2node(Part part) {

    	Node node = graphDb.createNode();				
		node.setProperty(EugeneConstants.NODE_NAME, part.getName());
		if(null != part.getPartType()) {
			node.setProperty("parttype", part.getPartType().getName());
		}
		node.setProperty(EugeneConstants.NODE_TYPE, Part.class.getCanonicalName());

		try {
			node.setProperty("property-values", 
				mapper.writeValueAsString(part.getPropertyValuesHashMap()));
			
			HashMap<String, PropertyValue> hmValues = part.getPropertyValuesHashMap();
			for(String s : hmValues.keySet()) {
				node.setProperty(s, hmValues.get(s).getValue());
			}
					
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// get the node of the part type
		//Node pt_node = this.queryNodeByName(part.getPartType().getName());
		//node.createRelationshipTo(pt_node, EugeneRelation.INSTANCE_OF);

		return node;
    }
    
    private enum EugeneRelation implements RelationshipType {
    	CONSISTS_OF, PERMUTATION_OF, PRODUCT_OF, INSTANCE_OF, CONSTRAINT, 
    	ON,
    	STARTSWITH, ENDSWITH, NEXTTO, CONTAINS, BEFORE
    }
    
    private Node device2node(Device device) 
    		throws EugeneException {
    	Node node = this.graphDb.createNode();
    	node.setProperty(EugeneConstants.NODE_NAME, device.getName());
		node.setProperty(EugeneConstants.NODE_TYPE, Device.class.getCanonicalName());
		
//		System.out.println("[device2node] -> "+device.getName());
		if(null != device.getDirections()) {
			node.setProperty(EugeneConstants.DIRECTION, device.getDirections());
//		} else {
//			System.out.println("[device2node] -> NO DIRECTIONS!");
		}
		
    	// iterate of the device's components
		int position = 0;
    	for(Component component:device.getComponents()) {
//    		System.out.println("[device2node] -> "+component);
    		Node new_node = null;
    		if(component instanceof Device) {    			
    			/* here we need to query the component */
    			new_node = this.queryNodeByName(component.getName());
    			if(null == new_node) {
    				new_node = device2node((Device)component);
    			}
    		} else if(component instanceof PartType) {
    			// here, we need to query the node... 
    			new_node = this.queryNodeByName(component.getName());
    			if(null == new_node) {
    				new_node = parttype2node((PartType)component);
    			}
    		} else if(component instanceof Part) {
    			new_node = this.queryNodeByName(component.getName());
    			if(null == new_node) {
    				new_node = this.part2node((Part)component);
    			}
    		}
    		
    		// build a relationship from the device to the node
    		Relationship relation = node.createRelationshipTo( new_node, EugeneRelation.CONSISTS_OF );
    		relation.setProperty("position", Integer.valueOf(position));
    		position++;
    	}
    	
    	return node;
	}
    
    public Node parttype2node(PartType component) {	
    	
		Node node = this.graphDb.createNode();
		node.setProperty(EugeneConstants.NODE_NAME, component.getName());
		node.setProperty(EugeneConstants.NODE_TYPE, PartType.class.getCanonicalName());
		for(Property property : component.getProperties()) {
			node.setProperty(property.getName(), property.getType());
		}

//		System.out.println("[Neo4j.parttype2node] -> "+node.getProperty(EugeneConstants.NODE_NAME));
		return node;
    }
    
    public Device node2device(Node node) {
    	Device d = new Device((String)node.getProperty(EugeneConstants.NODE_NAME));
    	
//    	System.out.println("[node2device] -> "+d.getName());
    	if(node.hasProperty(EugeneConstants.DIRECTION)) {
    		d.setDirections((char[])node.getProperty(EugeneConstants.DIRECTION));
    	}
    	
//    	System.out.println("[node2device] -> "+d.getName()+" -> "+Arrays.toString(d.getDirections()));
    	
		/* query the devices consisting components */
		/* traverse the graph */
		d.setComponents(this.queryConsistsOf(node.getId()));

    	return d;
    }
    
    public PartType node2parttype(Node node) {
    	PartType pt = new PartType((String)node.getProperty(EugeneConstants.NODE_NAME));

    	// TODO: List of Properties!
    	List<Property> properties = new ArrayList<Property>();
    	for(String propertyName : node.getPropertyKeys()) {
    		if(!(EugeneConstants.NODE_NAME.equals(propertyName) || EugeneConstants.NODE_TYPE.equals(propertyName))) {
    			properties.add(new Property(propertyName, (String)node.getProperty(propertyName)));
    		}
    	}
    	pt.setProperties(properties);
    	return pt;
    }

    public Part node2part(Node node) {
    	
    	Part part = null;
		try {
	    	// get the part type
	    	String sPartTypeName = (String)node.getProperty("parttype");
	    	Component component = this.queryByName(sPartTypeName);
	    	if(null != component) {
	    		/** TODO: properties **/
		        part = new Part(
		        		(PartType)component,
		        		(String)node.getProperty(EugeneConstants.NODE_NAME));
	    	} else {
	        	part = new Part(
	        			(PartType)SymbolTables.get(sPartTypeName),
	        			(String)node.getProperty(EugeneConstants.NODE_NAME));
	    	}
	    	
	    	/* set the property values */
//	    	System.out.println(node.getProperty("property-values"));
	    	TypeReference<HashMap<String,PropertyValue>> typeRef = 
	    			new TypeReference<HashMap<String,PropertyValue>>() {}; 
	    	part.setPropertiesValues(
	    			(HashMap<String,PropertyValue>)mapper.readValue(
	    					(String)node.getProperty("property-values"), 
	    					typeRef));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return part;
    	
    }
    
    public long[] getDeviceIds() {
		StringBuilder sb = new StringBuilder();
		
		/*
		 * 	START device=node(*)
		 *	WHERE HAS(device.type) AND device.type = "eugene.dom.components.Device"
		 *	RETURN device
		 */
		
		sb.append("START ").append("DEVICE=node(*) ")
			.append("WHERE HAS(DEVICE.").append(EugeneConstants.NODE_TYPE).append(") AND DEVICE.")
				.append(EugeneConstants.NODE_TYPE).append("=\"")
				.append(Device.class.getCanonicalName()).append("\" ")
			.append("RETURN DEVICE");
		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("DEVICE");
		
		
		long[] ids = new long[1];
		while(it.hasNext()) {
			ids = ArrayUtils.add(ids, it.next().getId());
		}
		ids = ArrayUtils.remove(ids, 0);
		
		return ids;
    }
    
    public long[] getOrderedDeviceComponents(long nDeviceId) {
    	/*
    	 * START d=node(13)
		 * MATCH d-[c:CONSISTS_OF]->pt
		 * RETURN pt.name
		 * ORDER BY c.position
    	 */

		StringBuilder sb = new StringBuilder();
		sb.append("START ").append("d=node(").append(nDeviceId).append(") ")
			.append("MATCH ").append("d-[c:CONSISTS_OF]->p ")
			.append("RETURN p ")
			.append("ORDER BY c.position");
		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("p");

		long[] components = new long[1];
		while(it.hasNext()) {
			Node node = it.next();
			if(Device.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				components = ArrayUtils.addAll(components, getOrderedDeviceComponents(node.getId()));
			} else {
				components = ArrayUtils.add(components, node.getId());
			}
		}
		
		return ArrayUtils.remove(components, 0);
    }
    
    
    public long[] getDeviceConsistsOf(long nDeviceId) {
		StringBuilder sb = new StringBuilder();
		sb.append("START ").append("X=node(").append(nDeviceId).append(") ")
			.append("MATCH ").append("X-[:").append(EugeneRelation.CONSISTS_OF).append("]->Y ")
			.append("RETURN Y");

		//System.out.println(sb.toString());
		
		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("Y");

		/** CAN THIS BE DONE ANY BETTER??? **/
		List<Long> lstLongIds = new ArrayList<Long>();
		while(it.hasNext()) {			
			lstLongIds.add(it.next().getId());
		}

		long[] lstIds = new long[lstLongIds.size()];
		int i=0;
		for(Long l : lstLongIds) {
			lstIds[i++] = l.longValue();
		} 
    	return lstIds;    	
    }
    
    public void put(Fact fact) 
    		throws EugeneException {
    	/*
    	 * START lhs=node(*), rhs=node(*)
    	 * WHERE HAS(lhs.name) AND HAS(rhs.name) AND lhs.name='<lhs-name>' AND rhs.name='<rhs-name>'
    	 * CREATE lhs - [:<RELATIONTYPE>] -> rhs
    	 */
    	
    	if(fact instanceof BinaryFact) {
    		
    		BinaryFact bf = (BinaryFact)fact;

    		long lhsId = bf.getAId();
    		if( lhsId == -1) {
    			lhsId = SymbolTables.getId(bf.getAName());
    		}
    		if(lhsId == -1) {
    			throw new EugeneException("Unknonw left-hand-side of relation "+bf.getRelationType()+"!");
    		}
    		
    		long rhsId = bf.getBId();
    		if( rhsId == -1) {
    			rhsId = SymbolTables.getId(bf.getBName());
    		}
    		if(rhsId == -1) {
    			throw new EugeneException("Unknonw right-hand-side of relation "+bf.getRelationType()+"!");
    		}

    		StringBuilder sb = new StringBuilder();

    		sb.append("START lhs=node(").append(lhsId).append("), rhs=node(").append(rhsId).append(") ")
    		  .append("CREATE UNIQUE lhs-[:").append(((BinaryFact)fact).getRelationType()).append("]->rhs");
    		
    		this.engine.execute(sb.toString());
    	}
    	
    }
    
    public long queryNodeIdByName(String name) {
    	//System.out.println("[Neo4j.queryNodeIdByName] -> "+name);
    	StringBuilder sb = new StringBuilder();
		sb.append("START ").append("X").append("=").append("node(*)").append(" ")
		  .append("WHERE ").append("HAS(X.").append(EugeneConstants.NODE_NAME).append(")").append(" AND ")
		  	.append("X.").append(EugeneConstants.NODE_NAME).append("=\"").append(name).append("\" ")
		  .append("RETURN ").append("X");

		ExecutionResult result = engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("X");
		if(it.hasNext()) {
			Node node = it.next();
			return node.getId();
		} 
		return -1;
    }
    
	public Component queryById(long id) {
		/* here we need to use Cypher to query all nodes
		 * with the given type
		 */
		StringBuilder sb = new StringBuilder();
		
		/*  CYPHER:
		 ************
		 * 	START part=node(*)
		 *	WHERE has(part.type) AND part.type = 'Promoter'
		 *	RETURN part
		 */
		sb.append("START ").append("X").append("=").append("node(").append(id).append(")")
		  .append("RETURN ").append("X");
				
		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("X");

		/** we just keep the indexes to the nodes in memory **/
		Component objComponent = null;
		while(it.hasNext()) {			
			Node node = it.next();
			
			try {
				String sType = (String)node.getProperty(EugeneConstants.NODE_TYPE);
				if(Part.class.getCanonicalName().equals(sType)) {
					
					return this.node2part(node);
					
				} else if (PartType.class.getCanonicalName().equals(sType)) {

					return this.node2parttype(node);
					
				} else if (Device.class.getCanonicalName().equals(sType)) {
					return this.node2device(node);
				}

			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}

		return objComponent;
    }
	
	public List<Component> queryConsistsOf(long id) {
		/*
		 *	START x = node(<id>)
		 *	MATCH x-[:CONSISTS_OF]->y
		 *	RETURN x.name, y.name
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("START ").append("X=node(").append(id).append(") ")
			.append("MATCH ").append("X-[:").append(EugeneRelation.CONSISTS_OF).append("]->Y ")
			.append("RETURN Y");

		ExecutionResult result = this.engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("Y");

		/** we just keep the indexes to the nodes in memory **/
		List<Component> lstComponents = new ArrayList<Component>();
		while(it.hasNext()) {			
			Node node = it.next();
			if(PartType.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				lstComponents.add(this.node2parttype(node));
			} else if(Device.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				lstComponents.add(this.node2device(node));
				//lstComponents.addAll(this.queryConsistsOf(node.getId()));
			} else if(Part.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				lstComponents.add(node2part(node));
			}
		}
		return lstComponents;
	}
    
    public Component queryByName(String name) {
		StringBuilder sb = new StringBuilder();
		
		/*  CYPHER:
		 ************
		 * 	START part=node(*)
		 *	WHERE has(part.type) AND part.type = 'Promoter'
		 *	RETURN part
		 */
		sb.append("START ").append("X").append("=").append("node(*)").append(" ")
		  .append("WHERE ").append("HAS(X.").append(EugeneConstants.NODE_NAME).append(")").append(" AND ")
		  	.append("X.").append(EugeneConstants.NODE_NAME).append("=\"").append(name).append("\" ")
		  .append("RETURN ").append("X");

		ExecutionResult result = engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("X");
		
		/** how can I check how many elements where returned?? **/
		while(it.hasNext()) {			
			Node node = it.next();
			if(PartType.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				return this.node2parttype(node);
			} else if(Device.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				return this.node2device(node);
			} else if(Part.class.getCanonicalName().equals(node.getProperty(EugeneConstants.NODE_TYPE))) {
				return node2part(node);
			}
		}
		return null;
    }
    
    public Node queryNodeByName(String name) {
		StringBuilder sb = new StringBuilder();
		
		/*  CYPHER:
		 ************
		 * 	START part=node(*)
		 *	WHERE has(part.type) AND part.type = 'Promoter'
		 *	RETURN part
		 */
		sb.append("START ").append("X").append("=").append("node(*)").append(" ")
		  .append("WHERE ").append("HAS(X.").append(EugeneConstants.NODE_NAME).append(")").append(" AND ")
		  	.append("X.").append(EugeneConstants.NODE_NAME).append("=\"").append(name).append("\" ")
		  .append("RETURN ").append("X");

//		System.out.println(sb.toString());
		
		ExecutionResult result = engine.execute(sb.toString());
		Iterator<Node> it = result.columnAs("X");
		
		/** how can I check how many elements where returned?? **/
		while(it.hasNext()) {			
			return it.next();
		}
		return null;
    }

    public List<Component> query(PartType objPartType) {
    	return null;
    }
    
    /* Give me all devices that are 
     * - instance of a given device, and
     * - comply with a given set of rules
     */
    public List<Device> query(Device objDeviceTemplate, List<Rule> lstRules) {
    	/* that's Eugene's heart */
    	return null;
    }
}
