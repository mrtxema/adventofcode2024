import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionContext {
    private final boolean applySwitches;
    private final AtomicBoolean enabled;
    private final AtomicInteger value;

    public ExecutionContext(boolean applySwitches) {
        this.applySwitches = applySwitches;
        this.enabled = new AtomicBoolean(true);
        this.value = new AtomicInteger(0);
    }

    public void enable(boolean newState) {
        if (applySwitches) {
            enabled.set(newState);
        }
    }

    public void addValue(int increment) {
        if (enabled.get()) {
            value.addAndGet(increment);
        }
    }

    public int getValue() {
        return value.intValue();
    }
}
