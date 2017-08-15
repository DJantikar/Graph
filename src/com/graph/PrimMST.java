package com.graph;
/*
 * Space complexity - O(E + V)
 * Time complexity - O(ElogV)
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class VertexValueComparator implements Comparator<Vertex<Integer>>{

	@Override
	public int compare(Vertex<Integer> v1, Vertex<Integer> v2) {	
		return v1.data - v2.data;
	}
	
}
public class PrimMST {
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<Integer>(false);
		g.addEdge(0,1,3);
		g.addEdge(0,3,1);
		g.addEdge(1,3,3);
		g.addEdge(1,2,1);
		g.addEdge(2,3,1);
		g.addEdge(2,4,5);
		g.addEdge(2,5,4);
		g.addEdge(3,4,6);
		g.addEdge(4,5,2);
		for(Vertex<Integer> v : g.allVertices.values())
			v.data = Integer.MAX_VALUE;
		g.allVertices.get(0).data=0;
		PrimMST obj = new PrimMST();
		List<Edge<Integer>> result = obj.findMinimumSpanningTree(g);
		result.stream().forEach(e -> System.out.println(e));
	}

	public List<Edge<Integer>> findMinimumSpanningTree(Graph<Integer> g) {
		List<Edge<Integer>> result = new ArrayList<Edge<Integer>>();
		
		PriorityQueue<Vertex<Integer>> pq = new PriorityQueue<>(new VertexValueComparator());
		pq.addAll(g.allVertices.values());
		
		Map<Integer,Edge<Integer>> vertexToEdge = new HashMap<>();
		while(!pq.isEmpty()){
			Vertex<Integer> minVertex = pq.poll();
			Edge<Integer> e = vertexToEdge.get(minVertex.id) ;
			if(e!= null){
				result.add(e);
			}
			for(Edge<Integer> edge : minVertex.edges){
				Vertex<Integer> adj = edge.getVertexForEdge2(minVertex.id);
				if(pq.contains(adj)){
					int oldDist = adj.data;
					int newDist = edge.weight;
					if(oldDist>newDist){
						pq.remove(adj);
						adj.data=newDist;
						pq.add(adj);
						vertexToEdge.put(adj.id, edge);
					}
				}
			}
		}
		return result;
	}

}
