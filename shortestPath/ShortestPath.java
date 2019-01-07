// O. Bittel;
// 18.10.2011

package shortestPath;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import directedgraph.*;
import shortestPath.sim.SYSimulation;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	private static double INF = java.lang.Double.POSITIVE_INFINITY;
	SYSimulation sim = null;
	Map<V,Double> dist; // Distanz für jeden Knoten
	Map<V,V> pred; // Vorgänger für jeden Knoten
	DirectedGraph<V> graph;
	Heuristic<V> h;

	/**
	 * Berechnet im Graph g kürzeste Wege nach dem A*-Verfahren.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		dist = new TreeMap<>();
		pred = new TreeMap<>(); 
		this.graph = g;
		this.h = h;
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	V start;
	V target;
	public void searchShortestPath(V s, V g) {
		target = g;
		start = s;
		List<V> kl = new LinkedList<>();
		for (V v : graph.getVertexSet()) {
			dist.put(v, INF);
		}
		dist.put(s, 0.0);
		kl.add(s);
		while (!kl.isEmpty()) {
			if (h == null) {
				h = (u, v) -> 0.0;
			}
			V visitedVertex = kl.get(0);
			for (V v : kl) {
				if (dist.get(visitedVertex) + h.estimatedCost(visitedVertex, g) > dist.get(v) + h.estimatedCost(v, g))
					visitedVertex = v;
			}
			kl.remove(visitedVertex);
//			System.out.println("Besuche Knoten " + visitedVertex + " mit d = " + dist.get(visitedVertex));
			if (visitedVertex == g)
				return;
			for (V w : graph.getSuccessorVertexSet(visitedVertex)) {
				if (dist.get(w) == INF) {
					kl.add(w);
				}
				if (dist.get(visitedVertex) + graph.getWeight(visitedVertex, w) < dist.get(w)) {
					pred.put(w, visitedVertex);
					dist.put(w, dist.get(visitedVertex) + graph.getWeight(visitedVertex, w));
				}
			}
		}
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		List<V> path = new LinkedList<>();
		for (V v = target; v != start; v = pred.get(v))
			path.add(v);
		path.add(start);
		Collections.reverse(path);
		return path;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		return dist.get(target);
	}

}
