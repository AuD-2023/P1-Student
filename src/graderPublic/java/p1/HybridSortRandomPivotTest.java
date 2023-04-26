package p1;

import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import p1.sort.ArraySortList;
import p1.sort.HybridSortRandomPivot;
import p1.transformers.MethodInterceptor;

@TestForSubmission
public class HybridSortRandomPivotTest {

    public void checkIllegalMethods()  {
        MethodInterceptor.reset();

        new HybridSortRandomPivot<>(3, Integer::compareTo).partition(new ArraySortList<>(new Integer[]{5,4,3,2,1}), 0, 4);

        IllegalMethodsCheck.checkMethods("^java/util/Random.+");
    }

}
