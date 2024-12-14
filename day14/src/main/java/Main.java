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
        Solver solver = new Solver(INPUT_FILE_NAME, 101, 103).parseFile();
        System.out.println("[Part 1] Safety factor: " + solver.part1());
        System.out.println("[Part 2] Easter egg seconds: " + solver.part2());
    }
}
