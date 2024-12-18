import java.util.Arrays;
import java.util.List;

public class ExecutionSpecParser {
    public ExecutionSpec parse(List<String> lines) {
        return new ExecutionSpec(
                parseRegisterValue(lines.get(0)),
                parseRegisterValue(lines.get(1)),
                parseRegisterValue(lines.get(2)),
                parseProgram(lines.get(4)));
    }

    private long parseRegisterValue(String spec) {
        return Long.parseLong(removeLabel(spec));
    }

    private List<Integer> parseProgram(String spec) {
        return Arrays.stream(removeLabel(spec).split(",")).map(Integer::valueOf).toList();
    }

    private String removeLabel(String spec) {
        return spec.substring(spec.indexOf(':') + 1).trim();
    }
}
