package ua.itea.collections;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyHashMap<K, V> extends AbstractMap<K, V> {
    private int size;
    int capacity;
    Entry<K, V>[] table;
    double loadFactor;

    public MyHashMap() {
	loadFactor = 0.75;
	capacity = 16;
	table = new Entry[capacity];
    }

    public V put(K key, V value) {
	int index = 0;
	if (null != key) index = hash(key);
	Entry<K, V> e = table[index];
	while (e != null) {
	    if (null == e.key && null == key || key.equals(e.key)) return null;
	    e = e.next;
	}
	if (size > capacity * loadFactor) increase();
	Entry<K, V> newEntry = new Entry<K, V>(hash(key), key, value, null);
	return addEntry(newEntry);
    }

    public void increase() {
	Entry<K, V>[] oldTable = table;
	capacity <<= 1;
	table = new Entry[capacity];

	Entry<K, V> e = null;
	for (int i = 0; i < oldTable.length; i++) {
	    if (oldTable[i] != null) {
		e = oldTable[i];
		while (e != null) {
		    addEntry(e);
		    e = e.next;
		}
	    }
	}
    }

    public V addEntry(Entry<K, V> e) {
	int index = 0;
	if (null != e.key)
	    index = hash(e.key);
	if (null == table[index]) {
	    table[index] = e;
	    return e.value;
	} else {
	    Entry<K, V> entry = table[index];
	    while (entry.next != null) {
		if(entry.key == e.key) return entry.setValue(e.value);
		entry = entry.next;
	    }
	    entry = e;
	    return e.value;
	}
    }

    public int hash(K key) {
	return key.hashCode() & (capacity - 1);
    }

    public static class Entry<K, V> implements Map.Entry<K, V> {
	int hash;
	K key;
	V value;
	Entry<K, V> next;

	Entry(int hash, K key, V value, Entry<K, V> next) {
	    this.hash = hash;
	    this.key = key;
	    this.value = value;
	    this.next = next;

	}

	public final K getKey() {
	    return key;
	}

	public final V getValue() {
	    return value;
	}

	public final V setValue(V newValue) {
	    V oldValue = value;
	    value = newValue;
	    return oldValue;
	}

	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
	    if (!(o instanceof Map.Entry))
		return false;
	    Map.Entry e = (Map.Entry) o;
	    Object k1 = getKey();
	    Object k2 = e.getKey();
	    if (k1 == k2 || (k1 != null && k1.equals(k2))) {
		Object v1 = getValue();
		Object v2 = e.getValue();
		if (v1 == v2 || (v1 != null && v1.equals(v2)))
		    return true;
	    }
	    return false;
	}

	public int hashCode() {
	    return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
	}

	public String toString() {
	    return getKey() + "=" + getValue();
	}

	void recordAccess(MyHashMap<K, V> m) {
	}

	void recordRemoval(MyHashMap<K, V> m) {
	}

    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
	// TODO Auto-generated method stub
	return null;
    }

    // class MyIterator

    /*
     * Adds a new entry with the specified key, value and hash code to the
     * specified bucket. It is the responsibility of this method to resize the
     * table if appropriate.
     *
     * Subclass overrides this to alter the behavior of put method.
     *
     * void addEntry(int hash, K key, V value, int bucketIndex) { if ((size >=
     * threshold) && (null != table[bucketIndex])) { resize(2 * table.length);
     * hash = (null != key) ? hash(key) : 0; bucketIndex = indexFor(hash,
     * table.length); }
     * 
     * createEntry(hash, key, value, bucketIndex); }
     **
     * 
     * Like addEntry except that this version is used when creating entries as
     * part of Map construction or "pseudo-construction" (cloning,
     * deserialization). This version needn't worry about resizing the table.
     *
     * Subclass overrides this to alter the behavior of HashMap(Map), clone, and
     * readObject.
     *
     * void createEntry(int hash, K key, V value, int bucketIndex) { Entry<K,V>
     * e = table[bucketIndex]; table[bucketIndex] = new Entry<>(hash, key,
     * value, e); size++; }
     * 
     * private abstract class HashIterator<E> implements Iterator<E> {
     * Entry<K,V> next; // next entry to return int expectedModCount; // For
     * fast-fail int index; // current slot Entry<K,V> current; // current entry
     * 
     * HashIterator() { expectedModCount = modCount; if (size > 0) { // advance
     * to first entry Entry[] t = table; while (index < t.length && (next =
     * t[index++]) == null) ; } }
     * 
     * public final boolean hasNext() { return next != null; }
     * 
     * final Entry<K,V> nextEntry() { if (modCount != expectedModCount) throw
     * new ConcurrentModificationException(); Entry<K,V> e = next; if (e ==
     * null) throw new NoSuchElementException();
     * 
     * if ((next = e.next) == null) { Entry[] t = table; while (index < t.length
     * && (next = t[index++]) == null) ; } current = e; return e; }
     * 
     * public void remove() { if (current == null) throw new
     * IllegalStateException(); if (modCount != expectedModCount) throw new
     * ConcurrentModificationException(); Object k = current.key; current =
     * null; HashMap.this.removeEntryForKey(k); expectedModCount = modCount; } }
     * 
     * private final class ValueIterator extends HashIterator<V> { public V
     * next() { return nextEntry().value; } }
     * 
     * private final class KeyIterator extends HashIterator<K> { public K next()
     * { return nextEntry().getKey(); } }
     * 
     * private final class EntryIterator extends HashIterator<Map.Entry<K,V>> {
     * public Map.Entry<K,V> next() { return nextEntry(); } }
     * 
     * // Subclass overrides these to alter behavior of views' iterator() method
     * Iterator<K> newKeyIterator() { return new KeyIterator(); } Iterator<V>
     * newValueIterator() { return new ValueIterator(); }
     * Iterator<Map.Entry<K,V>> newEntryIterator() { return new EntryIterator();
     * }
     */

}
