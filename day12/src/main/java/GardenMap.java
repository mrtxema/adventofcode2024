import common.movement.Direction;
import common.movement.Position;
import common.parser.CharGrid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GardenMap {
    private final CharGrid charGrid;

    public GardenMap(CharGrid charGrid) {
        this.charGrid = charGrid;
    }

    public List<Region> findAllRegions() {
        var positions = charGrid.getAllPositions().collect(Collectors.toSet());
        List<Region> regions = new ArrayList<>();
        while (!positions.isEmpty()) {
            var region = findRegionFromPlot(positions.iterator().next());
            regions.add(region);
            positions.removeAll(region.plots());
        }
        return regions;
    }

    private Region findRegionFromPlot(Position plot) {
        char plantType = charGrid.getChar(plot).orElseThrow();
        Set<Position> plots = new HashSet<>();
        completeRegion(plots, plot, plantType);
        return new Region(plots);
    }

    private void completeRegion(Set<Position> plots, Position plot, char plantType) {
        plots.add(plot);
        Direction.straight().stream()
                .map(direction -> direction.move(plot))
                .filter(neighbor -> charGrid.getChar(neighbor).filter(t -> t == plantType).isPresent())
                .filter(neighbor -> !plots.contains(neighbor))
                .forEach(neighbor -> completeRegion(plots, neighbor, plantType));
    }
}
