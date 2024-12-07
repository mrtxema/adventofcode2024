import java.util.function.BiFunction;

public enum Operator {
    ADD(Long::sum),
    MULTIPLY((a, b) -> a * b),
    CONCATENATION((a, b) -> Long.parseLong(a + "" + b));

    private final BiFunction<Long, Long, Long> function;

    Operator(BiFunction<Long, Long, Long> function) {
        this.function = function;
    }

    public long apply(long left, long right) {
        return function.apply(left, right);
    }
}
