package zombiecrushsaga2014;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 * This class stores the types of controls and their possible states which
 * we'll use to control the flow of the application. Note that these control
 * types and states are NOT flavor-specific.
 * @author nikhilnair
 */
public class ZombieCrushSagaConstants {
    
    // WE ONLY HAVE A LIMITIED NUMBER OF UI COMPONENT TYPES IN THIS APP
    
    // TILE SPRITE TYPES
    public static final String TILE_A_TYPE = "TILE_A_TYPE";
    public static final String STRIPED_TYPE = "STRIPED_";
    public static final String COLOR_BOMB = "COLOR_BOMB";
    public static final String WRAPPED_ZOMBIE = "WRAPPED_";
    
    public static final String G_TYPE = "TILE_G_TYPE";
    public static final String R_TYPE = "TILE_R_TYPE";
    public static final String P_TYPE = "TILE_P_TYPE";
    public static final String O_TYPE = "TILE_O_TYPE";
    public static final String Y_TYPE = "TILE_Y_TYPE";
    public static final String BLUE_TYPE = "TILE_BLUE_TYPE";
    public static final String GREEN_TYPE = "TILE_GREEN_TYPE";
    public static final String RED_TYPE = "TILE_RED_TYPE";
    public static final String PURPLE_TYPE = "TILE_PURPLE_TYPE";
    public static final String ORANGE_TYPE = "TILE_ORANGE_TYPE";
    public static final String YELLOW_TYPE = "TILE_YELLOW_TYPE";
    
    public static final String COLORED_TYPE = "COLORED_TYPE";
    public static final String TILE_SPRITE_TYPE_PREFIX = "TILE_";
    
    public static final String BOMB_TYPE = "BOMB_TYPE";
    public static final String BOMB_OVER = "BOMB_OVER";
    
    // EACH SCREEN HAS ITS OWN BACKGROUND TYPE
    public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";
    public static final String SPLASH_BACKGROUND_TYPE = "SPLASH_BACKGROUND_TYPE";
    public static final String SAGA_BACKGROUND_TYPE = "SAGA_BACKGROUND_TYPE";
    public static final String LEVE_SCORE_BACKGROUND_STATE = "LEVE_SCORE_BACKGROUND_STATE";
    public static final String GAME_BACKGROUND_TYPE = "GAME_BACKGROUND_TYPE";
    
    public static final String LEVEL_WON = "LEVEL_WON";
    public static final String LEVEL_LOST = "LEVEL_LOST";
    public static final String LEVEL_TYPES = "LEVEL_TYPES";
    // THIS REPRESENTS THE BUTTONS ON THE SPLASH SCREEN FOR LEVEL SELECTION
    public static final String SPLASH_BUTTON_TYPE = "LEVEL_SELECT_BUTTON_TYPE";

    // IN-GAME UI CONTROL TYPES
    public static final String NEW_GAME_BUTTON_TYPE = "NEW_GAME_BUTTON_TYPE";
    public static final String BACK_BUTTON_TYPE = "BACK_BUTTON_TYPE";
    public static final String TILE_COUNT_TYPE = "TILE_COUNT_TYPE";
    public static final String TIME_TYPE = "TIME_TYPE"; 
    public static final String STATS_BUTTON_TYPE = "STATS_BUTTON_TYPE";
    public static final String UNDO_BUTTON_TYPE = "UNDO_BUTTON_TYPE";
    public static final String TILE_STACK_TYPE = "TILE_STACK_TYPE";

    // DIALOG TYPES
    //public static final String STATS_DIALOG_TYPE = "STATS_DIALOG_TYPE";
    public static final String WIN_DIALOG_TYPE = "WIN_DIALOG_TYPE";
    public static final String LOSS_DIALOG_TYPE = "LOSS_DIALOG_TYPE";
    
    // WE'LL USE THESE STATES TO CONTROL SWITCHING BETWEEN THE TWO
    public static final String SPLASH_SCREEN_STATE = "SPLASH_SCREEN_STATE";
    public static final String GAME_SCREEN_STATE = "GAME_SCREEN_STATE";    
    public static final String SAGA_SCREEN_STATE = "SAGA_SCREEN_STATE";
    public static final String LEVEL_SCREEN_STATE = "LEVEL_SCREEN_STATE";
    
    public static final String PLAY_STATE = "PLAY_STATE";    
    public static final String RESET_STATE = "RESET_STATE";    
    public static final String QUIT_STATE = "QUIT_STATE";    
    
    public static final String SCROLL_UP_BUTTON_TYPE = "SCROLL_UP_BUTTON_TYPE";
    public static final String SCROLL_DOWN_BUTTON_TYPE = "SCROLL_DOWN_BUTTON_TYPE";
    public static final String EXIT_BUTTON_TYPE = "EXIT_BUTTON_TYPE";
    public static final String ONE_BUTTON_TYPE = "ONE_BUTTON_TYPE";
    public static final String TWO_BUTTON_TYPE = "TWO_BUTTON_TYPE";
    public static final String LEVEL_START_BUTTON_TYPE = "LEVEL_START_BUTTON_TYPE";
    public static final String LEVEL_BACK_BUTTON_TYPE = "LEVEL_BACK_BUTTON_TYPE";
    
    public static final String LEVEL_TYPE = "LEVEL_TYPE";
    public static final String LEVEL_TYPE2 = "LEVEL_TYPE2";
    
    //public static final String GOOD_STAR_TYPE = "GOOD_STAR_TYPE";
    public static final String GOOD_STAR_TYPE1 = "GOOD_STAR_TYPE1";
    public static final String GOOD_STAR_TYPE2 = "GOOD_STAR_TYPE2";
    public static final String GOOD_STAR_TYPE3 = "GOOD_STAR_TYPE3";
    
    public static final String PROGRESS_BAR = "PROGRESS_BAR";
    public static final String PROGRESS = "PROGRESS";
    public static final Insets PROGRESS_BAR_CORNERS = new Insets(7, 7, 13, 149);
    
    public static final String GAME_SCREEN_QUIT_TYPE = "GAME_SCREEN_QUI_TYPE";
    
    public static final int SCROLL_VALUE = 200;
    public static final int SCROLL_PACE = SCROLL_VALUE / 5;
    

    // THE TILES MAY HAVE 4 STATES:
        // - INVISIBLE_STATE: USED WHEN ON THE SPLASH SCREEN, MEANS A TILE
            // IS NOT DRAWN AND CANNOT BE CLICKED
        // - VISIBLE_STATE: USED WHEN ON THE GAME SCREEN, MEANS A TILE
            // IS VISIBLE AND CAN BE CLICKED (TO SELECT IT), BUT IS NOT CURRENTLY SELECTED
        // - SELECTED_STATE: USED WHEN ON THE GAME SCREEN, MEANS A TILE
            // IS VISIBLE AND CAN BE CLICKED (TO UNSELECT IT), AND IS CURRENTLY SELECTED     
        // - NOT_AVAILABLE_STATE: USED FOR A TILE THE USER HAS CLICKED ON THAT
            // IS NOT FREE. THIS LET'S US GIVE THE USER SOME FEEDBACK
    public static final String INVISIBLE_STATE = "INVISIBLE_STATE";
    public static final String VISIBLE_STATE = "VISIBLE_STATE";
    public static final String SELECTED_STATE = "SELECTED_STATE";
    public static final String INCORRECTLY_SELECTED_STATE = "NOT_AVAILABLE_STATE";
    public static final String MOUSE_OVER_STATE = "MOUSE_OVER_STATE";

    // THE BUTTONS MAY HAVE 2 STATES:
        // - INVISIBLE_STATE: MEANS A BUTTON IS NOT DRAWN AND CAN'T BE CLICKED
        // - VISIBLE_STATE: MEANS A BUTTON IS DRAWN AND CAN BE CLICKED
        // - MOUSE_OVER_STATE: MEANS A BUTTON IS DRAWN WITH SOME HIGHLIGHTING
            // BECAUSE THE MOUSE IS HOVERING OVER THE BUTTON

    // UI CONTROL SIZE AND POSITION SETTINGS
    
    public static final int UP_X = 1175;
    public static final int UP_Y = 100;
    public static final int DOWN_X = 1175;
    public static final int DOWN_Y = 600;
    public static final int EXIT_X = 1200;
    public static final int EXIT_Y = 40;
    public static final int Y_VALUE = -1800;
    public static final int BUTTON_X = 100;
    public static final int BUTTON_Y = 100;
    public static final int LEVEL_START_BUTTON_X = 700;
    public static final int LEVEL_START_BUTTON_Y = 500;
    public static final int QUIT_X = 1140;
    public static final int QUIT_Y = 40;
    public static final int STAR_X = 150;
    public static final int STAR_Y = 45;
    public static final int STAR_OFFSET = 200;
    public static final int PROGRESS_BAR_X = 60;
    public static final int PROGRESS_BAR_Y = 240;
    public static final int BOMB_X = 90;
    public static final int BOMB_Y = 460;
    
    // OR POSITIONING THE LEVEL SELECT BUTTONS
    public static final int SPLASH_BUTTON_WIDTH = 200;
    public static final int SPLASH_BUTTON_MARGIN = 20;
    public static final int SPLASH_BUTTON_Y = 600;

    // FOR STACKING TILES ON THE GRID
    public static final int NUM_TILES = 144;
    public static final int TILE_IMAGE_OFFSET = 1;
    public static final int TILE_IMAGE_WIDTH = 60;
    public static final int TILE_IMAGE_HEIGHT = 60;
    public static final int Z_TILE_OFFSET = 5;

    // FOR MOVING TILES AROUND
    public static final int MAX_TILE_VELOCITY = 40;
    public static final int TILE_STACK_OFFSET_X = 30;
    public static final int TILE_STACK_OFFSET_Y = 12;
    public static final int TILE_STACK_2_OFFSET_X = 105;

    public static final int TILE_STACK_X = 590;
    public static final int TILE_STACK_Y = -100;
    
    // UI CONTROLS POSITIONS IN THE GAME SCREEN
    
    // STATS DIALOG COORDINATES
    public static final int STATS_LEVEL_INC_Y = 30;
    public static final int STATS_LEVEL_X = 460;
    public static final int STATS_LEVEL_Y = 300;
    public static final int STATS_GAMES_Y = STATS_LEVEL_Y + (STATS_LEVEL_INC_Y * 2);
    public static final int STATS_WINS_Y = STATS_GAMES_Y + STATS_LEVEL_INC_Y;
    public static final int STATS_LOSSES_Y = STATS_WINS_Y + STATS_LEVEL_INC_Y;
    public static final int STATS_WIN_PERC_Y = STATS_LOSSES_Y + STATS_LEVEL_INC_Y;
    public static final int STATS_FASTEST_Y = STATS_WIN_PERC_Y + STATS_LEVEL_INC_Y;
    
    // TEXT TO PUT INSIDE THE STATS DIALOG
    public static final String STATS_LEVEL_GAMES_TEXT = "Games: ";
    public static final String STATS_LEVEL_WINS_TEXT = "Wins: ";
    public static final String STATS_LEVEL_LOSSES_TEXT = "Losses: ";
    public static final String STATS_LEVEL_WIN_PERC_TEXT = "Win%: ";
    public static final String STATS_LEVEL_FASTEST_TEXT = "Fastest Win: ";
    
    // THESE ARE USED FOR FORMATTING THE TIME OF GAME
    public static final long MILLIS_IN_A_SECOND = 1000;
    public static final long MILLIS_IN_A_MINUTE = 1000 * 60;
    public static final long MILLIS_IN_AN_HOUR  = 1000 * 60 * 60;

    // USED FOR DOING OUR VICTORY ANIMATION
    public static final int WIN_PATH_NODES = 4;
    public static final int WIN_PATH_TOLERANCE = 100;
    public static final int WIN_PATH_COORD = 100;

    // COLORS USED FOR RENDERING VARIOUS THINGS, INCLUDING THE
    // COLOR KEY, WHICH REFERS TO THE COLOR TO IGNORE WHEN
    // LOADING ART.
    public static final Color COLOR_KEY = new Color(255, 174, 201);
    public static final Color DEBUG_TEXT_COLOR = Color.BLACK;
    public static final Color TEXT_DISPLAY_COLOR = new Color (10, 160, 10);
    public static final Color SELECTED_TILE_COLOR = new Color(255,255,0,100);
    public static final Color INCORRECTLY_SELECTED_TILE_COLOR = new Color(255, 50, 50, 100);
    public static final Color STATS_COLOR = new Color(0, 60, 0);

    // FONTS USED DURING FOR TEXTUAL GAME DISPLAYS
    public static final Font TEXT_DISPLAY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    public static final Font DISPLAY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    public static final Font DEBUG_TEXT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    public static final Font STATS_FONT = new Font(Font.MONOSPACED, Font.BOLD, 36);
    public static final Font PERCENT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);
    
    // AND AUDIO STUFF
    public static final String SUCCESS_AUDIO_TYPE = "SUCCESS_AUDIO_TYPE";
    public static final String FAILURE_AUDIO_TYPE = "FAILURE_AUDIO_TYPE";
    public static final String THEME_SONG_TYPE = "THEME_SONG_TYPE";
}
