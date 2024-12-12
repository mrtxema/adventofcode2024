import common.movement.Direction;
import common.movement.Position;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Region(Set<Position> plots) {

    public long area() {
        return plots.size();
    }

    public long perimeter() {
        return plots.stream().flatMap(this::getFenceSections).count();
    }

    public long sides() {
        return plots.stream()
                .flatMap(this::getFenceSections)
                .collect(Collectors.groupingBy(FenceSection::direction))
                .entrySet().stream()
                .mapToLong(entry -> countAdjacentGroups(entry.getValue(), entry.getKey()))
                .sum();
    }

    private Stream<FenceSection> getFenceSections(Position plot) {
        return Direction.straight().stream()
                .map(direction -> new FenceSection(plot, direction))
                .filter(fenceSection -> !plots.contains(fenceSection.neighbor()));
    }

    private long countAdjacentGroups(List<FenceSection> fenceSections, Direction direction) {
        Function<FenceSection, Integer> classifier = switch (direction) {
            case NORTH, SOUTH -> section -> section.plot().y();
            default -> section -> section.plot().x();
        };
        Function<FenceSection, Integer> positionFunction = switch (direction) {
            case NORTH, SOUTH -> section -> section.plot().x();
            default -> section -> section.plot().y();
        };
        return fenceSections.stream()
                .collect(Collectors.groupingBy(classifier))
                .values().stream()
                .map(alignedSections -> alignedSections.stream().map(positionFunction).sorted().toList())
                .mapToLong(this::countAdjacentGroups)
                .sum();
    }

    private long countAdjacentGroups(List<Integer> sortedPositions) {
        if (sortedPositions.isEmpty()) {
            return 0;
        }
        int lastPosition = sortedPositions.get(0);
        int index = 1;
        int groups = 1;
        while (index < sortedPositions.size()) {
            int newPosition = sortedPositions.get(index);
            if (newPosition != lastPosition + 1) {
                groups++;
            }
            lastPosition = newPosition;
            index++;
        }
        return groups;
    }

    record FenceSection(Position plot, Direction direction) {

        public Position neighbor() {
            return direction.move(plot);
        }
    }
}
