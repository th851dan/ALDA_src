package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashDictionary1<K, V> implements Dictionary<K, V> {
	private int size = 23;
	@SuppressWarnings("unchecked")
	LinkedList<Entry<K, V>>[] tab = new LinkedList[size];
	
	@Override
	public V insert(K key, V value) {
		Entry<K, V> i = searchKey(key);
		if (i != null) {
			V v = i.getValue();
			i.setValue(value);
			return v;
		}
//		if (size() <= 2 * size) {
//			
//		}
		Entry<K, V> neu = new Entry<K, V>(key, value);
		tab[key.hashCode() % size].add(neu);
		return null;
	}

	@Override
	public V search(K key) {
		return searchKey(key) != null ? searchKey(key).getValue() : null;
	}
	
	private Entry<K, V> searchKey(K key) {
		if (size() != 0) {
			for (Entry<K, V> i : tab[key.hashCode() % size]) {
				if (key.equals(i.getKey()))
					return i;
			}
		}
		return null;
	}

	@Override
	public V remove(K key) {
		for (Entry<K, V> i : tab[key.hashCode() % size]) {
			if (key.equals(i.getKey())) {
				V val = i.getValue();
				tab[key.hashCode() % size].remove(key);
				return val;
			}

		}
		return null;
	}

	@Override
	public int size() {
		int count = 0;
		for (LinkedList<Entry<K, V>> i : tab)
				count = count + i.;
		return count;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K, V>>(){
			int currentIndex = 0;
			LinkedList<Entry<K, V>>[] cl = tab.clone();
			@Override
			public boolean hasNext() {
				if ((currentIndex == tab.length) || tab[currentIndex].size() == 0)
					return false;
				return true;
			}

			@Override
			public Entry<K, V> next() {
				if (!hasNext())
					throw new NoSuchElementException();
				if (cl[currentIndex] == null) {
					currentIndex++;
				}
				return cl[currentIndex].pollFirst();
			}
			
		};
	}
}
