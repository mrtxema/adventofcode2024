package instructions;

public interface ExecutionContext {

    long get(Register register);

    void set(Register register, long value);

    void jump(int instructionPointer);

    void output(int value);
}
