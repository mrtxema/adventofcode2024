import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VisualSimulation extends JPanel {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static final int PIXEL_SIZE = 8;
    private final RobotMap robotMap;
    private int movements = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VisualSimulation::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("Day 14 simulation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new VisualSimulation());
        f.pack();
        f.setVisible(true);
    }

    public VisualSimulation() {
        this.robotMap = new Solver(INPUT_FILE_NAME, WIDTH, HEIGHT).parseFile().getRobotMap();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                SwingUtilities.invokeLater(() -> findTree());
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * PIXEL_SIZE,HEIGHT * PIXEL_SIZE);
    }

    private void findTree() {
        movements = robotMap.findEasterEggSeconds();
        robotMap.simulateMovements(movements);
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        robotMap.getRobots().forEach(robot -> paintRobot(g, robot));
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("Movements: " + movements, 20, 20);
    }

    private void paintRobot(Graphics g, Robot robot) {
        g.setColor(Color.RED);
        g.fillRect(robot.position().x() * PIXEL_SIZE, robot.position().y() * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }
}
