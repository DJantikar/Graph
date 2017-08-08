package com.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/*
 * Runtime is  O(E+V) and space complexity  is O(v)
 */
public class DetectCycleInDirectedGraph {

	public static void main(String[] args) {
		
		Graph<Integer> graph = new Graph<Integer>(true);
		 graph.addEdge(1, 2);
	        graph.addEdge(1, 3);
	        graph.addEdge(2, 3);
	        graph.addEdge(4, 1);
	        graph.addEdge(4, 5);
	        graph.addEdge(5, 6);
	        graph.addEdge(6, 4);
		DetectCycleInDirectedGraph obj = new DetectCycleInDirectedGraph();
		if(obj.checkForCycle(graph))
			System.out.println("Cycle");
		else
			System.out.println("No Cycle");
	}

	public boolean checkForCycle(Graph<Integer> g) {
		Map<Integer,Integer> parent = new HashMap<>();
		Set<Integer> notCompletelyExplored = new HashSet<>();
		Set<Integer> completelyExplored = new HashSet<>();
		for(int v : g.allVertices.keySet()){
			if(completelyExplored.contains(v))
				continue;
			if(doDFS(g,v,notCompletelyExplored ,completelyExplored,parent)){
				return true;
			}
		}
		return false;
	}

	private boolean doDFS(Graph<Integer> g, int v, Set<Integer> notCompletelyExplored, Set<Integer> completelyExplored, Map<Integer, Integer> parent) {
		notCompletelyExplored.add(v);
		for(Vertex<Integer> child : g.allVertices.get(v).adjacentVertices){
			if(completelyExplored.contains(child.id))
				continue;
			if(notCompletelyExplored.contains(child.id))
				return true;
			if(doDFS(g,child.id,notCompletelyExplored ,completelyExplored,parent))
				return true;
		}
		completelyExplored.add(v);
		notCompletelyExplored.remove(v);
		return false;
	}

}
