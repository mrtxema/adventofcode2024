import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        long result = initSolver().part1();
        assertThat(result).isEqualTo(22);
    }

    @Test
    void testPart2() {
        String result = initSolver().part2();
        assertThat(result).isEqualTo("6,1");
    }

    private Solver initSolver() {
        return new Solver("test.txt", 7, 12).parseFile();
    }
}
