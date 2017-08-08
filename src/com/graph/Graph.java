package com.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

class Vertex<T>{

	int id;
	T data;
	List<Edge<T>> edges;
	List<Vertex<T>> adjacentVertices ;
	
	public Vertex(int v1) {
		edges=new ArrayList<>();
		adjacentVertices = new ArrayList<>();
		this.id=v1;
	}
	public void addAdjacentVertex(Edge<T> e , Vertex<T> v){
		this.adjacentVertices.add(v);
		this.edges.add(e);
	}
}

class Edge<T>{
	boolean isDirected = false;
	int weight ;
	Vertex<T> vertex1;
	Vertex<T> vertex2;
	public Vertex<T> getVertex1(){
		return vertex1;
	}
	public Vertex<T> getVertex2(){
		return vertex2;
	}
	public Edge(Vertex<T> vertex1,Vertex<T> vertex2 ,boolean isDirected , int weight){
		this.isDirected=isDirected;
		this.weight=weight;
		this.vertex1=vertex1;
		this.vertex2=vertex2;
	}
	public int getVertexForEdge(int oneVertex ){
		return this.vertex1.id == oneVertex ? this.vertex2.id : this.vertex1.id ;
	}
	public Vertex<T> getVertexForEdge2(int oneVertex ){
		return this.vertex1.id == oneVertex ? this.vertex2: this.vertex1 ;
	}
}

public class Graph<T> {
	boolean isDirected = false;
	List<Edge<T>> allEdges;
	HashMap<Integer,Vertex<T>> allVertices ;
	
	Graph(boolean isDirected){
		this.isDirected = isDirected;
		this.allEdges = new ArrayList<>();
		this.allVertices = new HashMap<>();
	}
	
	public void addEdge(int v1 , int v2){
		addEdge(v1,v2,0);
	}
	
	public void addEdge(int v1, int v2, int weight) {
		Vertex<T> vertex1;
		Vertex<T> vertex2;
		vertex1  = (allVertices.containsKey(v1)) ? allVertices.get(v1) : new Vertex<T>(v1);
		vertex2  = (allVertices.containsKey(v2)) ? allVertices.get(v2) : new Vertex<T>(v2);
		allVertices.put(v1,vertex1);
		allVertices.put(v2,vertex2);
		Edge<T> edge = new Edge<>(vertex1 , vertex2 , isDirected, weight);
		allEdges.add(edge);
		vertex1.addAdjacentVertex(edge, vertex2);
		if(!isDirected)
			vertex2.addAdjacentVertex(edge, vertex1);
	}
	
	static int [][] adjMatrix;
	public void createGraph(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of vertices in graph : ");
		int n = scan.nextInt();
		System.out.println("Enter number of edges in graph : ");
		int e = scan.nextInt();
		
		adjMatrix= new int[n][n];
		
		System.out.println("Enter edges (from  to)  : ");
		for(int i=0;i<e;i++){
			int from = scan.nextInt();
			int to = scan.nextInt();
			adjMatrix[from][to] = 1;
			addEdge(from,to);
		}
	}
	public void createGraphWithWeights(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of vertices in graph : ");
		int n = scan.nextInt();
		System.out.println("Enter number of edges in graph : ");
		int e = scan.nextInt();
		
		adjMatrix= new int[n][n];
		
		System.out.println("Enter edges (from  to)  : ");
		for(int i=0;i<e;i++){
			int from = scan.nextInt();
			int to = scan.nextInt();
			int weight = scan.nextInt();
			adjMatrix[from][to] = weight;
			addEdge(from,to,weight);
		}
	}
	public void printGraph(){
		System.out.println("*** adj matrix ****");
		for(int i=0;i<allVertices.size();i++){
			for(int j=0;j<allVertices.size();j++){
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("*** adj list ****");
		for(Entry<Integer, Vertex<T>> e : allVertices.entrySet()){
			System.out.print(e.getKey()+ " : ");
			e.getValue().adjacentVertices.stream().forEach(v -> System.out.print(v.id + " "));
			System.out.println();
		}
	}
	
}
