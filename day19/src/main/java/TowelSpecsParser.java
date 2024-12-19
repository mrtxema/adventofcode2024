import common.parser.IOUtils;
import java.util.Arrays;
import java.util.List;

public class TowelSpecsParser {

    public TowelSpecs parse(List<String> lines) {
        var sections = IOUtils.splitSections(lines, 2);
        return new TowelSpecs(parsePatterns(sections.get(0).get(0)), sections.get(1));
    }

    private List<String> parsePatterns(String spec) {
        return Arrays.stream(spec.split(",")).map(String::trim).toList();
    }
}
