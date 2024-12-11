import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedStone {
    private final Map<Integer, Long> stoneCountByBlink;
    private int lastBlinkCount;
    private StoneArrangement lastArrangement;

    public CachedStone(long value) {
        this.lastBlinkCount = 0;
        this.lastArrangement = new StoneArrangement(List.of(new BasicStone(value)));
        this.stoneCountByBlink = new ConcurrentHashMap<>(Map.of(0, 1L));
    }

    public long blink(StoneFactory stoneFactory, int blinkCount) {
        while (lastBlinkCount < blinkCount) {
            lastArrangement = lastArrangement.blink(stoneFactory);
            lastBlinkCount++;
            stoneCountByBlink.put(lastBlinkCount, lastArrangement.countStones(stoneFactory));
        }
        return stoneCountByBlink.get(blinkCount);
    }
}
