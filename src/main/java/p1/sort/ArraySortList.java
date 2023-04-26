package p1.sort;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * An implementation of {@link SortList} that uses an array to store the elements.
 * @param <E> the type of elements in this {@link ArraySortList}.
 */
public class ArraySortList<E> implements SortList<E> {

    /**
     * The array that stores the elements.
     */
    E[] elements;

    /**
     * The number of read operations performed on this {@link ArraySortList}.
     */
    private int readCount = 0;

    /**
     * The number of write operations performed on this {@link ArraySortList}.
     */
    private int writeCount = 0;

    /**
     * Creates a new {@link ArraySortList} with the specified elements.
     * @param elements the elements to store in this {@link ArraySortList}.
     */
    @SuppressWarnings("unchecked")
    public ArraySortList(List<E> elements) {
        this.elements = (E[]) elements.toArray();
    }

    /**
     * Creates a new {@link ArraySortList} with the specified elements.
     * @param elements the elements to store in this {@link ArraySortList}.
     */
    public ArraySortList(E[] elements) {
        this.elements = Arrays.copyOf(elements, elements.length);
    }

    @Override
    public E get(int index) {
        readCount++;
        return elements[index];
    }

    @Override
    public void set(int index, E value) {

        if (index >= elements.length || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }

        writeCount++;
        elements[index] = value;
    }

    @Override
    public @Nullable E remove(int index) {
        readCount++;
        writeCount++;
        E removed = elements[index];
        elements[index] = null;
        return removed;
    }

    @Override
    public int getSize() {
        return elements.length;
    }

    @Override
    public Iterator<@Nullable E> iterator() {
        return Arrays.stream(elements).iterator();
    }

    @Override
    public int getReadCount() {
        return readCount;
    }

    @Override
    public int getWriteCount() {
        return writeCount;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
