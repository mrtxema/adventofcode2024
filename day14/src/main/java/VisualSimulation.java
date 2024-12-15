import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class VisualSimulation extends JPanel implements ActionListener {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static final int PIXEL_SIZE = 8;
    private final RobotMap robotMap;
    private final Timer timer;
    private final int easterEggSeconds;
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
        this.easterEggSeconds = robotMap.findEasterEggSeconds();
        this.timer = new Timer(10, this);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        timer.start();
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * PIXEL_SIZE,HEIGHT * PIXEL_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==timer && movements < easterEggSeconds) {
            robotMap.simulateMovements(++movements);
            repaint();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        IntStream.range(0, robotMap.getRobots().size()).forEach(i -> paintRobot(g, robotMap.getRobots().get(i), i));
        g.setColor(Color.WHITE);
        g.fillRect(15, 5, 200, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("Movements: " + movements, 30, 30);
    }

    private void paintRobot(Graphics g, Robot robot, int robotNumber) {
        var color = movements == easterEggSeconds ? Color. decode("#006600") : getRobotColor(robotNumber);
        g.setColor(color);
        g.fillRect(robot.position().x() * PIXEL_SIZE, robot.position().y() * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }

    private Color getRobotColor(int robotNumber) {
        int red = ((robotNumber >> 6) & 0x7) * 0x22;
        int green = ((robotNumber >> 3) & 0x7) * 0x22;
        int blue = (robotNumber & 0x7) * 0x22;
        return new Color(red, green, blue);
    }
}
