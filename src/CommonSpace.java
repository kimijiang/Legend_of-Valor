/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */


// CommonSpace.java -  A subclass of cell, fight will happen in the common space under a probability.
public class CommonSpace extends Cell{

    CommonSpace() {
        super(" ");
    }

    public boolean ifEngageBattle() {
        // roll a dice
        if(0.5 > Math.random())
            return true;
        else
            return false;
    }

}
