/**
 * 
 */
package com.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import com.graph.Edge;

/**
 * @author Deepa Jantikar
 *
 */
class QueueNode{
	int vertexId;
	int distanceFromSource;
	public QueueNode(int vertexId, int distanceFromSource) {
		super();
		this.vertexId = vertexId;
		this.distanceFromSource = distanceFromSource;
	}
}
class QueueNodeComparator implements Comparator<QueueNode>{

	@Override
	public int compare(QueueNode q1, QueueNode q2) {
		// TODO Auto-generated method stub
		return q1.distanceFromSource - q2.distanceFromSource;
	}
	
}
public class DijkstraShortestPathToAllFromSource {
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);
		g.addEdge(0, 1, 2);
		g.addEdge(0, 2, 1);
		g.addEdge(1, 2, 3);
		g.addEdge(2, 3, 5);
		g.addEdge(2, 6, 12);
		g.addEdge(3, 5, 6);
		g.addEdge(3, 6, 6);
		//g.createGraph();
		//g.printGraph();
		DijkstraShortestPathToAllFromSource dijikstra = new DijkstraShortestPathToAllFromSource();
		dijikstra.findShortestPath(g,0);
	}

	public void findShortestPath(Graph<Integer> g, int sourceId) {
	// distances Map
		HashMap<Integer,Integer> dist = new HashMap<>();
	// parents Map
		HashMap<Integer,Integer> parents = new HashMap<>();	
	// Queue for vertices 
		PriorityQueue<QueueNode> pq = new PriorityQueue<QueueNode>(new QueueNodeComparator());
	// visited 
		HashSet<Integer> visited = new HashSet<>();
		HashMap<Integer , QueueNode> qNodeMap = new HashMap<>();
	// Add source
		dist.put(sourceId, 0);
		parents.put(sourceId, null);
		qNodeMap.put(sourceId, new QueueNode(sourceId,0));
		pq.add(qNodeMap.get(sourceId));
				
	// Initialize distances to max
		for(int v : g.allVertices.keySet()){
			if(v==sourceId)
				continue;
			dist.put(v, Integer.MAX_VALUE);
		}
	// start your BFS
		while(!pq.isEmpty()){
			QueueNode qn = pq.poll();
			visited.add(qn.vertexId);
			for (Edge<Integer> edge : g.allVertices.get(qn.vertexId).edges) {
				int adjVertexId = edge.getVertexForEdge(qn.vertexId);
				if(!visited.contains(adjVertexId)){
					int oldDist = dist.get(adjVertexId);
					int newDist = qn.distanceFromSource + edge.weight;
					if(newDist < oldDist){
						dist.put(adjVertexId, newDist);
						parents.put(adjVertexId, qn.vertexId);
						if(qNodeMap.get(adjVertexId) == null){
							qNodeMap.put(adjVertexId, new QueueNode(adjVertexId, newDist));
						}
						else{
							qNodeMap.get(adjVertexId).distanceFromSource=newDist;
						}					
					}
					pq.add(qNodeMap.get(adjVertexId));
				}
			}
		}
	 System.out.println("************");
	 System.out.println("Distances ...");
	 dist.entrySet().stream().forEach(e -> System.out.println("Vertex Id : " + e.getKey() + " , dist : "+e.getValue()));
	 System.out.println("************");
	 System.out.println("Parents");
	 parents.entrySet().stream().forEach(e -> System.out.println("Vertex Id : " + e.getKey() + " , Parent Vertex Id  : "+e.getValue()));
	 
	}

}
