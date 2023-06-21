package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Deals with the player.
 */
public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX; // Player's x position on the screen
    public final int screenY; // Player's y position on the screen
    public int hasKey = 0; // How many keys the player has

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // Player's x position on the screen is set to middle
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2); // Player's y position on the screen is set to middle

        solidArea = new Rectangle(15, 20, 18, 28); // x, y, width, height of hitbox
        solidAreaDefaultX= solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the player's default values
     */
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down"; // Can be any direction
    }

    /**
     * Stores the player's images & animations.
     */
    public void getPlayerImage() {
        upStand = setup("blue_up_stand");
        up1 = setup("blue_up_1");
        up2 = setup("blue_up_2");
        downStand = setup("blue_down_stand");
        down1 = setup("blue_down_1");
        down2 = setup("blue_down_2");
        leftStand = setup("blue_left_stand");
        left1 = setup("blue_left_1");
        left2 = setup("blue_left_2");
        rightStand = setup("blue_right_stand");
        right1 = setup("blue_right_1");
        right2 = setup("blue_right_2");
    }

    /**
     * Sets up the player images.
     * @param imageName
     */
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Updates the player's direction and animation.
     */
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

            // Checks for tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Checks for object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // If collision is not detected, player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // Starts walking animation if player was standing
            if (spriteNum == 0) {
                spriteNum = 1;
            }

            // Walking animation
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
            spriteNum = 0; // Standing animation
        }
    }

    /**
     * Picks up an object.
     * @param i index of object
     */
    public void pickUpObject(int i) {
        if(i != -1) {
            String objectName = gp.obj[i].name;
            switch(objectName) {
                case "Key":
                    gp.playSE(1);
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up a key!");
                    hasKey++;
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                        hasKey--;
                    } else {
                        gp.ui.showMessage("You need a key to open this door!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up boots and can run faster!");
                    speed += 1;
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    /**
     * Draws the player's image.
     * @param g2 Graphics2D
     */
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

        g2.drawImage(image, screenX, screenY, null); // Draws the player's image
    }
}
