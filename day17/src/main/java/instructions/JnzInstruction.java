package instructions;

public class JnzInstruction implements Instruction {
    private final int operand;

    public JnzInstruction(int operand) {
        this.operand = operand;
    }

    @Override
    public boolean run(ExecutionContext context) {
        if (context.get(Register.A) == 0) {
            return true;
        }
        context.jump(operand);
        return false;
    }

    @Override
    public String toString() {
        return "if (a != 0) jump " + operand;
    }
}
