package dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
	
	private static final int DEF_CAPACITY = 16;
	private int size;
	private Entry<K, V>[] data;
	
	@SuppressWarnings("unchecked")
	public SortedArrayDictionary() {
		size = 0;
		data = new Entry[DEF_CAPACITY];
	}
	public V insert(K key, V value) {
		int i = searchKey(key);
		if (i != -1) {
			V r = data[i].getValue();
			data[i].setValue(value);
			return r;
		}
		if (data.length == size) {
			ensureCapacity(2 * size);
		}
		int j = size - 1;
		while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
			data[j + 1] = data[j];
			j--;
		}
		data[j + 1] = new Entry<K, V>(key, value);
		size++;
		return null;
	}
	@SuppressWarnings({"unchecked"})
	private void ensureCapacity(int newCapacity) {
		Entry[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}

	private int searchKey(K key) {
		for (int i = 0; i < size; i++) {
			if (data[i].getKey().equals(key))
				return i;
		}
		return -1;
	}
	
	public V search(K key) {
		int i = searchKey(key);
		if (i < 0)
			return null;
		else 
			return data[i].getValue();
	}

	public V remove(K key) {
		int i = searchKey(key);
		if (i >= 0) {
			V r = data[i].getValue();
			for (int j = i; j < size - 1; j++) {
				data[j] = data[j + 1];
			}
			data[--size] = null;
			return r;
		}
		return null;
	}

	public int size() {
		return this.size;
	}

	public Iterator<Entry<K, V>> iterator() {
		
		return new Iterator<Entry<K, V>>(){
			int currentIndex = 0;
			
			@Override
			public boolean hasNext() {
				if (currentIndex == size)
					return false;
				return true;
			}

			@Override
			public Entry<K, V> next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return data[currentIndex++];
			}
			
		};
	}

}
