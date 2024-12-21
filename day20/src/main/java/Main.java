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
        Solver solver = new Solver(INPUT_FILE_NAME, 100).parseFile();
        System.out.println("[Part 1] Cheat count: " + solver.part1());
        System.out.println("[Part 2] Cheat count: " + solver.part2());
    }
}
