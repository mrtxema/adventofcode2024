package instructions;

public class AdvInstruction extends ComboOperandInstruction {

    public AdvInstruction(int operand) {
        super(operand);
    }

    @Override
    public boolean run(ExecutionContext context) {
        long result = context.get(Register.A) >> getOperandValue(context);
        context.set(Register.A, result);
        return true;
    }

    @Override
    public String toString() {
        return "a = a >> " + describeOperand();
    }
}
