

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Utils.java - Tools to obtain a list of random number and scan text files, which will be used sometimes.
public class Utils {

    // create a list of random numbers
    public static List<Integer> randomNumList(int size, int maxNum) {
        List<Integer> numList = new ArrayList<Integer>();
        Random rd = new Random();
        while(numList.size() < size) {
            int num = rd.nextInt(maxNum);
            if(!numList.contains(num)) {
                numList.add(num);
            }
        }
        return numList;
    }


    // scan text files to create object list
    public static List scanTextFile(String filePath) {
        List objectList = null;
        List<String> lines = Collections.emptyList();
        try {
//            FileInputStream fin = new FileInputStream(filePath);
//            InputStreamReader reader = new InputStreamReader(fin);
//            BufferedReader buffReader = new BufferedReader(reader);
//            String[] attributes = buffReader.readLine().split("/");
            lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
//            String data = "";
            if(filePath.contains("Armory")) {
                objectList = new ArrayList<Armor>();
                for(int i = 1; i<lines.size(); i++){
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Armor(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Double.valueOf(stats[3])));
                }
            }
            else if(filePath.contains("Weaponry")) {
                objectList = new ArrayList<Weapon>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Weapon(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Double.valueOf(stats[3]), Integer.parseInt(stats[4])));
                }
            }
            else if(filePath.contains("Potions")) {
                objectList = new ArrayList<Potion>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    List<String> affectedAtt;
                    if (stats[4].equals("All"))
                        affectedAtt = List.of(stats[5].split("/"));
                    else
                        affectedAtt = List.of(stats[4].split("/"));
                    objectList.add(new Potion(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Double.valueOf(stats[3]), affectedAtt));
                }
            }
            else if(filePath.contains("FireSpells")) {
                objectList = new ArrayList<FireSpell>();
                for (int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new FireSpell(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4])));
                }
            }
            else if(filePath.contains("IceSpells")) {
                objectList = new ArrayList<IceSpell>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new IceSpell(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4])));
                }
            }
            else if(filePath.contains("LightningSpells")) {
                objectList = new ArrayList<LightningSpell>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new LightningSpell(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4])));
                }
            }
            else if(filePath.contains("Warriors")) {
                objectList = new ArrayList<Warrior>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Warrior(stats[0], Double.valueOf(stats[1]), Double.valueOf(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[6])));
                }
            }
            else if(filePath.contains("Sorcerers")) {
                objectList = new ArrayList<Sorcerer>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Sorcerer(stats[0], Double.valueOf(stats[1]), Double.valueOf(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[6])));
                }
            }
            else if(filePath.contains("Paladins")) {
                objectList = new ArrayList<Paladin>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Paladin(stats[0], Double.valueOf(stats[1]), Double.valueOf(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[6])));
                }
            }
            else if(filePath.contains("Dragons")) {
                objectList = new ArrayList<Dragon>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Dragon(stats[0], Integer.parseInt(stats[1]), Double.valueOf(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4])));
                }
            }
            else if(filePath.contains("Exoskeletons")) {
                objectList = new ArrayList<Exoskeleton>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Exoskeleton(stats[0], Integer.parseInt(stats[1]), Double.valueOf(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4])));
                }
            }
            else if(filePath.contains("Spirits")) {
                objectList = new ArrayList<Spirit>();
                for(int i = 1; i<lines.size(); i++) {
                    String[] stats = lines.get(i).split("\\s+");
                    objectList.add(new Spirit(stats[0], Integer.parseInt(stats[1]), Double.valueOf(stats[2]), Double.valueOf(stats[3]), Double.valueOf(stats[4])));
                }
            }
//            buffReader.close();
        }
        catch (IOException e) {
            System.out.println("Something went wrong to read " + filePath);
            e.printStackTrace();
        }
        return objectList;
    }

}
