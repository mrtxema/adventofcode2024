import common.parser.IOUtils;
import java.util.stream.Collectors;

public class Solver {
    private final String fileName;
    private final ExecutionSpecParser executionSpecParser;
    private ExecutionSpec executionSpec;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.executionSpecParser = new ExecutionSpecParser();
    }

    public Solver parseFile() {
        executionSpec = executionSpecParser.parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        return this;
    }

    public String part1() {
        var executor = new ProgramExecutor(executionSpec);
        executor.run();
        return executor.getOutputs().stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public long part2() {
        return new ReverseEngineeringExecutor(executionSpec).findInput();
    }
}
