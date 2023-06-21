package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * Sets the screen and world settings, and starts the game thread.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen settings
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

    //FPS
    int FPS = 60; // Frames per second (can change)

    // System
    TileManager tileM = new TileManager(this); // Tile manager
    KeyHandler keyH = new KeyHandler(); // Key listener
    Sound music = new Sound(); // Music
    Sound se = new Sound(); // Sound effects
    public CollisionChecker cChecker = new CollisionChecker(this); // Collision checker
    public AssetSetter aSetter = new AssetSetter(this); // Asset setter
    public UI ui = new UI(this); // UI
    Thread gameThread;

    // Entities
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

    /**
     * Sets up the game.
     */
    public void setupGame() {
        aSetter.setObject();

        playMusic(0);
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

        // Debugging
        long drawStart = 0;
        if(keyH.debug) {
            drawStart = System.nanoTime();
        }

        tileM.draw(g2); // Draws tiles

        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this); // Draws objects
            }
        }

        player.draw(g2); // Draws player

        ui.draw(g2); // Draws UI

        //Debugging
        if(keyH.debug) {
            long drawEnd = System.nanoTime();
            long drawTime = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + drawTime + " ns", 10, 400);
            System.out.println("Draw Time: " + drawTime + " ns");
        }
        g2.dispose();
    }

    /**
     * Plays music on loop.
     * @param i index of song
     */
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    /**
     * Stops music.
     */
    public void stopMusic() {
        music.stop();
    }

    /**
     * Plays sound effect once.
     * @param i index of song
     */
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
