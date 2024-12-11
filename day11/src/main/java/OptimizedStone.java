import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OptimizedStone implements Stone {
    private final CachedStone cachedStone;
    private final AtomicInteger blinkCounter;

    public OptimizedStone(CachedStone cachedStone) {
        this.cachedStone = cachedStone;
        this.blinkCounter = new AtomicInteger(0);
    }

    @Override
    public List<Stone> blink(StoneFactory stoneFactory) {
        cachedStone.blink(stoneFactory, blinkCounter.incrementAndGet());
        return List.of(this);
    }

    @Override
    public long count(StoneFactory stoneFactory) {
        return cachedStone.blink(stoneFactory, blinkCounter.get());
    }
}
