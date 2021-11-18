/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Inventory.java - Store all things in hero's inventory.
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Equipment> equipments;

    Inventory() {
        equipments = new ArrayList<Equipment>();
    }

    public void add(Equipment item) {
        equipments.add(item);
    }

    public int getSize() {
        return equipments.size();
    }

    public void remove(Equipment item) {
        equipments.remove(item);
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

}
