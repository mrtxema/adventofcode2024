public record Movement(int deltaX, int deltaY) {

    public double factor() {
        return deltaX * 1.0d / deltaY;
    }
}
