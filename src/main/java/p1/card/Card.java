package p1.card;

/**
 * A card that consists of a {@link CardColor} and a value.
 * @param cardColor the color of the card.
 * @param cardValue the value of the card. Must be between 2 and 13 (both inclusive).
 *
 * @see CardColor
 */
public record Card(CardColor cardColor, int cardValue) {

    /**
     * Creates a new {@link Card} with the given {@link CardColor} and value.
     * @param cardColor the {@link CardColor} of the card.
     * @param cardValue the value of the card. Must be between 2 and 13 (both inclusive).
     * @throws IllegalArgumentException if the {@link CardColor} is {@code null} or the value is not between 2 and 13 (both inclusive).
     */
    public Card {
        if (cardColor == null) {
            throw new IllegalArgumentException("card color must not be null");
        }

        if (cardValue < 2 || cardValue > 13) {
            throw new IllegalArgumentException("card value must be between 2 and 13");
        }
    }

}
