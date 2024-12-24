import common.parser.IOUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CircuitParser {

    public Circuit parse(List<String> lines) {
        var sections = IOUtils.splitSections(lines, 2);
        return new Circuit(parseValues(sections.get(0)), parseGates(sections.get(1)));
    }

    private Map<String, Integer> parseValues(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(":", 2))
                .collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1].trim())));
    }

    private Map<String, Gate> parseGates(List<String> lines) {
        return lines.stream().map(this::parseGate).collect(Collectors.toMap(Gate::output, Function.identity()));
    }

    private Gate parseGate(String spec) {
        var parts = spec.split(" ");
        return new Gate(parts[0], parts[2], Operator.valueOf(parts[1]), parts[4]);
    }
}
