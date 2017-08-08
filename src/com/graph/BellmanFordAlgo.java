/**
 * 
 */
package com.graph;

import java.util.HashMap;
import java.util.Map;

import com.interview.graph.BellmanFordShortestPath;



/**
 * @author Deepa
 * It is algorithm to find shortest path from source to all other vertices in graph
 * Difference between Dijkstra's and this algo is , this algo works on negative weights graph as well
 * and determines negative cycle.
 *
 */
class NegativeCycleException extends RuntimeException{
	
}
public class BellmanFordAlgo {
	
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<Integer>(true);
		g.createGraphWithWeights();
		g.printGraph();
		BellmanFordAlgo b = new BellmanFordAlgo();
		int sourceVertexId = 0;
		b.findShortestPath(g,sourceVertexId);
	}

	private void findShortestPath(Graph<Integer> g,int sourceVertexId) {
		int INFINITY = 100000;
		HashMap<Integer , Integer> dist = new HashMap<>();
		Map<Integer , Integer> vertexParent = new HashMap<>();
		for(int vId  : g.allVertices.keySet()){
			dist.put(vId, INFINITY);
		}
		dist.put(sourceVertexId, 0);
		int n = g.allVertices.size();
		for(int i=1;i<= n;i++){
			for(Edge<Integer> edge : g.allEdges){
				int u = edge.getVertex1().id;
				int v = edge.getVertex2().id;
				if(dist.get(v) > edge.weight + dist.get(u)){
					if(i==n)
						throw new NegativeCycleException();
					dist.put(v,  edge.weight + dist.get(u));
					vertexParent.put(v, u);
				}
			}
		}
		System.out.println(dist);
	}
}
