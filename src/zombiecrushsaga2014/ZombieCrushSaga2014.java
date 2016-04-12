package zombiecrushsaga2014;

import java.util.logging.Level;
import java.util.logging.Logger;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;


/**
 *
 * @author nikhilnair
 */
public class ZombieCrushSaga2014 {
    
    // MINIGAME CONTAINS FULL INTERFACE, AND EVENTUALLY WILL 
    // THE KEY IN RUNNING THE UI AND EVERYTHING ELSE
    static ZombieCrushSagaMiniGame miniGame = new ZombieCrushSagaMiniGame();
    
    
    // WE'LL LOAD ALL THE UI AND ART PROPERTIES FROM FILES,
    // BUT WE'LL NEED THESE VALUES TO START THE PROCESS
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "./data/";
    
    public static void main(String[] args) {
        try {
            
        // LOAD SETTINGS FOR STARTING THE APP
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(ZombieCrushSagaPropertyType.UI_PROPERTIES_FILE_NAME, UI_PROPERTIES_FILE_NAME);
        props.addProperty(ZombieCrushSagaPropertyType.PROPERTIES_SCHEMA_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
        props.addProperty(ZombieCrushSagaPropertyType.DATA_PATH, DATA_PATH);
        props.loadProperties(UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
        
        // LOAD FLAVOR AS SPECIFIED BY THE PROPERTIES FILE
        String gameFlavorFile = props.getProperty(ZombieCrushSagaPropertyType.GAME_FLAVOR_FILE_NAME);
        props.loadProperties(gameFlavorFile, PROPERTIES_SCHEMA_FILE_NAME);
        
        // LOAD THE UI, WHICH WILL USE FLAVORED CONTENT
        String appTitle = props.getProperty(ZombieCrushSagaPropertyType.GAME_TITLE_TEXT);
        int fps = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.FPS));
        miniGame.initMiniGame(appTitle, fps);
        miniGame.startGame();
        
        } catch (InvalidXMLFileFormatException ex) {
            Logger.getLogger(ZombieCrushSaga2014.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public enum ZombieCrushSagaPropertyType {
        /* SETUP FILE NAMES *//* SETUP FILE NAMES */
        UI_PROPERTIES_FILE_NAME,
        PROPERTIES_SCHEMA_FILE_NAME,
        GAME_FLAVOR_FILE_NAME,
        DATA_PATH,
        RECORD_FILE_NAME,
        
        GAME_TITLE_TEXT,
        FPS,
        
        ERROR_DIALOG_TITLE_TEXT,
        GAME_WIDTH,
        GAME_HEIGHT,
        GAME_LEFT_OFFSET,
        GAME_TOP_OFFSET,
        IMG_PATH,
        WINDOW_ICON,
        
        BLANK_TILE_IMAGE_NAME, 
        BLANK_TILE_SELECTED_IMAGE_NAME,
        SPLASH_OPTIONS, 
        SPLASH_IMAGE_OPTIONS, 
        SPLASH_MOUSE_OVER_IMAGE_OPTIONS,
        
        
        /* BACKGROUNDS FOR THE FOUR SCREENS TO BE PRESENTED*/
        SPLASH_SCREEN_IMAGE_NAME,
        SAGA_BACKGROUND_IMAGE_NAME,
        LEVE_SCORE_BACKGROUND_IMAGE_NAME,
        GAME_BACKGROUND_IMAGE_NAME,
        
        /* SAGA SCREEN - LEVEL BUTTONS. ROW ONE CONTAINS LEVELS 1-5, ROW TWO CONTAINS LEVELS 6-10. */
        SAGA_LEVEL_BUTTONS_ROW_ONE,
        SAGA_LEVEL_BUTTONS_ROW_TWO,
        SAGA_LEVEL_BUTTONS_ROW_ONE_MOUSE_OVER,
        SAGA_LEVEL_BUTTONS_ROW_TWO_MOUSE_OVER,
        
        /* LEVEL MAP POITNS TO THE ".ZCS" FILES THAT CONTAINS THE LEVELS ITSELF*/
        SAGA_ROW_ONE_LEVEL_MAP,
        SAGA_ROW_TWO_LEVEL_MAP,
        
        /* SAGA BUTTONS */
        SAGA_EXIT_BUTTON,
        SAGA_EXIT_BUTTON_MOUSE_OVER,
        SAGA_SCROLL_DOWN_BUTTON,
        SAGA_SCROLL_DOWN_BUTTON_MOUSE_OVER,
        SAGA_SCROLL_UP_BUTTON,
        SAGA_SCROLL_UP_BUTTON_MOUSE_OVER,
        
        /* LEVEL SCORE BUTTONS*/
        LEVEL_SCORE_BACK_BUTTON,
        LEVEL_SCORE_BACK_BUTTON_MOUSE_OVER,
        LEVEL_SCORE_START_BUTTON,
        LEVEL_SCORE_START_BUTTON_MOUSE_OVER,
        LEVEL_SCORE_STARS,
        WIN_DIALOG,
        LOSS_DIALOG,
        
        /* GAME SCREEN BUTTONS*/
        GAME_SCREEN_QUIT_BUTTON,
        GAME_SCREEN_QUIT_BUTTON_MOUSE_OVER,
        PROGRESS_BAR, 
        BOMB,
        BOMB_MOUSE_OVER,
        
        /* ERROR TYPES */
        LOAD_LEVEL_ERROR,
        
        REGULAR_ZOMBIES, 
        STRIPED_ZOMBIES, 
        COLOR_BOMB, 
        WRAPPED_ZOMBIES,
        
    }
    
}
