package instructions;

public class BstInstruction extends ComboOperandInstruction {

    public BstInstruction(int operand) {
        super(operand);
    }

    @Override
    public boolean run(ExecutionContext context) {
        long result = getOperandValue(context) & 0x7;
        context.set(Register.B, result);
        return true;
    }

    @Override
    public String toString() {
        return "b = %s & 0x7".formatted(describeOperand());
    }
}
