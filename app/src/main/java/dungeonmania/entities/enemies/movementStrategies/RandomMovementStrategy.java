package dungeonmania.entities.enemies.movementStrategies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public class RandomMovementStrategy extends EnemyMovementStrategy {
    public RandomMovementStrategy(Enemy enemy) {
        super(enemy);
    }

    @Override
    public Position getNextPosition(Game game) {
        // Move random
        Random randGen = new Random();
        List<Position> pos = this.getEnemy().getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> game.canMoveTo(this.getEnemy(), p)).collect(Collectors.toList());

        if (pos.size() == 0) {
            return this.getEnemy().getPosition();
        }

        return pos.get(randGen.nextInt(pos.size()));
    }

}
