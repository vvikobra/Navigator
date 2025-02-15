package org.example.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    protected Node<T> start;

    public void add(T data) {
        Node<T> newNode = new Node<>(data, null);
        if (start != null) {
           newNode.next = start;
        }
        start = newNode;
    }

    public void remove(T element) {
        if (start == null) return;
        if (start.data != null && start.data.equals(element)) {
            start = start.next;
            return;
        }
        Node<T> current = start;
        while (current.next != null) {
            if (current.next.data != null && current.next.data.equals(element)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    public boolean contains(T element) {
        if (start == null) return false;
        Node<T> current = start;
        while (current.next != null) {
            if (current.next.data.equals(element)) return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public String toString() {
        Node<T> current = start;
        StringBuilder result = new StringBuilder();
        while (current != null) {
            result.append(current.data);
            current = current.next;
        }
        return result.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = start;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Конец списка");
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}