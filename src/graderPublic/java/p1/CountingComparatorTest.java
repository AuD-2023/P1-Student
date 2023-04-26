package p1;

import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import p1.comparator.CountingComparator;
import p1.transformers.MethodInterceptor;

@TestForSubmission
public class CountingComparatorTest {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void checkIllegalMethods()  {
        MethodInterceptor.reset();

        CountingComparator<Integer> countingComparator = new CountingComparator<>(Integer::compareTo);
        countingComparator.compare(2, 3);
        countingComparator.getComparisonsCount();
        countingComparator.reset();

        IllegalMethodsCheck.checkMethods();
    }

}
