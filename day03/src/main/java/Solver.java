import common.parser.IOUtils;

import java.util.Collection;
import java.util.List;

public class Solver {
    private final String fileName;
    private final InstructionListParser parser;
    private List<List<Instruction>> multiplicationsList;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.parser = new InstructionListParser();
    }

    public Solver parseFile() {
        multiplicationsList = IOUtils.readTrimmedLines(getClass().getResource(fileName), parser);
        return this;
    }

    public long part1() {
        var context = new ExecutionContext(false);
        multiplicationsList.stream().flatMap(Collection::stream).forEach(instruction -> instruction.apply(context));
        return context.getValue();
    }

    public long part2() {
        var context = new ExecutionContext(true);
        multiplicationsList.stream().flatMap(Collection::stream).forEach(instruction -> instruction.apply(context));
        return context.getValue();
    }
}
