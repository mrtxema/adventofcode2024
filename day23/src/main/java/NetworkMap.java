import common.math.CombinationGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NetworkMap {
    private final Map<String, Set<String>> connections;

    public NetworkMap(List<NetworkConnection> connectionList) {
        connections = new HashMap<>();
        for (var connection : connectionList) {
            connections.computeIfAbsent(connection.computer1(), c -> new HashSet<>(Set.of(c))).add(connection.computer2());
            connections.computeIfAbsent(connection.computer2(), c -> new HashSet<>(Set.of(c))).add(connection.computer1());
        }
    }

    public Set<Set<String>> getGroupsOfThree(Predicate<String> filter) {
        return connections.entrySet().stream()
                .filter(entry -> filter.test(entry.getKey()))
                .flatMap(entry -> getGroupsOfSize(entry.getValue(), 3).filter(group -> group.contains(entry.getKey())))
                .collect(Collectors.toSet());
    }

    private Stream<Set<String>> getGroupsOfSize(Set<String> connectedSet, int size) {
        if (connectedSet.size() < size) {
            return Stream.of();
        }
        Set<Set<String>> result = new HashSet<>();
        var generator = new CombinationGenerator<>(size, new ArrayList<>(connectedSet));
        while (generator.hasNext()) {
            var candidateGroup = new HashSet<>(generator.next());
            if (candidateGroup.size() == size && areCompletelyConnected(candidateGroup)) {
                result.add(candidateGroup);
            }
        }
        return result.stream();
    }

    private boolean areCompletelyConnected(Set<String> group) {
        return group.stream().allMatch(node -> connections.getOrDefault(node, Set.of()).containsAll(group));
    }

    public Set<String> getLargestGroup() {
        Set<String> maxClique = new HashSet<>();
        findMaxClique(new ArrayList<>(), new ArrayList<>(connections.keySet()), maxClique);
        return maxClique;
    }

    private void findMaxClique(List<String> potentialClique, List<String> remainingNodes, Set<String> maxClique) {
        if (remainingNodes.isEmpty()) {
            if (potentialClique.size() > maxClique.size()) {
                maxClique.clear();
                maxClique.addAll(potentialClique);
            }
            return;
        }

        if (potentialClique.size() + remainingNodes.size() <= maxClique.size()) {
            return;
        }

        String currentNode = remainingNodes.get(0);
        List<String> restOfNodes = remainingNodes.subList(1, remainingNodes.size());

        if (connections.getOrDefault(currentNode, Set.of()).containsAll(potentialClique)) {
            List<String> newPotentialClique = new ArrayList<>(potentialClique);
            newPotentialClique.add(currentNode);
            findMaxClique(newPotentialClique, restOfNodes, maxClique);
        }

        findMaxClique(potentialClique, restOfNodes, maxClique);
    }
}
