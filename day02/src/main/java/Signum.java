public enum Signum {
    NEGATIVE,
    ZERO,
    POSITIVE;

    public static Signum fromInteger(int number) {
        if (number == 0) {
            return ZERO;
        } else if (number < 0) {
            return NEGATIVE;
        } else {
            return POSITIVE;
        }
    }
}
