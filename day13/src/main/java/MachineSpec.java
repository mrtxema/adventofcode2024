import common.movement.Position;
import java.util.OptionalLong;

public record MachineSpec(Movement buttonAMovement, Movement buttonBMovement, Position prizePosition) {
    private static final double PRECISION = 0.0001;
    private static final int BUTTON_A_COST = 3;
    private static final int BUTTON_B_COST = 1;

    public long calculateTokensNeeded(long prizeDelta) {
        var aPushes = calculateButtonPushes(buttonAMovement, buttonBMovement.factor(), prizeDelta);
        var bPushes = calculateButtonPushes(buttonBMovement, buttonAMovement.factor(), prizeDelta);
        if (aPushes.isPresent() && bPushes.isPresent()) {
            return aPushes.getAsLong() * BUTTON_A_COST + bPushes.getAsLong() * BUTTON_B_COST;
        }
        return 0;
    }

    private OptionalLong calculateButtonPushes(Movement buttonMovement, double otherButtonFactor, long prizeDelta) {
        double divisor = buttonMovement.deltaX() - buttonMovement.deltaY() * otherButtonFactor;
        if (divisor == 0d) {
            throw new IllegalArgumentException("Equivalent equations found for machine " + this);
        }
        long prizeX = prizePosition.x() + prizeDelta;
        long prizeY = prizePosition.y() + prizeDelta;
        double calc = (prizeX - prizeY * otherButtonFactor) / divisor;
        long pushes = Math.round(calc);
        if (Math.abs(pushes - calc) < PRECISION) {
            return OptionalLong.of(pushes);
        }
        return OptionalLong.empty();
    }
}
