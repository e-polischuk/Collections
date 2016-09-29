package ua.itea.collections;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyHashMap<K, V> extends AbstractMap<K, V> {
    private int size;
    private int capacity;
    private Entry<K, V>[] table;
    private double loadFactor;

    public MyHashMap() {
	loadFactor = 0.75;
	capacity = 16;
	table = new Entry[capacity];
    }

    public V get(Object key) {
	return getEntry((K) key).getValue();
    }

    public V remove(Object key) {
	int index = 0;
	if (null != key)
	    index = hash((K) key);
	if (table[index] != null) {
	    Entry<K, V> pre = null;
	    for (Entry<K, V> e = table[index]; e != null; e = e.next) {
		if (key == e.key || (null != key && key.equals(e.key))) {
		    if(pre == null) {
			table[index] = e.next;
		    } else {
			pre.next = e.next;
		    }
		    V value = e.getValue();
		    size--;
		    System.out.println("Removed {key=" + key + ", value=" + value + "}; size = " + size
			    + "; capacity = " + capacity);
		    System.out.println(toString() + "\n");
		    return value;
		}
		pre = e;
	    }
	}
	throw new NoSuchElementException();
    }

    private Entry<K, V> getEntry(K key) {
	int index = 0;
	if (null != key)
	    index = hash(key);
	for (Entry<K, V> e = table[index]; e != null; e = e.next) {
	    if (key == e.key || (null != key && key.equals(e.key)))
		return e;
	}
	throw new NoSuchElementException();
    }

    public V put(K key, V value) {
	int index = 0;
	if (null != key)
	    index = hash(key);
	for (Entry<K, V> e = table[index]; e != null; e = e.next) {
	    if ((key == e.key || (null != key && key.equals(e.key)))
		    && (value == e.value || (null != value && value.equals(e.value))))
		return null;
	}
	if (size >= capacity * loadFactor)
	    resize();
	V val = addEntry(new Entry<K, V>(key, value));
	if (val == null)
	    size++;
	System.out.println("Added {key=" + key + ", value=" + value + "}; returned value = " + val + "; size = " + size
		+ "; capacity = " + capacity);
	System.out.println(toString() + "\n");
	return val;
    }

    public void resize() {
	if (capacity < Integer.MAX_VALUE >> 1) {
	    Entry<K, V>[] oldTable = table;
	    capacity <<= 1;
	    table = new Entry[capacity];
	    for (int i = 0; i < oldTable.length; i++) {
		for (Entry<K, V> e = oldTable[i]; e != null; e = e.next) {
		    addEntry(e);
		}
	    }
	}
    }

    private V addEntry(Entry<K, V> newEnt) {
	int index = 0;
	if (null != newEnt.key)
	    index = hash(newEnt.key);
	if (table[index] == null) {
	    table[index] = newEnt;
	} else {
	    Entry<K, V> e = table[index];
	    do {
		if (newEnt.key == e.key || (newEnt.key != null && newEnt.key.equals(e.key)))
		    return e.setValue(newEnt.value);
		if (e.next != null)
		    e = e.next;
	    } while (e.next != null);
	    e.next = newEnt;
	}
	return null;
    }

    public int hash(K key) {
	if (null == key)
	    return 0;
	return key.hashCode() & (capacity - 1);
    }

    public String toString() {
	StringBuffer sb = new StringBuffer("{");
	Iterator<Entry<K, V>> it = new EntryIterator();
	while (it.hasNext()) {
	    sb.append("(" + it.next().toString() + ")");
	}
	sb.append("}");
	return sb.toString();
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
	K key;
	V value;
	Entry<K, V> next;

	Entry(K key, V value) {
	    this.key = key;
	    this.value = value;
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

	public String toString() {
	    return getKey() + "=" + getValue();
	}

    }

    private abstract class MyHashIterator<E> implements Iterator<E> {
	Entry<K, V> current;
	// int expectedModCount;
	protected int passed;

	MyHashIterator() {
	    current = table[0];
	    passed = 0;
	}

	public boolean hasNext() {
	    if (passed < size)
		return true;
	    return false;
	}

	public Entry<K, V> nextEntry() {
	    if (current != null && current.next != null) {
		passed++;
		current = current.next;
		return current;
	    }
	    for (int i = hash(current == null ? null : current.key) + 1; i < table.length; i++) {
		if (table[i] != null) {
		    passed++;
		    current = (Entry<K, V>) table[i];
		    return current;
		}
	    }
	    return null;
	}

    }

    public class KeyIterator extends MyHashIterator<K> {
	public K next() {
	    return nextEntry().getKey();
	}
    }

    public class ValueIterator extends MyHashIterator<V> {
	public V next() {
	    return nextEntry().getValue();
	}
    }

    public class EntryIterator extends MyHashIterator<Entry<K, V>> {
	public Entry<K, V> next() {
	    return nextEntry();
	}
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
	// TODO Auto-generated method stub
	return null;
    }

}
