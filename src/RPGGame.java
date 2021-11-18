/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// RPGGame.java - The abstract class of all process the RPG game will proceed.
public abstract class RPGGame {

    protected Map map;
    protected Player player;


    public RPGGame() {

    }

    public abstract void initiateGame();

    public abstract void startGame();


}
