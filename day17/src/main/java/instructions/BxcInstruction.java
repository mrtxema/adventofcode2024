package instructions;

public class BxcInstruction implements Instruction {

    public BxcInstruction() {
    }

    @Override
    public boolean run(ExecutionContext context) {
        long result = context.get(Register.B) ^ context.get(Register.C);
        context.set(Register.B, result);
        return true;
    }

    @Override
    public String toString() {
        return "b = b ^ c";
    }
}
