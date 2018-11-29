package dictionary;

import java.util.Iterator;
import java.util.LinkedList;

public class BinaryTreeDictionary<K  extends Comparable<? super K>, V> implements Dictionary<K, V> {
	
	static private class Node<K, V> { 
		private Node<K, V> parent;
		private K key;
		private V value;
		private Node<K, V>  left;
		private Node<K, V> right;
		private Node(K k, V v) { 
			key = k;
			value = v;
			left = null;
			right = null;
			parent = null;
			}
		@Override
		public String toString() {
			return key + " : " + value;
		}
		}
	
	private Node<K, V> root = null;
	private int size;
	private V oldValue;
	@Override
	public V insert(K key, V value) {
		root = insertR(key, value, root);
		if (root != null)
			root.parent = null;
		return oldValue;
	}

	private Node<K, V> insertR(K key, V value, Node<K, V> p) {
		if (p == null) {
			p = new Node<K, V>(key, value);
			size++;
			oldValue = null;
		} else {
			if (key.compareTo(p.key) < 0) {
				p.left = insertR(key, value, p.left);
				if (p.left != null)
					p.left.parent = p;
			} else {
				if (key.compareTo(p.key) > 0) {
					p.right = insertR(key, value, p.right);
					if (p.right != null)
						p.right.parent = p;
				} else {
					oldValue = p.value;
					p.value = value;
				}
			}
		}
		return p;
	}

	@Override
	public V search(K key) {
		return searchR(key, root);
	}

	private V searchR(K key, Node<K, V> p) {
		if (p == null)
			return null;
		if (key.compareTo(p.key) < 0)
			return searchR(key,p.left);
		else if(key.compareTo(p.key) > 0)
			return searchR(key,p.right);
		else
			return p.value;
	}

	@Override
	public V remove(K key) {
		root = removeR(key, root);
		return oldValue;
	}

	private Node<K, V> removeR(K key, Node<K, V> p) {
		if(p == null) {
			oldValue = null;
		}
		else if(key.compareTo(p.key) < 0)
			p.left = removeR(key, p.left); 
		else if(key.compareTo(p.key) > 0)
			p.right = removeR(key, p.right);
		else if(p.left == null || p.right == null) { 
			// p muss gelöscht werden 
			// und hat ein oder kein Kind: 
			oldValue= p.value;
			if (p.left == null && p.right == null) {
				p = null;
			} else {
				if (p.left == null) {
					p.right.parent = p.right.parent.parent;
					p = p.right;
				} else {
					p.left.parent = p.left.parent.parent;
					p = p.left;
				}
			}
		} else {
			// p muss gelöscht werden und hat zwei Kinder: 
			MinEntry<K,V> min = new MinEntry<K,V>();
			p.right = getRemMinR(p.right, min);
			oldValue= p.value;
			p.key= min.key;
			p.value = min.value;
		}
		return p;
	}
	private Node<K, V> getRemMinR(Node<K, V> p, MinEntry<K, V> min) {
		assert p != null;
		if (p.left == null) {
			min.key = p.key;
			min.value = p.value;
			if (p.right != null)
				p.right.parent = p.right.parent.parent;
			p = p.right;
		} else {
			p.left = getRemMinR(p.left, min);
		}
		return p;
	}
	private static class MinEntry<K, V> { 
		private K key;
		private V value;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new BTDIterator(root);
	}

	private class BTDIterator implements Iterator<Entry<K, V>> {
		int currentIndex = 0;
		LinkedList<Entry<K, V>> l = new LinkedList<>();
		public BTDIterator(Node<K, V> p) {
			addList(l, p);
		}
		private void addList(LinkedList<Entry<K, V>> l, Node<K, V> p) {
			if (p != null) {
				addList(l, p.left);
				l.add(new Entry<K, V>(p.key, p.value));
				addList(l, p.right);
			}
		}
		@Override
		public boolean hasNext() {
			return currentIndex < l.size();
		}

		@Override
		public Entry<K, V> next() {
			return l.get(currentIndex++);
		}
	}

	public void prettyPrint() {
		prettyPrintR(root);
	}
	
	private String s = "";
	private void prettyPrintR(Node<K, V> p) {
		System.out.print(s);
		if (p != null) {
			String a;
			if (p.parent == null) {
				a = "no parent";
			} else {
				a = p.parent.key.toString();
			}
			System.out.println(p + " has parent: " + a);
			if (s.length() == 0)
				s = s + "I___";
			else 
				s = "    " + s;
			prettyPrintR(p.left);
			prettyPrintR(p.right);
			s = s.substring(4);
		} else {
			System.out.println("#");
		}

	}
}
