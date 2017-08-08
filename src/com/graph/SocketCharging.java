package com.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Socket{
	int x;
	int y;
	int id;
}
public class SocketCharging {
    static int vId = 0;
    static int prev =-1;
    static int l=0;
    static Socket first =null;
    static Socket last =new Socket();		
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        //HashMap<Integer,Socket> h = new HashMap<>();
        int [][]loc = new int[n+1][n+1];
  
        for(int a0 = 0; a0 < m; a0++){
            int x = in.nextInt();
            int y = in.nextInt();
            loc[n-y][x]=1;
        }
        for(int i=0;i<=n;i++){
        	for(int j=0;j<=n;j++){
        		System.out.print(loc[i][j]+" ");
        	}
        	System.out.println();
        }
        Graph<Integer> g = new Graph<Integer>(false);
        createGraph(n, loc, g);
        System.out.println(findMinTime(g,k));
        
//        System.out.println("***********************");
//        for(Edge<Integer> e : g.allEdges){
//        	 System.out.println(e.vertex1.id + " : "+e.weight+" : "+e.vertex2.id);
//        }
//        System.out.println("***********************");
//        for(Vertex<Integer> v : g.allVertices.values()){
//        	System.out.println(v.id);
//        	for(Edge<Integer> e : v.edges){
//           	 System.out.println(e.vertex1.id + " : "+e.weight+" : "+e.vertex2.id);
//           }
//        	System.out.println("-----------------------------");
//        }
        
	}
	private static int findMinTime(Graph<Integer> g, int k ) {
		HashSet<Integer> visited = new HashSet<Integer>();
		HashMap<Integer , Integer> sourceAndMinTime  = new HashMap<>();
		int min = Integer.MAX_VALUE;
		for(int v : g.allVertices.keySet()){
			sourceAndMinTime.put(v, doDFS(g.allVertices.get(v) , visited, 0 ,0, k));
			if(sourceAndMinTime.get(v) < min)
				min = sourceAndMinTime.get(v);
			visited.clear();
		}
		return min;
		
	}
	private static int doDFS(Vertex<Integer> v, Set<Integer> visited, int len, int edge ,int k) {
		if(k==0 || visited.contains(v.id))
			return len;
		visited.add(v.id);
		len=len+edge;
		int res = Integer.MAX_VALUE;
		for(Edge<Integer> e : v.edges){
			int otherVertexId = e.getVertexForEdge(v.id);
			if(!visited.contains(otherVertexId)){
				Vertex<Integer> adjV = e.getVertexForEdge2(v.id);	
				int min= doDFS(adjV , visited,len,e.weight, k-1);
				res = res < min ? res : min;
				visited.remove(adjV.id);
			}
		}
		return res;
	}
	private static void createGraph(int n, int[][] loc, Graph<Integer> g) {
		//go right
        for(int j=0;j<=n;j++){
        	if(j>0 && prev!=-1)
        		l++;
        	check(loc, g,0, j);
        }
        //go down
        for(int i=1;i<=n;i++){
        	if(i>0 && prev!=-1)
        		l++;
        	check(loc, g,i, n);
        }
        //go left
        for(int j=n-1;j>=0;j--){
        	if(j<n && prev!=-1)
        		l++;
        	check(loc, g,n, j);
        }
        //go up
        for(int i=n-1;i>0;i--){
        	if(i<n && prev!=-1)
        		l++;
        	check(loc, g,i, 0);
        }
        int lastLen = Math.abs(first.x-last.x) + Math.abs(first.y-last.y) ;
        g.addEdge(first.id, last.id, lastLen);
        
	}
	private static void check(int[][] loc, Graph<Integer> g, int i ,int j) {
		if(loc[i][j]==1){
			Vertex<Integer> tmp = new Vertex<Integer>(vId);
			if(null == first){
				first = new Socket();
				first.x=i;
				first.y=j;
				first.id=vId;
			}
			else {
				last.x=i;
				last.y=j;
				last.id=vId;
			}
			vId++;
			if(prev!=-1){
				g.addEdge(prev, tmp.id, l);
				l=0;
			}
			prev= tmp.id;
		}
	}

}
