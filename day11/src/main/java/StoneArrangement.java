import java.util.ArrayList;
import java.util.List;

public record StoneArrangement(List<Stone> stones) {

    public StoneArrangement blink(StoneFactory stoneFactory) {
        List<Stone> newStones = new ArrayList<>();
        for (Stone stone : stones) {
            newStones.addAll(stone.blink(stoneFactory));
        }
        return new StoneArrangement(newStones);
    }

    public long countStones(StoneFactory stoneFactory) {
        return stones.stream().mapToLong(stone -> stone.count(stoneFactory)).sum();
    }
}
