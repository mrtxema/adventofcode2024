package instructions;

public abstract class ComboOperandInstruction implements Instruction {
    private final int operand;

    public ComboOperandInstruction(int operand) {
        this.operand = operand;
    }

    protected long getOperandValue(ExecutionContext context) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> context.get(Register.A);
            case 5 -> context.get(Register.B);
            case 6 -> context.get(Register.C);
            default -> throw new IllegalArgumentException("Invalid operand: " + operand);
        };
    }

    protected String describeOperand() {
        return switch (operand) {
            case 0, 1, 2, 3 -> String.valueOf(operand);
            case 4 -> "a";
            case 5 -> "b";
            case 6 -> "c";
            default -> throw new IllegalArgumentException("Invalid operand: " + operand);
        };
    }
}
