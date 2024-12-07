import java.util.List;

public record Equation(long result, List<Long> operands) {

    public int getOperatorCount() {
        return operands.size() - 1;
    }

    public boolean isSatisfied(List<Operator> operators) {
        if (operators.size() != operands.size() - 1) {
            throw new IllegalArgumentException("Too few operators: " + operators);
        }
        var calculation = operands.get(0);
        for (var i = 0; i < operators.size(); i++) {
            calculation = operators.get(i).apply(calculation, operands.get(i + 1));
        }
        return calculation == result;
    }
}
