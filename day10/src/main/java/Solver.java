import common.parser.IOUtils;
import common.parser.StringUtils;

public class Solver {
    private final String fileName;
    private TopographicMap map;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        int[][] heights = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                .map(line -> StringUtils.characters(line).mapToInt(c -> c - '0').toArray())
                .toArray(int[][]::new);
        map = new TopographicMap(heights);
        return this;
    }

    public long part1() {
        return map.getTrailheads().stream().mapToInt(map::getTrailheadScore).sum();
    }

    public long part2() {
        return map.getTrailheads().stream().mapToInt(map::getTrailheadRating).sum();
    }
}
