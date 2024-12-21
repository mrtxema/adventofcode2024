package common.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DijkstraCalculator<T> {
    private final Graph<T> graph;

    public DijkstraCalculator(Graph<T> graph) {
        this.graph = graph;
    }

    public long calculateShortestDistanceFrom(T startNode) {
        return isReachable(startNode).orElseThrow(() -> new IllegalStateException("No solution found"));
    }

    public OptionalLong isReachable(T startNode) {
        var queue = new GraphDistanceCalculationQueue<>(graph.getNodeComparator());
        queue.add(startNode, 0);
        while (queue.isNotEmpty()) {
            T current = queue.pop();
            var currentDistance = queue.getDistance(current);
            if (graph.isEndNode(current)) {
                return OptionalLong.of(currentDistance);
            }
            for (Map.Entry<T, Long> entry : graph.getAdjacentNodesWithDistance(current).entrySet()) {
                T candidate = entry.getKey();
                long newCost = currentDistance + entry.getValue();
                if (newCost < queue.getDistance(candidate)) {
                    queue.add(candidate, newCost);
                }
            }
        }
        return OptionalLong.empty();
    }

    public Set<T> getAllShortestPathsNodesFrom(T startNode) {
        var queue = new GraphDistanceCalculationQueue<>(graph.getNodeComparator());
        return getAllShortestPathsNodesFrom(startNode, queue);
    }

    public Set<T> getAllShortestPathsNodesFrom(T startNode, GraphDistanceCalculationQueue<T> queue) {
        var paths = new HashMap<T, List<NodePath<T>>>();
        queue.add(startNode, 0);
        paths.put(startNode, new ArrayList<>(List.of(new NodePath<>(List.of(startNode)))));
        while (queue.isNotEmpty()) {
            var current = queue.pop();
            var currentDistance = queue.getDistance(current);
            if (graph.isEndNode(current)) {
                return paths.get(current).stream()
                        .flatMap(nodePath -> nodePath.states().stream())
                        .collect(Collectors.toSet());
            }
            for (var entry : graph.getAdjacentNodesWithDistance(current).entrySet()) {
                var candidate = entry.getKey();
                long newCost = currentDistance + entry.getValue();
                if (newCost < queue.getDistance(candidate)) {
                    queue.add(candidate, newCost);
                    var newPaths = paths.get(current).stream().map(nodePath -> nodePath.concat(candidate)).toList();
                    paths.put(candidate, new ArrayList<>(newPaths));
                } else if (newCost == queue.getDistance(candidate)) {
                    var newPaths = paths.get(current).stream().map(nodePath -> nodePath.concat(candidate)).toList();
                    paths.get(candidate).addAll(newPaths);
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }


    private record NodePath<T>(List<T> states) {

        NodePath<T> concat(T node) {
            return new NodePath<>(Stream.concat(states.stream(), Stream.of(node)).toList());
        }
    }
}
