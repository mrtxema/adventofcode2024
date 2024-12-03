public class Enable implements Instruction {

    @Override
    public void apply(ExecutionContext context) {
        context.enable(true);
    }
}
