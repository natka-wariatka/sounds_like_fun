import javax.swing.*;
import java.awt.*;

//żyrafa
public class Dog {
    private int x, y;
    private int velocityY = 0;
    private boolean isJumping = false;
    private boolean isCrouching = false;

    private final int STANDING_WIDTH = 70;
    private final int STANDING_HEIGHT = 140;
    private final int CROUCHING_WIDTH = 115;
    private final int CROUCHING_HEIGHT = 80;

    private Image standingImage, crouchingImage;

    public Dog() {
        this.x = 50;
        this.y = 300 - STANDING_HEIGHT; // ustawienia pozycji początkowej
        this.standingImage = new ImageIcon(getClass().getResource("/piesek.png")).getImage();
        this.crouchingImage = new ImageIcon(getClass().getResource("/piesek_czolganie.png")).getImage();
    }

    public void draw(Graphics g) {
        if (isCrouching) {
            g.drawImage(crouchingImage, x, y + (STANDING_HEIGHT - CROUCHING_HEIGHT), CROUCHING_WIDTH, CROUCHING_HEIGHT, null);
        } else {
            g.drawImage(standingImage, x, y, STANDING_WIDTH, STANDING_HEIGHT, null);
        }
    }

    public void update() {
        if (isJumping) {
            y += velocityY;
            velocityY += 1; // grawitacja ściągająca żyrafę ze skoku
            if (y >= 300 - STANDING_HEIGHT) {
                y = 300 - STANDING_HEIGHT;
                isJumping = false;
                velocityY = 0;
            }
        }
    }

    public void jump() {
        if (!isJumping && !isCrouching) {
            velocityY = -17; // moc skoku
            isJumping = true;
        }
    }

    public void crouch() {
        isCrouching = true;
    }

    public void standUp() {
        isCrouching = false;
    }

    public Rectangle getBounds() {
        if (isCrouching) {
            return new Rectangle(x, y + (STANDING_HEIGHT - CROUCHING_HEIGHT), CROUCHING_WIDTH, CROUCHING_HEIGHT);
        } else {
            return new Rectangle(x, y, STANDING_WIDTH, STANDING_HEIGHT);
        }
    }
}

