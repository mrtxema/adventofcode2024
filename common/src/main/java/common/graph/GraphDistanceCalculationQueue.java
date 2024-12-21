package common.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GraphDistanceCalculationQueue<T> {
    private static final long INFINITE_DISTANCE = Long.MAX_VALUE / 2;
    private final Map<T, Long> distances;
    private final TreeSet<WeightedValue<T>> queue;

    public GraphDistanceCalculationQueue(Comparator<T> nodeComparator) {
        this.distances = new HashMap<>();
        this.queue = new TreeSet<>(WeightedValue.comparator(nodeComparator));
    }

    void add(T state, long distance) {
        distances.put(state, distance);
        queue.add(new WeightedValue<>(state, distance));
    }

    boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    T pop() {
        WeightedValue<T> value = queue.pollFirst();
        return value != null ? value.value() : null;
    }

    public long getDistance(T state) {
        return distances.getOrDefault(state, INFINITE_DISTANCE);
    }

    private record WeightedValue<T>(T value, long weight) {

        static <T> Comparator<WeightedValue<T>> comparator(Comparator<T> nodeComparator) {
            return Comparator.<WeightedValue<T>>comparingLong(WeightedValue::weight)
                    .thenComparing(WeightedValue::value, nodeComparator);
        }
    }
}
