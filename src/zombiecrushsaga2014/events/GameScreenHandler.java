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
public class GameScreenHandler implements ActionListener {

    // THE MAHJONG GAME CONTAINING THE UNDO BUTTON
    private ZombieCrushSagaMiniGame miniGame;
    
    /**
     * This constructor simply inits the object by 
     * keeping the game for later.
     * 
     * @param initGame The Mahjong game that contains
     * the back button.
     */
    public GameScreenHandler(ZombieCrushSagaMiniGame initGame) {
        miniGame = initGame;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // ARE WE ON THE GAME SCREEN? THEN PROCEED IF NON OF THE TILES ARE MOVING.
        if(miniGame.isCurrentScreenState(GAME_SCREEN_STATE) && ((ZombieCrushSagaDataModel)miniGame.getDataModel()).getMovingTiles().isEmpty()) {
            Iterator<Sprite> buttons = miniGame.getGUIButtons().values().iterator();
            
            while(buttons.hasNext()) {
                Sprite button = buttons.next();
                if (button.getState().equals(MOUSE_OVER_STATE)) {
                    if(button.getID() == 19)    // QUIT GAME
                        ((ZombieCrushSagaDataModel)miniGame.getDataModel()).endGame();
                    else if(button.getID() == 21)
                        ((ZombieCrushSagaDataModel)miniGame.getDataModel()).activateSpecialChainsaw();
                }
            }
        }
    }
    
}
