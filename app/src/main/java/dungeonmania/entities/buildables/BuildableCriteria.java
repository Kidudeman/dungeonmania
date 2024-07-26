package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.Inventory;
import dungeonmania.map.GameMap;

public interface BuildableCriteria {
    boolean checkCriteria(Inventory items, GameMap map);
}
