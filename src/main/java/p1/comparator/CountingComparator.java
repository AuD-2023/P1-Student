package p1.comparator;

import java.util.Comparator;

/**
 * A {@link Comparator} that stores the number of comparisons made by the {@link #compare(Object, Object)} method.
 * <p>
 * The number of comparisons can be reset using the {@link #reset()} method.
 * <p>
 * The actual comparison is delegated to another {@link Comparator}.
 *
 * @param <T>
 */
public class CountingComparator<T> implements Comparator<T> {

    /**
     * The {@link Comparator} that performs the actual comparison.
     */
    private final Comparator<T> delegate;

    /**
     * Creates a new {@link CountingComparator} that delegates the actual comparison to the given {@link Comparator}.
     * @param delegate the {@link Comparator} that performs the actual comparison.
     */
    public CountingComparator(Comparator<T> delegate) {
        this.delegate = delegate;
    }

    /**
     * Resets the number of comparisons made by the {@link #compare(Object, Object)} method to 0.
     */
    public void reset() {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO H1 b): remove if implemented
    }

    /**
     * Compares its two arguments for order using the delegate {@link Comparator} given in the constructor.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     *
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(T o1, T o2) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO H1 b): remove if implemented
    }

    /**
     * Returns the number of comparisons made by the {@link #compare(Object, Object)} method since the last time {@link #reset()} got called.
     * If the {@link #reset()} method did not get called yet, the number of comparisons made since the creation of this object will be returned.
     * <p>
     * The number of comparisons is equal to the number of times the {@link #compare(Object, Object)} method got called.
     *
     * @return the number of comparisons made.
     */
    public int getComparisonsCount() {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO H1 b): remove if implemented
    }
}
