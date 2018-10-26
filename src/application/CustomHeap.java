package application;

import java.util.Objects;

public class CustomHeap<T extends Comparable<T>> {

    private final T[] elements;

    private final boolean isMinHeap;

    private int size;

    @SuppressWarnings("unchecked")
    public CustomHeap(int capacity, boolean isMinHeap) {
        this.elements = (T[]) new Comparable[capacity];
        this.size = 0;
        this.isMinHeap = isMinHeap;
    }

    public void insert(T element) {
        elements[size] = element;
        stablizeInsert(size);
        size++;
    }

    public T remove() {
        if (isEmpty()) {
            return null;
        }

        T top = elements[0];
        int lastElementIndex = size - 1;
        swap(0, lastElementIndex, true);
        size--;
        stablizeDelete(0);
        return top;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return elements[0];
    }

    private int getParentIndex(int elementIndex) {
        return Math.floorDiv(elementIndex - 1, 2);
    }

    private int getLeftChild(int currentIndex) {
        return currentIndex * 2 + 1;
    }

    private int getRightChild(int currentIndex) {
        return currentIndex * 2 + 2;
    }

    private void stablizeInsert(int elementIndex) {
        int parentIndex = getParentIndex(elementIndex);

        if (parentIndex < 0 || parentIndex >= size) {
            return;
        }

        int comparison = elements[elementIndex].compareTo(elements[parentIndex]);

        if (isMinHeap && comparison < 0 || !isMinHeap && comparison > 0) {
            swap(elementIndex, parentIndex, false);
            stablizeInsert(parentIndex);
        }

    }

    private void stablizeDelete(int currentIndex) {
        if (currentIndex >= size) {
            return;
        }

        int leftChildIndex = getLeftChild(currentIndex);

        int rightChildIndex = getRightChild(currentIndex);

        if (leftChildIndex >= size && rightChildIndex >= size) {
            return;
        }

        T childToPromote = getChildToPromote(leftChildIndex, rightChildIndex);

        boolean isLeftForPromotion = childToPromote == null
                || Objects.equals(childToPromote, elements[leftChildIndex]);

        boolean isRightForPromotion = childToPromote == null
                || Objects.equals(childToPromote, elements[rightChildIndex]);

        if (leftChildIndex < size && isLeftForPromotion
                && (isMinHeap && elements[leftChildIndex].compareTo(elements[currentIndex]) < 0
                        || !isMinHeap && elements[leftChildIndex].compareTo(elements[currentIndex]) > 0)) {
            swap(currentIndex, leftChildIndex, false);
            stablizeDelete(leftChildIndex);
            return;
        }

        if (rightChildIndex < size && isRightForPromotion
                && (isMinHeap && elements[rightChildIndex].compareTo(elements[currentIndex]) < 0
                        || !isMinHeap && elements[rightChildIndex].compareTo(elements[currentIndex]) > 0)) {
            swap(currentIndex, rightChildIndex, false);
            stablizeDelete(rightChildIndex);
        }

    }

    private T getChildToPromote(int leftChildIndex, int rightChildIndex) {
        if (leftChildIndex >= size && rightChildIndex >= size) {
            return null;
        }

        int comparison = elements[leftChildIndex].compareTo(elements[rightChildIndex]);

        if (isMinHeap) {
            return comparison <= 0 ? elements[leftChildIndex] : elements[rightChildIndex];
        }

        return comparison >= 0 ? elements[leftChildIndex] : elements[rightChildIndex];
    }

    private void swap(int currentIndex, int swapIndex, boolean changeToNull) {
        T temp = changeToNull ? null : elements[currentIndex];
        elements[currentIndex] = elements[swapIndex];
        elements[swapIndex] = temp;

    }

}
