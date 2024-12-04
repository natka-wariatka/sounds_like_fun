import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CoinManager {
    private ArrayList<Coin> coins;
    private Image coinImage;

    public CoinManager() {
        this.coins = new ArrayList<>();
        this.coinImage = new ImageIcon(getClass().getResource("/moneta.png")).getImage();
    }

    public void update() {
        for (Iterator<Coin> iterator = coins.iterator(); iterator.hasNext();) {
            Coin coin = iterator.next();
            coin.update();
            if (coin.isOutOfScreen()) {
                iterator.remove();
            }
        }

        // losowe dodawanie monety do zebrania
        if (new Random().nextInt(100) < 2) {
            coins.add(new Coin(coinImage));
        }
    }

    public void draw(Graphics g) {
        for (Coin coin : coins) {
            coin.draw(g);
        }
    }

    public boolean isCoinCollected(Dog dog) {
        for (Iterator<Coin> iterator = coins.iterator(); iterator.hasNext();) {
            Coin coin = iterator.next();
            if (coin.getBounds().intersects(dog.getBounds())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}

class Coin {
    private int x, y;
    private Image image;
    private final int SPEED = 5;

    public Coin(Image image) {
        this.x = 800;
        this.y = 250 + new Random().nextInt(50); // Losowa wysokość
        this.image = image;
    }

    public void update() {
        x -= SPEED;
    }

    public boolean isOutOfScreen() {
        return x + image.getWidth(null) < 0;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 20, 20, null);
    }
// do czego służą te funkcję idk tak w sumie pisze to bo chłop robi foty
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }
}
