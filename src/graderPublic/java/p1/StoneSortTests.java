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
import p1.sort.SortList;
import p1.sort.StoneSort;
import p1.transformers.MethodInterceptor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static p1.OperationSortList.Operation.toHTMLList;

@TestForSubmission
public class StoneSortTests {

    private static final Comparator<Integer> COMPARATOR = Comparator.naturalOrder();
    private static final StoneSort<Integer> STONE_SORT = new StoneSort<>(COMPARATOR);

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods();
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H7_StoneSortTests.json", data = "alreadySortedTest")
    public void testAlreadySorted(@Property("values") List<Integer> values) {
        testSorting(values, values);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H7_StoneSortTests.json", data = "oneItemTest")
    public void testOneItem(@Property("values") List<Integer> values) {
        testSorting(values, values);
    }

    @Test
    public void testEmpty() {
        testSorting(List.of(), List.of());
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H7_StoneSortTests.json", data = "twoItemsTest")
    public void testTwoItems(@Property("values") List<Integer> values) {
        testSorting(values, values.stream().sorted(COMPARATOR).toList());
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H7_StoneSortTests.json", data = "multipleItemsTest")
    public void testMultipleItems(@Property("values") List<Integer> values) {
        testSorting(values, values.stream().sorted(COMPARATOR).toList());
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H7_StoneSortTests.json", data = "operationOrderTest")
    public void testOperationOrder(@Property("values") List<Integer> values, @Property("operations") List<String> operations) {

        List<OperationSortList.Operation> ops = operations.stream().map(OperationSortList.Operation::of).toList();

        OperationSortList sortList = new OperationSortList(values);
        call(() -> STONE_SORT.sort(sortList), contextBuilder()
                .subject("StoneSort#sort()")
                .add("values", values)
                .add("comparator", "natural_order")
                .build(),
            result -> "sort() should not throw an exception.");

        Context context = contextBuilder()
            .subject("StoneSort#sort()")
            .add("values", values)
            .add("comparator", "natural_order")
            .add("actual operations", toHTMLList(sortList.operations))
            .add("expected operations", toHTMLList(ops))
            .build();

        assertEquals(ops.size(), sortList.operations.size(), context,
            result -> "sort() did not perform the correct amount of read and write operations on the sortList.");

        for (int i = 0; i < ops.size(); i++) {
            int finalI = i;
            assertEquals(ops.get(i), sortList.operations.get(i), context,
                result -> "sort() did not perform the correct operations on the sortList at index %d.".formatted(finalI));
        }
    }

    private void testSorting(List<Integer> values, List<Integer> expected) {

        SortList<Integer> sortList = new ArraySortList<>(values);

        call(() -> STONE_SORT.sort(sortList), contextBuilder()
                .subject("StoneSort#sort()")
                .add("values", values)
                .add("comparator", "natural_order")
                .build(),
            result -> "insertionSort() should not throw an exception.");

        Context context = contextBuilder()
            .subject("StoneSort#sort()")
            .add("values", values)
            .add("comparator", "natural_order")
            .add("actual", sortList)
            .add("expected", expected)
            .build();

        assertTrue(isSorted(sortList, expected), context,
            result -> "The sortList is not sorted.");
    }

    private boolean isSorted(SortList<Integer> sortList, List<Integer> expected) {

        for (int i = 0; i < expected.size(); i++) {
            if (!Objects.equals(sortList.get(i), expected.get(i))) {
                return false;
            }
        }

        return true;
    }

}
