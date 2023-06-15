package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    /**
     * Loads the map from a text file.
     * @param gp
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[40];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    /**
     * Stores tile images.
     */
    public void getTileImage() {
        try {
            for (int i = 0; i < 6; i++) {
                tile[i] = new Tile();
            }
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass01.png"));
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water01.png"));
            tile[2].collision = true;
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road00.png"));
//            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
//            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor01.png"));
//            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass00.png"));
//            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass01.png"));
//            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hut.png"));
//            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road00.png"));
//            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road01.png"));
//            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road02.png"));
//            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road03.png"));
//            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road04.png"));
//            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road05.png"));
//            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road06.png"));
//            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road07.png"));
//            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road08.png"));
//            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road09.png"));
//            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road10.png"));
//            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road11.png"));
//            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road12.png"));
//            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/table01.png"));
//            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
//            tile[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
//            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water00.png"));
//            tile[22].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water01.png"));
//            tile[23].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water02.png"));
//            tile[24].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water03.png"));
//            tile[25].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water04.png"));
//            tile[26].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water05.png"));
//            tile[27].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water06.png"));
//            tile[28].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water07.png"));
//            tile[29].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water08.png"));
//            tile[30].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water09.png"));
//            tile[31].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water10.png"));
//            tile[32].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water11.png"));
//            tile[33].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water12.png"));
//            tile[34].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water13.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map from a text file.
     * @param filePath The path to the text file.
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while(col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the map.
     * @param g2 The graphics object.
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
