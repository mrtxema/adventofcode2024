import common.graph.DijkstraCalculator;
import common.graph.Graph;
import common.graph.GraphDistanceCalculationQueue;
import common.movement.Direction;
import common.movement.Position;
import common.parser.CharGrid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RaceTrack {
    private final CharGrid map;
    private final Position startNode;

    public RaceTrack(CharGrid map) {
        this.map = map;
        this.startNode = map.findPosition(RaceTrackGraph.START).orElseThrow();
    }

    public long countCheats(int minSavedDistance, int maxCheatDistance) {
        var graph = new RaceTrackGraph(map);
        var queue = new GraphDistanceCalculationQueue<>(graph.getNodeComparator());
        var nodes = new ArrayList<>(new DijkstraCalculator<>(graph).getAllShortestPathsNodesFrom(startNode, queue));
        long cheats = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            var node1 = nodes.get(i);
            var dist1 = queue.getDistance(node1);
            for (int j = i + 1; j < nodes.size(); j++) {
                var node2 = nodes.get(j);
                var dist2 = queue.getDistance(node2);
                var straightDist = calculateStraightDistance(node1, node2);
                var savedDist = Math.abs(dist1 - dist2) - straightDist;
                if (straightDist <= maxCheatDistance && savedDist >= minSavedDistance) {
                    cheats++;
                }
            }
        }
        return cheats;
    }

    private int calculateStraightDistance(Position position1, Position position2) {
        return Math.abs(position1.x() - position2.x()) + Math.abs(position1.y() - position2.y());
    }

    private static class RaceTrackGraph implements Graph<Position> {
        static final char WALL = '#';
        static final char START = 'S';
        static final char END = 'E';
        private final CharGrid map;

        RaceTrackGraph(CharGrid map) {
            this.map = map;
        }

        @Override
        public boolean isEndNode(Position node) {
            return getChar(node) == END;
        }

        @Override
        public Map<Position, Long> getAdjacentNodesWithDistance(Position node) {
            return Direction.straight().stream()
                    .map(d -> d.move(node))
                    .filter(map::isInBounds)
                    .filter(this::isValidPosition)
                    .collect(Collectors.toMap(Function.identity(), s -> 1L));
        }

        @Override
        public Comparator<Position> getNodeComparator() {
            return Comparator.naturalOrder();
        }

        private boolean isValidPosition(Position p) {
            return getChar(p) != WALL;
        }

        private char getChar(Position position) {
            return map.getChar(position).orElseThrow();
        }
    }
}
