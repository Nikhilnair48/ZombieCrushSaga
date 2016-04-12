/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zombiecrushsaga2014.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import mini_game.Sprite;
import properties_manager.PropertiesManager;
import zombiecrushsaga2014.ZombieCrushSaga2014.ZombieCrushSagaPropertyType;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;
import zombiecrushsaga2014.level.ZombieCrushSagaLevelManager;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;

/**
 *
 * @author nikhilnair
 */
public class LevelScoreHandler implements ActionListener {

    // THE MAHJONG GAME CONTAINING THE BACK BUTTON
    private ZombieCrushSagaMiniGame game;
    
    
    /**
     * This constructor simply inits the object by 
     * keeping the game for later.
     * 
     * @param initGame The Mahjong game that contains
     * the back button.
     */
    public LevelScoreHandler(ZombieCrushSagaMiniGame initGame) {
        game = initGame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.isCurrentScreenState(LEVEL_SCREEN_STATE)) {
            Iterator<Sprite> buttons = game.getGUIButtons().values().iterator();
        
            while(buttons.hasNext()) {
                Sprite button = buttons.next();
                if(button.getState().equals(MOUSE_OVER_STATE)) {
                    if(button.getID() == 17) {  // BACK BUTTON ID=17
                        game.switchToSagaScreen();
                        break;
                    } else {                    // START BUTTON ID=18
                        setLevel();
                        game.switchToGameScreen();
                        break;
                    }
                } 
            }   // end while
        }
    }   // end method
    
    public void setLevel() {
        ZombieCrushSagaLevelManager fileManager = game.getFileManager();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String dataPath = props.getProperty(ZombieCrushSagaPropertyType.DATA_PATH);
        ArrayList<String> levels = new ArrayList<String>();
        int levelNum = game.getLevel();
        
        if(levelNum <= 5) {
            levels = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_ONE_LEVEL_MAP);
            fileManager.loadLevel(dataPath + levels.get(levelNum-1));
        } else {
            levels = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.SAGA_ROW_TWO_LEVEL_MAP);
            fileManager.loadLevel(dataPath + levels.get(levelNum-6));
            fileManager.loadJelly(dataPath + levels.get(levelNum-6));
        }
    }
}
