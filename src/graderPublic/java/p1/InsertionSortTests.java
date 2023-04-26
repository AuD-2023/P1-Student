package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.ArraySortList;
import p1.sort.HybridSort;
import p1.sort.SortList;
import p1.transformers.MethodInterceptor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static p1.OperationSortList.Operation.toHTMLList;

@TestForSubmission
public class InsertionSortTests {

    private static final Comparator<Integer> COMPARATOR = Comparator.naturalOrder();
    private static final HybridSort<Integer> HYBRID_SORT = new HybridSort<>(5, COMPARATOR);

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods();
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3_InsertionSortTests.json", data = "twoItemsTest")
    public void testTwoItems(@Property("values") List<Integer> values) {

        testSorting(values, 0, values.size() - 1, values.stream().sorted(COMPARATOR).toList());
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3_InsertionSortTests.json", data = "multipleItemsTest")
    public void testMultipleItems(@Property("values") List<Integer> values) {

        testSorting(values, 0, values.size() - 1, values.stream().sorted(COMPARATOR).toList());
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3_InsertionSortTests.json", data = "operationOrderTest")
    public void testOperationOrder(@Property("values") List<Integer> values,
                                   @Property("left") Integer left,
                                   @Property("right") Integer right,
                                   @Property("operations") List<String> operations) {

        List<OperationSortList.Operation> ops = operations.stream().map(OperationSortList.Operation::of).toList();

        OperationSortList sortList = new OperationSortList(values);
        call(() -> HYBRID_SORT.insertionSort(sortList, left, right), contextBuilder()
            .subject("HybridSort#insertionSort()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("comparator", "natural_order")
            .build(),
            result -> "insertionSort() should not throw an exception.");

        Context context = contextBuilder()
            .subject("HybridSort#insertionSort()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("comparator", "natural_order")
            .add("actual operations", toHTMLList(sortList.operations))
            .add("expected operations", toHTMLList(ops))
            .build();

        assertEquals(ops.size(), sortList.operations.size(), context,
            result -> "insertionSort() did not perform the correct amount of read and write operations on the sortList.");

        for (int i = 0; i < ops.size(); i++) {
            int finalI = i;
            assertEquals(ops.get(i), sortList.operations.get(i), context,
                result -> "insertionSort() did not perform the correct operations on the sortList at index %d.".formatted(finalI));
        }
    }

    private void testSorting(List<Integer> values, Integer left, Integer right, List<Integer> expected) {
        SortList<Integer> sortList = new ArraySortList<>(values);
        call(() -> HYBRID_SORT.insertionSort(sortList, left, right), contextBuilder()
            .subject("HybridSort#insertionSort()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("comparator", "natural_order")
            .build(),
            result -> "insertionSort() should not throw an exception.");

        Context context = contextBuilder()
            .subject("HybridSort#insertionSort()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("comparator", "natural_order")
            .add("actual", sortList)
            .add("expected", getTotalExpected(values, left, right, expected))
            .build();

        assertTrue(isSorted(sortList, left, right, expected), context,
            result -> "The sortList should be sorted between the indices %d and %d (inclusive) after calling insertionSort()."
                .formatted(left, right));
    }

    private List<Integer> getTotalExpected(List<Integer> values, Integer left, Integer right, List<Integer> expected) {
        return Stream.concat(Stream.concat(values.stream().limit(left), expected.stream()), values.stream().skip(right + 1)).toList();
    }

    private boolean isSorted(SortList<Integer> sortList, Integer left, Integer right, List<Integer> expected) {

        for (int i = left; i < right; i++) {
            if (!Objects.equals(sortList.get(i), expected.get(i))) {
                return false;
            }
        }

        return true;
    }

}
