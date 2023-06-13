package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            up = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/lemon_blue_up.png"));
            down = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/lemon_blue_down.png"));
            left = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/lemon_blue_left.png"));
            right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/lemon_blue_right.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {

        if(keyH.upPressed) {
            direction = "up";
            y -= speed;
        } else if(keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if(keyH.leftPressed) {
            direction = "left";
            x -= speed;
        } else if(keyH.rightPressed) {
            direction = "right";
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
