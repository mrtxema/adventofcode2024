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

public class ReverseEngineeringExecutor implements ExecutionContext {
    private final ExecutionSpec executionSpec;
    private final Map<Register, Long> registers;
    private final List<Integer> outputs;
    private int instructionPointer;

    public ReverseEngineeringExecutor(ExecutionSpec executionSpec) {
        this.executionSpec = executionSpec;
        this.registers = new EnumMap<>(Register.class);
        this.outputs = new ArrayList<>();
    }

    public long findInput() {
        int value = 0;
        while (true) {
            if (test(value)) {
                return value;
            }
            value++;
        }
    }

    public boolean test(long registerAValue) {
        this.registers.put(Register.A, registerAValue);
        this.registers.put(Register.B, executionSpec.registerB());
        this.registers.put(Register.C, executionSpec.registerC());
        this.outputs.clear();
        this.instructionPointer = 0;
        while (true) {
            try {
                if (parseNextInstruction(instructionPointer).run(this)) {
                    instructionPointer += 2;
                }
                if (!canBeSolution()) {
                    return false;
                }
            } catch (HaltProgramException e) {
                return outputs.equals(executionSpec.program());
            }
        }
    }

    private boolean canBeSolution() {
        if (outputs.isEmpty()) {
            return true;
        }
        int lastOutputIndex = outputs.size() - 1;
        return lastOutputIndex < executionSpec.program().size()
                && outputs.get(lastOutputIndex).equals(executionSpec.program().get(lastOutputIndex));
    }

    private Instruction parseNextInstruction(int pointer) throws HaltProgramException {
        if (pointer >= executionSpec.program().size() - 1) {
            throw new HaltProgramException();
        }
        int opcode = executionSpec.program().get(pointer);
        int operand = executionSpec.program().get(pointer + 1);
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
