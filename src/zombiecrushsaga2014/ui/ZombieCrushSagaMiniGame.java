package zombiecrushsaga2014.ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import properties_manager.PropertiesManager;
import zombiecrushsaga2014.ZombieCrushSaga2014.ZombieCrushSagaPropertyType;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;
import zombiecrushsaga2014.data.ZombieCrushSagaDataModel;
import zombiecrushsaga2014.data.ZombieCrushSagaRecord;
import zombiecrushsaga2014.events.ExitHandler;
import zombiecrushsaga2014.events.GameScreenHandler;
import zombiecrushsaga2014.events.LevelScoreHandler;
import zombiecrushsaga2014.events.SagaHandler;
import zombiecrushsaga2014.events.SplashHandler;
import zombiecrushsaga2014.level.ZombieCrushSagaLevelManager;
/**
 *
 * @author nikhilnair
 */
public class ZombieCrushSagaMiniGame extends MiniGame {
    
    private ZombieCrushSagaRecord record;
    
    private ZombieCrushSagaErrorHandler errorHandler;
    
    private ZombieCrushSagaLevelManager fileManager;
    
    private String currentScreenState, levelPath;
    
    private int currentLevel;
    
    private float yTarget;
    
    private int movesForLevel;
    
    public void switchToGameScreen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        guiDecor.get(LEVEL_TYPES).setState(INVISIBLE_STATE);
        
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setEnabled(false);
        
        guiDecor.get(GOOD_STAR_TYPE1).setState(INVISIBLE_STATE);
        guiDecor.get(GOOD_STAR_TYPE2).setState(INVISIBLE_STATE);
        guiDecor.get(GOOD_STAR_TYPE3).setState(INVISIBLE_STATE);
        
        guiButtons.get(GAME_SCREEN_QUIT_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(GAME_SCREEN_QUIT_TYPE).setEnabled(true);
        
        guiButtons.get(BOMB_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(BOMB_TYPE).setEnabled(true);
        
        guiDecor.get(PROGRESS_BAR).setState(VISIBLE_STATE);
        
        ((ZombieCrushSagaDataModel)data).enableTiles(true);
        ((ZombieCrushSagaDataModel)data).reset(this);
        
        
        guiDecor.get(LEVEL_TYPES).setState(INVISIBLE_STATE);
        
        currentScreenState = GAME_SCREEN_STATE;
    }
    
    public void switchToLevelScoreScreen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        guiDecor.get(BACKGROUND_TYPE).setState(LEVEL_SCREEN_STATE);

        guiButtons.get(EXIT_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(EXIT_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SCROLL_UP_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLL_UP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SCROLL_DOWN_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLL_DOWN_BUTTON_TYPE).setEnabled(false);

        ArrayList<String> levelsRowOne = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
        for (String levelFile : levelsRowOne) {
            guiButtons.get(levelFile).setState(INVISIBLE_STATE);
            guiButtons.get(levelFile).setEnabled(false);
        }

        ArrayList<String> levelsRowTwo = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
        for (String levelFile : levelsRowTwo) {
            guiButtons.get(levelFile).setState(INVISIBLE_STATE);
            guiButtons.get(levelFile).setEnabled(false);
        }
        guiButtons.get(GAME_SCREEN_QUIT_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(GAME_SCREEN_QUIT_TYPE).setEnabled(false);

        guiButtons.get(BOMB_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(BOMB_TYPE).setEnabled(false);

        guiDecor.get(PROGRESS_BAR).setState(INVISIBLE_STATE);

        ((ZombieCrushSagaDataModel) data).enableTiles(false);
        
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setEnabled(true);
        
        currentScreenState = LEVEL_SCREEN_STATE;
    }

    public void switchToSagaScreen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        guiDecor.get(BACKGROUND_TYPE).setState(SAGA_SCREEN_STATE);
        guiDecor.get(LEVEL_TYPES).setState(INVISIBLE_STATE);
        
        guiDecor.get(GOOD_STAR_TYPE1).setState(INVISIBLE_STATE);
        guiDecor.get(GOOD_STAR_TYPE2).setState(INVISIBLE_STATE);
        guiDecor.get(GOOD_STAR_TYPE3).setState(INVISIBLE_STATE);
        
        // ENABLE ALL BUTTONS ON THE SAGA SCREEN
        guiButtons.get(EXIT_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(EXIT_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(SCROLL_UP_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(SCROLL_UP_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(SCROLL_DOWN_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(SCROLL_DOWN_BUTTON_TYPE).setEnabled(true);
        // ENABLE ALL THE LEVEL BUTTONS ON THE SAGA SCREEN
        ArrayList<String> levelsRowOne = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
        for (String levelFile : levelsRowOne) {
            guiButtons.get(levelFile).setState(VISIBLE_STATE);
            guiButtons.get(levelFile).setEnabled(true);
        }
        
        ArrayList<String> levelsRowTwo = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
        for (String levelFile : levelsRowTwo) {
            guiButtons.get(levelFile).setState(VISIBLE_STATE);
            guiButtons.get(levelFile).setEnabled(true);
        }
        // DISABLE ALL THE SPLASH SCREEN BUTTONS
        ArrayList<String> splash = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SPLASH_OPTIONS);
        for (String levelFile : splash) {
            guiButtons.get(levelFile).setState(INVISIBLE_STATE);
            guiButtons.get(levelFile).setEnabled(false);
        }
        // DISABLE ALL BUTTONS ON THE LEVEL SCORE SCREEN
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setEnabled(false);

        currentScreenState = SAGA_SCREEN_STATE;
        //System.out.println("IN SAGA");
    }
    
    public void activateLevelButtons() {
        String path = "./zcs/";
        String dataPath = "./data/./zcs/";
        
        guiButtons.get(path+"Level_"+(1)+".zcs").setState(VISIBLE_STATE);
        guiButtons.get(path+"Level_"+(1)+".zcs").setEnabled(true);
        
        for(int i = 1; i < 10; i ++) {
            if(record.getHighestScore(dataPath + "Level_"+ (i) + ".zcs") > 0) {
                guiButtons.get(path+"Level_"+(i+1)+".zcs").setState(VISIBLE_STATE);
                guiButtons.get(path+"Level_"+(i+1)+".zcs").setEnabled(true);
            }
            else
                break;
            
        }
    }
    
    @Override
    public void initData() {
        // INIT OUR ERROR HANDLER
        errorHandler = new ZombieCrushSagaErrorHandler(window);
        
        // INIT OUR FILE MANAGER
        fileManager = new ZombieCrushSagaLevelManager(this);
        
        // LOAD THE PLAYER'S RECORD FROM A FILE
        record = fileManager.loadRecord();
    
        // INIT OUR DATA MANAGER
        data = new ZombieCrushSagaDataModel(this);
        
                // LOAD THE GAME DIMENSIONS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        int gameWidth = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH.toString()));
        int gameHeight = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT.toString()));
        data.setGameDimensions(gameWidth, gameHeight);

        // THIS WILL CHANGE WHEN WE LOAD A LEVEL
        boundaryLeft = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_LEFT_OFFSET.toString()));
        boundaryTop = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_TOP_OFFSET.toString()));
        boundaryRight = gameWidth - boundaryLeft;
        boundaryBottom = gameHeight;
    }

    @Override
    public void initGUIControls() {
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
 
        // FIRST PUT THE ICON IN THE WINDOW
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);        
        String windowIconFile = props.getProperty(ZombieCrushSagaPropertyType.WINDOW_ICON);
        img = loadImage(imgPath + windowIconFile);
        window.setIconImage(img);
        
        // CONSTRUCT THE PANEL WHERE WE'LL DRAW EVERYTHING
        canvas = new ZombieCrushSagaPanel(this, (ZombieCrushSagaDataModel)data);
        
        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        currentScreenState = SPLASH_SCREEN_STATE;
        sT = new SpriteType(BACKGROUND_TYPE);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.SPLASH_SCREEN_IMAGE_NAME));
        sT.addState(SPLASH_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.SAGA_BACKGROUND_IMAGE_NAME));
        sT.addState(SAGA_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVE_SCORE_BACKGROUND_IMAGE_NAME));
        sT.addState(LEVEL_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.GAME_BACKGROUND_IMAGE_NAME));
        sT.addState(GAME_SCREEN_STATE, img);
        
        s = new Sprite(sT, 0, 0, 0, 0, SPLASH_SCREEN_STATE);
        guiDecor.put(BACKGROUND_TYPE, s);
        
        // ADD A BUTTON FOR EACH OPTION ON SPLASH SCREEN
        ArrayList<String> splashOptions = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SPLASH_OPTIONS);
        ArrayList<String> splashImageNames = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SPLASH_IMAGE_OPTIONS);
        ArrayList<String> splashMouseOverImageNames = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SPLASH_MOUSE_OVER_IMAGE_OPTIONS);
        float totalWidth = splashOptions.size() * (SPLASH_BUTTON_WIDTH + SPLASH_BUTTON_MARGIN) - SPLASH_BUTTON_MARGIN;
        float gameWidth = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH));
        x = (gameWidth - totalWidth)/2.0f;
        y = SPLASH_BUTTON_Y;
        for (int i = 0; i < splashOptions.size(); i++) {
            sT = new SpriteType(SPLASH_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + splashImageNames.get(i), COLOR_KEY);
            sT.addState(VISIBLE_STATE, img);
            img = loadImageWithColorKey(imgPath + splashMouseOverImageNames.get(i), COLOR_KEY);
            sT.addState(MOUSE_OVER_STATE, img);
            s = new Sprite(sT, x, y, 0, 0, VISIBLE_STATE);
            guiButtons.put(splashOptions.get(i), s);
            x += SPLASH_BUTTON_WIDTH + SPLASH_BUTTON_MARGIN;
        }
        
        ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
        ArrayList<String> levelImageNames = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_LEVEL_BUTTONS_ROW_ONE);
        ArrayList<String> levelMouseOverImageNames = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_LEVEL_BUTTONS_ROW_ONE_MOUSE_OVER);
        this.initializeLevelButtons(levels, levelImageNames, levelMouseOverImageNames, imgPath, BUTTON_Y);
        
        levels = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
        levelImageNames = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_LEVEL_BUTTONS_ROW_TWO);
        levelMouseOverImageNames = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_LEVEL_BUTTONS_ROW_TWO_MOUSE_OVER);
        this.initializeLevelButtons(levels, levelImageNames, levelMouseOverImageNames, imgPath, BUTTON_Y+240);
        
        // SAGA QUIT BUTTON
        String quit = props.getProperty(ZombieCrushSagaPropertyType.SAGA_EXIT_BUTTON);
        sT = new SpriteType(EXIT_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + quit, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String quitMouseOverButton = props.getProperty(ZombieCrushSagaPropertyType.SAGA_EXIT_BUTTON_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + quitMouseOverButton, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, EXIT_X, EXIT_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(EXIT_BUTTON_TYPE, s);
        
        // SAGA SCROLL UP BUTTON
        String scrollUp = props.getProperty(ZombieCrushSagaPropertyType.SAGA_SCROLL_UP_BUTTON);
        sT = new SpriteType(SCROLL_UP_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + scrollUp, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String scrollUpMouseOverButton = props.getProperty(ZombieCrushSagaPropertyType.SAGA_SCROLL_UP_BUTTON_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + scrollUpMouseOverButton, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, UP_X, UP_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(SCROLL_UP_BUTTON_TYPE, s);
        
        // SAGA SCROLL DOWN BUTTON
        String scrollDown = props.getProperty(ZombieCrushSagaPropertyType.SAGA_SCROLL_DOWN_BUTTON);
        sT = new SpriteType(SCROLL_DOWN_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + scrollDown, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String scrollDownMouseOverButton = props.getProperty(ZombieCrushSagaPropertyType.SAGA_SCROLL_DOWN_BUTTON_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + scrollDownMouseOverButton, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, DOWN_X, DOWN_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(SCROLL_DOWN_BUTTON_TYPE, s);
        
        // LEVEL SCORE BACK BUTTON
        String backButton = props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_BACK_BUTTON);
        sT = new SpriteType(LEVEL_BACK_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + backButton, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String backButtonMouseOver = props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_BACK_BUTTON_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + backButtonMouseOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, LEVEL_START_BUTTON_X-250, LEVEL_START_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(LEVEL_BACK_BUTTON_TYPE, s);
        
        // LEVEL SCORE START BUTTON
        String scrollButton = props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_START_BUTTON);
        sT = new SpriteType(LEVEL_START_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + scrollButton, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String scrollButtonMouseOver = props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_START_BUTTON_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + scrollButtonMouseOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, LEVEL_START_BUTTON_X, LEVEL_START_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(LEVEL_START_BUTTON_TYPE, s);
        
        // GAME SCREEN QUIT BUTTON
        String gameQuitButton = props.getProperty(ZombieCrushSagaPropertyType.GAME_SCREEN_QUIT_BUTTON);
        sT = new SpriteType(GAME_SCREEN_QUIT_TYPE);
        img = loadImageWithColorKey(imgPath + gameQuitButton, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String gameQuitButtonMouseOver = props.getProperty(ZombieCrushSagaPropertyType.GAME_SCREEN_QUIT_BUTTON_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + gameQuitButtonMouseOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, QUIT_X, QUIT_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(GAME_SCREEN_QUIT_TYPE, s);
        
        sT = new SpriteType(PROGRESS_BAR);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.PROGRESS_BAR));
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, PROGRESS_BAR_X, PROGRESS_BAR_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(PROGRESS_BAR, s);
        
        String bomb = props.getProperty(ZombieCrushSagaPropertyType.BOMB);
        sT = new SpriteType(BOMB_TYPE);
        img = loadImageWithColorKey(imgPath + bomb, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String bombOverButton = props.getProperty(ZombieCrushSagaPropertyType.BOMB_MOUSE_OVER);
        img = loadImageWithColorKey(imgPath + bombOverButton, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, BOMB_X, BOMB_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(BOMB_TYPE, s);
        
        //LEVEL_SCORE_STARS
        
        //WIN AND LOSE DIALOGS
        sT = new SpriteType(LEVEL_TYPES);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.WIN_DIALOG));
        sT.addState(LEVEL_WON, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LOSS_DIALOG));
        sT.addState(LEVEL_LOST, img);
        s = new Sprite(sT, 525, 40, 0, 0, INVISIBLE_STATE);
        guiDecor.put(LEVEL_TYPES, s);
        
        sT = new SpriteType(GOOD_STAR_TYPE1);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_STARS));
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 90, 32, 0, 0, INVISIBLE_STATE);
        guiDecor.put(GOOD_STAR_TYPE1, s);
        
        sT = new SpriteType(GOOD_STAR_TYPE2);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_STARS));
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 90, 250, 0, 0, INVISIBLE_STATE);
        guiDecor.put(GOOD_STAR_TYPE2, s);
        
        sT = new SpriteType(GOOD_STAR_TYPE3);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCORE_STARS));
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 90, 465, 0, 0, INVISIBLE_STATE);
        guiDecor.put(GOOD_STAR_TYPE3, s);
        
        // THEN THE TILES STACKED TO THE TOP LEFT
        ((ZombieCrushSagaDataModel)data).initTiles();
    }

    public void initializeLevelButtons(ArrayList<String> levels, ArrayList<String> levelImageNames, ArrayList<String> levelMouseOverImageNames, String imgPath, float y) { 
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        float totalWidth = levels.size() * (SPLASH_BUTTON_WIDTH + SPLASH_BUTTON_MARGIN) - SPLASH_BUTTON_MARGIN;
        float gameWidth = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH));
        float x = (gameWidth - totalWidth)/2.0f;
        //float y = BUTTON_Y;
        for (int i = 0; i < levels.size(); i++)
        {
            SpriteType sT = new SpriteType(LEVEL_TYPE);
            BufferedImage img = loadImageWithColorKey(imgPath + levelImageNames.get(i), COLOR_KEY);
            sT.addState(VISIBLE_STATE, img);
            img = loadImageWithColorKey(imgPath + levelMouseOverImageNames.get(i), COLOR_KEY);
            sT.addState(MOUSE_OVER_STATE, img);
            Sprite s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
            guiButtons.put(levels.get(i), s);
            x += SPLASH_BUTTON_WIDTH; //+ LEVEL_BUTTON_MARGIN;
        }
    }
    
    @Override
    public void initGUIHandlers() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String dataPath = props.getProperty(ZombieCrushSagaPropertyType.DATA_PATH);
        
        // SPLASH BUTTON EVENT HANDLERS 
        ArrayList<String> splash = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SPLASH_OPTIONS);
        for (String levelFile : splash) {
            SplashHandler slh = new SplashHandler(this);
            guiButtons.get(levelFile).setActionListener(slh);
        }
        
        // SAGA ROW ONE BUTTON
        ArrayList<String> levelsRowOne = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
        for (String rowOne : levelsRowOne) {
            SagaHandler slh = new SagaHandler(this);
            guiButtons.get(rowOne).setActionListener(slh);
        }
        
        // SAGA ROW TWO BUTTON
        ArrayList<String> levelsRowTwo = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
        for (String rowTwo : levelsRowTwo) {
            SagaHandler slh = new SagaHandler(this);
            guiButtons.get(rowTwo).setActionListener(slh);
        }
        
        // WE'LL HAVE A CUSTOM RESPONSE FOR WHEN THE USER CLOSES THE WINDOW
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        ExitHandler eh = new ExitHandler(this);
        window.addWindowListener(eh);
        
        SagaHandler sh = new SagaHandler(this);
        guiButtons.get(SCROLL_UP_BUTTON_TYPE).setActionListener(sh);
        guiButtons.get(SCROLL_DOWN_BUTTON_TYPE).setActionListener(sh);
        guiButtons.get(EXIT_BUTTON_TYPE).setActionListener(sh);
        
        LevelScoreHandler lsh = new LevelScoreHandler(this);
        guiButtons.get(LEVEL_BACK_BUTTON_TYPE).setActionListener(lsh);
        guiButtons.get(LEVEL_START_BUTTON_TYPE).setActionListener(lsh);
        
        GameScreenHandler gsh = new GameScreenHandler(this);
        guiButtons.get(GAME_SCREEN_QUIT_TYPE).setActionListener(gsh);
        guiButtons.get(BOMB_TYPE).setActionListener(gsh);
    }

    @Override
    public void reset() {
        data.reset(this);
    }

    @Override
    public void updateGUI() {
        if (currentScreenState.equals(SAGA_SCREEN_STATE) && (yTarget != guiDecor.get(BACKGROUND_TYPE).getY())) {
            guiDecor.get(BACKGROUND_TYPE).update(this);

            PropertiesManager props = PropertiesManager.getPropertiesManager();
            
            ArrayList<String> rowOne = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
            for (String buttons : rowOne)
                guiButtons.get(buttons).update(this);
            
            ArrayList<String> rowTwo = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
            for (String buttons : rowTwo)
                guiButtons.get(buttons).update(this);
        }
        
        // GO THROUGH THE VISIBLE BUTTONS TO TRIGGER MOUSE OVERS
        Iterator<Sprite> buttonsIt = guiButtons.values().iterator();
        while (buttonsIt.hasNext()) {
            Sprite button = buttonsIt.next();
            
            // ARE WE ENTERING A BUTTON?
            if (button.getState().equals(VISIBLE_STATE)) {
                if (button.containsPoint(data.getLastMouseX(), data.getLastMouseY())) {
                    button.setState(MOUSE_OVER_STATE);
                }
            }
            // ARE WE EXITING A BUTTON?
            else if (button.getState().equals(MOUSE_OVER_STATE)) {
                 if (!button.containsPoint(data.getLastMouseX(), data.getLastMouseY())) {
                    button.setState(VISIBLE_STATE);
                }
            }
        }
    }

    public void scrollSagaScreen(int ID) {
        float y = guiDecor.get(BACKGROUND_TYPE).getY();     
        float maxHeight = guiDecor.get(BACKGROUND_TYPE).getAABBheight() * -1 + this.getDataModel().getGameHeight();
        float v = 0;
        
        if (ID == 15) {          // SCROLL UP
            if (y < -200) {
                yTarget = y + 200;
                v = (yTarget-y)/10;
                guiDecor.get(BACKGROUND_TYPE).setVy(v);
                scrollLevelButtons(v);
            } else {
                yTarget = 0;
                v = (yTarget-y)/8;
                guiDecor.get(BACKGROUND_TYPE).setVy(v);
                scrollLevelButtons(v);
            }
        } else if (ID == 16) {   // SCROLL DOWN
            if (y > maxHeight + 200) {  // far from the bottom
                yTarget = y - 200;
                v = (yTarget-y)/10;
                guiDecor.get(BACKGROUND_TYPE).setVy(v);
                scrollLevelButtons(v);
            } else {    // close to the bottom
                yTarget = maxHeight;
                v = (yTarget-y)/8;
                guiDecor.get(BACKGROUND_TYPE).setVy(v);
                scrollLevelButtons(v);
            }
        }
    }
    
    public void scrollLevelButtons(float val) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> rowOne = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
        for(String buttons : rowOne)
            guiButtons.get(buttons).setVy(val);
        
        ArrayList<String> rowTwo = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
        for(String buttons : rowTwo)
            guiButtons.get(buttons).setVy(val);
    }

    public ZombieCrushSagaRecord getPlayerRecord() { return record; }
    
    /**
     * This method forces the file manager to save the current player record.
     */
    public void savePlayerRecord() { fileManager.saveRecord(record); }
    
    public void setPlayerRecord(ZombieCrushSagaRecord record) { record = record; }
    
    public ZombieCrushSagaErrorHandler getErrorHandler() { return errorHandler; }
    
    public boolean isCurrentScreenState(String testScreenState) {
        return testScreenState.equals(currentScreenState);
    }
    
    public void setLevel(int level) { currentLevel = level; }
    
    public int getLevel() { return currentLevel; }
    
    public void setLevelPath(String path) { levelPath = path; }
    
    public String getLevelPath() { return levelPath; }
    
    public int getMoves() { return movesForLevel; }
    
    public void setMoves(int moves) { movesForLevel = moves; }
    
    public ZombieCrushSagaLevelManager getFileManager() { return fileManager; }

    /**
     * This method updates the game grid boundaries, which will depend
     * on the level loaded.
     */    
    public void updateBoundaries()
    {
        // NOTE THAT THE ONLY ONES WE CARE ABOUT ARE THE LEFT & TOP BOUNDARIES
        float totalWidth = ((ZombieCrushSagaDataModel)data).getGridColumns() * TILE_IMAGE_WIDTH;
        float halfTotalWidth = totalWidth/2.0f;
        float halfViewportWidth = data.getGameWidth()/2.0f;
        boundaryLeft = halfViewportWidth - halfTotalWidth;

        // THE LEFT & TOP BOUNDARIES ARE WHERE WE START RENDERING TILES IN THE GRID
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        float topOffset = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_TOP_OFFSET.toString()));
        float totalHeight = ((ZombieCrushSagaDataModel)data).getGridRows() * TILE_IMAGE_HEIGHT;
        float halfTotalHeight = totalHeight/2.0f;
        float halfViewportHeight = (data.getGameHeight() - topOffset)/2.0f;
        boundaryTop = topOffset + halfViewportHeight - halfTotalHeight;
    }

    @Override
    public void initAudioContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
