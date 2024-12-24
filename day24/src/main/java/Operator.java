import java.util.function.BinaryOperator;

public enum Operator {
    AND((a, b) -> a & b),
    OR((a, b) -> a | b),
    XOR((a, b) -> a ^ b);

    private final BinaryOperator<Integer> function;

    Operator(BinaryOperator<Integer> function) {
        this.function = function;
    }

    public int resolve(int value1, int value2) {
        return function.apply(value1, value2);
    }
}
