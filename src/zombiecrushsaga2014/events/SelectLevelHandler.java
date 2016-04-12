/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zombiecrushsaga2014.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;

/**
 *
 * @author nikhilnair
 */
public class SelectLevelHandler implements ActionListener
{
    // HERE'S THE GAME WE'LL UPDATE
    private ZombieCrushSagaMiniGame game;
    
    // HERE'S THE LEVEL TO LOAD
    private String levelFile;
    
    public SelectLevelHandler(ZombieCrushSagaMiniGame initGame) {
        game = initGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.isCurrentScreenState(SAGA_SCREEN_STATE)) {
            
        }
    }
    
}