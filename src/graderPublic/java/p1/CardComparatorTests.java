package p1;

import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import p1.card.Card;
import p1.card.CardColor;
import p1.comparator.CardComparator;
import p1.transformers.MethodInterceptor;

@TestForSubmission
public class CardComparatorTests {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void checkIllegalMethods()  {
        MethodInterceptor.reset();

        new CardComparator().compare(new Card(CardColor.CLUBS, 2), new Card(CardColor.HEARTS, 3));
        new CardComparator().compare(new Card(CardColor.CLUBS, 2), new Card(CardColor.CLUBS, 3));

        IllegalMethodsCheck.checkMethods();
    }

}
