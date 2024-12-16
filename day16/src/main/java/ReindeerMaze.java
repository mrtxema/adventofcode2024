import common.graph.DijkstraCalculator;
import common.graph.Graph;
import common.movement.Direction;
import common.movement.Position;
import common.parser.CharGrid;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReindeerMaze implements Graph<ReindeerState> {
    private static final char START = 'S';
    private static final char END = 'E';
    private static final char WALL = '#';
    private static final long FORWARD_COST = 1;
    private static final long ROTATE_COST = 1000;
    private final CharGrid charGrid;
    private final Position startPosition;
    private final Position endPosition;

    public ReindeerMaze(CharGrid charGrid) {
        this.charGrid = charGrid;
        this.startPosition = charGrid.findPosition(START).orElseThrow();
        this.endPosition = charGrid.findPosition(END).orElseThrow();
    }

    public long getLowestScorePath() {
        var initialState = new ReindeerState(startPosition, Direction.EAST);
        return new DijkstraCalculator<>(this).calculateShortestDistanceFrom(initialState);
    }

    public long countNodesInLowestScorePaths() {
        var initialState = new ReindeerState(startPosition, Direction.EAST);
        return new DijkstraCalculator<>(this).getAllShortestPathsNodesFrom(initialState).stream()
                .map(ReindeerState::position)
                .distinct()
                .count();
    }

    @Override
    public boolean isEndNode(ReindeerState node) {
        return node.position().equals(endPosition);
    }

    @Override
    public Map<ReindeerState, Long> getAdjacentNodesWithDistance(ReindeerState node) {
        return Direction.straight().stream()
                .filter(d -> areNotOpposite(d, node.direction()))
                .map(d -> new ReindeerState(d.move(node.position()), d))
                .filter(state -> charGrid.getChar(state.position()).filter(c -> c != WALL).isPresent())
                .collect(Collectors.toMap(Function.identity(), state -> calculateCost(node, state)));
    }

    private boolean areNotOpposite(Direction d1, Direction d2) {
        var set = EnumSet.of(d1, d2);
        return !set.equals(EnumSet.of(Direction.NORTH, Direction.SOUTH))
                && !set.equals(EnumSet.of(Direction.EAST, Direction.WEST));
    }

    private long calculateCost(ReindeerState from, ReindeerState to) {
        return from.direction() == to.direction() ? FORWARD_COST : (ROTATE_COST + FORWARD_COST);
    }

    @Override
    public Comparator<ReindeerState> getNodeComparator() {
        return Comparator.comparing(ReindeerState::position).thenComparing(ReindeerState::direction);
    }
}
