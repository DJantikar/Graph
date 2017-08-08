package com.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class TopologicalSort {

	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);
		g.createGraph();
		g.printGraph();
		TopologicalSort ts = new TopologicalSort();
		ts.topoSort(g);
		ts.topoSortDFS(g);
		

	}
	//Non-DFS : Time O(n2) Space (n2)
	public  void topoSort(Graph<Integer> g) {
		ArrayList<Integer> a = new ArrayList<>();
		int n = g.adjMatrix.length;
		int gCopy[][] = g.adjMatrix;
		while(a.size() < n){
			for(int j=0;j<n;j++){
				if(a.contains(j))
					continue;
				boolean hasNoInNodes = false;
				for(int i=0;i<n;i++){
					if(gCopy[i][j] != 0){
						hasNoInNodes=true;
						break;
					}
				}
				if(!hasNoInNodes){
					a.add(j);
					Arrays.fill(gCopy[j], 0);
				}
			}
		}
		System.out.println("Topo Sort ...");
		System.out.println(a);
	}
	
	// DFS : Time O(n) Space(n)
	public void topoSortDFS(Graph<Integer> g){
		Deque<Integer> d = new ArrayDeque<>();
		doDFS(g, 0 , d);
		for(int id : d)
			d.poll();
	}
	public  void doDFS(Graph<Integer> g, int vertexId, Deque<Integer> d) {
		if(g.allVertices.containsKey(vertexId))
			return;
		d.offerLast(vertexId);
		for(Vertex<Integer> childVertex: g.allVertices.get(vertexId).adjacentVertices){
			if(d.contains(childVertex.id))
				continue;
			doDFS(g,childVertex.id,d);
		}
	}
}
