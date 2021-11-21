/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Map.java - Store information of each cell in the map and heroes team can access on the map.
public class Map {

    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";


    private int length_side;
    private int num_cells;
    private int[] location_player;
    private int[][] heroPostion;
    private List<Integer[]> monsterPostion;
    private Cell[][] cells;

    Map(int n) {
        length_side = n;
        num_cells = n * n;
        location_player = new int[2];
        cells = new Cell[n][n];
        randomlyAssigned();
        initiateLocation();
    }

    // All cells are randomly assigned with CommonSpace, Inaccessible and Market
    public void randomlyAssigned() {

        for(int row = 0; row < length_side; row++){
            for(int col = 0; col < length_side; col++){
                if (row == 2 || row == 5){
                    cells[row][col] = new Inaccessible();
                    continue;
                }
                if (col == 0 || col == 7){
                    List<Spell> spellList = new ArrayList<>();
                    spellList.addAll(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/FireSpells.txt"));
                    spellList.addAll(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/IceSpells.txt"));
                    spellList.addAll(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/LightningSpells.txt"));
                    cells[row][col] = new NexusCell(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Weaponry.txt"),
                            Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Armory.txt"),
                            spellList,
                            Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Potions.txt"));
                }

                Random r = new Random();
                int n = r.nextInt(5);
                if (n == 0){
                    cells[row][col] = new BushCell();
                } else if (n == 1){
                    cells[row][col] = new CaveCell();
                } else if (n == 2){
                    cells[row][col] = new KoulouCell();
                } else {
                    cells[row][col] = new PlainCell();
                }
            }
        }
//        int num_inAcc = (int) (num_cells * 0.2);
//        int num_market = (int) (num_cells * 0.3);
//        int num_common = num_cells - num_inAcc - num_market;
//        List<Integer> numList = Utils.randomNumList(num_inAcc + num_market, num_cells);
//        for(int i = 0; i<numList.size(); i++) {
//            int index = numList.get(i);
//            int row = index / length_side;
//            int column = index % length_side;
//            if(i < num_inAcc)
//                cells[row][column] = new Inaccessible();
//            else  {
//                List<Spell> spellList = new ArrayList<>();
//                spellList.addAll(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/FireSpells.txt"));
//                spellList.addAll(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/IceSpells.txt"));
//                spellList.addAll(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/LightningSpells.txt"));
//                cells[row][column] = new Market(Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Weaponry.txt"),
//                        Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Armory.txt"),
//                        spellList,
//                        Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Potions.txt"));
//            }
//        }
//        // assigned with CommonSpace
//        for(int i = 0; i< num_cells; i++) {
//            int row = i / length_side;
//            int column = i % length_side;
//            if(!numList.contains(i))
//                cells[row][column] = new CommonSpace();
//        }
    }


    public void initiateLocation() {
        this.heroPostion[0][0] = 0;
        this.heroPostion[0][1] = 7;
        this.heroPostion[1][0] = 3;
        this.heroPostion[1][1] = 7;
        this.heroPostion[2][0] = 6;
        this.heroPostion[2][1] = 7;
        // randomly assign the initial location of player
//        Random rd = new Random();
//        location_player[0] = rd.nextInt(length_side);
//        location_player[1] = rd.nextInt(length_side);
//        while(!(cells[location_player[0]][location_player[1]] instanceof CommonSpace)) {
//            location_player[0] = rd.nextInt(length_side);
//            location_player[1] = rd.nextInt(length_side);
//        }
    }

    public void monstersSpawn(){
        monsterPostion.add(new Integer[]{0,0});
        monsterPostion.add(new Integer[]{3,0});
        monsterPostion.add(new Integer[]{6,0});
    }

    public void newMonster(int x, int y){
        monsterPostion.add(new Integer[]{x,y});
    }

    public void updateLocation(int x, int y, int hero) {
        heroPostion[hero][0] = x;
        heroPostion[hero][1] = y;
    }

    public void updateMonsterLocation(int monster){
        monsterPostion.get(monster)[1] += 1;
    }


    public void displayMap() {
        // display the whole map
        System.out.println(ANSI_RED + "X" + ANSI_RESET + ":Inaccessible " + ANSI_BLUE + "N" + ANSI_RESET + ":Nexus  "+ ANSI_GREEN +"*" +ANSI_RESET+":Heroes Team");
        for(int i = 0; i<length_side; i++) {
            drawRowBoarder(i);
            drawCellContent(i);
            drawRowBoarder(i);
            System.out.println();
        }
        drawLine();
        System.out.println("W/w: move up, A/a: move left, S/s: move down, D/d: move right, Q/q: quit game, I/i: show information, G/g: show inventory.");
    }

    private void drawRowBoarder(int row){
        for (int col = 0; col<length_side; col++){
            String sign = cells[col][row].getSign();
            System.out.print(sign+" - "+sign+" - "+sign+"  ");
        }
        System.out.println();
    }

    private void drawCellContent(int row){
        for (int col = 0; col<length_side; col++){
            String hero = "  ";
            String monster = "  ";
            for (int i = 0; i<heroPostion.length; i++){
                if (heroPostion[i][0] == col && heroPostion[i][1] == row){
                    hero = "H" + (i+1);
                }
            }
            for (int i = 0; i<monsterPostion.size(); i++){
                if (monsterPostion.get(i)[0] == col && monsterPostion.get(i)[1] == row){
                    monster = "M" + (i+1);
                }
            }
            System.out.print("| "+hero+" "+monster+" |  ");
        }
        System.out.println();
    }

    private void drawLine() {
        // draw every single horizontal line
        for(int i = 0; i<length_side; i++)
            System.out.print("+---");
        System.out.println("+");
    }

    private void drawCell(int row) {
        // draw every single cell
        for(int i = 0; i<length_side; i++)
            if(location_player[0] == i && location_player[1] == row)
                System.out.print("| " + ANSI_GREEN + "*" + ANSI_RESET + " ");
            else
                System.out.format("| %s ", cells[i][row].getSign());
        System.out.println("|");

    }

    public int[] getLocation_player() {return location_player;}

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public int getLength_side() {return length_side;}

    public boolean checkCell(int x,int y){
        if(cells[x][y].getFlag()==1){
            return false;
        }
        return true;
    }

    public void setCell(int x,int y,int flag) {
        cells[x][y].setFlag(flag);
    }
    public int[][] getLocationOfHeroes(){
        return heroPostion;
    }

    public int[][] getLocationOfMonsters(){
        int[][] a = new int[monsterPostion.size()][monsterPostion.get(0).length];
        int i = 0;
        for (Integer[] n : monsterPostion){
            a[i][0] = n[0];
            a[i][1] = n[1];
        }
        return a;
    }

    public int getEnemy(int monster){
        int monsterRow = monsterPostion.get(monster)[1]+1;
        int monsterCol = monsterPostion.get(monster)[0];
        for(int i = 0; i<heroPostion.length; i++){
            int heroRow = heroPostion[i][1];
            int heroCol = heroPostion[i][0];
            if ((heroCol == monsterCol && heroRow == monsterRow) ||
                    (heroCol == monsterCol+1 && heroRow == monsterRow) ||
                    (heroCol == monsterCol-1 && heroRow == monsterRow)){
                return i;
            }
        }
        return -1;
    }


}
