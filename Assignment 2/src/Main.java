import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;

public class Main extends JFrame implements Runnable {

    private class Canvas extends JPanel implements KeyListener {

        private Stage stage;

        public Canvas() {
            // Setting dimension of graphics panel.
            setPreferredSize(new Dimension(1280, 720));
            // Making instance of Stage.
            stage = new Stage();
        }

        @Override
        public void paint(Graphics g) {
            // Updates changes that happens to the stage.
            stage.update();
            // Constantly paints the stage.
            stage.paint(g, getMousePosition());
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Notifies all "registered" entities about key typed.
            stage.notifyAll(e.getKeyChar(), stage.grid);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        // Not used.
        }

        @Override
        public void keyReleased(KeyEvent e) {
        // Not used.
        }
    }

    public static void main(String[] args) {
        Main window = new Main();
        window.run();
    }

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        this.setContentPane(canvas);
        this.addKeyListener(canvas);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void run() {
        // Paints the screen 50 times a second.
        while (true) {
            Instant startTime = Instant.now();
            this.repaint();
            Instant endTime = Instant.now();
            try {
                Thread.sleep(20 - Duration.between(startTime, endTime).toMillis());
            } catch (InterruptedException e) {
                System.out.println("thread was interrupted, but really, who cares?");
                System.out.println(e.toString());
            }
        }
    }
}
