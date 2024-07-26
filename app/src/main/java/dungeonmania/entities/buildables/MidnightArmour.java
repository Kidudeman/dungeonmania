package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.enemies.ZombieToast;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.map.GameMap;

public class MidnightArmour extends Buildable {
    private double midnightArmourAttack;
    private double midnightArmourDefence;

    public MidnightArmour(double midnightArmourAttack, double midnightArmourDefence) {
        super(null);
        this.midnightArmourAttack = midnightArmourAttack;
        this.midnightArmourDefence = midnightArmourDefence;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(0, midnightArmourAttack, midnightArmourDefence, 1, 1));
    }

    @Override
    public void use(Game game) {
    }

    public static boolean checkBuildCriteria(Inventory items, GameMap map) {
        List<Sword> swords = items.getEntities(Sword.class);
        List<SunStone> sunStones = items.getEntities(SunStone.class);
        List<ZombieToast> zombies = map.getEntities(ZombieToast.class);

        if (swords.size() >= 1 && sunStones.size() >= 1 && zombies.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void build(Inventory items) {
        List<Sword> swords = items.getEntities(Sword.class);
        List<SunStone> sunStones = items.getEntities(SunStone.class);
        items.remove(swords.get(0));
        items.remove(sunStones.get(0));
    }

}
