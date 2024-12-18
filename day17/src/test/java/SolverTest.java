import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        String result = initSolver("test.txt").part1();
        assertThat(result).isEqualTo("4,6,3,5,6,3,5,2,1,0");
    }

    @Test
    void testPart2() {
        assertThat(initSolver("test2.txt").part2()).isEqualTo(117440);
        assertThat(initSolver("input.txt").part2()).isEqualTo(202322936867370L);
    }

    private Solver initSolver(String fileName) {
        return new Solver(fileName).parseFile();
    }
}
