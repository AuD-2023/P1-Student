package p1;

import p1.sort.ArraySortList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class OperationSortList extends ArraySortList<Integer> {

    public final List<Operation> operations = new ArrayList<>();

    public OperationSortList(List<Integer> values) {
        super(values);
    }

    @Override
    public Integer get(int index) {
        operations.add(new ReadOperation(index));
        return super.get(index);
    }

    @Override
    public void set(int index, Integer value) {
        operations.add(new WriteOperation(index, value));
        super.set(index, value);
    }

    @Override
    public Integer remove(int index) {
        operations.add(new ReadOperation(index));
        operations.add(new WriteOperation(index, null));
        return super.remove(index);
    }

    interface Operation {

        static Operation of(String operation) {
            if (operation.startsWith("R")) {
                return new ReadOperation(Integer.parseInt(operation.substring(1)));
            } else if (operation.startsWith("W")) {
                String[] parts = operation.substring(1).split("=");
                return new WriteOperation(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else {
                throw new IllegalArgumentException("Unknown operation: " + operation);
            }
        }

        static String toHTMLList(List<Operation> list) {
            return "\\<ul\\>" + list.stream().map(Operation::toString).map(s -> "\\<li\\>" + s + "\\</li\\>").collect(Collectors.joining()) + "\\</ul\\>";
        }
    }

    record ReadOperation(int index) implements Operation {

        @Override
        public String toString() {
            return "Read(index=%d)".formatted(index);
        }
    }

    record WriteOperation(int index, Integer value) implements Operation {

        @Override
        public String toString() {
            return "Write(index=%d, value=%d)".formatted(index, value);
        }
    }
}
