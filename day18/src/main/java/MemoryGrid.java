import common.graph.DijkstraCalculator;
import common.graph.Graph;
import common.movement.Direction;
import common.movement.Position;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MemoryGrid implements Graph<Position> {
    private final Set<Position> bytePositions;
    private final int gridSize;
    private final Position endPosition;

    public MemoryGrid(Collection<Position> bytePositions, int gridSize) {
        this.bytePositions = new HashSet<>(bytePositions);
        this.gridSize = gridSize;
        this.endPosition = new Position(gridSize - 1, gridSize - 1);
    }

    public long getShortestPathLength() {
        var startPosition = new Position(0, 0);
        return new DijkstraCalculator<>(this).calculateShortestDistanceFrom(startPosition);
    }

    public boolean isReachable() {
        var startPosition = new Position(0, 0);
        return new DijkstraCalculator<>(this).isReachable(startPosition).isPresent();
    }

    @Override
    public boolean isEndNode(Position node) {
        return node.equals(endPosition);
    }

    @Override
    public Map<Position, Long> getAdjacentNodesWithDistance(Position node) {
        return Direction.straight().stream()
                .map(direction -> direction.move(node))
                .filter(this::isValidPosition)
                .collect(Collectors.toMap(Function.identity(), p -> 1L));
    }

    private boolean isValidPosition(Position position) {
        return !bytePositions.contains(position)
                && position.x() >= 0
                && position.x() < gridSize
                && position.y() >= 0
                && position.y() < gridSize;
    }

    @Override
    public Comparator<Position> getNodeComparator() {
        return Comparator.naturalOrder();
    }
}
