package com.graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;

public class Graph_O {

	private static class GraphNode {
		int data;
		GraphNode[] adjNodes ;		
	}
	
	int numberOfNodes ;
	int numberOfEdges ;
	int adjMatrix[][] ;
	HashMap<Integer,ArrayList<Integer>> graphNodesList = new HashMap<Integer,ArrayList<Integer>>();
	
	public boolean isCyclePresent(){
		HashSet<Integer> visited = new HashSet<Integer>();
		boolean cyclePresent=false;
		cyclePresent = dfs(0,visited , cyclePresent);
		return cyclePresent;
	}

	private boolean dfs(int root,HashSet<Integer> visited, boolean cyclePresent) {
		if( graphNodesList.get(root)==null || cyclePresent)
			return cyclePresent;
		visited.add(root);
		for(int child : graphNodesList.get(root)){
			if(visited.contains(child)){
				cyclePresent=true;
			}
			cyclePresent=dfs(child,visited,cyclePresent);
		}
		return cyclePresent;
	}

	public boolean isGraphDirected(){
		for(int i=0;i< numberOfNodes ;i++){
			for(int j=0;j< numberOfNodes ; j++){
				if(adjMatrix[i][j] != adjMatrix[j][i])
					return true;
			}
		}
		return false;
	}
	
	public void createGraph(){
		Scanner scan = new Scanner(System.in);
		numberOfNodes = scan.nextInt();
		numberOfEdges = scan.nextInt();
		
		adjMatrix= new int[numberOfNodes][numberOfNodes];
		
		
		for(int i=0;i<numberOfEdges;i++){
			int from = scan.nextInt();
			int to = scan.nextInt();
			adjMatrix[from][to] = 1;
			if(graphNodesList.get(from)==null)
				graphNodesList.put(from, new ArrayList<Integer>());
			graphNodesList.get(from).add(to);
		}
	}
	
	public void printGraph(){
		System.out.println("*** adj matrix ****");
		for(int i=0;i<numberOfNodes;i++){
			for(int j=0;j<numberOfNodes;j++){
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("*** adj list ****");
		for(Entry<Integer,ArrayList<Integer>> e : graphNodesList.entrySet())
			System.out.println(e.getKey()+ " : "+e.getValue());
	}
	

	public static void main(String[] args) {
		Graph_O g = new Graph_O();
		g.createGraph();
		g.printGraph();
		System.out.println(" **** Cycle Detection *****");
		System.out.println(g.isCyclePresent());
	}
}
