package dungeonmania.entities.enemies.snake;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeBody extends Enemy implements SnakeNode {
    private SnakeNode right;
    private SnakeHead head;
    private SnakeNode left;
    private SnakeState state;
    private int index;

    public SnakeBody(SnakeHead head, SnakeState state, SnakeNode left, Position position, double health,
            double attack) {
        super(position, health, attack);
        right = null;
        this.left = left;
        this.state = state;
        this.index = left.getIndex() + 1;
        this.head = head;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof SnakeNode) {
            SnakeNode node = (SnakeNode) entity;
            return (!isInSameSnake(node) && node.getState().isInvisible())
                    || (isInSameSnake(node) && isAdjacentNode(node) && node != head);
        }
        return entity instanceof Player;
    }

    @Override
    public void onDestroy(Game game) {
        if (state.isInvincible()) {
            if (right != null) {
                SnakeHead newHead = splitSnake(game);
                destroySnakeNode(game);
                newHead.move(game);
            } else {
                destroySnakeNode(game);
            }

        } else {
            destroyRightNodes(game);
        }

    }

    @Override
    public SnakeState getState() {
        return state;
    }

    @Override
    public SnakeNode getRight() {
        return right;
    }

    public SnakeNode getLeft() {
        return left;
    }

    @Override
    public Position getPos() {
        return getPosition();
    }

    @Override
    public List<Position> getPrevPos() {
        return head.getPrevPos();
    }

    @Override
    public Position calculateLastPos() {
        return getPrevPos().get(getPrevPos().size() - index - 1);
    }

    @Override
    public Position calculateNextPosition(Game game) {
        if (state.isHibernating()) {
            return getPosition();
        }
        return getPrevPos().get(getPrevPos().size() - index);
    }

    @Override
    public void detach() {
        right = null;
    }

    @Override
    public void disconnect() {
        left.detach();
        left = null;
        right = null;
        head = null;
    }

    @Override
    public void connectRight(SnakeNode right) {
        this.right = right;
    }

    public void connectLeft(SnakeNode left) {
        this.left = left;
    }

    @Override
    public int getMovementPriority() {
        return Game.AI_MOVEMENT_CALLBACK;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public SnakeHead getHead() {
        return head;
    }

    public void setHead(SnakeHead head) {
        this.head = head;
    }

    @Override
    public List<Position> updatePrevPos() {
        List<Position> newPrevPos = new ArrayList<>(getPrevPos());

        newPrevPos.subList(newPrevPos.size() - right.getIndex(), newPrevPos.size()).clear();
        return newPrevPos;
    }

}
