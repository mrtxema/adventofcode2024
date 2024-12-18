package instructions;

public class OutInstruction extends ComboOperandInstruction {

    public OutInstruction(int operand) {
        super(operand);
    }

    @Override
    public boolean run(ExecutionContext context) {
        int result = (int) (getOperandValue(context) & 0x7);
        context.output(result);
        return true;
    }

    @Override
    public String toString() {
        return "output %s & 0x7".formatted(describeOperand());
    }
}
