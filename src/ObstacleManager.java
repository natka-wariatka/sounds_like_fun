import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private Image birdImage;
    private Image logImage;
    private int spawnCounter = 0; // czas pomiędzy przeszkodami
    private final int MIN_SPAWN_INTERVAL = 50; // minimalna liczba klatek pomiędzy przeszkodami

    public ObstacleManager() {
        this.obstacles = new ArrayList<>();
        this.birdImage = new ImageIcon(getClass().getResource("/ptak.png")).getImage(); //chmura
        this.logImage = new ImageIcon(getClass().getResource("/kloda.png")).getImage(); //kłoda
    }

    public void update() {
        spawnCounter++;

        for (Iterator<Obstacle> iterator = obstacles.iterator(); iterator.hasNext();) {
            Obstacle obstacle = iterator.next();
            obstacle.update();
            if (obstacle.isOutOfScreen()) {
                iterator.remove(); // usuwanie przeszkód poza ekranem
            }
        }

        // tworzenie nowej przeszkody co losową liczbę klatek po minięciu minimalnego odstępu
        if (spawnCounter >= MIN_SPAWN_INTERVAL && new Random().nextInt(100) < 10) {
            obstacles.add(new Obstacle(birdImage, logImage));
            spawnCounter = 0; // reset czasu pomiędzy przeszkodami
        }
    }

    public void draw(Graphics g) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
    }

    public boolean isCollision(Dog dog) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(dog.getBounds())) {
                return true;
            }
        }
        return false;
    }
}

class Obstacle {
    private int x, y;
    private Image image;
    private final int SPEED = 5;

    public Obstacle(Image birdImage, Image logImage) {
        this.x = 800;

        // losowanie przeszkody
        if (new Random().nextBoolean()) {
            this.image = birdImage;
            this.y = 180; // ustawienie wysokości chmury
        } else {
            this.image = logImage;
            this.y = 300 - 30; // ustawienie wysokości kłody
        }
    }

    public void update() {
        x -= SPEED;
    }

    public boolean isOutOfScreen() {
        return x + image.getWidth(null) < 0;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 50, 30, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 30);
    }
}
