import common.parser.IOUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PrintingInstructionsParser {

    public PrintingInstructions parse(List<String> lines) {
        var sections = IOUtils.splitSections(lines, 2);
        return new PrintingInstructions(parseRules(sections.get(0)), parseUpdates(sections.get(1)));
    }

    private Map<Integer, Set<Integer>> parseRules(List<String> lines) {
        return lines.stream()
                .map(line -> line.split("\\|", 2))
                .map(parts -> new PageOrderingRule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .collect(Collectors.groupingBy(
                        PageOrderingRule::before,
                        Collectors.mapping(PageOrderingRule::after, Collectors.toSet())));
    }

    private List<UpdatePages> parseUpdates(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(","))
                .map(parts -> Arrays.stream(parts).map(Integer::valueOf).toList())
                .map(UpdatePages::new)
                .toList();
    }

    private record PageOrderingRule(int before, int after) {
    }
}
