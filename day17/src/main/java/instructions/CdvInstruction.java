package instructions;

public class CdvInstruction extends ComboOperandInstruction {

    public CdvInstruction(int operand) {
        super(operand);
    }

    @Override
    public boolean run(ExecutionContext context) {
        long result = context.get(Register.A) >> getOperandValue(context);
        context.set(Register.C, result);
        return true;
    }

    @Override
    public String toString() {
        return "c = a >> " + describeOperand();
    }
}
