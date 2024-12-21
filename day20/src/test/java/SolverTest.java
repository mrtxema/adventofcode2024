import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        int expectedCheats = 0;
        assertThat(initSolver(64).part1()).isEqualTo(expectedCheats += 1);
        assertThat(initSolver(40).part1()).isEqualTo(expectedCheats += 1);
        assertThat(initSolver(38).part1()).isEqualTo(expectedCheats += 1);
        assertThat(initSolver(36).part1()).isEqualTo(expectedCheats += 1);
        assertThat(initSolver(20).part1()).isEqualTo(expectedCheats += 1);
        assertThat(initSolver(12).part1()).isEqualTo(expectedCheats += 3);
        assertThat(initSolver(10).part1()).isEqualTo(expectedCheats += 2);
        assertThat(initSolver(8).part1()).isEqualTo(expectedCheats += 4);
        assertThat(initSolver(6).part1()).isEqualTo(expectedCheats += 2);
        assertThat(initSolver(4).part1()).isEqualTo(expectedCheats += 14);
        assertThat(initSolver(2).part1()).isEqualTo(expectedCheats += 14);
    }

    @Test
    void testPart2() {
        int expectedCheats = 0;
        assertThat(initSolver(76).part2()).isEqualTo(expectedCheats += 3);
        assertThat(initSolver(74).part2()).isEqualTo(expectedCheats += 4);
        assertThat(initSolver(72).part2()).isEqualTo(expectedCheats += 22);
        assertThat(initSolver(70).part2()).isEqualTo(expectedCheats += 12);
        assertThat(initSolver(68).part2()).isEqualTo(expectedCheats += 14);
        assertThat(initSolver(66).part2()).isEqualTo(expectedCheats += 12);
        assertThat(initSolver(64).part2()).isEqualTo(expectedCheats += 19);
        assertThat(initSolver(62).part2()).isEqualTo(expectedCheats += 20);
        assertThat(initSolver(60).part2()).isEqualTo(expectedCheats += 23);
        assertThat(initSolver(58).part2()).isEqualTo(expectedCheats += 25);
        assertThat(initSolver(56).part2()).isEqualTo(expectedCheats += 39);
        assertThat(initSolver(54).part2()).isEqualTo(expectedCheats += 29);
        assertThat(initSolver(52).part2()).isEqualTo(expectedCheats += 31);
        assertThat(initSolver(50).part2()).isEqualTo(expectedCheats += 32);
    }

    private Solver initSolver(int minPicoSeconds) {
        return new Solver("test.txt", minPicoSeconds).parseFile();
    }
}
