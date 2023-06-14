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

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        //hitbox
        solidArea = new Rectangle(10, 20, 28, 28);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 23;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            upStand = ImageIO.read(getClass().getResourceAsStream("/player/blue_up_stand.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/blue_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/blue_up_2.png"));
            downStand = ImageIO.read(getClass().getResourceAsStream("/player/blue_down_stand.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/blue_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/blue_down_2.png"));
            leftStand = ImageIO.read(getClass().getResourceAsStream("/player/blue_left_stand.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/blue_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/blue_left_2.png"));
            rightStand = ImageIO.read(getClass().getResourceAsStream("/player/blue_right_stand.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/blue_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/blue_right_2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
            } else if(keyH.downPressed) {
                direction = "down";
            } else if(keyH.leftPressed) {
                direction = "left";
            } else if(keyH.rightPressed) {
                direction = "right";
            }

            //checks for collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //if collision is not detected, player can move
            if (collisionOn == false) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if (spriteNum == 0) {
                spriteNum = 1;
            }
            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 0; //standing animation
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 0) {
                    image = upStand;
                } else if(spriteNum == 1) {
                    image = up1;
                } else if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 0) {
                    image = downStand;
                } else if(spriteNum == 1) {
                    image = down1;
                } else if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 0) {
                    image = leftStand;
                } else if(spriteNum == 1) {
                    image = left1;
                } else if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 0) {
                    image = rightStand;
                } else if(spriteNum == 1) {
                    image = right1;
                } else if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
