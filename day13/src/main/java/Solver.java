import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private final MachineSpecParser machineSpecParser;
    private List<MachineSpec> machineSpecs;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.machineSpecParser = new MachineSpecParser();
    }

    public Solver parseFile() {
        machineSpecs = IOUtils.splitSections(IOUtils.readTrimmedLines(getClass().getResource(fileName))).stream()
                .map(machineSpecParser::parse)
                .toList();
        return this;
    }

    public long part1() {
        return machineSpecs.stream().mapToLong(machine -> machine.calculateTokensNeeded(0)).sum();
    }

    public long part2() {
        return machineSpecs.stream().mapToLong(machine -> machine.calculateTokensNeeded(10000000000000L)).sum();
    }
}
