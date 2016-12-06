package qiucw.tacoma.uw.edu.tcss445team1;

import android.annotation.TargetApi;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Chenwei Qiu on 12/6/2016.
 */

public class ScoreListTest {

    ScoreList scoreList = new ScoreList();

    @Before
    public void initialize(){
        scoreList.add("a");
        scoreList.add("b");
        scoreList.add("c");
    }

    @Test
    public void testGetter(){
        assertEquals(scoreList.getArrayList().size(), 3);
    }

    @Test
    public void addTest(){
        assertEquals(scoreList.getArrayList().get(0).toString(), "a");
        assertEquals(scoreList.getArrayList().get(1).toString(), "b");
        assertEquals(scoreList.getArrayList().get(2).toString(), "c");
    }

}
