package p1;

import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import p1.sort.HybridOptimizer;
import p1.sort.HybridSort;
import p1.transformers.MethodInterceptor;

@TestForSubmission
public class HybridOptimizerTest {

    public void checkIllegalMethods()  {
        MethodInterceptor.reset();

        HybridOptimizer.optimize(new HybridSort<>(3, Integer::compare), new Integer[]{5,4,3,2,1});

        IllegalMethodsCheck.checkMethods("^java/util/Arrays.+");
    }

}
