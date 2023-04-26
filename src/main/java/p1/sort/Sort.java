package p1.sort;

/**
 * An interface that represents a sorting algorithm.
 * The sorting algorithm is applied to a {@link SortList}.
 * The algorithm is implemented in the {@link #sort(SortList)} method.
 * <p>
 * Every implementation must store the number of comparisons made by the sorting algorithm. This can be done by using a {@link p1.comparator.CountingComparator}.
 *
 * @param <T> the type of the elements in the {@link SortList} that will be sorted.
 */
public interface Sort<T> {

    /**
     * Sorts the given {@link SortList}.
     * @param sortList the {@link SortList} to sort.
     */
    void sort(SortList<T> sortList);

    /**
     * Returns the number of comparisons made by the sorting algorithm during the last execution of the {@link #sort(SortList)} method
     * or null if the {@link #sort(SortList)} has not been called yet.
     * @return the number of comparisons made by the sorting algorithm.
     */
    int getComparisonsCount();

}
