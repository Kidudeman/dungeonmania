package dungeonmania.entities.inventory;

/**
 * A marker interface for InventoryItem
 */
public interface InventoryItem {
    default boolean add(InventoryItem item, Inventory inventory) {
        inventory.add(item);
        return true;
    }
}
