import common.parser.IOUtils;

public class Solver {
    private final String fileName;
    private String denseMap;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        denseMap = IOUtils.readTrimmedLines(getClass().getResource(fileName)).get(0);
        return this;
    }

    public long part1() {
        var diskMap = DiskMap.fromDenseMap(denseMap);
        diskMap.compactBlocks();
        return diskMap.checksum();
    }

    public long part2() {
        var diskMap = DiskMap.fromDenseMap(denseMap);
        diskMap.compactFiles();
        return diskMap.checksum();
    }
}
