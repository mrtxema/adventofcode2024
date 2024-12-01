import common.parser.IOUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solver {
    private final String fileName;
    private final LocationPairParser locationPairParser;
    private List<LocationPair> locationPairs;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.locationPairParser = new LocationPairParser();
    }

    public Solver parseFile() {
        locationPairs = IOUtils.readTrimmedLines(getClass().getResource(fileName), locationPairParser);
        return this;
    }

    public long part1() {
        List<Integer> leftList = locationPairs.stream().map(LocationPair::left).sorted().toList();
        List<Integer> rightList = locationPairs.stream().map(LocationPair::right).sorted().toList();
        if (leftList.size() != rightList.size()) {
            throw new IllegalStateException("List size mismatch: %d - %d".formatted(leftList.size(), rightList.size()));
        }
        long totalDistence = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistence += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return totalDistence;
    }

    public long part2() {
        Map<Integer, List<Integer>> rightGroups = locationPairs.stream()
                .map(LocationPair::right)
                .collect(Collectors.groupingBy(Function.identity()));
        return locationPairs.stream()
                .mapToInt(LocationPair::left)
                .map(location -> location * rightGroups.getOrDefault(location, List.of()).size())
                .sum();
    }
}
