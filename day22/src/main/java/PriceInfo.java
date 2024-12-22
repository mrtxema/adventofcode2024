import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record PriceInfo(List<Integer> prices, List<Integer> diffs) {

    public int tradeBananas(List<Integer> sequence) {
        for (var j = 4; j < diffs.size(); j++) {
            if (diffs.subList(j - 4, j).equals(sequence)) {
                return prices.get(j);
            }
        }
        return 0;
    }

    public Set<List<Integer>> generateSequences() {
        Set<List<Integer>> result = new HashSet<>();
        for (var j = 4; j < diffs.size(); j++) {
            result.add(diffs.subList(j - 4, j));
        }
        return result;
    }
}
