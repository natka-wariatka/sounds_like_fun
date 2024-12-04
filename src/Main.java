import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Dog dog;
    private ObstacleManager obstacleManager;
    private CoinManager coinManager;
    private boolean isCrouching = false;
    private int coinCount = 0;  // licznik zebranych monet
    private boolean gameOver = false;  // końca gry
    private JButton restartButton;  // przycisk restartu

    public Main() {
        this.dog = new Dog();
        this.obstacleManager = new ObstacleManager();
        this.coinManager = new CoinManager();

        /*// timer do pętli gry
        this.timer = new Timer(20, this);
        this.timer.start();*/

        // ustawienia panelu gry
        this.setFocusable(true);
        this.addKeyListener(this);

        // obsługa przycisku restartu
        restartButton = new JButton("Zacznij jeszcze raz");
        restartButton.setBounds(150, 150, 200, 50);
        restartButton.setVisible(false);  // na początku ukryty
        restartButton.addActionListener(e -> restartGame());
        this.add(restartButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // tło
        g.setColor(new Color(135, 206, 235)); // błękitny
        g.fillRect(0, 0, getWidth(), getHeight());

        // rysowanie elementów gry
        dog.draw(g);
        obstacleManager.draw(g);
        coinManager.draw(g);

        // wyświetlanie licznika zebranych monet
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Monety: " + coinCount, 10, 20);

        // jeśli koniec gry, wyświetl przycisk restartu
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Impact", 1, 30));
            g.drawString("Koniec gry!", 310, 100);
            restartButton.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            dog.update();
            obstacleManager.update();
            coinManager.update();
            checkCollisions();
            repaint();
        }
    }

    private void checkCollisions() {
        if (obstacleManager.isCollision(dog)) {
            System.out.println("Koniec gry!");
            gameOver = true;
            timer.stop();  // zkończenie gry
        }
        if (coinManager.isCoinCollected(dog)) {
            coinCount++; // dodanie monet
            System.out.println("Moneta zebrana!");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                dog.jump();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                dog.crouch();
                isCrouching = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            dog.standUp();
            isCrouching = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // restartowanie gry
    private void restartGame() {
        dog = new Dog();  // zresetowanie żyrafy
        obstacleManager = new ObstacleManager();  // zresetowanie przeszkód
        coinManager = new CoinManager();  // zresetowanie monet
        coinCount = 0;  // zresetowanie licznika monet
        gameOver = false;  // zmiana wskaźnika na początek gry
        timer.start();  // zrestartowanie timera
        restartButton.setVisible(false);  // ukrycie przycisku restartu
        repaint();  // przerysowanie panelu
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dino Piesek");
        Main game = new Main();
        frame.add(game);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
