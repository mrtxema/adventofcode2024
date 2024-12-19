import common.parser.IOUtils;
import java.util.HashMap;
import java.util.Map;

public class Solver {
    private final String fileName;
    private final TowelSpecsParser towelSpecsParser;
    private final Map<String, Long> cache = new HashMap<>();
    private TowelSpecs towelSpecs;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.towelSpecsParser = new TowelSpecsParser();
    }

    public Solver parseFile() {
        towelSpecs = towelSpecsParser.parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        return this;
    }

    public long part1() {
        return towelSpecs.towelDesigns().stream().filter(design -> countCombinations(cache, design) > 0).count();
    }

    public long part2() {
        return towelSpecs.towelDesigns().stream().mapToLong(design -> countCombinations(cache, design)).sum();
    }

    private long countCombinations(Map<String, Long> cache, String remainingDesign) {
        var cachedResult = cache.get(remainingDesign);
        if (cachedResult != null) {
            return cachedResult;
        }
        var computedResult = computeCombinations(cache, remainingDesign);
        cache.put(remainingDesign, computedResult);
        return computedResult;
    }

    private long computeCombinations(Map<String, Long> cache, String remainingDesign) {
        long count = 0;
        for (String pattern : towelSpecs.towelPatterns()) {
            if (remainingDesign.startsWith(pattern)) {
                var newRemaining = remainingDesign.substring(pattern.length());
                count += newRemaining.isEmpty() ? 1 : countCombinations(cache, newRemaining);
            }
        }
        return count;
    }
}
