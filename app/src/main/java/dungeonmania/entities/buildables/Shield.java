package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.map.GameMap;

public class Shield extends Buildable implements Breakable {
    private int durability;
    private double defence;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = durability;
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.removeItemFromPlayer(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence, 1, 1));
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public static boolean checkBuildCriteria(Inventory items, GameMap map) {
        List<Wood> wood = items.getEntities(Wood.class);
        List<Treasure> treasure = items.getEntities(Treasure.class);
        List<Key> keys = items.getEntities(Key.class);
        if (wood.size() >= 2 && (treasure.size() >= 1 || keys.size() >= 1)) {
            return true;
        }
        return false;
    }

    @Override
    public void build(Inventory items) {
        List<Wood> wood = items.getEntities(Wood.class);
        List<Key> keys = items.getEntities(Key.class);
        items.remove(wood.get(0));
        items.remove(wood.get(1));
        if (items.countCoins() >= 1) {
            items.remove(items.getCoins());
        } else if (keys.size() >= 1) {
            items.remove(keys.get(0));
        }
    }

}
