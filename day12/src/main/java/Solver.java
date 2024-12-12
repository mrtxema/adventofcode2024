import common.parser.CharGrid;
import common.parser.IOUtils;

public class Solver {
    private final String fileName;
    private GardenMap gardenMap;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        gardenMap = new GardenMap(new CharGrid(IOUtils.readCharMap(getClass().getResource(fileName))));
        return this;
    }

    public long part1() {
        return gardenMap.findAllRegions().stream().mapToLong(region -> region.area() * region.perimeter()).sum();
    }

    public long part2() {
        return gardenMap.findAllRegions().stream().mapToLong(region -> region.area() * region.sides()).sum();
    }
}
