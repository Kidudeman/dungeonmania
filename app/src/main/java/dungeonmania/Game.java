package dungeonmania;

import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

import dungeonmania.battles.BattleFacade;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.EntityInterface;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.Goal;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Game {
    private String id;
    private String name;
    private Goal goals;
    private GameMap map;
    private Player player;
    private BattleFacade battleFacade;
    private EntityFactory entityFactory;
    private boolean isInTick = false;
    public static final int PLAYER_MOVEMENT = 0;
    public static final int PLAYER_MOVEMENT_CALLBACK = 1;
    public static final int AI_MOVEMENT = 2;
    public static final int AI_MOVEMENT_CALLBACK = 3;
    public static final int ITEM_LONGEVITY_UPDATE = 4;
    public static final int EVALUATE_LOGICAL_ENTITIES = 5;

    private ComparableCallback currentAction = null;

    private int tickCount = 0;
    private PriorityQueue<ComparableCallback> sub = new PriorityQueue<>();
    private PriorityQueue<ComparableCallback> addingSub = new PriorityQueue<>();

    public Game(String dungeonName) {
        this.name = dungeonName;
        this.map = new GameMap();
        this.battleFacade = new BattleFacade();
    }

    public void init() {
        this.id = UUID.randomUUID().toString();
        map.init();
        this.tickCount = 0;
        player = map.getPlayer();
        register(() -> player.onTickPotion(tickCount), PLAYER_MOVEMENT, "potionQueue");
        register(() -> player.onTickSceptre(tickCount, map), PLAYER_MOVEMENT, "sceptreQueue");
    }

    public Game tick(Direction movementDirection) {
        registerOnce(() -> player.move(this.map, movementDirection), PLAYER_MOVEMENT, "playerMoves");
        tick();
        return this;
    }

    public Game tick(String itemUsedId) throws InvalidActionException {
        Entity item = player.getEntity(itemUsedId);
        if (item == null)
            throw new InvalidActionException(String.format("Item with id %s doesn't exist", itemUsedId));
        if (!(item instanceof Bomb) && !(item instanceof Potion) && !(item instanceof Sceptre))
            throw new IllegalArgumentException(String.format("%s cannot be used", item.getClass()));

        registerOnce(() -> {
            if (item instanceof Bomb)
                player.use((Bomb) item, map);
            if (item instanceof Potion)
                player.use((Potion) item, tickCount);
            if (item instanceof Sceptre)
                player.use((Sceptre) item, tickCount, map);
        }, PLAYER_MOVEMENT, "playerUsesItem");
        tick();
        return this;
    }

    public void battle(Player player, Enemy enemy) {
        battleFacade.battle(this, player, enemy);
        if (player.getBattleStatistics().getHealth() <= 0) {
            map.destroyEntity(player);
        }
        if (enemy.getBattleStatistics().getHealth() <= 0) {
            map.destroyEntity(enemy);
            player.incrementEnemiesKilled();
        }
    }

    public Game build(String buildable) throws InvalidActionException {
        List<String> buildables = player.getBuildables(map);
        if (!buildables.contains(buildable)) {
            throw new InvalidActionException(String.format("%s cannot be built", buildable));
        }
        registerOnce(() -> player.build(buildable, entityFactory, map), PLAYER_MOVEMENT, "playerBuildsItem");
        tick();
        return this;
    }

    public Game interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity e = map.getEntity(entityId);
        if (e == null || !(e instanceof Interactable))
            throw new IllegalArgumentException("Entity cannot be interacted");
        if (!((Interactable) e).isInteractable(player)) {
            throw new InvalidActionException("Entity cannot be interacted");
        }
        registerOnce(() -> ((Interactable) e).interact(player, this), PLAYER_MOVEMENT, "playerInteracts");
        tick();
        return this;
    }

    public void register(Runnable r, int priority, String id) {
        if (isInTick)
            addingSub.add(new ComparableCallback(r, priority, id));
        else
            sub.add(new ComparableCallback(r, priority, id));
    }

    public void registerOnce(Runnable r, int priority, String id) {
        if (isInTick)
            addingSub.add(new ComparableCallback(r, priority, id, true));
        else
            sub.add(new ComparableCallback(r, priority, id, true));
    }

    public void unsubscribe(String id) {
        if (this.currentAction != null && id.equals(this.currentAction.getId())) {
            this.currentAction.invalidate();
        }

        for (ComparableCallback c : sub) {
            if (id.equals(c.getId())) {
                c.invalidate();
            }
        }
        for (ComparableCallback c : addingSub) {
            if (id.equals(c.getId())) {
                c.invalidate();
            }
        }
    }

    public int tick() {
        PriorityQueue<ComparableCallback> nextTickSub = new PriorityQueue<>();
        isInTick = true;
        while (!sub.isEmpty()) {
            currentAction = sub.poll();
            currentAction.run();
            if (currentAction.isValid()) {
                nextTickSub.add(currentAction);
            }
        }
        isInTick = false;
        nextTickSub.addAll(addingSub);
        addingSub = new PriorityQueue<>();
        sub = nextTickSub;
        tickCount++;
        return tickCount;
    }

    public int getTick() {
        return this.tickCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Goal getGoals() {
        return goals;
    }

    public void setGoals(Goal goals) {
        this.goals = goals;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public void setEntityFactory(EntityFactory factory) {
        entityFactory = factory;
    }

    public int getCollectedTreasureCount() {
        return player.getCollectedTreasureCount();
    }

    public BattleFacade getBattleFacade() {
        return battleFacade;
    }

    // Player Methods

    public void removeItemFromPlayer(InventoryItem item) {
        this.player.remove(item);
    }

    public Position getPlayerPosition() {
        return this.player.getPosition();
    }

    public Position getPlayersPreviousDistinctPosition() {
        return this.player.getPreviousDistinctPosition();
    }

    public List<String> getPlayerBuildables() {
        return this.player.getBuildables(map);
    }

    public Inventory getPlayerInventory() {
        return this.player.getInventory();
    }

    public Potion getPlayerEffectivePotion() {
        return this.player.getEffectivePotion();
    }

    public boolean isPlayerDefined() {
        return this.player != null;
    }

    public int getEnemiesKilledByPlayer() {
        return this.player.getEnemiesKilled();
    }

    public Player getPlayer() {
        return this.player;
    }

    // GameMap methods

    public GameMap getMap() {
        return this.map;
    }

    public List<Entity> getEntities() {
        return this.map.getEntities();
    }

    public <T extends Entity> List<T> getEntities(Class<T> type) {
        return this.map.getEntities(type);
    }

    public <T extends EntityInterface> List<T> getEntities(Position postion, Class<T> type) {
        return this.map.getEntities(postion, type);
    }

    public List<Entity> getEntities(Position position) {
        return this.map.getEntities(position);
    }

    public void addEntity(Entity entity) {
        this.map.addEntity(entity);
    }

    public void destroyEntity(Entity entity) {
        this.map.destroyEntity(entity);
    }

    public void moveTo(Entity entity, Position position) {
        this.map.moveTo(entity, position);
    }

    public void moveTo(Entity entity, Direction direction) {
        this.map.moveTo(entity, direction);
    }

    public boolean canMoveTo(Entity entity, Position position) {
        return this.map.canMoveTo(entity, position);
    }

    public Position dijkstraPathFind(Position src, Position dest, Entity entity) {
        return this.map.dijkstraPathFind(src, dest, entity);
    }
}
