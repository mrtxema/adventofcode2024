import java.util.List;
import java.util.Map;
import java.util.Set;

public record PrintingInstructions(Map<Integer, Set<Integer>> rules, List<UpdatePages> updatePagesList) {
}

record UpdatePages(List<Integer> pages) {
}
