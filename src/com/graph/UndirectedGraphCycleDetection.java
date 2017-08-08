package com.graph;

import java.util.HashSet;
import java.util.Set;
/*
 * Runtime and space complexity  is O(v)
 */
public class UndirectedGraphCycleDetection {
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(false);	
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 5);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(4, 5);
		UndirectedGraphCycleDetection obj = new UndirectedGraphCycleDetection();
		System.out.println(obj.checkIfCyclePresent(g));
		
	}

	public boolean checkIfCyclePresent(Graph<Integer> g) {
		Set<Integer> visited = new HashSet<>();
		for(int v : g.allVertices.keySet()){
			if(visited.contains(v))
				continue;
			if(doDFS(g,v,visited,null))
				return true;
		}
		return false;
	}

	public boolean doDFS(Graph<Integer> g, int v, Set<Integer> visited, Vertex<Integer> parent) {
		visited.add(v);
		for(Vertex<Integer> child : g.allVertices.get(v).adjacentVertices){
			if(parent!=null && child.id == parent.id)
				continue;
			if(visited.contains(child.id))
				return true;
			if(doDFS(g,child.id,visited,g.allVertices.get(v)))
				return true;
		}
		return false;
	}

}
