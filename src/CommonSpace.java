/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */


// CommonSpace.java -  A subclass of cell, fight will happen in the common space under a probability.
public abstract class CommonSpace extends Cell{

    CommonSpace(String sign) {
        super(sign);
    }

    public boolean ifEngageBattle() {
        // roll a dice
        if(0.5 > Math.random())
            return true;
        else
            return false;
    }

}
