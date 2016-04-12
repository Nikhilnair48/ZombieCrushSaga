/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zombiecrushsaga2014.data;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mini_game.MiniGame;
import mini_game.MiniGameDataModel;
import mini_game.SpriteType;
import properties_manager.PropertiesManager;
import zombiecrushsaga2014.ZombieCrushSaga2014.ZombieCrushSagaPropertyType;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;
import zombiecrushsaga2014.ui.ZombieCrushSagaPanel;
import zombiecrushsaga2014.ui.ZombieCrushSagaTile;


/**
 *
 * @author nikhilnair
 */
public class ZombieCrushSagaDataModel extends MiniGameDataModel {
    
    // THIS CLASS HAS A REFERERENCE TO THE MINI GAME SO THAT IT
    // CAN NOTIFY IT TO UPDATE THE DISPLAY WHEN THE DATA MODEL CHANGES
    private MiniGame miniGame;
    
    // THE LEVEL GRID REFERS TO THE LAYOUT FOR A GIVEN LEVEL, MEANING
    // HOW MANY TILES FIT INTO EACH CELL WHEN FIRST STARTING A LEVEL
    private int[][] levelGrid;
    
    // LEVEL GRID DIMENSIONS
    private int gridColumns;
    private int gridRows;
    
    // THIS STORES THE TILES ON THE GRID DURING THE GAME
    //private ArrayList<ZombieCrushSagaTile>[][] tileGrid;
    private ZombieCrushSagaTile[][] tileGrid;
    
    // THESE ARE THE TILES THE PLAYER HAS MATCHED
    private ArrayList<ZombieCrushSagaTile> stackTiles;
    private List<ZombieCrushSagaTile>[] stripedTiles;
    private ArrayList<ZombieCrushSagaTile> colorBomb;
    private List<ZombieCrushSagaTile>[] wrappedTiles;
    
    // THESE ARE THE TILES THAT ARE MOVING AROUND, AND SO WE HAVE TO UPDATE
    //private ArrayList<ZombieCrushSagaTile> movingTiles;
    private ArrayList<ZombieCrushSagaTile> movingTiles;
    public ArrayList<ZombieCrushSagaTile> tempMovingTiles;
    
    // THIS IS A SELECTED TILE, MEANING THE FIRST OF A PAIR THE PLAYER
    // IS TRYING TO MATCH. THERE CAN ONLY BE ONE OF THESE AT ANY TIME
    private ZombieCrushSagaTile selectedTile;
    private ZombieCrushSagaTile errorTile;
    
    // THE INITIAL LOCATION OF TILES BEFORE BEING PLACED IN THE GRID
    private int unassignedTilesX;
    private int unassignedTilesY;
    
    // THE PATH TO THE FILE BEING PLAYED
    private String currentLevel;
    
    // WHEN A PLAYER CLICKS ON TWO NEIGHBORING TILES, WE MAKE A MOVE.
    private ZombieCrushSagaMove move;
    
    private int currentScore;   // CURRENT SCORE IN THE GAME
    private int[][] scores;     // AN ARRAY THE SIZE OF THE GRID TO DISPLAY THE NUMBER OF POINTS OVER THE GRID
    private int movesForLevel;  // MOVES FOR THE CURRENT LEVEL
    private int starsForLevel;  // STARS FOR THE CURRENT LEVEL
    private int[][] jellyGrid;
    private boolean gameOver;
    private boolean usedBomb, bombActivated;
    
    private GregorianCalendar endTime;  // NOT USED

    /**
     * Constructor for initializing this data model, it will create
     * the data structures for storing tilesToAdd, but not the tile grid
     * itself, that is dependent of file loading, and so should be
     * subsequently initialized.
     * 
     * @param initMiniGame The Mahjong game UI.
     */
    public ZombieCrushSagaDataModel(MiniGame initMiniGame) {
        
        // KEEP THE GAME FOR LATER
        miniGame = initMiniGame;
        
        // INIT THESE FOR HOLDING MATCHED AND MOVING TILES
        stackTiles = new ArrayList<>();
        
        stripedTiles = (List<ZombieCrushSagaTile>[])Array.newInstance(ArrayList.class, 6);
        for(int i = 0; i < stripedTiles.length; i++)
            stripedTiles[i] = new ArrayList<>();
        
        colorBomb = new ArrayList<>();
        
        wrappedTiles = (List<ZombieCrushSagaTile>[])Array.newInstance(ArrayList.class, 6);
        for(int i = 0; i < wrappedTiles.length; i++)
            wrappedTiles[i] = new ArrayList<>();
        
        movingTiles = new ArrayList<>();
        tempMovingTiles = new ArrayList<>();
        
        selectedTile = null;
        move = new ZombieCrushSagaMove();
        
        gameOver = false;
        usedBomb = false;
        bombActivated = false;
        
    }
    
    // INIT METHODS - AFTER CONSTRUCTION, THESE METHODS SETUP A GAME FOR USE
        // - initTiles
        // - initTile
        // - initSpriteType
        // - initLevelGrid

    /**
     * This method loads the tilesToAdd, creating an individual sprite for each. Note
 that tilesToAdd may be of various types, which is important during the tile
 matching tests.
     */
    public void initTiles() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();        
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
        int spriteTypeID = 0;
        SpriteType sT;
    
        ArrayList<String> typeATiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.REGULAR_ZOMBIES);
        for (int i = 0; i < typeATiles.size(); i++) {
            String imgFile = imgPath + typeATiles.get(i);
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            for (int j = 0; j < 50; j++) {
                initTile(sT, TILE_A_TYPE);
            }
            spriteTypeID++;
        }
        
        ArrayList<String> typeBTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.STRIPED_ZOMBIES);
        for (int i = 0; i < typeBTiles.size(); i++) {
            String imgFile = imgPath + typeBTiles.get(i);
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            for (int j = 0; j < 50; j++) {
                initTile(sT, STRIPED_TYPE + i);
            }
            spriteTypeID++;
        }    
        
        ArrayList<String> colorBombs = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.COLOR_BOMB);
        for (int i = 0; i < colorBombs.size(); i++) {
            String imgFile = imgPath + colorBombs.get(i);
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            for (int j = 0; j < 25; j++) {
                initTile(sT, COLOR_BOMB);
            }
            spriteTypeID++;
        }
        
        ArrayList<String> wrappedZombies = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.WRAPPED_ZOMBIES);
        for (int i = 0; i < wrappedZombies.size(); i++) {
            String imgFile = imgPath + wrappedZombies.get(i);
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            for (int j = 0; j < 25; j++) {
                initTile(sT, WRAPPED_ZOMBIE + i);
            }
            spriteTypeID++;
        }
    }

    /**
     * Helper method for loading the tilesToAdd, it constructs the prescribed
 tile type using the provided sprite type.
     * 
     * @param sT The sprite type to use to represent this tile during rendering.
     * 
     * @param tileType The type of tile. Note that there are 3 broad categories.
     */
    private void initTile(SpriteType sT, String tileType)
    {
        // CONSTRUCT THE TILE
        ZombieCrushSagaTile newTile = new ZombieCrushSagaTile(sT, unassignedTilesX, unassignedTilesY, 0, 0, INVISIBLE_STATE, tileType);
        
        // AND ADD IT TO THE STACK
        //if(newTile != null) {
        switch (tileType) {
            case "TILE_A_TYPE":
                stackTiles.add(newTile);
                break;
            case "COLOR_BOMB":
                colorBomb.add(newTile);
                break;
        }
        
        for(int i = 0; i < 6; i++) {
            if(tileType.equals("STRIPED_" + i)) {
                stripedTiles[i].add(newTile);
                return;
            } else if(tileType.equals("WRAPPED_" + i)) {
                wrappedTiles[i].add(newTile);
                return;
            }
        }
        
    }

    /**
     * This helper method initializes a sprite type for a tile or set of
 similar tilesToAdd to be created.
     */
    private SpriteType initTileSpriteType(String imgFile, String spriteTypeID)
    {
        // WE'LL MAKE A NEW SPRITE TYPE FOR EACH GROUP OF SIMILAR LOOKING TILES
        SpriteType sT = new SpriteType(spriteTypeID);
        addSpriteType(sT);
        
        // LOAD THE ART
        BufferedImage img = miniGame.loadImageWithColorKey(imgFile, COLOR_KEY);
        Image tempImage = img.getScaledInstance(TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT, BufferedImage.SCALE_SMOOTH);
        img = new BufferedImage(TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(tempImage, 0, 0, null);
        
        // WE'LL USE THE SAME IMAGE FOR ALL STATES
        sT.addState(INVISIBLE_STATE, img);
        sT.addState(VISIBLE_STATE, img);
        sT.addState(SELECTED_STATE, img);
        sT.addState(INCORRECTLY_SELECTED_STATE, img);
        
        return sT;
    }
 
    /**
     * Called after a level has been selected, it initializes the grid
     * so that it is the proper dimensions.
     * 
     * @param initGrid The grid distribution of tilesToAdd, where each cell 
 specifies the number of tilesToAdd to be stacked in that cell.
     * 
     * @param initGridColumns The columns in the grid for the level selected.
     * 
     * @param initGridRows The rows in the grid for the level selected.
     */
    public void initLevelGrid(int[][] initGrid, int initGridColumns, int initGridRows) {
        // KEEP ALL THE GRID INFO
        levelGrid = initGrid;
        gridColumns = initGridColumns;
        gridRows = initGridRows;
        
        // SET THE SIZE OF SCORES TO THE SIZE OF THE GRID
        scores = new int[gridRows][gridColumns];

        // AND BUILD THE TILE GRID FOR STORING THE TILES
        // SINCE WE NOW KNOW ITS DIMENSIONS
        tileGrid = new ZombieCrushSagaTile[gridColumns][gridRows];
        
        // MAKE ALL THE TILES VISIBLE
        enableTiles(true);
    }
    
    /**
     * initJellyGrid - Initialize the jelly grid as we begin the level.
     * @param initGrid
     * @param initGridColumns
     * @param initGridRows 
     */
    public void initJellyGrid(int[][] initGrid, int initGridColumns, int initGridRows) {
        jellyGrid = new int[initGridColumns][initGridRows];
        for(int i = 0; i < gridColumns; i++)
            for(int j = 0; j < gridRows; j++)
                jellyGrid[i][j] = initGrid[i][j];
    }

    // CALC METHODS - DETERMINE THE LOCATION OF A TILE
        // - calculateTileXInGrid
        // - calculateTileYInGrid
        // - calculateGridCellColumn
        // - calculateGridCellRow
    
    /**
     * Used to calculate the x-axis pixel location in the game grid for a tile
     * placed at column with stack position z.
     * 
     * @param column The column in the grid the tile is located.
     * 
     * @param z The level of the tile in the stack at the given grid location.
     * 
     * @return The x-axis pixel location of the tile 
     */
    public int calculateTileXInGrid(int column)
    {
        int cellWidth = TILE_IMAGE_WIDTH;
        float leftEdge = miniGame.getBoundaryLeft();
        return (int)(leftEdge + (cellWidth * column)); // - (Z_TILE_OFFSET * z)
    }
    
    /**
     * Used to calculate the y-axis pixel location in the game grid for a tile
     * placed at row with stack position z.
     * 
     * @param row The row in the grid the tile is located.
     * 
     * @param z The level of the tile in the stack at the given grid location.
     * 
     * @return The y-axis pixel location of the tile 
     */
    public int calculateTileYInGrid(int row)
    {
        int cellHeight = TILE_IMAGE_HEIGHT;
        float topEdge = miniGame.getBoundaryTop();
        return (int)(topEdge + (cellHeight * row)); // - (Z_TILE_OFFSET * z)
    }

    /**
     * Used to calculate the grid column for the x-axis pixel location.
     * 
     * @param x The x-axis pixel location for the request.
     * 
     * @return The column that corresponds to the x-axis location x.
     */
    public int calculateGridCellColumn(int x)
    {
        float leftEdge = miniGame.getBoundaryLeft();
        x = (int)(x - leftEdge);
        return x / TILE_IMAGE_WIDTH;
    }

    /**
     * Used to calculate the grid row for the y-axis pixel location.
     * 
     * @param y The y-axis pixel location for the request.
     * 
     * @return The row that corresponds to the y-axis location y.
     */
    public int calculateGridCellRow(int y)
    {
        float topEdge = miniGame.getBoundaryTop();
        y = (int)(y - topEdge);
        return y / TILE_IMAGE_HEIGHT;
    }
    
    // GAME DATA SERVICE METHODS
        // -enableTiles
        // -moveAllTilesToStack
        // -moveTiles
        // -processMove
        // -selectTile

    /**
     * This method can be used to make all of the tilesToAdd either visible (true)
 or invisible (false). This should be used when switching between the
     * splash and game screens.
     * 
     * @param enable Specifies whether the tilesToAdd should be made visible or not.
     */
    public void enableTiles(boolean enable) {
        // PUT ALL THE TILES IN ONE PLACE WHERE WE CAN PROCESS THEM TOGETHER
        moveAllTilesToStack();
        for (ZombieCrushSagaTile tile : stackTiles) 
            this.enableStackTiles(tile);
        
        int i = 0;
        while (i < 6) {
            for (ZombieCrushSagaTile tile : stripedTiles[i]) 
                this.enableStackTiles(tile);
            i++;
        }
        
        for (ZombieCrushSagaTile tile : colorBomb) 
            this.enableStackTiles(tile);
        
        i = 0;
        while (i < 6) {
            for (ZombieCrushSagaTile tile : wrappedTiles[i]) 
                this.enableStackTiles(tile);
            i++;
        }
    }
    
    public void enableStackTiles(ZombieCrushSagaTile tile) {
        tile.setX(TILE_STACK_X);
        tile.setY(TILE_STACK_Y);
        tile.setState(VISIBLE_STATE);
    }
 
    /**
     * This method moves all the tilesToAdd not currently in the stack 
 to the stack.
     */
    public void moveAllTilesToStack() {
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                ArrayList<ZombieCrushSagaTile> cellStack = new ArrayList<ZombieCrushSagaTile>();
                cellStack.add(tileGrid[i][j]);
                if(tileGrid[i][j] != null) 
                    moveTiles(cellStack, stackTiles);
            }
        }        
    }

    /**
     * This method removes all the tilesToAdd in from argument and moves them
     * to argument.
     * 
     * @param from The source data structure of tilesToAdd.
     * 
     * @param to The destination data structure of tilesToAdd.
     */
    private void moveTiles(ArrayList<ZombieCrushSagaTile> from, ArrayList<ZombieCrushSagaTile> to) {
        // GO THROUGH ALL THE TILES, TOP TO BOTTOM
        for (int i = from.size()-1; i >= 0; i--) {
            ZombieCrushSagaTile tile = from.remove(i);
            
            // ONLY ADD IT IF IT'S NOT THERE ALREADY
            if (!to.contains(from))
                to.add(tile);
            
        }        
    }
    
    /**
     * This method attempts to select the selectTile argument. Note that
     * this may be the first or second selected tile. If a tile is already
     * selected, it will attempt to process a match/move.
     * 
     * @param selectTile The tile to select.
     */
    public void selectTile(ZombieCrushSagaTile selectTile) {
        if(selectedTile == selectTile) {    // IF THE SAME TILE IS SELECTED, DESELECT IT
            selectedTile = null;
            selectTile.setState(VISIBLE_STATE);
            return;
        }
        
        if(selectedTile == null) {  // IF NONE OF THE TILES HAVE BEEN SELECTED, SELECT THE TILE
            selectedTile = selectTile;
            selectedTile.setState(SELECTED_STATE);
        } else {    // IF FIRST TILE IS ALREADY SELECTED, AND A SECOND TILE IS CLICKED UPON:
            if (checkTilePositions(selectedTile, selectTile)) {  // ARE THE TWO TILES NEIGHTBORS?
                move.col1 = selectedTile.getGridColumn();
                move.row1 = selectedTile.getGridRow();
                move.col2 = selectTile.getGridColumn();
                move.row2 = selectTile.getGridRow();

                processMove();
                boolean moveOneResult = checkTile(move.col1, move.row1);
                boolean moveTwoResult = checkTile(move.col2, move.row2);

                if (moveOneResult || moveTwoResult) {
                    if (movesForLevel > 0) // IF ALL WENT AS PLANNED, A MOVE HAS BEEN MADE
                    {
                        movesForLevel = movesForLevel - 1;
                    }
                } else {
                    //moveBack();
                }
                
                // IF THERE AREN'T ANY MORE MOVES, WE SHOULD END THE GAME
                if (movesForLevel == 0)
                    endGame();
            }
        }
    }
    
    /**
     * checkLeftOfTile - Using the row & column given, we compare all the tilesToAdd to the left
     * of the given tile and return how many of them are similar.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @return 
     */
    public int checkLeftOfTile(int col, int row) {
        int counter = 0;

        // IF WE HAVE REACHED THE FIRST COLUMN, OR IF THE TWO NEIGHBORING TILES ARE NOT THE SAME
        if(col == 0 || tileGrid[col-1][row] == null || (!tileGrid[col][row].match(tileGrid[col-1][row]))) 
            return 0;
        else {  // KEEP GOING UNTIL WE HIT THE END OF THE ROW
            counter = 1 + checkLeftOfTile(col-1, row);
        }
        return counter;
    }
    
    /**
     * checkRightOfTile - Using the row & column given, we compare all the tilesToAdd to the right
     * of the given tile and return how many of them are similar.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @return 
     */
    public int checkRightOfTile(int col, int row) {
        int counter = 0;

        // IF WE HAVE REACHED THE LAST COLUMN, OR IF THE TWO NEIGHBORING TILES ARE NOT THE SAME
        if(col == gridColumns-1 || tileGrid[col+1][row] == null || !tileGrid[col][row].match(tileGrid[col+1][row])) 
            return 0;
        else  // KEEP GOING UNTIL WE HIT THE END OF THE ROW
            counter = 1 + checkRightOfTile(col+1, row);
        
        return counter;
    }
    
    /**
     * checkTopOfTile - Using the row & column given, we compare all the tilesToAdd to the top
     * of the given tile and return how many of them are similar.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @return 
     */
    public int checkTopOfTile(int col, int row) {
        int counter = 0;
        
        // IF WE HAVE REACHED THE TOP MOST ROW, OR IF THE TWO NEIGHBORING TILES ARE NOT THE SAME
        if(row == 0 || tileGrid[col][row-1] == null || !tileGrid[col][row].match(tileGrid[col][row-1]))
            return 0;
        else    // KEEP GOING UNTIL WE HIT THE END OF THE ROW
            counter = 1 + checkTopOfTile(col, row-1);

        return counter;
    }
    
    /**
     * checkBottomOfTile - Using the row & column given, we compare all the tilesToAdd to the bottom
     * of the given tile and return how many of them are similar.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @return 
     */
    public int checkBottomOfTile(int col, int row) {
        int counter = 0;
        
        // IF WE HAVE REACHED THE TOP MOST ROW, OR IF THE TWO NEIGHBORING TILES ARE NOT THE SAME
        if(row == gridRows-1 || tileGrid[col][row+1] == null || !tileGrid[col][row].match(tileGrid[col][row+1]))
            return 0;
        else  // KEEP GOING UNTIL WE HIT THE END OF THE ROW
            counter = 1 + checkBottomOfTile(col, row+1);
        
        return counter;
    }
    
    public int countToLeft(int col, int row) {
        int counter = 0;

        for (int i = col - 1; i >= 0; i--) {
            if(tileGrid[i][row] == null)
                break;
            else
                counter++;
        }
        return counter;
    }
    
    public int countToRight(int col, int row) {
        int counter = 0;

        for (int i = col + 1;  i <= gridColumns - 1; i++) {
            if(tileGrid[i][row] == null)
                break;
            else
                counter++;
        }
        return counter;
    }
    
    public int countToTop(int col, int row) {
        int counter = 0;

        for (int i = row - 1;  i >= 0; i--) {
            if(tileGrid[col][i] == null)
                break;
            else
                counter++;
        }
        return counter;
    }
    
    public int countToBottom(int col, int row) {
        int counter = 0;

        for (int i = row + 1; i <= gridRows - 1; i++) {
            if(tileGrid[col][i] == null)
                break;
            else
                counter++;
        }
        return counter;
    }
    
    /**
     * removeTilesToRight - Removes the tile at the given column and row, along 
     *  with the specified tilesToAdd to the right.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @param numTiles number of tilesToAdd to the right that have to be deleted
     */
    public void removeTilesToRight(int col, int row, int numTiles) {
        for (int i = 0; i < numTiles; i++) {
            if (col+i < gridColumns && tileGrid[col + i][row] != null) {
                tempMovingTiles.add(tileGrid[col+i][row]);
                tileGrid[col+i][row] = null;
            }
            if(((ZombieCrushSagaMiniGame)miniGame).getLevel() > 5 && jellyGrid[col + i][row] == 1)
                jellyGrid[col + i][row] = 0;
        }
    }
    
    /**
     * removeTlesTOLeft - Removes the tile at the given column and row, along 
     *  with the specified tilesToAdd to the left.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @param numTiles number of tilesToAdd to the left that have to be deleted
     */
    public void removeTilesToLeft(int col, int row, int numTiles) {
        for (int i = 0; i < numTiles; i++) {
            if (col-i >= 0 && tileGrid[col - i][row] != null) {
                tempMovingTiles.add(tileGrid[col - i][row]);
                tileGrid[col - i][row] = null;
            }
            if(((ZombieCrushSagaMiniGame)miniGame).getLevel() > 5 && jellyGrid[col - i][row] == 1)
                jellyGrid[col - i][row] = 0;
        }
    }
    
    /**
     * removeTlesTOLeft - Removes the tile at the given column and row, along 
     * with the specified tilesToAdd to the top.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @param numTiles number of tilesToAdd to the top that have to be deleted
     */
    public void removeTilesToTop(int col, int row, int numTiles) {
        for (int i = 0; i < numTiles; i++) {
            if (row-i >= 0 && tileGrid[col][row - i] != null) {
                tempMovingTiles.add(tileGrid[col][row - i]);
                tileGrid[col][row - i] = null;
            }
            if(((ZombieCrushSagaMiniGame)miniGame).getLevel() > 5 && jellyGrid[col][row - i] == 1)
                jellyGrid[col][row - i] = 0;
        }
    }
    
    /**
     * removeTlesTOLeft - Removes the tile at the given column and row, along 
     * with the specified tilesToAdd to the bottom.
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @param numTiles number of tilesToAdd to the bottom that have to be deleted
     */
    public void removeTilesToBottom(int col, int row, int numTiles) {
        for (int i = 0; i < numTiles; i++) {
            if (row+i < gridRows && tileGrid[col][row + i] != null) {
                tempMovingTiles.add(tileGrid[col][row + i]);
                tileGrid[col][row + i] = null;
            }
            if(((ZombieCrushSagaMiniGame)miniGame).getLevel() > 5 && jellyGrid[col][row + i] == 1)
                jellyGrid[col][row + i] = 0;
        }
    }
    
    /**
     * This method updates all the necessary state information
     * to process the move argument.
     * 
     * @param move The move to make. Note that a move specifies
     * the cell locations for a match.
     */
    public void processMove() {
        
        ZombieCrushSagaTile tileOne = tileGrid[move.col1][move.row1];   // REMOVE THE MOVE TILES FROM THE GRID
        ZombieCrushSagaTile tileTwo = tileGrid[move.col2][move.row2];
        tileGrid[move.col1][move.row1] = null;
        tileGrid[move.col2][move.row2] = null;
        
        tileOne.setState(VISIBLE_STATE);    // MAKE SURE BOTH ARE UNSELECTED
        tileTwo.setState(VISIBLE_STATE);
        
        tileOne.setTarget(tileTwo.getX(), tileTwo.getY());  // SWAP THE TILES
        tileOne.startMovingToTarget(5);
        tileTwo.setTarget(tileOne.getX(), tileOne.getY());
        tileTwo.startMovingToTarget(5);
        
        int col = tileOne.getGridColumn();
        int row = tileOne.getGridRow();
        tileOne.setGridCell(tileTwo.getGridColumn(), tileTwo.getGridRow()); // SWAP GRID CELLS
        tileTwo.setGridCell(col, row);
        
        tileGrid[move.col1][move.row1] = tileTwo;
        tileGrid[move.col2][move.row2] = tileOne;
        
        movingTiles.add(tileOne);   // MOVING TILES MAKES SURE THEY SWAP
        movingTiles.add(tileTwo);
        
        selectedTile = null;    // FINALLY, MAKE SURE SELECTED TILES ARE DESELECTED
        
    }
    
    /**
     * processMatches - 
     */
    public boolean processMatches() {
        boolean result = false;
        if(!checkTile(move.col1, move.row1)) {  // && !checkTile(move.col2, move.row2)
            //moveBack(); LET'S NOT MOVE BACK FOR NOW
            result = true;
        }
        return result;
    }
    
    public void setGameScore(int numTiles, int multiplier, int col, int row) {
        
        if(numTiles == 3) {
            currentScore = currentScore + (multiplier *60);
            scores[row][col] = multiplier*60;
        }
        else if(numTiles == 4) {
            currentScore = currentScore + (multiplier *120);
            scores[row][col] = multiplier*120;
        }
        else if(numTiles == 5) {
            currentScore = currentScore + (multiplier *200);
            scores[row][col] = multiplier*200;
        }
    }
    
    /**
     * If the tiles have reached their location on the grid, 
     * reset the array so that we don't display points on the grid.
     */
    public void resetPointsArray() {
        if(movingTiles.isEmpty())
            scores = new int[gridRows][gridColumns];
    }
    
    /**
     * checkTile - check all four sides of this tile to 
     * @param col
     * @param row
     * @return 
     */
    public boolean checkTile(int col, int row) {
        boolean result = false;
        int tilesToLeft = checkLeftOfTile(col, row);
        int tilesToRight = checkRightOfTile(col, row);
        int tilesToTop = checkTopOfTile(col, row); 
        int tilesToBottom = checkBottomOfTile(col, row);
        
        if (!(result = removeSpecial(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row))) {
            if (!(result = removeT(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row))) {
                if (!(result = removeL(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row))) {
                    if (!(result = removeFiveTiles(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row))) {
                        if (!(result = removeFourTiles(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row))) {
                            result = removeThreeTiles(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row);
                        }   // END REMOVE FOUR OF A KIND
                    }   // END REMOVE FIVE OF A KIND
                }   // END REMOVE L COMBINATION
            }   // END REMOVE T COMBINATION
        }   // END REMOVE SPECIAL 
        if (result)
            rainingTiles();
        
        return result;
    }
    
    public boolean removeThreeTiles(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        if (tilesToBottom == 2) {           // SCENARIO: TWO TILES TO BOTTOM
            removeTilesToBottom(col, row, tilesToBottom + 1);
        } else if (tilesToTop == 2) {       // SCENARIO: TWO TILES TO TOP
            removeTilesToTop(col, row, tilesToTop + 1);
        } else if (tilesToLeft == 2) {      // SCENARIO: TWO TILES TO LEFT
            removeTilesToLeft(col, row, tilesToLeft + 1);
        } else if (tilesToRight == 2) {     //  SCENARIO: TWO TILES TO RIGHT
            removeTilesToRight(col, row, tilesToRight + 1);
        } else if (tilesToBottom == 1 && tilesToTop == 1) { // SCENARIO: ONE TILE TO THE BOTTOM AND ONE TILE TO THE TOP
            removeTilesToTop(col, row, tilesToTop + 1);
            removeTilesToBottom(col, row + 1, tilesToBottom);
        } else if (tilesToLeft == 1 && tilesToRight == 1) { // SCENARIO: ONE TILE TO THE RIGHT AND ONE TILE TO THE LEFT
            removeTilesToLeft(col, row, tilesToLeft + 1);
            removeTilesToRight(col + 1, row, tilesToRight);
        } else
            result = false;
        
        if(result)
            setGameScore(3, 1, col, row);
        
        return result;
    }
    
    public boolean removeSpecial(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = false;
        String ID = tileGrid[col][row].getSpriteType().getSpriteTypeID();
        
        if(ID.equals("TILE_6") || ID.equals("TILE_7") || ID.equals("TILE_8") || ID.equals("TILE_9") || ID.equals("TILE_10") || ID.equals("TILE_11")) {
            result = removedStripedCombo(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row);
        } else if(ID.equals("TILE_13") || ID.equals("TILE_14") || ID.equals("TILE_15") || ID.equals("TILE_16") || ID.equals("TILE_17") || ID.equals("TILE_18")) {
            result = removeWrappedCombo(tilesToRight, tilesToLeft, tilesToTop, tilesToBottom, col, row);
        } else if(ID.equals("TILE_12")) {
            if(!(result = explode(col, row)))
                result = explode(move.col2, move.row2);
        }
        if(result) {
            rainingTiles();
            setGameScore(5, 3, col, row);
        }
        
        return result;
    }
    
    public boolean explode(int col, int row) {
        boolean result = false;
        int ID1 = specialToRegular(tileGrid[move.col1][move.row1].getSpriteType().getSpriteTypeID());
        int ID2 = specialToRegular(tileGrid[move.col2][move.row2].getSpriteType().getSpriteTypeID());
        
        // REMOVE THE COLOR BOMB FROM THE GRID, AND SET THE ID TO -1. THIS IS TO PREVENT MULTIPLE COLOR BOMBS ON THE GRID TO BE REMOVED.
        if(ID2 == 12) {
            tileGrid[move.col2][move.row2] = null;
            ID2 = -1;
        }
        else {
            tileGrid[move.col1][move.row1] = null;
            ID1 = -1;
        }
        // IMMEDIATELY FILL THE GAP
        rainingTiles();
        
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                if (levelGrid[i][j] == 1) {
                    int testID = specialToRegular(tileGrid[i][j].getSpriteType().getSpriteTypeID());
                    
                    if (testID == ID1 || testID == ID2) {
                        result = true;
                        tileGrid[i][j] = null;
                    }
                }
            }
        }
        
        return result;
    }
    
    public boolean removeWrappedCombo(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        
        if (tilesToRight == 1 && tilesToLeft == 1) {         // THREE OF A KIND SCENARIO
            removeTilesToRight(col-1, row, tilesToLeft + 2);
            if(row - 1 < 0) {
                removeTilesToRight(col-1, row+1, tilesToRight + 2);
                removeTilesToRight(col-1, row+2, tilesToRight + 2);
            } else  if(row + 1 >= gridRows) {
                removeTilesToRight(col-1, row-1, tilesToRight + 2);
                removeTilesToRight(col-1, row-2, tilesToRight + 2);
            } else {
                removeTilesToRight(col-1, row-1, tilesToRight + 2);
                removeTilesToRight(col-1, row+1, tilesToRight + 2);
            }
            rainingTiles();
            
        } else if (tilesToTop == 1 && tilesToBottom == 1) {
            removeTilesToTop(col, row, tilesToTop + 1);
            if(col - 1 < 0) {
                removeTilesToRight(col, row-1, tilesToTop + 2);
                removeTilesToRight(col, row+1, tilesToTop + 2);
            } else  if(col + 1 >= gridColumns) {
                removeTilesToRight(col-2, row-1, tilesToTop + 2);
                removeTilesToRight(col-2, row+1, tilesToTop + 2);
            } else {
                removeTilesToRight(col-1, row-1, tilesToTop + 2);
                removeTilesToRight(col-1, row+1, tilesToTop + 2);
            }
            rainingTiles();
            
        } else if (tilesToRight == 2) {
            removeTilesToRight(col, row, tilesToRight + 1);
            rainingTiles();
            if(row - 1 < 0) {
                removeTilesToRight(col, row+1, tilesToRight + 1);
                removeTilesToRight(col, row+2, tilesToRight + 1);
            } else  if(row + 1 >= gridRows) {
                removeTilesToRight(col, row-1, tilesToRight + 1);
                removeTilesToRight(col, row-2, tilesToRight + 1);
            } else {
                removeTilesToRight(col, row-1, tilesToRight + 1);
                removeTilesToRight(col, row+1, tilesToRight + 1);
            }
            rainingTiles();
            
        } else if (tilesToLeft == 2) {
            removeTilesToLeft(col, row, tilesToLeft + 1);
            if(row - 1 < 0) {
                removeTilesToLeft(col, row+1, tilesToLeft + 1);
                removeTilesToLeft(col, row+2, tilesToLeft + 1);
            } else  if(row + 1 >= gridRows) {
                removeTilesToLeft(col, row-1, tilesToLeft + 1);
                removeTilesToLeft(col, row-2, tilesToLeft + 1);
            } else {
                removeTilesToLeft(col, row-1, tilesToLeft + 1);
                removeTilesToLeft(col, row+1, tilesToLeft + 1);
            }
            rainingTiles();
            
        } else if (tilesToTop == 2) {
            removeTilesToTop(col, row, tilesToTop + 1);
            if(col - 1 < 0) {
                removeTilesToTop(col+1, row, tilesToTop + 1);
                removeTilesToTop(col+2, row, tilesToTop + 1);
            } else  if(col + 1 >= gridColumns) {
                removeTilesToTop(col-1, row, tilesToTop + 1);
                removeTilesToTop(col-2, row, tilesToTop + 1);
            } else {
                removeTilesToTop(col-1, row, tilesToTop + 1);
                removeTilesToTop(col+1, row, tilesToTop + 1);
            }
            rainingTiles();
            
        } else if (tilesToBottom == 2) {
            removeTilesToTop(col, row + 1, 1);
            if(col - 1 < 0) {
                removeTilesToBottom(col+1, row, tilesToBottom + 1);
                removeTilesToBottom(col+2, row, tilesToBottom + 1);
            } else  if(col + 1 >= gridColumns) {
                removeTilesToBottom(col-1, row, tilesToBottom + 1);
                removeTilesToBottom(col-2, row, tilesToBottom + 1);
            } else {
                removeTilesToBottom(col-1, row, tilesToBottom + 1);
                removeTilesToBottom(col+1, row, tilesToBottom + 1);
            }
            rainingTiles();
            
        } else
            result = false;
        return result;
    }
    
    public boolean removedStripedCombo(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        
        int left = countToLeft(col, row);
        int right = countToRight(col, row);
        int bottom = countToBottom(col, row);
        int top = countToTop(col, row);
        
        if (tilesToRight == 1 && tilesToLeft == 1) {         // THREE OF A KIND SCENARIO
            removeTilesToLeft(col, row, left);
            removeTilesToRight(col + 1, row, right);
            
        } else if (tilesToTop == 1 && tilesToBottom == 1) {
            removeTilesToTop(col, row, top);
            removeTilesToBottom(col, row + 1, bottom);
            
        } else if (tilesToRight == 2) {
            removeTilesToRight(col+1, row, right);
            removeTilesToLeft(col, row, left+1);
            
        } else if (tilesToLeft == 2) {
            removeTilesToLeft(col-1, row, left);
            removeTilesToRight(col, row, right+1);
            
        } else if (tilesToTop == 2) {
            removeTilesToTop(col, row-1, top);
            removeTilesToBottom(col, row, bottom+1);
            
        } else if (tilesToBottom == 2) {
            removeTilesToBottom(col, row+1, bottom);
            removeTilesToTop(col, row, top+1);
        } else
            result = false;
        if(result)
            rainingTiles();
        
        return result;
    }
    
    public boolean removeL(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        String ID = tileGrid[col][row].getSpriteType().getSpriteTypeID();
        
        if (tilesToRight == 2 && tilesToTop == 2) {                  // L COMBINATION #1
            removeTilesToRight(col, row, tilesToRight + 1);
            removeTilesToTop(col, row - 1, tilesToTop);
            addWrapped(row, col, 0, ID);

        } else if (tilesToRight == 2 && tilesToBottom == 2) {        // L COMBINATION #2
            removeTilesToRight(col, row, tilesToRight + 1);
            removeTilesToBottom(col, row + 1, tilesToBottom);
            addWrapped(row, col, 0, ID);

        } else if (tilesToLeft == 2 && tilesToTop == 2) {            // L COMBINATION #3
            removeTilesToLeft(col, row, tilesToLeft + 1);
            removeTilesToTop(col, row - 1, tilesToTop);
            addWrapped(row, col, 0, ID);

        } else if (tilesToLeft == 2 && tilesToBottom == 2) {         // L COMBINATION #4
            removeTilesToLeft(col, row, tilesToLeft + 1);
            removeTilesToBottom(col, row + 1, tilesToBottom);
            addWrapped(row, col, 0, ID);
        } else
            result = false;
        
        if(result)
            this.setGameScore(5, 1, col, row);
        
        return result;
    }
    
    public boolean removeT(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        String ID = tileGrid[col][row].getSpriteType().getSpriteTypeID();
        
        if (tilesToRight == 1 && tilesToLeft == 1 && tilesToBottom == 2) {       // T COMBINATION #1
            removeTilesToRight(col + 1, row, tilesToRight);
            removeTilesToLeft(col - 1, row, tilesToLeft);
            removeTilesToBottom(col, row, tilesToBottom + 1);
            addWrapped(row, col, 0, ID);
        } else if (tilesToRight == 1 && tilesToLeft == 1 && tilesToTop == 2) {    // T COMBINATION #2
            removeTilesToRight(col + 1, row, tilesToRight);
            removeTilesToLeft(col - 1, row, tilesToLeft);
            removeTilesToTop(col, row, tilesToTop + 1);
            addWrapped(row, col, 0, ID);
        } else if (tilesToTop == 1 && tilesToBottom == 1 && tilesToRight == 2) {  // T COMBINATION #3
            removeTilesToTop(col, row - 1, tilesToTop);
            removeTilesToBottom(col, row + 1, tilesToBottom);
            removeTilesToRight(col, row, tilesToRight + 1);
            addWrapped(row, col, 0, ID);
        } else if (tilesToTop == 1 && tilesToBottom == 1 && tilesToLeft == 2) {   // T COMBINATION #4
            removeTilesToTop(col, row - 1, tilesToTop);
            removeTilesToBottom(col, row + 1, tilesToBottom);
            removeTilesToLeft(col, row, tilesToLeft + 1);
            addWrapped(row, col, 0, ID);
        } else
            result = false;
        
        if(result)
            this.setGameScore(5, 1, col, row);
        return result;
    }
    
    /**
     * addWrapped - Add a color bomb to the given row/col.
     * @param row 
     * @param col 
     * @param which IDK yet
     * @param ID  
     */
    public void addWrapped(int row, int col, int which, String ID) {
        int num = this.specialToRegular(ID);
        //if(which == 1) {
            //num = num-13;
        //}
        ZombieCrushSagaTile tiles = null;
        switch(num) {
            case 0:
                tiles = wrappedTiles[0].remove(wrappedTiles[0].size() - 1);     // TILE TO ADD
                break;
            case 1:
                tiles = wrappedTiles[1].remove(wrappedTiles[1].size() - 1);     // TILE TO ADD
                break;
            case 2:
                tiles = wrappedTiles[2].remove(wrappedTiles[2].size() - 1);     // TILE TO ADD
                break;
            case 3:
                tiles = wrappedTiles[3].remove(wrappedTiles[3].size() - 1);     // TILE TO ADD
                break;
            case 4:
                tiles = wrappedTiles[4].remove(wrappedTiles[4].size() - 1);     // TILE TO ADD
                break;
            case 5:
                tiles = wrappedTiles[5].remove(wrappedTiles[5].size() - 1);     // TILE TO ADD
                break;
        }
        
        tileGrid[col][row] = tiles;
        tiles.setGridCell(col, row);
        float x = calculateTileXInGrid(col);
        float y = calculateTileYInGrid(row);
        tiles.setTarget(x, y);
        tiles.startMovingToTarget(5);
        movingTiles.add(tiles);
    }
    
    /**
     * removeFiveTiles - If there is a possible five of a kind, a color bomb
     * has to be added to the grid.
     * @param tilesToRight number of similar tiles to the right of the given tile
     * @param tilesToLeft number of similar tiles to the left of the given tile
     * @param tilesToTop number of similar tiles to the top of the given tile
     * @param tilesToBottom number of similar tiles to the bottom of the given tile
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @return 
     */
    public boolean removeFiveTiles(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        String ID = tileGrid[col][row].getSpriteType().getSpriteTypeID();
        
        if (tilesToLeft == 2 && tilesToRight == 2) {        // SCENARIO 1 FOR 5 OF A KIND: 2 TILES TO THE LEFT AND 2 TILES TO THE RIGHT
            removeTilesToRight(col, row, tilesToRight + 1);
            removeTilesToLeft(col - 1, row, tilesToLeft);
            addColorBomb(row, col, ID);
        } else if (tilesToTop == 2 && tilesToBottom == 2) { // SCENARIO 2 FOR 5 OF A KIND: 2 TILES TO THE TOP AND 2 TILES TO THE BOTTOM
            removeTilesToTop(col, row, tilesToTop + 1);
            removeTilesToBottom(col, row + 1, tilesToBottom);
            addColorBomb(row, col, ID);
        } else
            result = false;
        
        if(result)
            setGameScore(5, 1, col, row);

        return result;
    }
    
    /**
     * addColorBomb - Add a color bomb to the given row/col.
     * @param row the col in which the color bomb goes in
     * @param col the row in which the color bomb goes in
     * @param ID the ID of the given tile
     */
    public void addColorBomb(int row, int col, String ID) {
        ZombieCrushSagaTile tiles = null;
        
        if (ID.equals("TILE_0") || ID.equals("TILE_1") || ID.equals("TILE_2") || ID.equals("TILE_3") || ID.equals("TILE_4") || ID.equals("TILE_5"))
            tiles = colorBomb.remove(colorBomb.size() - 1);     // TILE TO ADD       

        tileGrid[col][row] = tiles;
        tiles.setGridCell(col, row);
        float x = calculateTileXInGrid(col);
        float y = calculateTileYInGrid(row);
        tiles.setTarget(x, y);
        tiles.startMovingToTarget(5);
        movingTiles.add(tiles);
    }
    
    /**
     * removeFourTiles - Check for all combinations of four of a kind, and
     * if there is one - replace it with a striped tile.
     * @param tilesToRight number of similar tiles to the right of the given tile
     * @param tilesToLeft number of similar tiles to the left of the given tile
     * @param tilesToTop number of similar tiles to the top of the given tile
     * @param tilesToBottom number of similar tiles to the bottom of the given tile
     * @param col the col in which the tile lies on
     * @param row the row in which the tile lies on
     * @return 
     */
    public boolean removeFourTiles(int tilesToRight, int tilesToLeft, int tilesToTop, int tilesToBottom, int col, int row) {
        boolean result = true;
        String ID = tileGrid[col][row].getSpriteType().getSpriteTypeID();;
        
        if ((tilesToRight == 1 && tilesToLeft == 2)) {      // SCENARIO 1 FOR 4 OF A KIND: ONE TILE TO THE RIGHT AND TWO TILES TO THE LEFT
            removeTilesToRight(col + 1, row, tilesToRight);
            removeTilesToLeft(col, row, tilesToLeft + 1);
            addStripedZombie(row, col, 0, ID);

        } else if (tilesToRight == 2 && tilesToLeft == 1) { // SCENARIO 2 FOR 4 OF A KIND: ONE TILE TO THE LEFT AND TWO TILES TO THE RIGHT
            removeTilesToRight(col, row, tilesToRight + 1);
            removeTilesToLeft(col - 1, row, tilesToLeft);
            addStripedZombie(row, col, 0, ID);

        } else if ((tilesToTop == 1 && tilesToBottom == 2)) {   // SCENARIO 3 FOR 4 OF A KIND: ONE TILE TO THE TOP AND TWO TILES TO THE BOTTOM
            removeTilesToTop(col, row - 1, tilesToTop);
            removeTilesToBottom(col, row, tilesToBottom + 1);
            addStripedZombie(row, col, 0, ID);

        } else if (tilesToTop == 2 && tilesToBottom == 1) {     // SCENARIO 4 FOR 4 OF A KIND: ONE TILE TO THE BOTTOM AND TWO TILES TO THE TOP
            removeTilesToTop(col, row, tilesToTop + 1);
            removeTilesToBottom(col, row + 1, tilesToBottom);
            addStripedZombie(row, col, 0, ID);
        } else
            result = false;
        
        if(result)
            setGameScore(4, 1, col, row);
        
        return result;
    }
    
    /**
     * addStripedZombie - Add striped tile to the specific row/col
     * @param row the row in which the tile lies on
     * @param col the col in which the tile lies on
     * @param which 
     * @param ID ID of the tile in this row/col
     */
    public void addStripedZombie(int row, int col, int which, String ID) {
        int num = this.specialToRegular(ID);
        
        /*if(which == 1) {
            num = num-6;
        }*/
        ZombieCrushSagaTile tilesToAdd = null;
        
        switch(num) {
            case 0:
                tilesToAdd = stripedTiles[0].remove(stripedTiles[0].size()-1);     // TILE TO ADD
                break;
            case 1:
                tilesToAdd = stripedTiles[1].remove(stripedTiles[1].size()-1);     // TILE TO ADD
                break;
            case 2:
                tilesToAdd = stripedTiles[2].remove(stripedTiles[2].size()-1);     // TILE TO ADD
                break;
            case 3:
                tilesToAdd = stripedTiles[3].remove(stripedTiles[3].size()-1);     // TILE TO ADD            
                break;
            case 4:
                tilesToAdd = stripedTiles[4].remove(stripedTiles[4].size()-1);     // TILE TO ADD            
                break;
            case 5:
                tilesToAdd = stripedTiles[5].remove(stripedTiles[5].size()-1);     // TILE TO ADD            
                break;       
        }
        
        tileGrid[col][row] = tilesToAdd;
        tilesToAdd.setGridCell(col, row);
        float x = calculateTileXInGrid(col);
        float y = calculateTileYInGrid(row);
        tilesToAdd.setTarget(x, y);
        tilesToAdd.startMovingToTarget(5);
        movingTiles.add(tilesToAdd);
    
    }
    
    /**
     * specialToRegular - Given the String ID of a tile, the method will return
     * the integer ID.
     * @param ID - ID of the tile; ex - TILE_1
     * @return 
     */
    public int specialToRegular(String ID) {
        
        int newID;
        if(ID.length() == 6) {
            newID = Integer.parseInt(ID.substring(ID.length()-1));   
        } else {
            newID = Integer.parseInt(ID.substring(5));
        }
        
        return newID;
    }
    
    /**
     * rainingTiles - When tile(s) are deleted from the grid, we need
     * to fill in those blanks.
     */
    public void rainingTiles() {
        boolean thereIsANull = false;
        
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                if (levelGrid[i][j] == 1) {
                    if (tileGrid[i][j] == null && j == 0) { // IF WE ARE ON THE FIRST ROW, JUST FILL THE HOLE AT THE TOP OF THE GRID
                        // REMOVE THE TILE OUT OF THE STACK, AND PLACE IT ON THE GRID
                        tileGrid[i][j] = stackTiles.remove(stackTiles.size() - 1);
                        tileGrid[i][j].setGridCell(i, j);

                        // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                        // OUT WHERE IT'S GOING AND GET IT MOVING
                        float x = calculateTileXInGrid(i);
                        float y = calculateTileYInGrid(j);
                        tileGrid[i][j].setTarget(x, y);
                        tileGrid[i][j].startMovingToTarget(5);
                        movingTiles.add(tileGrid[i][j]);
                        
                    } else if (tileGrid[i][j] == null && j >= 0) {  // IF WE ARE NOT ON THE FIRST ROW, THE TILE ABOVE THE ROW HAS TO BE BROUGHT DOWN
                        
                        ZombieCrushSagaTile tile = tileGrid[i][j-1];
                        tileGrid[i][j-1] = null;
                        thereIsANull = true;    // TO GO BACK TO FILL THE HOLES IN THE GRID
                        
                        tileGrid[i][j] = tile;
                        tile.setGridCell(i, j);

                        // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                        // OUT WHERE IT'S GOING AND GET IT MOVING
                        float x = calculateTileXInGrid(i);
                        float y = calculateTileYInGrid(j);
                        tile.setTarget(x, y);
                        tile.startMovingToTarget(5);
                        movingTiles.add(tile);
                    }
                }
            }
        }
        if(thereIsANull)
            rainingTiles();
    }

    /**
     * moveBack - If the attempted move by the player is not valid,
 return the tilesToAdd to the original location.
     */
    public void moveBack() {
        ZombieCrushSagaTile tile1 = tileGrid[move.col1][move.row1];
        ZombieCrushSagaTile tile2 = tileGrid[move.col2][move.row2];

        tileGrid[move.col1][move.row1]=(tile2);
        tile2.setGridCell(move.col1, move.row1);
        float x = calculateTileXInGrid(move.col1);
        float y = calculateTileYInGrid(move.row1);
        tile2.setTarget(x, y);
        tile2.startMovingToTarget(5);
        movingTiles.add(tile2);

        tileGrid[move.col2][move.row2] = (tile1);
        tile1.setGridCell(move.col2, move.row2);
        x = calculateTileXInGrid(move.col2);
        y = calculateTileYInGrid(move.row2);
        tile1.setTarget(x, y);
        tile1.startMovingToTarget(5);
        movingTiles.add(tile1);
    }
    
    /**
     * checkTilePositions - Check to see if the two given tilesToAdd are next to each other on the grid.
     * @param tileOne - First tile selected by user
     * @param tileTwo - The tile we have to check with tileOne to see if they are neighbors or not.
     * @return 
     */
    public boolean checkTilePositions(ZombieCrushSagaTile tileOne, ZombieCrushSagaTile tileTwo) {
        boolean result = false;
        
        // ROW AND COLUMN POSITIONS OF BOTH THE TILES
        int s1col = tileOne.getGridColumn();
        int s1row = tileOne.getGridRow();
        int s2col = tileTwo.getGridColumn();
        int s2row = tileTwo.getGridRow();
        
        // TWO OPTIONS:
        if ((s1row == s2row) && ((s1col == s2col - 1) || (s1col == s2col + 1)))   // SAME ROW, BUT COLUMNS COULD BE OFF BY ONE
            result = true;
        else if ((s1col == s2col) && ((s1row == s2row - 1) || (s1row == s2row + 1)))    // SAME COLUMN, BUT ROWS COULD BE OFF BY ONE
            result = true;

        return result;
    }
    
    // OVERRIDDEN METHODS
        // - checkMousePressOnSprites
        // - checkMouseDrag
        // - endGameAsWin
        // - endGameAsLoss
        // - reset
        // - updateAll
        // - updateDebugText
    
    /**
     * This method provides a custom game response for handling mouse clicks on
     * the game screen. We'll use this to close game dialogs as well as to
     * listen for mouse clicks on grid cells.
     *
     * @param game The Mahjong game.
     *
     * @param x The x-axis pixel location of the mouse click.
     *
     * @param y The y-axis pixel location of the mouse click.
     */
    @Override
    public void checkMousePressOnSprites(MiniGame game, int x, int y) {

        // FIGURE OUT THE CELL IN THE GRID
        int col = calculateGridCellColumn(x);
        int row = calculateGridCellRow(y);
        
        // CHECK FOR MOUSE PRESS ONLY IF THERE ARE NO TILES MOVING, AND IF THE PLAYER CLICKED WITHIN THE BOUNDARIES
        if (movingTiles.isEmpty() && row >= 0 && row < gridRows && col >= 0 && col < gridColumns) {
            ZombieCrushSagaTile tile = tileGrid[col][row];
            if(bombActivated && !usedBomb) {
                tileGrid[col][row] = null;
                miniGame.getCanvas().setCursor(Cursor.getDefaultCursor());
                rainingTiles();
                usedBomb = true;
            } else
                selectTile(tile);
        }
    }
    
    @Override
    public void checkMouseDrag(MiniGame game, int x, int y) {
     
        // FIGURE OUT THE CELL IN THE GRID
        int col = calculateGridCellColumn(x);
        int row = calculateGridCellRow(y);

        // CHECK FOR MOUSE PRESS ONLY IF THERE ARE NO TILES MOVING, AND IF THE PLAYER CLICKED WITHIN THE BOUNDARIES
        if (movingTiles.isEmpty() && row >= 0 && row < gridRows && col >= 0 && col < gridColumns) {
            ZombieCrushSagaTile tile = tileGrid[col][row];
            if (tile.containsPoint(x, y))
                selectTile(tile);

            if (selectedTile != tile)
                if (tile.containsPoint(x, y))
                    selectTile(tile);
        }
    }

    public boolean jellyCleared() {
        
        for(int i = 0; i < gridColumns;i++) {
            for(int j = 0; j < gridRows; j++) {
                if(jellyGrid[i][j] == 1)
                    return false;
            }
        }
        return true;
    }
    
    public void activateSpecialChainsaw() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
        
        if (!bombActivated) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(imgPath + "./zcs/game_screen/Bomb.png");
            Cursor c = toolkit.createCustomCursor(image, new Point(miniGame.getCanvas().getLocation()), imgPath);
            miniGame.getCanvas().setCursor(c);
            bombActivated = true;
        } else {
            miniGame.getCanvas().setCursor(Cursor.getDefaultCursor());
            bombActivated = false;
        }
    }
    
    public void endGame() {
        clearGrid();
        enableTiles(false);
        movingTiles.clear();
        
        int level = ((ZombieCrushSagaMiniGame)miniGame).getLevel();

        if(level > 5 && !jellyCleared())
            endGameAsLoss();
        
        if (level == 1) {
            if (currentScore >= 500) {
                starsForLevel = 3;
            } else if (currentScore >= 400) {
                starsForLevel = 2;
            } else if (currentScore >= 300) {
                starsForLevel = 1;
            }
        } else if (level == 2) {
            if (currentScore >= 1900) {
                starsForLevel = 3;
            } else if (currentScore >= 2100) {
                starsForLevel = 2;
            } else if (currentScore >= 2400) {
                starsForLevel = 1;
            }
        } else if (level == 3) {
            if (currentScore >= 4000) {
                starsForLevel = 3;
            } else if (currentScore >= 6000) {
                starsForLevel = 2;
            } else if (currentScore >= 8000) {
                starsForLevel = 1;
            }
        } else if (level == 4) {
            if (currentScore >= 4500) {
                starsForLevel = 3;
            } else if (currentScore >= 6000) {
                starsForLevel = 2;
            } else if (currentScore >= 9000) {
                starsForLevel = 1;
            }
        } else if (level == 5) {
            if (currentScore >= 5000) {
                starsForLevel = 3;
            } else if (currentScore >= 8000) {
                starsForLevel = 2;
            } else if (currentScore >= 12000) {
                starsForLevel = 1;
            }
        } else if (level == 6) {
            if (currentScore >= 9000) {
                starsForLevel = 3;
            } else if (currentScore >= 11000) {
                starsForLevel = 2;
            } else if (currentScore >= 13000) {
                starsForLevel = 1;
            }
        } else if (level == 7) {
            if (currentScore >= 60000) {
                starsForLevel = 3;
            } else if (currentScore >= 75000) {
                starsForLevel = 2;
            } else if (currentScore >= 85000) {
                starsForLevel = 1;
            }
        } else if (level == 8) {
            if (currentScore >= 20000) {
                starsForLevel = 3;
            } else if (currentScore >= 30000) {
                starsForLevel = 2;
            } else if (currentScore >= 45000) {
                starsForLevel = 1;
            }
        } else if (level == 9) {
            if (currentScore >= 22000) {
                starsForLevel = 3;
            } else if (currentScore >= 44000) {
                starsForLevel = 2;
            } else if (currentScore >= 66000) {
                starsForLevel = 1;
            }
        } else if (level == 10) {
            if (currentScore >= 40000) {
                starsForLevel = 3;
            } else if (currentScore >= 70000) {
                starsForLevel = 2;
            } else if (currentScore >= 100000) {
                starsForLevel = 1;
            }
        }
        
        if(starsForLevel > 0)
            endGameAsWin();
        else
            endGameAsLoss();
    }
    
    /**
     * Called when the game is won, it will record the ending game time, update
     * the player record, display the win dialog, and play the win animation.
     */
    @Override
    public void endGameAsWin() {
        // UPDATE THE GAME STATE USING THE INHERITED FUNCTIONALITY
        super.endGameAsWin();
        miniGame.getCanvas().setCursor(Cursor.getDefaultCursor());
        
        // RECORD IT AS A WIN
        if (((ZombieCrushSagaMiniGame) miniGame).getPlayerRecord().getHighestScore(currentLevel) < currentScore) { //((ZombieCrushSagaMiniGame) miniGame).getPlayerRecord().getHighestScore(currentLevel) > 0
            ((ZombieCrushSagaMiniGame) miniGame).getPlayerRecord().addScore(currentLevel, currentScore, starsForLevel);
            ((ZombieCrushSagaMiniGame) miniGame).savePlayerRecord();
        }
        
        ((ZombieCrushSagaMiniGame) miniGame).switchToLevelScoreScreen();
        ((ZombieCrushSagaMiniGame) miniGame).getGUIDecor().get(LEVEL_TYPES).setState(LEVEL_WON);
        gameOver = true;
    }
    
    public void endGameAsLoss() {
        // UPDATE THE GAME STATE USING THE INHERITED FUNCTIONALITY
        super.endGameAsLoss();
        
        // RECORD THE LOSS
        ((ZombieCrushSagaMiniGame) miniGame).switchToLevelScoreScreen();
        ((ZombieCrushSagaMiniGame) miniGame).getGUIDecor().get(LEVEL_TYPES).setState(LEVEL_LOST);
        gameOver = true;
    }
    
    /**
     * Called when a game is started, the game grid is reset.
     * 
     * @param game 
     */
    @Override
    public void reset(MiniGame game) {
        // RANDOMLY ORDER THEM        
        Collections.shuffle(stackTiles);
        clearGrid();
        usedBomb = false;
        currentScore = 0;
        starsForLevel = 0;
        movesForLevel = ((ZombieCrushSagaMiniGame)game).getMoves();
        
        // NOW LET'S REMOVE THEM FROM THE STACK
        // AND PUT THE TILES IN THE GRID        
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                if (levelGrid[i][j] == 1) {
                    
                        // TAKE THE TILE OUT OF THE STACK
                        ZombieCrushSagaTile tile = stackTiles.remove(stackTiles.size() - 1);
                        
                        // PUT IT IN THE GRID
                        tileGrid[i][j] = tile;
                        tile.setGridCell(i, j);

                        // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                        // OUT WHERE IT'S GOING AND GET IT MOVING
                        float x = calculateTileXInGrid(i);
                        float y = calculateTileYInGrid(j);
                        tile.setTarget(x, y);
                        tile.startMovingToTarget(MAX_TILE_VELOCITY);
                        movingTiles.add(tile);
                }
            }
        }     
        // AND START ALL UPDATES
        beginGame();
    }    

    public void clearGrid() {
        for(int i = 0; i < gridColumns; i++)
            for(int j = 0; j < gridRows; j++)
                tileGrid[i][j] = null;
    }
    
    /**
     * Called each frame, this method updates all the game objects.
     * 
     * @param game The Mahjong game to be updated.
     */
    @Override
    public void updateAll(MiniGame game) {
        // MAKE SURE THIS THREAD HAS EXCLUSIVE ACCESS TO THE DATA
        try {
            game.beginUsingData();
            // IF THE GAME IS STILL ON, THE TIMER SHOULD CONTINUE
            if (inProgress() && movingTiles.isEmpty())
                checkGameGridForMatches();
            
            for(int i = 0; i < tempMovingTiles.size(); i++) {
                ZombieCrushSagaTile tile2 = tempMovingTiles.get(i);
                tile2.setTarget(1280, tile2.getY());
                tile2.startMovingToTarget(5);
                movingTiles.add(tile2);
            }

            // WE ONLY NEED TO UPDATE AND MOVE THE MOVING TILES
            for (int i = 0; i < movingTiles.size(); i++) {
                // GET THE NEXT TILE
                ZombieCrushSagaTile tile = movingTiles.get(i);
            
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
            
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget()) {
                    movingTiles.remove(tile);
                    if(tempMovingTiles.contains(tile))
                        tempMovingTiles.remove(tile);
                }
            }
        }
        finally {
            // MAKE SURE WE RELEASE THE LOCK WHETHER THERE IS
            // AN EXCEPTION THROWN OR NOT
            game.endUsingData();
        }
    }
    
    public void checkGameGridForMatches() {
        boolean result = false;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                if (levelGrid[col][row] == 1 && tileGrid[col][row] != null) {

                    int tilesToRight = checkRight(col, row);
                    int tilesToBottom = checkBottom(col, row);

                    if (tilesToRight + 1 > 2) {
                        result = true;
                        removeTilesToRight(col, row, 3);
                        setGameScore(3, 1, col, row);
                    } else if (tilesToBottom + 1 > 2) {
                        result = true;
                        removeTilesToBottom(col, row, 3);
                        setGameScore(3, 1, col, row);
                    }

                }   // END OF BIG IF
            }   // END OF INNER LOOP
        }   // END OF OUTER LOOP
        if (result)
            rainingTiles(); // FILL IN ANY GAPS FROM REMOVING TILES
    }
    
    public int checkRight(int col, int row) {
        int counter = 0;

        // IF WE HAVE REACHED THE LAST COLUMN, OR IF THE TWO NEIGHBORING TILES ARE NOT THE SAME
        if(col == gridColumns-1 || tileGrid[col+1][row] == null || !tileGrid[col][row].gridMatch(tileGrid[col+1][row])) 
            return 0;
        else  // KEEP GOING UNTIL WE HIT THE END OF THE ROW
            counter = 1 + checkRight(col+1, row);
        
        return counter;
    }
    
    public int checkBottom(int col, int row) {
        int counter = 0;
        
        // IF WE HAVE REACHED THE TOP MOST ROW, OR IF THE TWO NEIGHBORING TILES ARE NOT THE SAME
        if(row == gridRows-1 || tileGrid[col][row+1] == null || !tileGrid[col][row].gridMatch(tileGrid[col][row+1]))
            return 0;
        else  // KEEP GOING UNTIL WE HIT THE END OF THE ROW
            counter = 1 + checkBottom(col, row+1);
        
        return counter;
    }
    
    /**
     * This method is for updating any debug text to present to
     * the screen. In a graphical application like this it's sometimes
     * useful to display data in the GUI.
     * 
     * @param game The Mahjong game about which to display info.
     */
    @Override
    public void updateDebugText(MiniGame game) {}    
    
    // ACCESSOR METHODS

    /**
     * Accessor method for getting the level currently being played.
     * 
     * @return The level name used currently for the game screen.
     */
    public String getCurrentLevel() { return currentLevel; }

    /**
     * Accessor method for getting the number of tile columns in the game grid.
     * 
     * @return The number of columns (left to right) in the grid for the level
     * currently loaded.
     */
    public int getGridColumns() { return gridColumns; }
    
    /**
     * Accessor method for getting the number of tile rows in the game grid.
     * 
     * @return The number of rows (top to bottom) in the grid for the level
     * currently loaded.
     */
    public int getGridRows() { return gridRows; }

    /**
     * Accessor method for getting the tile grid, which has all the
     * tilesToAdd the user may select from.
     * 
     * @return The main 2D grid of tilesToAdd the user selects tilesToAdd from.
     */
    public ZombieCrushSagaTile[][] getTileGrid() { return tileGrid; }
    
    /**
     * Accessor method for getting the stack tilesToAdd.
     * 
     * @return The stack tilesToAdd, which are the tilesToAdd the matched tilesToAdd
 are placed in.
     */
    public ArrayList<ZombieCrushSagaTile> getStackTiles() { return stackTiles; }

    /**
     * Accessor method for getting the moving tilesToAdd.
     * 
     * @return The moving tilesToAdd, which are the tilesToAdd currently being
     * animated as they move around the game. 
     */
    public ArrayList<ZombieCrushSagaTile> getMovingTiles() { return movingTiles; }
    
    /**
     * Mutator method for setting the currently loaded level.
     * 
     * @param initCurrentLevel The level name currently being used
     * to play the game.
     */
    public void setCurrentLevel(String initCurrentLevel) { currentLevel = initCurrentLevel; }
    
    /**
     * Accessor method for getting the current score in the game.
     * @return the integer value of the current score
     */
    public int getCurrentScore() { return currentScore; }
    
    public int[][] getCurrentScoreArray() { return scores; }
    
    public int getMoves() { return movesForLevel; }
    
    public void setMoves(int moves) { movesForLevel = moves; }
    
    public int getStars() { return starsForLevel; }
    
    public void setStars(int stars) { starsForLevel = stars; }
    
    public int[][] getLevelGrid() { return levelGrid; }
    
    public int[][] getJellyGrid() { return jellyGrid; }
    
    public boolean gameOver() { return gameOver; }
}
