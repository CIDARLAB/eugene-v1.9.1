package org.cidarlab.eugene.visual.graphviz;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cidarlab.eugene.dom.rules.Rule;
import org.cidarlab.eugene.exception.EugeneException;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class RuleGraphVisualizer
{
    private static Set<String> vertices = new HashSet<String>();
    private static Set<String> edges = new HashSet<String>();
    
//    public RuleGraphVisualizer() {
//    	this.vertices = new HashSet<String>();
//    	this.edges = new HashSet<String>();
//    } 

    public static void addVertex(String rules) {
    	vertices.add(rules);
    }
    
    public void addEdge(String from, String to) {
    	
    }
    
    public static void visualize(String sFileName)
    		throws EugeneException {

        DirectedGraph<String, DefaultEdge> ruleGraph =
            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

       	for(String rules : vertices) {
       		System.out.println(rules);
       		ruleGraph.addVertex(rules);
       	}

        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
        try {
        	exporter.export(new FileWriter(sFileName), ruleGraph);
        } catch(Exception e) {
        	throw new EugeneException(e.getMessage());
        }
    }

}
