import common.parser.IOUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {
    private final String fileName;
    private PrintingInstructions printingInstructions;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        printingInstructions = new PrintingInstructionsParser()
                .parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        return this;
    }

    public long part1() {
        return printingInstructions.updatePagesList().stream()
                .map(UpdatePages::pages)
                .filter(this::isInRightOrder)
                .mapToInt(this::getMiddleNumber)
                .sum();
    }

    public long part2() {
        return printingInstructions.updatePagesList().stream()
                .map(UpdatePages::pages)
                .filter(pages -> !isInRightOrder(pages))
                .map(pages -> fixOrder(new ArrayList<>(), new HashSet<>(pages)))
                .mapToInt(this::getMiddleNumber)
                .sum();
    }

    private boolean isInRightOrder(List<Integer> pageList) {
        if (pageList.isEmpty()) {
            return true;
        }
        int lastPage = pageList.get(pageList.size() - 1);
        Set<Integer> afterPages = printingInstructions.rules().getOrDefault(lastPage, Set.of());
        if (pageList.stream().anyMatch(afterPages::contains)) {
            return false;
        }
        return isInRightOrder(pageList.subList(0, pageList.size() - 1));
    }

    private List<Integer> fixOrder(List<Integer> fixedPages, Set<Integer> pendingPages) {
        if (pendingPages.isEmpty()) {
            return fixedPages;
        }
        int nextPage = getNextPage(pendingPages);
        fixedPages.add(nextPage);
        pendingPages.remove(nextPage);
        return fixOrder(fixedPages, pendingPages);
    }

    private int getNextPage(Set<Integer> pendingPages) {
        return pendingPages.stream()
                .filter(page -> pendingPages.stream()
                        .noneMatch(otherPage ->
                                printingInstructions.rules().getOrDefault(otherPage, Set.of()).contains(page)))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Loop detected for pages " + pendingPages));
    }

    private int getMiddleNumber(List<Integer> pageList) {
        return pageList.get(pageList.size() / 2);
    }
}
