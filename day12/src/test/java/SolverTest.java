import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        assertThat(initSolver("test.txt").part1()).isEqualTo(140);
        assertThat(initSolver("test2.txt").part1()).isEqualTo(772);
        assertThat(initSolver("test3.txt").part1()).isEqualTo(1930);
    }

    @Test
    void testPart2() {
        assertThat(initSolver("test.txt").part2()).isEqualTo(80);
        assertThat(initSolver("test2.txt").part2()).isEqualTo(436);
        assertThat(initSolver("test3.txt").part2()).isEqualTo(1206);
        assertThat(initSolver("test4.txt").part2()).isEqualTo(236);
        assertThat(initSolver("test5.txt").part2()).isEqualTo(368);
    }

    private Solver initSolver(String filename) {
        return new Solver(filename).parseFile();
    }
}
