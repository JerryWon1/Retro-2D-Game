package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // 3x scale

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // 16 tiles wide
    public final int maxScreenRow = 12; // 12 tiles tall
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //World Settings
    public final int maxWorldCol = 50; // 50 tiles wide
    public final int maxWorldRow = 50; // 50 tiles tall
    public final int worldWidth = tileSize * maxWorldCol; // 2400 pixels
    public final int worldHeight = tileSize * maxWorldRow; // 2400 pixels

    //FPS
    int FPS = 60; //frames per second (can change)

    TileManager tileM = new TileManager(this); // Tile manager
    KeyHandler keyH = new KeyHandler(); // Key listener
    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this); // Collision checker
    public AssetSetter aSetter = new AssetSetter(this); // Asset setter
    public Player player = new Player(this, keyH); // Player

    public SuperObject[] obj = new SuperObject[10]; // Can display maximum of 10 objects at once (can increase but will affect performance)

    /**
     * GamePanel constructor to set the panel's preferred size, background color, and double buffering.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
    }

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Game loop.
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; // 0.0166666666666667 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Updates the game.
     */
    public void update() {
        player.update();
    }

    /**
     * Draws the game.
     * @param g Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // Draws tiles

        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this); // Draws objects
            }
        }

        player.draw(g2); // Draws player

        g2.dispose();
    }
}
