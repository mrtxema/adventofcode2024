import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record PriceInfo(List<Integer> prices, List<Integer> diffs) {

    public List<SequenceInfo> generateSequences() {
        Set<List<Integer>> sequences = new HashSet<>();
        List<SequenceInfo> result = new ArrayList<>();
        for (var j = 4; j < diffs.size(); j++) {
            var sequence = diffs.subList(j - 4, j);
            if (sequences.add(sequence)) {
                result.add(new SequenceInfo(diffs.subList(j - 4, j), prices.get(j)));
            }
        }
        return result;
    }
}
