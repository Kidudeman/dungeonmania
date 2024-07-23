package dungeonmania.entities.enemies.movementStrategies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

import java.util.List;

public class SequencedMovementStrategy extends EnemyMovementStrategy {
    private List<Position> positionSequence;
    private boolean forward;
    private int next;

    public SequencedMovementStrategy(Enemy enemy) {
        super(enemy);
    }

    public void updateNext() {
        if (this.forward) {
            this.next = (this.next + 1) % positionSequence.size();
        } else {
            this.next = (this.next + positionSequence.size() - 1) % positionSequence.size();
        }
    }

    @Override
    public Position getNextPosition(Game game) {
        Position nextPosition = positionSequence.get(this.next);

        List<Entity> entities = game.getEntities(nextPosition);

        if (entities != null && !entities.isEmpty()
                && entities.stream().anyMatch(e -> !this.getEnemy().canMoveOnto(game.getMap(), e))) {
            this.forward = !this.forward;
            updateNext();
            updateNext();
        }

        nextPosition = positionSequence.get(this.next);
        entities = game.getEntities(nextPosition);

        if (entities == null || entities.isEmpty()
                || entities.stream().allMatch(e -> this.getEnemy().canMoveOnto(game.getMap(), e))) {
            updateNext();
        }

        return nextPosition;
    }

    public void config(List<Position> positionSequence, boolean forward) {
        this.positionSequence = positionSequence;
        this.forward = forward;
    }

    public void config(List<Position> positionSequence, boolean forward, int current) {
        this.positionSequence = positionSequence;
        this.forward = forward;
        this.next = current;
    }
}
