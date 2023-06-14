package entity;

import java.awt.image.BufferedImage;

public class Entity {

        public int worldX, worldY, speed;

        public BufferedImage upStand, up1, up2, downStand, down1, down2, rightStand, right1, right2, leftStand, left1, left2;
        public String direction;

        public int spriteCounter = 0;
        public int spriteNum = 1;
}
