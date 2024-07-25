package dungeonmania.entities.enemies.snake;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.SnakeConsumable;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeHead extends Enemy implements SnakeNode {
    private SnakeState state;
    private SnakeNode right;
    private List<Position> prevPos;

    private double attackArrowBuff;
    private double healthTreasureBuff;
    private double healthKeyBuff;

    public SnakeHead(SnakeState state, List<Position> prevPos, SnakeNode right, Position position, double health,
            double attack, double attackArrowBuff, double healthTreasureBuff, double healthKeyBuff) {
        super(position, health, attack);
        this.state = state;
        this.right = right;
        this.prevPos = prevPos;
        this.attackArrowBuff = attackArrowBuff;
        this.healthTreasureBuff = healthTreasureBuff;
        this.healthKeyBuff = healthKeyBuff;
    }

    public SnakeHead(Position position, double health, double attack, double attackArrowBuff, double healthTreasureBuff,
            double healthKeyBuff) {
        super(position, health, attack);
        right = null;
        state = new SnakeState();
        this.prevPos = new ArrayList<Position>();
        this.attackArrowBuff = attackArrowBuff;
        this.healthTreasureBuff = healthTreasureBuff;
        this.healthKeyBuff = healthKeyBuff;
    }

    public void appendNode(Game game) {
        SnakeNode end = this;
        for (SnakeNode curr = this; curr != null; curr = curr.getRight()) {
            if (curr.getRight() == null) {
                end = curr;
            }
        }

        game.getEntityFactory().buildSnakeBody(end, game);

    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof SnakeNode) {
            SnakeNode node = (SnakeNode) entity;

            return !isInSameSnake(node) && node.getState().isInvisible();
        }
        return entity instanceof Player;
    }

    @Override
    public void onDestroy(Game game) {
        destroyRightNodes(game);

    }

    @Override
    public SnakeState getState() {
        return state;
    }

    @Override
    public SnakeNode getRight() {
        return right;
    }

    @Override
    public Position calculateLastPos() {
        return prevPos.get(prevPos.size() - 1);
    }

    @Override
    public Position getPos() {
        return getPosition();
    }

    @Override
    public void disconnect() {
        right = null;
    }

    @Override
    public void detach() {
        right = null;
    }

    @Override
    public void connectRight(SnakeNode right) {
        this.right = right;
    }

    public double getAttackArrowBuff() {
        return attackArrowBuff;
    }

    public double getHealthTreasureBuff() {
        return healthTreasureBuff;
    }

    public double getHealthKeyBuff() {
        return healthKeyBuff;
    }

    public int getSize() {
        int size = 0;
        for (SnakeNode curr = this; curr != null; curr = curr.getRight()) {
            size++;
        }
        return size;
    }

    @Override
    public List<Position> getPrevPos() {
        return prevPos;
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public SnakeHead getHead() {
        return this;
    }

    public void updateNodeIndices() {
        int i = 1;
        for (SnakeBody curr = (SnakeBody) this.getRight(); curr != null; curr = (SnakeBody) curr.getRight(), i++) {
            curr.setIndex(i);
            curr.setHead(this);
        }
    }

    @Override
    public List<Position> updatePrevPos() {
        List<Position> newPrevPos = new ArrayList<>(getPrevPos());

        if (hasMoved()) {
            newPrevPos.subList(newPrevPos.size() - right.getIndex() - 1, newPrevPos.size()).clear();
        } else {
            newPrevPos.subList(newPrevPos.size() - right.getIndex(), newPrevPos.size()).clear();
        }
        return newPrevPos;
    }

    public boolean hasMoved() {
        return !Position.isAdjacent(getPos(), right.getPos());
    }

    @Override
    public Position calculateNextPosition(Game game) {
        GameMap map = game.getMap();
        List<SnakeConsumable> collectables = map.getEntities(SnakeConsumable.class);

        SnakeConsumable nearestCollectable = null;
        int shortestPathLength = Integer.MAX_VALUE;

        // Find nearest collectable
        for (SnakeConsumable collectable : collectables) {
            int pathLength = map.dijkstraPathLength(getPosition(), collectable.getPosition(), this);
            if (pathLength < shortestPathLength) {
                shortestPathLength = pathLength;
                nearestCollectable = collectable;
            }
        }

        if (nearestCollectable == null) {
            state.enterHibernation();
            return getPos();
        } else {
            state.endHibernation();
            prevPos.add(getPosition());

            return map.dijkstraPathFind(getPosition(), nearestCollectable.getPosition(), this);
        }
    }
}
