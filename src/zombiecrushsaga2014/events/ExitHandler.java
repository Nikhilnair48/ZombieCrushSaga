package zombiecrushsaga2014.events;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import zombiecrushsaga2014.ui.ZombieCrushSagaMiniGame;
import static zombiecrushsaga2014.ZombieCrushSagaConstants.*;

/**
 * This class manages when the user clicks the window X to
 * kill the application.
 * 
 * @author Richard McKenna
 */
public class ExitHandler extends WindowAdapter
{
    private ZombieCrushSagaMiniGame miniGame;
    
    public ExitHandler(ZombieCrushSagaMiniGame initMiniGame)
    {
        miniGame = initMiniGame;
    }
    
    /**
     * This method is called when the user clicks the window'w X. We 
     * respond by giving the player a loss if the game is still going on.
     * 
     * @param we Window event object.
     */
    @Override
    public void windowClosing(WindowEvent we)
    {
        // IF THE GAME IS STILL GOING ON, END IT AS A LOSS
        if (miniGame.isCurrentScreenState(SPLASH_SCREEN_STATE) || miniGame.isCurrentScreenState(SAGA_SCREEN_STATE))
        {
            miniGame.getDataModel().endGameAsLoss();
        }
        // AND CLOSE THE ALL
        System.exit(0);
    }

    
    public void actionPerformed(ActionEvent ae)
    {
        System.exit(0);
    }
}