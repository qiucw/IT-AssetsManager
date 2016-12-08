package qiucw.tacoma.uw.edu.tcss445team1;

import java.util.ArrayList;

/**
 * Scorelist for the score in ScoreActivity
 *  @author Chenwei Qiu
 * @version 1.0
 */

public class ScoreList {

    private ArrayList<String> arrayList;

    /**
     * constructor of ScoreList
     */
    public ScoreList(){
        arrayList = new ArrayList<>();
    }

    /**
     * add the string to the arraylist
     * @param string the element that you want to add
     */
    public void add(String string){
        arrayList.add(string);
    }

    /**
     * getter for the arraylsit
     * @return the arraylsit in the ScoreList class
     */
    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
