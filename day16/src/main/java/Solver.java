import common.parser.CharGrid;
import common.parser.IOUtils;

public class Solver {
    private final String fileName;
    private ReindeerMaze reindeerMaze;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        reindeerMaze = new ReindeerMaze(new CharGrid(IOUtils.readCharMap(getClass().getResource(fileName))));
        return this;
    }

    public long part1() {
        return reindeerMaze.getLowestScorePath();
    }

    public long part2() {
        return reindeerMaze.countNodesInLowestScorePaths();
    }
}
