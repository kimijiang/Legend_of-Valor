/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Inaccessible.java - A subclass of cell, it represents the cell can't be accessed by heroes.
public class Inaccessible extends Cell{

    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_RED = "\033[31m";

    Inaccessible() {
        super(ANSI_RED + "I" + ANSI_RESET);
    }

}
