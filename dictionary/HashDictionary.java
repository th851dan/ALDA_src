package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.math.BigInteger;
import java.util.Random;
import java.security.SecureRandom;


public class HashDictionary<K, V> implements Dictionary<K, V> {
	private int size;
	private int bitLength = 2;
	
	LinkedList<Entry<K, V>>[] tab;
	
	
	@SuppressWarnings("unchecked")
	public HashDictionary() {
		this.size = 0;
		this.tab = new LinkedList[3];
		for (int i = 0; i < tab.length; i++)
			tab[i] = new LinkedList<>();
	}
	
	@Override
	public V insert(K key, V value) {
		Entry<K, V> i = searchKey(key);
		if (i != null) {
			V v = i.getValue();
			i.setValue(value);
			return v;
		}
		if (2 * tab.length <= this.size() ) {
			ensureCapacity();
		}
		Entry<K, V> neu = new Entry<K, V>(key, value);
		tab[Math.abs(key.hashCode() % tab.length)].add(neu);
		size++;
		return null;
	}
	@SuppressWarnings({"unchecked"})
	private void ensureCapacity() {
		Random rnd = new SecureRandom();
		LinkedList[] old = tab;
		tab = new LinkedList[BigInteger.probablePrime(++bitLength, rnd).intValue()];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = new LinkedList<>();
		}
		for (int i = 0; i < old.length; i++) {
			for(Entry<K, V> ent : (LinkedList<Entry<K, V>>) old[i]) {
				insert(ent.getKey(), ent.getValue());
			}
		}			
	}

	@Override
	public V search(K key) {
		return searchKey(key) != null ? searchKey(key).getValue() : null;
	}
	
	private Entry<K, V> searchKey(K key) {
		if (size() != 0) {
			for (Entry<K, V> i : tab[Math.abs(key.hashCode() % tab.length)]) {
				if (key.equals(i.getKey()))
					return i;
			}
		}
		return null;
	}

	@Override
	public V remove(K key) {
		for (Entry<K, V> i : tab[Math.abs(key.hashCode() % tab.length)]) {
			if (key.equals(i.getKey())) {
				V val = i.getValue();
				tab[Math.abs(key.hashCode() % tab.length)].remove(i);
				return val;
			}
		}
		return null;
	}

	@Override
	public int size() {
		return this.size;
	}
	

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K, V>>(){
			int currentIndex = 0;
			Iterator<Entry<K, V>> ite = tab[0].iterator();
			@Override
			public boolean hasNext() {
				if(ite.hasNext())
					return true;
				while (++currentIndex < tab.length) {
					ite = tab[currentIndex].iterator();
					if(ite.hasNext())
						return true;
				}
				return false;
			}

			@Override
			public Entry<K, V> next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return ite.next();
			}
			
		};
	}
}
