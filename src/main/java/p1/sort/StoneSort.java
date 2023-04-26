package p1.sort;

import p1.comparator.CountingComparator;

import java.util.Comparator;

/**
 * A sorting algorithm that uses the stoneSort algorithm.
 * <p>
 * StoneSort is a variation of the bubbleSort algorithm where "low elements are falling down like stones" instead of
 * "high element are rising up like bubbles".
 * @param <T> the type of the elements to be sorted.
 */
public class StoneSort<T> implements Sort<T> {

    /**
     * The comparator used for comparing the sorted elements.
     */
    private final CountingComparator<T> comparator;

    /**
     * Creates a new {@link StoneSort} instance.
     * @param comparator the comparator used for comparing the sorted elements.
     */
    public StoneSort(Comparator<T> comparator) {
        this.comparator = new CountingComparator<>(comparator);
    }

    @Override
    public void sort(SortList<T> sortList) {
        comparator.reset();
        throw new UnsupportedOperationException("Not implemented yet"); //TODO H2 a): remove if implemented
    }

    @Override
    public int getComparisonsCount() {
        return comparator.getComparisonsCount();
    }

}
