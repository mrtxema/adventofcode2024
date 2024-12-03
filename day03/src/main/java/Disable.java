public class Disable implements Instruction {

    @Override
    public void apply(ExecutionContext context) {
        context.enable(false);
    }
}
