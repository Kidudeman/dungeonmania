package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.map.GameMap;

public class Sceptre extends Buildable {
    private int mindControlDuration;

    public Sceptre(int mindControlDuration) {
        super(null);
        this.mindControlDuration = mindControlDuration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 0, 0, false, false, true));
    }

    @Override
    public void use(Game game) {
        return;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }

    public static boolean checkBuildCriteria(Inventory items, GameMap map) {

        List<Wood> wood = items.getEntities(Wood.class);
        List<Arrow> arrows = items.getEntities(Arrow.class);
        List<Treasure> treasure = items.getEntities(Treasure.class);
        List<Key> keys = items.getEntities(Key.class);
        List<SunStone> sunStones = items.getEntities(SunStone.class);

        if ((wood.size() >= 1 || arrows.size() >= 2) && (treasure.size() >= 2 || keys.size() >= 1)
                && sunStones.size() >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public void build(Inventory items) {
        List<Wood> wood = items.getEntities(Wood.class);
        List<Arrow> arrows = items.getEntities(Arrow.class);
        List<Key> keys = items.getEntities(Key.class);
        List<SunStone> sunStones = items.getEntities(SunStone.class);
        if (wood.size() >= 1) {
            items.remove(wood.get(0));
        } else {
            items.remove(arrows.get(0));
            items.remove(arrows.get(1));
        }

        if (items.countCoins() >= 1) {
            items.remove(items.getCoins());
        } else if (keys.size() >= 1) {
            items.remove(keys.get(0));
        }
        items.remove(sunStones.get(0));
    }
}
