// O. Bittel;
// 22.02.2017

package directedgraph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	// ...

	/**
	 * FÃ¼hrt eine topologische Sortierung fÃ¼r g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
		topSort(g, ts);
    }
    private void topSort(DirectedGraph<V> g, List<V> l) {
    	List<V> besucht = new LinkedList<>();
    	Map<V, Integer> inDegree = new TreeMap<>();
    	Queue<V> q = new ArrayDeque<>();
    	for (V v : g.getVertexSet()) {
    		besucht.add(v);
    		int size = g.getPredecessorVertexSet(v).size();
//    		for (V w : g.getPredecessorVertexSet(v)) {
//    			if (besucht.contains(w))
//    				size--;
//    		}
    		inDegree.put(v, size);
    		if (size == 0) {
    			q.add(v);
    		}
    	}
    	while(!q.isEmpty()) {
    		V v = q.remove();
    		l.add(v);
    		for (V w : g.getSuccessorVertexSet(v)) {
    			inDegree.put(w, inDegree.get(w) - 1);
    			if (inDegree.get(w) == 0) {
    				q.add(w);
    			}
    		}
    	}
    	if(l.size() != g.getNumberOfVertexes())
    		l = null;
    }
	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurÃ¼ck,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		g.addEdge(6, 9);
		g.addEdge(9, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}
		DirectedGraph<String> g1 = new AdjacencyListDirectedGraph<>();
		g1.addEdge("Unterhose", "Hose");
		g1.addEdge("Unterhemd", "Hemd");
		g1.addEdge("Hose", "Schuhe");
		g1.addEdge("Hose", "Gürtel");
		g1.addEdge("Socken", "Schuhe");
		g1.addEdge("Hemd", "Pulli");
		g1.addEdge("Schuhe", "Handschuhe");
		g1.addEdge("Gürtel", "Mantel");
		g1.addEdge("Pulli", "Mantel");
		g1.addEdge("Mantel", "Schal");
		g1.addEdge("Schal", "Handschuhe");
		g1.addEdge("Mütze", "Handschuhe");
		
		
		TopologicalSort<String> ts1 = new TopologicalSort<>(g1);
		
		if (ts1.topologicalSortedList() != null) {
			System.out.println(ts1.topologicalSortedList());
		}
	}
}
