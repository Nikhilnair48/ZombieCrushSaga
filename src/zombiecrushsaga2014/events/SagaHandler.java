/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zombiecrushsaga2014.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import mini_game.Sprite;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;
import zombiecrushsaga2014.data.ZombieCrushSagaDataModel;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;

/**
 *
 * @author nikhilnair
 */
public class SagaHandler implements ActionListener {
    
    private ZombieCrushSagaMiniGame miniGame;
    
    /**
     * This constructor simply inits the object by 
     * keeping the game for later.
     * 
     * @param initMiniGame
     * @param initGame The Mahjong game that contains
     * the back button.
     */    
    public SagaHandler(ZombieCrushSagaMiniGame initMiniGame) {
        miniGame = initMiniGame;
    }
    
    /**
     * This method provides the undo response, which is to remove the
     * top two tiles from the tiles stack and send them back to their
     * most recent grid locations.
     * 
     * @param ae The event object.
     */
    @Override
    public void actionPerformed(ActionEvent ae) { 
        if(miniGame.isCurrentScreenState(SAGA_SCREEN_STATE)) { // ARE WE ON THE SAGA SCREEN?
            Iterator<Sprite> buttons = miniGame.getGUIButtons().values().iterator();
            
            while(buttons.hasNext()) { // CHECK ALL BUTTONS
                Sprite button = buttons.next();
                
                if(button.getState().equals(MOUSE_OVER_STATE)) { // CHECK IF THE CURSOR IS OVER THE BUTTON
                    switch(button.getID()) {
                        case 4:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_1.zcs");
                            miniGame.setLevel(1);
                            miniGame.setMoves(6);
                            break;
                        case 5:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_2.zcs");
                            miniGame.setLevel(2);
                            miniGame.setMoves(15);
                            break;
                        case 6:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_3.zcs");
                            miniGame.setLevel(3);
                            miniGame.setMoves(18);
                            break;
                        case 7:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_4.zcs");
                            miniGame.setLevel(4);
                            miniGame.setMoves(15);
                            break;
                        case 8:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_5.zcs");
                            miniGame.setLevel(5);
                            miniGame.setMoves(20);
                            break;
                        case 9:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_6.zcs");
                            miniGame.setLevel(6);
                            miniGame.setMoves(25);
                            break;
                        case 10:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_7.zcs");
                            miniGame.setLevel(7);
                            miniGame.setMoves(50);
                            break;
                        case 11:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_8.zcs");
                            miniGame.setLevel(8);
                            miniGame.setMoves(20);
                            break;
                        case 12:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_9.zcs");
                            miniGame.setLevel(9);
                            miniGame.setMoves(25);
                            break;
                        case 13:
                            miniGame.switchToLevelScoreScreen();
                            miniGame.setLevelPath("./data/./zcs/levels/Level_10.zcs");
                            miniGame.setLevel(10);
                            miniGame.setMoves(40);
                            break;
                        case 14:
                            miniGame.killApplication();
                            break;
                        case 15:
                            miniGame.scrollSagaScreen(button.getID());
                            break;
                        case 16: 
                            miniGame.scrollSagaScreen(button.getID());
                            break;
                        
                    }
                }
            }
        }
    }
}
