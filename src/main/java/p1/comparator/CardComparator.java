package p1.comparator;

import p1.card.Card;
import p1.card.CardColor;

import java.util.Comparator;

/**
 * Compares two {@linkplain Card cards}.
 * <p>
 * The cards are first compared by their value and then by their {@link CardColor}.
 *
 * @see Card
 */
public class CardComparator implements Comparator<Card> {

    /**
     * Compares two {@linkplain Card cards}.
     * <p>
     * The cards are first compared by their value and then by their {@link CardColor}.
     * <p>
     * The value of the cards compared by the natural order of the {@link Integer} class.
     * <p>
     * The color of the cards compared using the following order: {@link CardColor#CLUBS} > {@link CardColor#SPADES} >.{@link CardColor#HEARTS} > {@link CardColor#DIAMONDS}.
     *
     * @param o1 the first {@link Card} to compare.
     * @param o2 the second {@link Card} to compare.
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     * @throws NullPointerException if either of the {@link Card}s is null.
     *
     * @see Card
     * @see CardColor
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(Card o1, Card o2) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO H1 a): remove if implemented
    }
}
