package dungeonmania.entities.enemies.snake;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public interface SnakeNode {
    public static final double DEFAULT_KEY_BUFF = 2.0;
    public static final double DEFAULT_TREASURE_BUFF = 1.0;
    public static final double DEFAULT_ARROW_BUFF = 2.0;
    public static final double DEFAULT_ATTACK = 1.0;
    public static final double DEFAULT_HEALTH = 5.0;

    public SnakeState getState();

    public SnakeNode getRight();

    public Position getPos();

    public List<Position> getPrevPos();

    public Position calculateLastPos();

    public int getIndex();

    public void disconnect();

    public void detach();

    public void connectRight(SnakeNode right);

    public default void destroyAllNodes(Game game) {
        SnakeNode current = this;
        while (current != null) {
            SnakeNode nextNode = current.getRight();
            current.destroySnakeNode(game);

            current = nextNode;
        }
    }

    public default void destroySnakeNode(Game game) {
        disconnect();
        game.getMap().removeNode((Entity) this);
        game.unsubscribe(((Entity) this).getId());
    }

    public SnakeHead getHead();

    public default boolean isInSameSnake(SnakeNode node) {
        return node.getHead().equals(getHead());
    }

    public default boolean isAdjacentNode(SnakeNode node) {
        return getIndex() + 1 == node.getIndex() || getIndex() - 1 == node.getIndex();
    }
}
