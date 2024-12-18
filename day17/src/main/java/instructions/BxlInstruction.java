package instructions;

public class BxlInstruction implements Instruction {
    private final int operand;

    public BxlInstruction(int operand) {
        this.operand = operand;
    }

    @Override
    public boolean run(ExecutionContext context) {
        long result = context.get(Register.B) ^ operand;
        context.set(Register.B, result);
        return true;
    }

    @Override
    public String toString() {
        return "b = b ^ " + operand;
    }
}
