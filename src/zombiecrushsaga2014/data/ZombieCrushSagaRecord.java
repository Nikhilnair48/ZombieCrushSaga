/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zombiecrushsaga2014.data;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author nikhilnair
 */
public class ZombieCrushSagaRecord {
    
    private HashMap<String, ZombieCrushSagaLevelRecord> levelRecords;
    
    public ZombieCrushSagaRecord() {
    levelRecords = new HashMap();
    }

    /**
     * This method gets the games played for a given level.
     * 
     * @param levelName Level for the request.
     * 
     * @return The number of games played for the levelName level.
     */
    public int getHighestScore(String levelName) 
    {
        ZombieCrushSagaLevelRecord rec = levelRecords.get(levelName);

        // IF levelName ISN'T IN THE RECORD OBJECT
        // THEN SIMPLY RETURN 0
        if (rec == null)
            return 0;
        // OTHERWISE RETURN THE GAMES PLAYED
        else
            return rec.highestScore; 
    }
    
    public int getStars(String levelName) {
        ZombieCrushSagaLevelRecord rec = levelRecords.get(levelName);
        
        if(rec == null)
            return 0;
        else
            return rec.stars;
    }
    
    /**
     * Adds the record for a level
     * @param levelName
     * @param rec 
     */
    public void addZombieCrushLevelRecord(String levelName, ZombieCrushSagaLevelRecord rec) {
        levelRecords.remove(levelName);
        levelRecords.put(levelName, rec);
    }
    
    /**
     * This method adds a win to the current player's record according
     * to the level being played.
     * 
     * @param levelName The level being played that the player won.
     * @param score The final score the current level 
     * @param The stars received fro the current level
     */
    public void addScore(String levelName, int score, int stars)
    {
        // GET THE RECORD FOR levelName
        ZombieCrushSagaLevelRecord rec = levelRecords.remove(levelName);
        
        if(rec == null) {
            rec = new ZombieCrushSagaLevelRecord();
            rec.highestScore = score;
            rec.stars = stars;
            levelRecords.put(levelName, rec);
        }
        else {
            rec.highestScore = score;
            rec.stars = stars;
        }
    }
    
        /**
     * This method constructs and fills in a byte array with all the
     * necessary data stored by this object. We do this because writing
     * a byte array all at once to a file is fast. Certainly much faster
     * than writing to a file across many write operations.
     * 
     * @return A byte array filled in with all the data stored in this
     * object, which means all the player records in all the levels.
     * 
     * @throws IOException Note that this method uses a stream that
     * writes to an internal byte array, not a file. So this exception
     * should never happen.
     */
    public byte[] toByteArray() throws IOException
    {
        Iterator<String> keysIt = levelRecords.keySet().iterator();
        int numLevels = levelRecords.keySet().size();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(numLevels);
        while(keysIt.hasNext())
        {
            String key = keysIt.next();
            dos.writeUTF(key);
            ZombieCrushSagaLevelRecord rec = levelRecords.get(key);
            dos.writeInt(rec.highestScore);
            dos.writeInt(rec.stars);
            
        }
        // AND THEN RETURN IT
        return baos.toByteArray();
    }
    
}
