package minimalspanningTree;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.HashMap;

public class TelNet {
	private int lbg;
	private int size;
	private LinkedList<TelKnoten> vers;
	private LinkedList<TelVerbindung> minSpanTree;
	private PriorityQueue<TelVerbindung> edges;
	public TelNet(int lbg) {
		this.lbg = lbg;
		this.size = 0;
		vers = new LinkedList<>();
		edges = new PriorityQueue<>();
		minSpanTree = new LinkedList<>();
	}
	
	public boolean addTelKnoten(int x, int y) {
		for (TelKnoten v : vers) {
			if (v.x == x && v.y == y)
				return false;
		}
		TelKnoten v = new TelKnoten(x, y);
		vers.add(v);
		for (TelKnoten u : vers) {
			if (dist(u, v) != 0 && dist(u, v) <= lbg)
				edges.add(new TelVerbindung(u, v, dist(u, v)));
		}
		size++;
		return true;
	}
	
	public boolean computeOptTelNet() {
		HashMap<TelKnoten, Integer> mapV = new HashMap<>();
		UnionFind forest = new UnionFind(size());
		int i = 0;
		for (TelKnoten v : vers) {
			mapV.put(v, i);
			i++;
		}
		while (forest.size != 1 && !edges.isEmpty()) {
			TelVerbindung tV = edges.remove();
			int i1 = forest.find(mapV.get(tV.u));
			int i2 = forest.find(mapV.get(tV.v));
			if (i1 != i2) {
				forest.union(i1, i2);
				minSpanTree.add(new TelVerbindung(tV.u, tV.v, dist(tV.u, tV.v)));
			}
		}
		if (edges.isEmpty() && forest.size() != 1)
			return false;
		return true;
	}
	
	
	private int dist(TelKnoten u, TelKnoten v) {
		return Math.abs(u.x - v.x) + Math.abs(u.y - v.y);
	}
	
	public List<TelVerbindung> getOptTelNet() throws java.lang.IllegalStateException {
		return minSpanTree;
	}
	
	public int getOptTelNetKosten() throws java.lang.IllegalStateException {
		int cost = 0;
		for (TelVerbindung tV : minSpanTree) {
			cost += tV.c;
		}
		return cost;
	}
	
	public void generateRandomTelNet(int n, int xMax, int yMax) {
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			this.addTelKnoten(ran.nextInt(xMax), ran.nextInt(yMax));
		}
	}
	
	public void drawOptTelNet(int xMax, int yMax) throws java.lang.IllegalStateException {
        StdDraw.setXscale(0, 1010);
        StdDraw.setYscale(0, 1010);
    	StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.001);
        for (TelKnoten tK : vers) {
        	StdDraw.point(tK.x, tK.y);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.0002);
        for (TelVerbindung tV : minSpanTree) {
        	StdDraw.line(tV.u.x, tV.u.y, tV.v.x, tV.u.y);
        	StdDraw.line(tV.v.x, tV.u.y, tV.v.x, tV.v.y);
        }
	}
	
	public int size() {
		return size;
	}
	public static void main(String[] args) {
		TelNet telnet = new TelNet(7);
		telnet.addTelKnoten(1, 1);
		telnet.addTelKnoten(3, 1);
		telnet.addTelKnoten(4, 2);
		telnet.addTelKnoten(3, 4);
		telnet.addTelKnoten(7, 5);
		telnet.addTelKnoten(2, 6);
		telnet.addTelKnoten(4, 7);
		telnet.computeOptTelNet();
		System.out.println(telnet.getOptTelNetKosten());
		System.out.println(telnet.getOptTelNet());
		
		TelNet ranNet = new TelNet(100);
		ranNet.generateRandomTelNet(1000, 1000, 1000);
		ranNet.computeOptTelNet();
		ranNet.drawOptTelNet(1000, 1000);
		System.out.println(ranNet.getOptTelNetKosten());
	}

}
