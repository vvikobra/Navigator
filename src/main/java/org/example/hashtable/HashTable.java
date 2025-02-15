package org.example.hashtable;

import org.example.ArrayList;
import org.example.linkedlist.LinkedList;

import java.util.Arrays;
import java.util.Iterator;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75d;

    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

    public HashTable() {
        slots = new LinkedList[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
        this.count = 0;
    }

    public HashTable(int capacity) {
        slots = new LinkedList[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    public void add(K key, V value) {
        growIfNeeded();

        KeyValue<K, V> keyValue = new KeyValue<>(key, value);
        int index = this.findSlotNumber(key);
        if (this.slots[index] == null) this.slots[index] = new LinkedList<>();

        if (this.slots[index].contains(keyValue)) return;

        this.slots[index].add(keyValue);
        this.count++;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.slots.length;
    }

    private void growIfNeeded() {
        if ((double) (this.size() + 1) / this.capacity() > LOAD_FACTOR) {
            this.grow();
        }
    }

    private void grow() {
        HashTable<K, V> newHashTable = new HashTable<>(2 * this.capacity);
        for (LinkedList<KeyValue<K, V>> element : this.slots) {
            if (element != null) {
                for (KeyValue<K, V> keyValue : element) {
                    newHashTable.add(keyValue.getKey(), keyValue.getValue());
                }
            }
        }
        this.slots = newHashTable.slots;
        capacity *= 2;
    }

    public int size() {
        return count;
    }

    public int capacity() {
        return capacity;
    }

    public V get(K key) {
        KeyValue<K, V> keyValue = this.find(key);
        if (keyValue == null) return null;
        return keyValue.getValue();
    }

    public KeyValue<K, V> find(K key) {
        int index = this.findSlotNumber(key);
        if (this.slots[index] == null) return null;

        for (KeyValue<K, V> element : this.slots[index]) {
            if (element.getKey().equals(key)) {
                return element;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        KeyValue<K, V> keyValue = this.find(key);
        return keyValue != null;
    }

    public void remove(K key) {
        int index = findSlotNumber(key);
        if (containsKey(key)) {
            for (KeyValue<K, V> element : this.slots[index]) {
                if (element.getKey().equals(key)) {
                    count--;
                    this.slots[index].remove(element);
                    return;
                }
            }
        }
    }

    public Iterable<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (LinkedList<KeyValue<K, V>> element : this.slots) {
            if (element != null) {
                for (KeyValue<K, V> keyValue : element) values.add(keyValue.getValue());
            }
        }
        return values;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        ArrayList<KeyValue<K, V>> result = new ArrayList<>();
        for (LinkedList<KeyValue<K, V>> element : this.slots) {
            if (element != null) {
                for (KeyValue<K, V> keyValue : element) result.add(keyValue);
            }
        }
        return result.iterator();
    }

    @Override
    public String toString() {
        return Arrays.toString(slots);
    }
}