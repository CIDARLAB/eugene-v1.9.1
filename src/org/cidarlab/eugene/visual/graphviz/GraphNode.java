package org.cidarlab.eugene.visual.graphviz;

import java.util.ArrayList;
import java.util.List;

import org.cidarlab.eugene.dom.rules.Rule;

public class GraphNode {

	List<GraphEdge> edges;
	List<Rule> rules;
	long solutions;
	double time;
	
	public GraphNode() {
		this.edges = new ArrayList<GraphEdge>();
		this.rules = new ArrayList<Rule>();
	}
}
