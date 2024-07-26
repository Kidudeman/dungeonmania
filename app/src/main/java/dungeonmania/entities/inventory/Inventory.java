package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.BuildableRegistry;
import dungeonmania.entities.buildables.MidnightArmour;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.buildables.Shield;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.map.GameMap;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public List<String> getBuildables(Inventory items, GameMap map) {

        List<String> result = new ArrayList<>();
        List<String> entities = Arrays.asList("bow", "shield", "sceptre", "midnight_armour");
        boolean check = false;
        for (String entity : entities) {
            try {
                buildableRegistration(entity);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
            check = BuildableRegistry.checkCriteria(entity, items, map);
            if (check) {
                result.add(entity);
            }
        }

        return result;
    }

    public InventoryItem checkBuildCriteria(Player p, boolean remove, String entity, EntityFactory factory,
            GameMap map) {

        Inventory inventory = p.getInventory();
        try {
            buildableRegistration(entity);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        switch (entity) {
        case "bow":
            Bow bow = factory.buildBow();
            bow.build(inventory);
            return bow;
        case "shield":
            Shield shield = factory.buildShield();
            shield.build(inventory);
            return shield;
        case "sceptre":
            Sceptre sceptre = factory.buildScepter();
            sceptre.build(inventory);
            return sceptre;
        case "midnight_armour":
            MidnightArmour midnightArmour = factory.buildMidnightArmour();
            midnightArmour.build(inventory);
            return midnightArmour;
        default:
            return null;
        }
    }

    public void buildableRegistration(String entity) throws InvalidActionException {
        if (BuildableRegistry.getBuildableCriteria(entity) == null) {
            switch (entity) {
            case "bow":
                BuildableRegistry.registerBuildable("bow", Bow::checkBuildCriteria);
            case "shield":
                BuildableRegistry.registerBuildable("shield", Shield::checkBuildCriteria);
            case "sceptre":
                BuildableRegistry.registerBuildable("sceptre", Sceptre::checkBuildCriteria);
            case "midnight_armour":
                BuildableRegistry.registerBuildable("midnight_armour", MidnightArmour::checkBuildCriteria);
            default:
                return;
            }
        }
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public Treasure getCoins() {
        for (InventoryItem item : items)
            if (item.getClass() == Treasure.class)
                return Treasure.class.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public int countCoins() {
        int count = 0;
        for (InventoryItem item : items)
            if (item.getClass() == Treasure.class)
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

}
