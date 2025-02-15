package org.example;

import java.util.*;

public class ArrayList<T> implements Iterable<T> {
    private T[] array;
    private int size;
    private int count;

    public ArrayList(T[] elements) {
        this.array = elements;
        this.size = elements.length;
        this.count = elements.length;
    }

    public ArrayList(int size) {
        this.size = size;
        this.count = 0;
        array = (T[]) new Object[size];
    }

    public ArrayList() {
        this.size = 10;
        this.count = 0;
        array = (T[]) new Object[size];
    }

    public void add(T element) {
        if (count >= size) {
            grow();
        }
        array[count++] = element;
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            return array[index];
        } else throw new IndexOutOfBoundsException();
    }

    public int size() {
        return this.size;
    }

    private void grow() {
        T[] newArray = (T[]) new Object[size * 2];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
        size *= 2;
    }

    public void sort(Comparator<T> comparator) {
        T[] temp = (T[]) new Object[size];
        mergeSort(temp, 0, size - 1, comparator);
    }

    private void mergeSort(T[] temp, int leftIndex, int rightIndex, Comparator<T> comparator) {
        if (leftIndex < rightIndex) {
            int middle = (leftIndex + rightIndex) / 2;
            mergeSort(temp, leftIndex, middle, comparator);
            mergeSort(temp, middle + 1, rightIndex, comparator);
            merge(temp, leftIndex, middle, rightIndex, comparator);
        }
    }

    private void merge(T[] temp, int leftIndex, int middle, int rightIndex, Comparator<T> comparator) {
        int leftPointer = leftIndex;
        int rightPointer = middle + 1;
        int tempIndex = leftIndex;
        while (leftPointer <= middle && rightPointer <= rightIndex) {
            T leftElement = array[leftPointer];
            T rightElement = array[rightPointer];
            if (leftElement == null || rightElement == null) {
                if (leftElement == null) {
                    temp[tempIndex++] = rightElement;
                    rightPointer++;
                } else {
                    temp[tempIndex++] = leftElement;
                    leftPointer++;
                }
            } else if (comparator.compare(leftElement, rightElement) <= 0) {
                temp[tempIndex++] = leftElement;
                leftPointer++;
            } else {
                temp[tempIndex++] = rightElement;
                rightPointer++;
            }
        }
        while (leftPointer <= middle) {
            temp[tempIndex++] = array[leftPointer++];
        }
        while (rightPointer <= rightIndex) {
            temp[tempIndex++] = array[rightPointer++];
        }
        for (int i = leftIndex; i <= rightIndex; i++) {
            array[i] = temp[i];
        }
    }

    public int indexOf(T element) {
        for (int i = 0; i < size(); i++) {
            if (element.equals(array[i])) return i;
        }
        return -1;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size(); i++) {
            if (element.equals(array[i])) return true;
        }
        return false;
    }

    public static <T> ArrayList<T> asList(T... elements) {
        return new ArrayList<>(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                return array[currentIndex++];
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
