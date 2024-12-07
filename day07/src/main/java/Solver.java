import common.math.CombinationGenerator;
import common.parser.IOUtils;

import java.util.Arrays;
import java.util.List;

public class Solver {
    private final String fileName;
    private final EquationParser equationParser;
    private List<Equation> equations;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.equationParser = new EquationParser();
    }

    public Solver parseFile() {
        equations = IOUtils.readTrimmedLines(getClass().getResource(fileName), equationParser);
        return this;
    }

    public long part1() {
        var operators = List.of(Operator.ADD, Operator.MULTIPLY);
        return equations.stream().filter(eq -> canBeSatisfied(eq, operators)).mapToLong(Equation::result).sum();
    }

    public long part2() {
        var operators = Arrays.asList(Operator.values());
        return equations.stream().filter(eq -> canBeSatisfied(eq, operators)).mapToLong(Equation::result).sum();
    }

    private boolean canBeSatisfied(Equation equation, List<Operator> operators) {
        var generator = new CombinationGenerator<>(equation.getOperatorCount(), operators);
        while (generator.hasNext()) {
            if (equation.isSatisfied(generator.next())) {
                return true;
            }
        }
        return false;
    }
}
