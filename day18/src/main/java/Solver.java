import common.movement.Position;
import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private final int gridSize;
    private final int numBytes;
    private final PositionParser positionParser;
    private List<Position> positions;

    public Solver(String fileName, int gridSize, int numBytes) {
        this.fileName = fileName;
        this.gridSize = gridSize;
        this.numBytes = numBytes;
        this.positionParser = new PositionParser();
    }

    public Solver parseFile() {
        positions = IOUtils.readTrimmedLines(getClass().getResource(fileName), positionParser);
        return this;
    }

    public long part1() {
        var simulationPositions = positions.size() > numBytes ? positions.subList(0, numBytes) : positions;
        return new MemoryGrid(simulationPositions, gridSize).getShortestPathLength();
    }

    public String part2() {
        for (int index = numBytes; index < positions.size(); index++) {
            if (!new MemoryGrid(positions.subList(0, index + 1), gridSize).isReachable()) {
                var blockingBytePosition = positions.get(index);
                return "%d,%d".formatted(blockingBytePosition.x(), blockingBytePosition.y());
            }
        }
        throw new IllegalStateException("No solution found");
    }
}
