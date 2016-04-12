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
import java.util.TreeMap;
import javax.swing.JOptionPane;
import mini_game.Sprite;
import properties_manager.PropertiesManager;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;
import zombiecrushsaga2014.data.ZombieCrushSagaRecord;
import zombiecrushsaga2014.ZombieCrushSaga2014.ZombieCrushSagaPropertyType;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;



/**
 *
 * @author nikhilnair
 */
public class SplashHandler implements ActionListener {
    // THE MAHJONG GAME CONTAINING THE BACK BUTTON

    private ZombieCrushSagaMiniGame game;

    /**
     * This constructor simply inits the object by keeping the game for later.
     *
     * @param initGame The Mahjong game that contains the back button.
     */
    public SplashHandler(ZombieCrushSagaMiniGame initGame) {
        // WE'LL NEED THIS WHEN WE RESPOND, SO KEEP A REFERERNCE
        game = initGame;
    }

    /**
     * This is the method that responds to the click on the back button.
     *
     * @param ae Event object for the back button button click.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        // IF A GAME IS IN PROGRESS, THE PLAYER LOSES
        if (game.isCurrentScreenState(SPLASH_SCREEN_STATE)) {
            //    game.switchToSagaScreen();

            TreeMap<String, Sprite> sprite = game.getGUIButtons();
            Iterator<Sprite> buttonsIt = game.getGUIButtons().values().iterator();

            while (buttonsIt.hasNext()) {
                Sprite button = buttonsIt.next();
                if (button.getState().equals(MOUSE_OVER_STATE)) {
                    if (button.getID() == 1) // IF PLAY BUTTON WAS PRESSED
                    {
                        game.switchToSagaScreen();
                        //ZombieCrushSagaRecord r = game.getFileManager().loadRecord();
                        //game.setPlayerRecord(r);

                        //game.getGUIButtons().get(splash.get(0)).setY(100);
                    } else if (button.getID() == 2) // IF QUIT WAS PRESSED
                    {
                         game.killApplication();
                    } else if (button.getID() == 3) // IF RESET WAS PRESSED
                    {
                        ZombieCrushSagaRecord r = new ZombieCrushSagaRecord();
                        game.setPlayerRecord(r);
                        //game.savePlayerRecord();
                        JOptionPane.showMessageDialog(null, "RESET SUCCESSFUL");
                    }
                }

            }

        }
    }
    
}