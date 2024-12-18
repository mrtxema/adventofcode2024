import instructions.Register;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReverseEngineeringExecutor {
    private final ExecutionSpec executionSpec;

    public ReverseEngineeringExecutor(ExecutionSpec executionSpec) {
        this.executionSpec = executionSpec;
    }

    public long findInput() {
        return reverseEngineer();
    }

    public long reverseEngineer() {
        Set<Long> validValuesForA = Set.of(0L);
        for (int i = executionSpec.program().size() - 1; i >= 0; i--) {
            int requiredValue = executionSpec.program().get(i);
            Set<Long> nextValidValuesForA = new HashSet<>();
            for (long validValue : validValuesForA) {
                long aShifted = validValue << 3;
                for (long candidateA = aShifted; candidateA < aShifted + 8; candidateA++) {
                    List<Integer> out = runProgramForA(candidateA);
                    if (!out.isEmpty() && out.get(0) == requiredValue) {
                        nextValidValuesForA.add(candidateA);
                    }
                }
            }
            validValuesForA = nextValidValuesForA;
        }
        return Collections.min(validValuesForA);
    }

    private List<Integer> runProgramForA(long candidateA) {
        var executor = new ProgramExecutor(executionSpec);
        executor.set(Register.A, candidateA);
        executor.run();
        return executor.getOutputs();
    }
}
