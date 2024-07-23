package dungeonmania.entities.enemies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.movementStrategies.SequencedMovementStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    private List<Position> movementTrajectory;
    private int nextPositionElement;
    private boolean forward;
    private SequencedMovementStrategy movementStrategy;

    public static final int DEFAULT_SPAWN_RATE = 0;
    public static final double DEFAULT_ATTACK = 5;
    public static final double DEFAULT_HEALTH = 10;

    public Spider(Position position, double health, double attack) {
        super(position.asLayer(Entity.DOOR_LAYER + 1), health, attack);
        /**
         * Establish spider movement trajectory Spider moves as follows:
         *  8 1 2       10/12  1/9  2/8
         *  7 S 3       11     S    3/7
         *  6 5 4       B      5    4/6
         */
        movementTrajectory = position.getAdjacentPositions();
        nextPositionElement = 1;
        forward = true;

        movementStrategy = new SequencedMovementStrategy(this);
        movementStrategy.config(movementTrajectory, forward, nextPositionElement);
    };

    @Override
    public Position calculateNextPosition(Game game) {
        return this.movementStrategy.getNextPosition(game);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return !(entity instanceof Boulder);
    }
}
