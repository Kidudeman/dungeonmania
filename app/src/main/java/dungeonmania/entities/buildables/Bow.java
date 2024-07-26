package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.map.GameMap;

public class Bow extends Buildable implements Breakable {
    private int durability;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
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
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 2, 1));
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public static boolean checkBuildCriteria(Inventory items, GameMap map) {
        List<Wood> wood = items.getEntities(Wood.class);
        List<Arrow> arrows = items.getEntities(Arrow.class);
        if (wood.size() >= 1 && arrows.size() >= 3) {
            return true;
        }
        return false;
    }

    @Override
    public void build(Inventory items) {
        List<Wood> wood = items.getEntities(Wood.class);
        List<Arrow> arrows = items.getEntities(Arrow.class);
        items.remove(wood.get(0));
        items.remove(arrows.get(0));
        items.remove(arrows.get(1));
        items.remove(arrows.get(2));
    }
}
