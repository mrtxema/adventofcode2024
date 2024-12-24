import common.parser.IOUtils;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {
    private final String fileName;
    private final NetworkConnectionParser networkConnectionParser;
    private List<NetworkConnection> networkConnections;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.networkConnectionParser = new NetworkConnectionParser();
    }

    public Solver parseFile() {
        networkConnections = IOUtils.readTrimmedLines(getClass().getResource(fileName), networkConnectionParser);
        return this;
    }

    public long part1() {
        return new NetworkMap(networkConnections).getGroupsOfThree(computer -> computer.startsWith("t")).size();
    }

    public String part2() {
        return new NetworkMap(networkConnections).getLargestGroup().stream().sorted().collect(Collectors.joining(","));
    }
}
