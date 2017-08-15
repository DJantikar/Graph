package com.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;



import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;

/*
 * Time : O(2^n * n2) Space : O(2^n)
 */
class VertexAndSubset{
	int vertex;
	Set<Integer> s ;
	public VertexAndSubset(int v , Set<Integer> s) {
		this.vertex=v;
		this.s=new HashSet<Integer>(s);
	}

//	@Override
//	public boolean equals(Object obj) {
//		VertexAndSubset vs = (VertexAndSubset) obj;
//			if(vs != null && 
//			   this.vertex == vs.vertex &&
//			   this.s.equals(vs.s)){
//				return true;
//			}
//		return false;
//	}
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexAndSubset index = (VertexAndSubset) o;

        if (vertex != index.vertex) return false;
        return !(s != null ? !s.equals(index.s) : index.s != null);
    }

	
	
}
public class TravellingSalesmanProblem {
	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		System.out.println("Enter number of nodes : ");
		int n = scan.nextInt();
		System.out.println("Enter distance matrix : ");
		int[][] distMatrix = new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				distMatrix[i][j] = scan.nextInt();
			}
		}
		scan.close();
		findTSPSolution(0,distMatrix);
	}

	public static void findTSPSolution(int source ,int[][] distMatrix) {
		// Find all subsets of vertices excluding source
		List<Set<Integer>> allSubsets = getAllSubsets(source , distMatrix.length);
		System.out.println(allSubsets);
		// For each vertex excluding source , find  costs to reach the vertex from source via all vertices in each subset.
		// Note the minimum cost and vertex in the subset in mincostDP and parent maps
		
		HashMap<VertexAndSubset,Integer> minCostDP = new HashMap<>();
		HashMap<VertexAndSubset,Integer> parent = new HashMap<>();
		
		
		for(Set<Integer> subset : allSubsets){
				for(int v = 0;v<distMatrix.length;v++){
					 if(v==source || subset.contains(v))
							 continue;
					 int minCostValue = Integer.MAX_VALUE;
					 int minCostParent = -1;
					 
					 VertexAndSubset minCostKey = new VertexAndSubset(v, subset);
					 
					 for(int vertex : subset){
						 int currentVertex = v;
						 int prevVertex = vertex;
						 Set<Integer> prevVertexSubset = new HashSet<Integer>(subset);
						 prevVertexSubset.remove(prevVertex);
						 int cost = distMatrix[prevVertex][currentVertex] + getCost(prevVertex,prevVertexSubset,minCostDP);
						 if(cost<minCostValue){
							 minCostValue=cost;
							 minCostParent=prevVertex;
						 }
					 }
					 
					 if(subset.size()==0){
						 minCostValue = distMatrix[source][v];
						 minCostParent=source;
					 }
					 
					 minCostDP.put(minCostKey, minCostValue);
					 parent.put(minCostKey, minCostParent);
				 }			 
		 }
		 int currentVertex = source;			 
		 Set<Integer> subset = new HashSet<Integer>(allSubsets.get(allSubsets.size()-1));
		 int minCostValue = Integer.MAX_VALUE;
		 int minCostParent = -1;
		 VertexAndSubset minCostKey = new VertexAndSubset(source, subset);
		 for(int prevVertex : subset){
			 Set<Integer> prevVertexSubset = new HashSet<Integer>(subset);
			 prevVertexSubset.remove(prevVertex);
			 int cost = distMatrix[prevVertex][currentVertex] + getCost(prevVertex,prevVertexSubset,minCostDP);
			 if(cost<minCostValue){
				 minCostValue=cost;
				 minCostParent=prevVertex;
			 }
		 }
		 minCostDP.put(minCostKey, minCostValue);
		 parent.put(minCostKey, minCostParent);
		 System.out.println("TSP cost : "+minCostValue);
		 System.out.println("TSP tour : "+printTour(parent , source , distMatrix.length));
	}

	public static String printTour(HashMap<VertexAndSubset, Integer> parent, int sourceVertex, int n) {
		int source = sourceVertex;
		Set<Integer> s = new HashSet<>();
		for(int i=0;i<n;i++){
			if(i==source)
				continue;
			s.add(i);
		}
		Deque<Integer> stack = new LinkedList<Integer>();
		do{
			stack.push(source);
			for(Entry<VertexAndSubset,Integer> e : parent.entrySet()){
				if(e.getKey().vertex==source && e.getKey().s.equals(s))
					{ 
						source=e.getValue();
						break;
					}
			}
			//source = parent.get(new VertexAndSubset(source, s));
			s.remove(source);
		}while(source!=sourceVertex);
		stack.push(source);
		StringJoiner joiner = new StringJoiner("=>");
		stack.forEach(v -> joiner.add(String.valueOf(v)));
		return joiner.toString();
	}

	public static int getCost(int prevVertex, Set<Integer> prevVertexSubset,
			HashMap<VertexAndSubset, Integer> minCostDP) {
		//VertexAndSubset tempKey = new VertexAndSubset(prevVertex, prevVertexSubset);		
		int cost = Integer.MAX_VALUE;
		for(Entry<VertexAndSubset, Integer> entry : minCostDP.entrySet()){
			if(entry.getKey().vertex==prevVertex && entry.getKey().s.equals(prevVertexSubset)){
				cost=entry.getValue();
				break;
			}
		}
		return cost;
	}

	public static List<Set<Integer>> getAllSubsets(int source, int length) {
		int[] vertices = new int[length-1];
		int[] vCount = new int[length-1];
		int j = 0;
		for(int i=0;i<length;i++){
			if(i==source)
				continue;
			vertices[j]=i;
			vCount[j]=1;
			j++;
		}
		List<Set<Integer>> subsets = new ArrayList<>();
		int[] result = new int[length-1];
		findCombinations(0,vertices,vCount,result,0,subsets);
		subsets.sort(new Comparator<Set<Integer>>(){

			@Override
			public int compare(Set<Integer> s1, Set<Integer> s2) {
				// TODO Auto-generated method stub
				return s1.size() - s2.size();
			}});
		return subsets;
	}

	public static void findCombinations(int pos, int[] vertices, int[] vCount, int[] result, int level,
			List<Set<Integer>> subsets){
		
		createSubset(level,result,subsets);
		for(int p=pos ;p<vertices.length;p++){
			if(vCount[p]==0)
				continue;
			result[level] = vertices[p];
			vCount[p]--;
			findCombinations(p,vertices,vCount,result,level+1,subsets);
			vCount[p]++;
		}
	}

	public static void createSubset(int length, int[] result,List<Set<Integer>> subsets) {
		Set<Integer> subset = new HashSet<>();
		for(int i=0;i<length;i++){
			subset.add(result[i]);
		}
		subsets.add(subset);
	}
}
