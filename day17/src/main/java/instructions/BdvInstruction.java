package instructions;

public class BdvInstruction extends ComboOperandInstruction {

    public BdvInstruction(int operand) {
        super(operand);
    }

    @Override
    public boolean run(ExecutionContext context) {
        long result = context.get(Register.A) >> getOperandValue(context);
        context.set(Register.B, result);
        return true;
    }

    @Override
    public String toString() {
        return "b = a >> " + describeOperand();
    }
}
