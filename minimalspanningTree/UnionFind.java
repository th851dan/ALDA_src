package minimalspanningTree;

import java.util.Map;
import java.util.TreeMap;


public class UnionFind {
	Map<Integer, Integer> map;
	int size;
	public UnionFind(int n) {
		size = n;
		map = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			map.put(i, -1);
		 }
	}
	
	public int find(int e) {
		while (map.get(e) >= 0) {
			e = map.get(e);
		}
		return e;
	}
	
	public void union(int s1, int s2) {
		if (map.get(s1) >= 0 || map.get(s2) >= 0)
			return;
		if (s1 == s2)
			return;
		if (-map.get(s1) < -map.get(s2))
			map.put(s1, s2);
		else {
			if (map.get(s1) == map.get(s2))
				map.put(s1, map.get(s1) - 1);
			map.put(s2, s1);
		}
		size--;
	}
	
	public int size() {
//		int size = 0;
//		for (int s : map.keySet()) {
//			if (map.get(s) < 0)
//				size++;
//		}
		return size;
	}
	public static void main(String[] args) {
		UnionFind un = new UnionFind(5);
		System.out.println(un.size());
		System.out.println(un.find(4));
		un.union(2, 3);
		System.out.println(un.find(3));
		System.out.println(un.find(2));
		System.out.println(un.size());
	}

}
