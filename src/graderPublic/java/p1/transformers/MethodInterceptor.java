package p1.transformers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MethodInterceptor {

    private static final LinkedList<Invocation> INVOCATIONS = new LinkedList<>();

    public static void reset() {
        INVOCATIONS.clear();
    }

    @SuppressWarnings("unused")//injected by MethodInterceptorTransformer
    public static void addInvocation(Invocation invocation) {
        INVOCATIONS.add(invocation);
    }

    public static List<Invocation> getInvocations() {
        return Collections.unmodifiableList(INVOCATIONS);
    }

    public record Invocation(String signature, Object objectRef, Object... params) {}
}
