package common.graph;

import java.util.Comparator;
import java.util.Map;

public interface Graph<T> {

    boolean isEndNode(T node);

    Map<T, Long> getAdjacentNodesWithDistance(T node);

    Comparator<T> getNodeComparator();
}
