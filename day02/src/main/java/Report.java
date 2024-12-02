import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public record Report(List<Integer> levels) {

    public boolean isSafe() {
        return checkSafety(levels).isSafe();
    }

    public boolean isSafeWithDampener() {
        var result = checkSafety(levels);
        if (result.isSafe()) {
            return true;
        }

        // Apply dampener
        var invalidIndices = new TreeSet<>(result.invalidDiffIndices());
        if (result.increasingIndices().size() < result.decreasingIndices().size()) {
            invalidIndices.addAll(result.increasingIndices());
        } else {
            invalidIndices.addAll(result.decreasingIndices());
        }
        if (invalidIndices.size() > 2) {
            return false;
        }
        invalidIndices.add(invalidIndices.first() - 1);
        for (int index : invalidIndices) {
            var levelsCopy = new ArrayList<>(levels);
            levelsCopy.remove(index);
            if (checkSafety(levelsCopy).isSafe()) {
                return true;
            }
        }
        return false;
    }

    private static SafetyResult checkSafety(List<Integer> levelValues) {
        List<Integer> increasingIndices = new ArrayList<>();
        List<Integer> decreasingIndices = new ArrayList<>();
        List<Integer> invalidDiffIndices = new ArrayList<>();
        for (int i = 1; i < levelValues.size(); i++) {
            int diff = levelValues.get(i) - levelValues.get(i - 1);
            int absDiff = Math.abs(diff);
            if (absDiff < 1 || absDiff > 3) {
                invalidDiffIndices.add(i);
            } else {
                switch (Signum.fromInteger(diff)) {
                    case POSITIVE -> increasingIndices.add(i);
                    case NEGATIVE -> decreasingIndices.add(i);
                }
            }
        }
        return new SafetyResult(increasingIndices, decreasingIndices, invalidDiffIndices);
    }

    private record SafetyResult(List<Integer> increasingIndices, List<Integer> decreasingIndices,
                                List<Integer> invalidDiffIndices) {

        public boolean isSafe() {
            return (increasingIndices.isEmpty() || decreasingIndices.isEmpty()) && invalidDiffIndices.isEmpty();
        }
    }
}
