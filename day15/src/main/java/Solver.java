import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private final WarehouseParser warehouseParser;
    private List<String> lines;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.warehouseParser = new WarehouseParser();
    }

    public Solver parseFile() {
        lines = IOUtils.readTrimmedLines(getClass().getResource(fileName));
        return this;
    }

    public long part1() {
        Warehouse warehouse = warehouseParser.parse(lines);
        return warehouse.simulate().getBoxesPositions().stream().mapToLong(p -> p.y() * 100L + p.x()).sum();
    }

    public long part2() {
        Warehouse warehouse = warehouseParser.parseScaledUp(lines);
        return warehouse.simulate().getBoxesPositions().stream().mapToLong(p -> p.y() * 100L + p.x()).sum();
    }
}
