package dungeonmania.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity implements Battleable, Overlappable {
    enum PlayerState {
        BaseState, InvincibleState, InvisibleState, SceptreActiveState
    };

    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 5.0;
    private BattleStatistics battleStatistics;
    private Inventory inventory;
    private Queue<Potion> queuePotion = new LinkedList<>();
    private Potion inEffectivePotion = null;
    private Queue<Sceptre> queueSceptre = new LinkedList<>();
    private Sceptre inEffectiveSceptre = null;
    private int nextTriggerPotion = 0;
    private int nextTriggerSceptre = 0;

    private int collectedTreasureCount = 0;
    private int enemiesKilled = 0;

    private PlayerState state;

    public Player(Position position, double health, double attack) {
        super(position);
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_PLAYER_DAMAGE_REDUCER);
        inventory = new Inventory();
        state = PlayerState.BaseState;
    }

    public int getCollectedTreasureCount() {
        return collectedTreasureCount;
    }

    public boolean hasWeapon() {
        return inventory.hasWeapon();
    }

    public BattleItem getWeapon() {
        return inventory.getWeapon();
    }

    public void useWeapon(Game game) {
        this.getWeapon().use(game);
    }

    public List<String> getBuildables(GameMap map) {
        List<String> buildableList = inventory.getBuildables(inventory, map);
        return buildableList;
    }

    public boolean build(String entity, EntityFactory factory, GameMap map) {
        InventoryItem item = inventory.checkBuildCriteria(this, true, entity, factory, map);
        if (item == null)
            return false;
        return inventory.add(item);
    }

    public void move(GameMap map, Direction direction) {
        this.setFacing(direction);
        map.moveTo(this, Position.translateBy(this.getPosition(), direction));
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Enemy) {
            if (entity instanceof Mercenary) {
                if (((Mercenary) entity).isAllied())
                    return;
            }
            game.battle(this, (Enemy) entity);
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public Entity getEntity(String itemUsedId) {
        return inventory.getEntity(itemUsedId);
    }

    public void removeInventoryItem(InventoryItem entity) {
        this.inventory.remove(entity);
    }

    public List<Entity> getInventoryItems() {
        return this.inventory.getEntities();
    }

    public <T> List<T> getInventoryItems(Class<T> clz) {
        return this.inventory.getEntities(clz);
    }

    public boolean pickUp(Entity item) {
        if (item instanceof Treasure)
            collectedTreasureCount++;
        InventoryItem inventoryItem = (InventoryItem) item;
        return inventoryItem.add(inventoryItem, getInventory());
    }

    public List<Key> getKeys() {
        return this.inventory.getEntities(Key.class);
    }

    public Potion getEffectivePotion() {
        return inEffectivePotion;
    }

    public <T extends InventoryItem> void use(Class<T> itemType) {
        T item = inventory.getFirst(itemType);
        if (item != null)
            inventory.remove(item);
    }

    public void useBribe() {
        Treasure treasure = inventory.getCoins();
        if (treasure != null)
            inventory.remove(treasure);
    }

    public void use(Bomb bomb, GameMap map) {
        inventory.remove(bomb);
        bomb.onPutDown(map, getPosition());
    }

    public void triggerNextPotion(int currentTick) {
        if (queuePotion.isEmpty()) {
            inEffectivePotion = null;
            state = PlayerState.BaseState;
            return;
        }
        inEffectivePotion = queuePotion.remove();
        if (inEffectivePotion instanceof InvincibilityPotion) {
            state = PlayerState.InvincibleState;
        } else {
            state = PlayerState.InvisibleState;
        }
        nextTriggerPotion = currentTick + inEffectivePotion.getDuration();
    }

    public void changeState(PlayerState playerState) {
        state = playerState;
    }

    public void use(Potion potion, int tick) {
        inventory.remove(potion);
        queuePotion.add(potion);
        if (inEffectivePotion == null) {
            triggerNextPotion(tick);
        }
    }

    public void onTickPotion(int tick) {
        if (inEffectivePotion == null || tick == nextTriggerPotion) {
            triggerNextPotion(tick);
        }
    }

    public Sceptre getEffectiveSceptre() {
        return inEffectiveSceptre;
    }

    public void triggerNextSceptre(int currentTick, GameMap map) {
        if (queueSceptre.isEmpty()) {
            if (inEffectiveSceptre == null) {
                return;
            }
            inEffectiveSceptre = null;
            List<Mercenary> mercs = map.getEntities(Mercenary.class);
            for (Mercenary merc : mercs) {
                merc.setTempAllied(false);
            }
            return;
        }
        inEffectiveSceptre = queueSceptre.remove();
        List<Mercenary> mercs = map.getEntities(Mercenary.class);
        for (Mercenary merc : mercs) {
            merc.setTempAllied(true);
        }
        nextTriggerSceptre = currentTick + inEffectiveSceptre.getMindControlDuration();
    }

    public void use(Sceptre sceptre, int tick, GameMap map) {
        inventory.remove(sceptre);
        queueSceptre.add(sceptre);
    }

    public void onTickSceptre(int tick, GameMap map) {
        if (inEffectiveSceptre == null || tick == nextTriggerSceptre) {
            triggerNextSceptre(tick, map);
        }
    }

    public void remove(InventoryItem item) {
        inventory.remove(item);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    public <T extends InventoryItem> int countEntityOfType(Class<T> itemType) {
        return inventory.count(itemType);
    }

    public int countTreasureBribe() {
        return inventory.countCoins();
    }

    public BattleStatistics applyBuff(BattleStatistics origin) {
        switch (this.state) {
        case InvincibleState:
            return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, true, true, false));
        case InvisibleState:
            return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, false, false, false));
        case SceptreActiveState:
            return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 0, 0, false, false, true));
        default:
            return origin;
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void incrementEnemiesKilled() {
        this.enemiesKilled++;
    }

    public int getEnemiesKilled() {
        return this.enemiesKilled;
    }
}
