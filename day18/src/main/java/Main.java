public class Main {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Solver solver = new Solver(INPUT_FILE_NAME, 71, 1024).parseFile();
        System.out.println("[Part 1] Number of steps: " + solver.part1());
        System.out.println("[Part 2] Coordinates: " + solver.part2());
    }
}
