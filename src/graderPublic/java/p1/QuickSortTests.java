package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.mockito.ArgumentCaptor;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.ArraySortList;
import p1.sort.HybridSort;
import p1.sort.SortList;
import p1.transformers.MethodInterceptor;

import java.util.Comparator;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertCallEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertSame;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@SuppressWarnings("DuplicatedCode")
@TestForSubmission
public class QuickSortTests {

    private static final Comparator<Integer> COMPARATOR = Comparator.naturalOrder();
    private static HybridSort<Integer> hybridSort;

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
        hybridSort = spy(new HybridSort<>(5, COMPARATOR));
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods();
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H4_QuickSortTests.json", data = "insertionTest")
    public void testInsertionCall(@Property("values") List<Integer> values,
                                  @Property("left") Integer left,
                                  @Property("right") Integer right,
                                  @Property("k") Integer k,
                                  @Property("calls") Boolean calls) {

        Context context = contextBuilder()
            .subject("HybridSort#quickSort()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("k", k)
            .build();

        hybridSort.setK(k);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<SortList<Integer>> sortListCaptor = ArgumentCaptor.forClass(SortList.class);
        ArgumentCaptor<Integer> leftCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> rightCaptor = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(hybridSort).insertionSort(sortListCaptor.capture(), leftCaptor.capture(), rightCaptor.capture());
        doReturn(0).when(hybridSort).partition(any(), anyInt(), anyInt());
        doCallRealMethod().doNothing().when(hybridSort).quickSort(any(), anyInt(), anyInt());

        SortList<Integer> sortList = new ArraySortList<>(values);

        hybridSort.quickSort(sortList, left, right);

        if (calls) {
            checkVerify(() -> verify(hybridSort, never()).partition(any(), anyInt(), anyInt()), context,
                "partition() was called when it should not have been.");

            checkVerify(() -> verify(hybridSort, times(1)).quickSort(any(), anyInt(), anyInt()), context,
                "quickSort() was called when it should not have been.");

            checkVerify(() -> verify(hybridSort, times(1)).insertionSort(any(), anyInt(), anyInt()), context,
                "insertionSort() was not called exactly once when it should have been.");

            assertSame(sortList, sortListCaptor.getValue(), context, result -> "insertionSort() was called with the wrong SortList.");
            assertEquals(left, leftCaptor.getValue(), context, result -> "insertionSort() was called with the wrong left index.");
            assertEquals(right, rightCaptor.getValue(), context, result -> "insertionSort() was called with the wrong right index.");
        } else {
            checkVerify(() -> verify(hybridSort, never()).insertionSort(any(), anyInt(), anyInt()), context,
                "insertionSort() was called when it should not have been.");
        }
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H4_QuickSortTests.json", data = "quickSortTest")
    public void testQuickSortRecursion(@Property("values") List<Integer> values,
                              @Property("left") Integer left,
                              @Property("right") Integer right,
                              @Property("k") Integer k,
                              @Property("calls") Boolean calls,
                              @Property("partitioner") Integer partitioner) {

        Context context = contextBuilder()
            .subject("HybridSort#quickSort()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("k", k)
            .add("partitioner", partitioner)
            .build();

        hybridSort.setK(k);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<SortList<Integer>> quickSortSortListCaptor = ArgumentCaptor.forClass(SortList.class);
        ArgumentCaptor<Integer> quickSortLeftCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> quickSortRightCaptor = ArgumentCaptor.forClass(Integer.class);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<SortList<Integer>> partitionSortListCaptor = ArgumentCaptor.forClass(SortList.class);
        ArgumentCaptor<Integer> partitionLeftCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> partitionRightCaptor = ArgumentCaptor.forClass(Integer.class);

        doNothing().when(hybridSort).insertionSort(any(), anyInt(), anyInt());
        doReturn(partitioner).when(hybridSort).partition(partitionSortListCaptor.capture(), partitionLeftCaptor.capture(), partitionRightCaptor.capture());
        doCallRealMethod().doNothing().when(hybridSort).quickSort(quickSortSortListCaptor.capture(), quickSortLeftCaptor.capture(), quickSortRightCaptor.capture());

        SortList<Integer> sortList = new ArraySortList<>(values);

        hybridSort.quickSort(sortList, left, right);

        if (calls) {
            checkVerify(() -> verify(hybridSort, never()).insertionSort(any(), anyInt(), anyInt()), context,
                "quickSort() was called when it should not have been.");
            checkVerify(() -> verify(hybridSort, times(1)).partition(any(), anyInt(), anyInt()), context,
                "partition() was not called exactly once when it should have been.");
            checkVerify(() -> verify(hybridSort, times(3)).quickSort(any(), anyInt(), anyInt()), context,
                "quickSort() was not called exactly twice when it should have been.");

            assertSame(sortList, partitionSortListCaptor.getValue(), context, result -> "partition() was called with the wrong SortList.");
            assertEquals(left, partitionLeftCaptor.getValue(), context, result -> "partition() was called with the wrong left index.");
            assertEquals(right, partitionRightCaptor.getValue(), context, result -> "partition() was called with the wrong right index.");

            assertSame(sortList, quickSortSortListCaptor.getAllValues().get(1), context, result -> "quickSort() was called with the wrong SortList at the first call.");
            assertSame(sortList, quickSortSortListCaptor.getAllValues().get(2), context, result -> "quickSort() was called with the wrong SortList at the second call.");

            boolean leftFirst = quickSortLeftCaptor.getAllValues().get(1).equals(left);

            if (leftFirst) {
                assertEquals(left, quickSortLeftCaptor.getAllValues().get(1), context, result -> "quickSort() was called with the wrong left index at the first call.");
                assertEquals(partitioner, quickSortRightCaptor.getAllValues().get(1), context, result -> "quickSort() was called with the wrong right index at the first call.");

                assertEquals(partitioner + 1, quickSortLeftCaptor.getAllValues().get(2), context, result -> "quickSort() was called with the wrong left index at the second call.");
                assertEquals(right, quickSortRightCaptor.getAllValues().get(2), context, result -> "quickSort() was called with the wrong right index at the second call.");
            } else {
                assertEquals(partitioner + 1, quickSortLeftCaptor.getAllValues().get(1), context, result -> "quickSort() was called with the wrong left index at the first call.");
                assertEquals(right, quickSortRightCaptor.getAllValues().get(1), context, result -> "quickSort() was called with the wrong right index at the first call.");

                assertEquals(left, quickSortLeftCaptor.getAllValues().get(2), context, result -> "quickSort() was called with the wrong left index at the second call.");
                assertEquals(partitioner, quickSortRightCaptor.getAllValues().get(2), context, result -> "quickSort() was called with the wrong right index at the second call.");
            }
        } else {
            checkVerify(() -> verify(hybridSort, never()).partition(any(), anyInt(), anyInt()), context, "partition() was called when it should not have been.");
            checkVerify(() -> verify(hybridSort, times(1)).quickSort(any(), anyInt(), anyInt()), context, "quickSort() was called when it should not have been.");
        }
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H4_QuickSortTests.json", data = "alreadyPartitionedTest")
    public void testAlreadyPartitioned(@Property("values") List<Integer> values,
                                       @Property("left") Integer left,
                                       @Property("right") Integer right) {

       checkPartitioning(values, left, right, left, values);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H4_QuickSortTests.json", data = "partitionTwoItemsTest")
    public void testPartitionTwoItems(@Property("values") List<Integer> values,
                                       @Property("left") Integer left,
                                       @Property("right") Integer right,
                                       @Property("expected") List<Integer> expected) {

        checkPartitioning(values, left, right, left, expected);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H4_QuickSortTests.json", data = "partitionThreeItemsTest")
    public void testPartitionThreeItems(@Property("values") List<Integer> values,
                                      @Property("left") Integer left,
                                      @Property("right") Integer right,
                                      @Property("expectedPartitioner") Integer expectedPartitioner,
                                      @Property("expected") List<Integer> expected) {

        checkPartitioning(values, left, right, expectedPartitioner, expected);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H4_QuickSortTests.json", data = "partitionMultipleItemsTest")
    public void testPartitionMultipleItems(@Property("values") List<Integer> values,
                                        @Property("left") Integer left,
                                        @Property("right") Integer right,
                                        @Property("expectedPartitioner") Integer expectedPartitioner,
                                        @Property("expected") List<Integer> expected) {

        checkPartitioning(values, left, right, expectedPartitioner, expected);
    }

    private void checkPartitioning(List<Integer> values, int left, int right, int expectedPartitioner, List<Integer> expected) {

        SortList<Integer> sortList = new ArraySortList<>(values);

        assertCallEquals(expectedPartitioner, () -> hybridSort.partition(sortList, left, right), contextBuilder()
            .subject("HybridSort#partition()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("expected", expected)
            .build(), result -> "partition() returned the wrong value.");

        Context context = contextBuilder()
            .subject("HybridSort#partition()")
            .add("values", values)
            .add("left", left)
            .add("right", right)
            .add("expected", expected)
            .add("actual", sortList)
            .build();

        for (int i = 0; i < values.size(); i++) {
            int finalI = i;
            assertEquals(expected.get(i), sortList.get(i), context, result -> "sortList contains wrong value at index %d.".formatted(finalI));
        }
    }

    private void checkVerify(Runnable verifier, Context context, String msg) {
        try {
            verifier.run();
        } catch (MockitoAssertionError e) {
            fail(context, result -> msg + " Original error message:\n" + e.getMessage());
        } catch (Exception e) {
            fail(context, result -> "Unexpected Exception:\n" + e.getMessage());
        }
    }

}
