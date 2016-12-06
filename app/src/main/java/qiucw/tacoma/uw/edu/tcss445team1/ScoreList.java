package qiucw.tacoma.uw.edu.tcss445team1;

import java.util.ArrayList;

/**
 * Created by Chenwei Qiu on 12/5/2016.
 */

public class ScoreList {

    private ArrayList<String> arrayList;

    public ScoreList(){
        arrayList = new ArrayList<>();
    }

    public void add(String string){
        arrayList.add(string);
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    @Override
    public String toString() {
        return arrayList.toString();
    }
}
