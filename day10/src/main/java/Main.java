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
        Solver solver = new Solver(INPUT_FILE_NAME).parseFile();
        System.out.println("[Part 1] Trailhead score sum: " + solver.part1());
        System.out.println("[Part 2] Trailhead rating sum: " + solver.part2());
    }
}
