import common.parser.IOUtils;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
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
        List<SequenceInfo> sequenceInfoList = priceInfos.stream()
                .flatMap(priceInfo -> priceInfo.generateSequences().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.mapping(Function.identity(), Collectors.counting())))
                .entrySet().stream()
                .map(entry -> new SequenceInfo(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(SequenceInfo::count).reversed())
                .toList();

        long maxBananas = 0;
        for (var sequenceInfo : sequenceInfoList) {
            if (sequenceInfo.count() * 9 < maxBananas) {
                return maxBananas;
            }
            var bananas = priceInfos.stream().mapToLong(priceInfo -> priceInfo.tradeBananas(sequenceInfo.sequence())).sum();
            maxBananas = Math.max(maxBananas, bananas);
        }
        return maxBananas;
    }

    private record SequenceInfo(List<Integer> sequence, long count) {
    }
}
