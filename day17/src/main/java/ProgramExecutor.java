import instructions.AdvInstruction;
import instructions.BdvInstruction;
import instructions.BstInstruction;
import instructions.BxcInstruction;
import instructions.BxlInstruction;
import instructions.CdvInstruction;
import instructions.ExecutionContext;
import instructions.Instruction;
import instructions.JnzInstruction;
import instructions.OutInstruction;
import instructions.Register;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ProgramExecutor implements ExecutionContext {
    private final ExecutionSpec executionSpec;
    private final Map<Register, Long> registers;
    private final List<Integer> outputs;
    private int instructionPointer;

    public ProgramExecutor(ExecutionSpec executionSpec) {
        this.executionSpec = executionSpec;
        this.registers = new EnumMap<>(Map.of(
                Register.A, executionSpec.registerA(),
                Register.B, executionSpec.registerB(),
                Register.C, executionSpec.registerC()));
        this.outputs = new ArrayList<>();
        this.instructionPointer = 0;
    }

    public void run() {
        while (true) {
            try {
                if (parseNextInstruction().run(this)) {
                    instructionPointer += 2;
                }
            } catch (HaltProgramException e) {
                return;
            }
        }
    }

    private Instruction parseNextInstruction() throws HaltProgramException {
        if (instructionPointer >= executionSpec.program().size() - 1) {
            throw new HaltProgramException();
        }
        int opcode = executionSpec.program().get(instructionPointer);
        int operand = executionSpec.program().get(instructionPointer + 1);
        return switch (opcode) {
            case 0 -> new AdvInstruction(operand);
            case 1 -> new BxlInstruction(operand);
            case 2 -> new BstInstruction(operand);
            case 3 -> new JnzInstruction(operand);
            case 4 -> new BxcInstruction();
            case 5 -> new OutInstruction(operand);
            case 6 -> new BdvInstruction(operand);
            case 7 -> new CdvInstruction(operand);
            default -> throw new IllegalArgumentException("Unknown opcode: " + opcode);
        };
    }

    public List<Integer> getOutputs() {
        return outputs;
    }

    @Override
    public long get(Register register) {
        return registers.get(register);
    }

    @Override
    public void set(Register register, long value) {
        registers.put(register, value);
    }

    @Override
    public void jump(int instructionPointer) {
        this.instructionPointer = instructionPointer;
    }

    @Override
    public void output(int value) {
        outputs.add(value);
    }

    private static class HaltProgramException extends RuntimeException {
    }
}
