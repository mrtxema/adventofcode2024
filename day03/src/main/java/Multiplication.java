public record Multiplication(int left, int right) implements Instruction {

    @Override
    public void apply(ExecutionContext context) {
        context.addValue(left * right);
    }
}
