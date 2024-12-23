import common.parser.IOUtils;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {
    private final String fileName;
    private final SecretNumberGenerator generator;
    private List<Long> secretNumbers;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.generator = new SecretNumberGenerator();
    }

    public Solver parseFile() {
        secretNumbers = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream().map(Long::valueOf).toList();
        return this;
    }

    public long part1() {
        return secretNumbers.stream().mapToLong(initialNumber -> generator.generate(initialNumber, 2000)).sum();
    }

    public long part2() {
        var priceInfos = secretNumbers.stream().map(initialNumber -> generator.generatePrices(initialNumber, 2000)).toList();
        return priceInfos.stream()
                .flatMap(priceInfo -> priceInfo.generateSequences().stream())
                .collect(Collectors.groupingBy(SequenceInfo::sequence, Collectors.reducing(SequenceInfo.EMPTY, SequenceInfo::merge)))
                .values().stream()
                .max(Comparator.comparing(SequenceInfo::monkeys))
                .orElse(SequenceInfo.EMPTY)
                .monkeys();
    }
}
