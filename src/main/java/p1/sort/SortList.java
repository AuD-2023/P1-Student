package p1.sort;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 * Like an array, but with methods.
 * <p>
 * An interface that represents an abstract data structure for storing a constant number of elements.
 * The elements are ordered and can be accessed by their index.
 * The values at any given index can be changed, but the size of the {@link SortList} cannot be changed.
 * <p>
 * An important distinction between this and a @{@link java.util.List} is that the contents of the {@link SortList} are
 * not shifted when an element is removed, similar to an array. This means that the {@link SortList} can contain gaps (null) elements.
 * <p>
 * Every implementation must store the number of read and write operations performed on the {@link SortList}.
 *
 * @param <E> the type of elements in this {@link SortList}
 */
public interface SortList<E> extends Iterable<@Nullable E> {

    /**
     * Returns the element at the specified index in this {@link SortList}.
     * <p>
     * Calling this method causes one read operation to be performed.
     *
     * @param index the index of the element to return.
     * @return the element at the specified index in this {@link SortList}.
     */
    @Nullable E get(int index);

    /**
     * Replaces the element at the specified index in this {@link SortList} with the specified element.
     * <p>
     * Calling this method causes one write operation to be performed.
     * @param index the index of the element to replace.
     * @param value the element to be stored at the specified index.
     */
    void set(int index, @Nullable E value);

    /**
     * Removes the element at the specified index in this {@link SortList}.
     * <p>
     * The elements after the removed element are not shifted to the left. Instead, the removed element is replaced with a null element.
     * <p>
     *
     * Calling this method is therefor equivalent to the following code:
     * {@code
     *   E removed = sortList.get(index);
     *   sortList.set(index, null);
     * }
     * <p>
     * Calling this method causes one read and one write operation to be performed.
     *
     * @param index the index of the element to be removed.
     * @return the element that was removed from the {@link SortList}.
     */
    @Nullable E remove(int index);

    /**
     * Returns the number of elements in this {@link SortList}.
     * @return the number of elements in this {@link SortList}.
     */
    int getSize();

    /**
     * Returns an {@link Iterator} over the elements in this {@link SortList} in proper sequence, including gaps (null) elements.
     */
    @Override
    Iterator<@Nullable E> iterator();

    /**
     * Returns the number of read operations performed on this {@link SortList}.
     * @return the number of read operations performed on this {@link SortList}.
     */
    int getReadCount();

    /**
     * Returns the number of write operations performed on this {@link SortList}.
     * @return the number of write operations performed on this {@link SortList}.
     */
    int getWriteCount();

    /**
     * Returns a new {@link SortList} containing the elements of the specified array.
     * @param elements the array whose elements are to be placed into this {@link SortList}.
     * @param <E> the type of elements in this {@link SortList}.
     * @return a new {@link SortList} containing the elements of the specified array.
     */
    static <E> SortList<E> of(E[] elements) {
        return new ArraySortList<>(elements);
    }
}
