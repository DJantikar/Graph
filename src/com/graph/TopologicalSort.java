package com.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
/*
 *  * Given a directed acyclic graph, do a topological sort on this graph.
 *
 * Do DFS by keeping visited. Put the vertex which are completely explored into a stack.
 * Pop from stack to get sorted order.
 *
 * Space and time complexity is O(n).
 */
public class TopologicalSort {

	public static void main(String[] args) {
		Graph<Integer> graph = new Graph<>(true);
        graph.addEdge(1, 3);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);
        graph.addEdge(6, 3);
        graph.addEdge(3, 8);
        graph.addEdge(8, 11);
		//g.printGraph();
		TopologicalSort ts = new TopologicalSort();
		//ts.topoSort(g);
		ts.topoSortDFS(graph);
		

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
		Deque<Integer> stack = new ArrayDeque<>();
		Set<Integer> visited = new HashSet<>();
		for(int v : g.allVertices.keySet()){
			if(!visited.contains(v))
				doDFS(g, v , visited , stack);
		}
		System.out.println("******************");
		for(int id : stack)
			System.out.print(stack.poll() + " ");
	}
	public  void doDFS(Graph<Integer> g, int vertexId, Set<Integer> visited, Deque<Integer> stack ) {
		
		visited.add(vertexId);
		for(Vertex<Integer> childVertex: g.allVertices.get(vertexId).adjacentVertices){
			if(visited.contains(childVertex.id))
				continue;
			doDFS(g,childVertex.id,visited, stack);
		}
		stack.offerFirst(vertexId);
	}
}
