package dungeonmania.entities.enemies.movementStrategies;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class EnemyAvoidsPlayerMovementStrategy extends EnemyMovementStrategy {
    public EnemyAvoidsPlayerMovementStrategy(Enemy enemy) {
        super(enemy);
    }

    @Override
    public Position getNextPosition(Game game) {
        Position plrDiff = Position.calculatePositionBetween(game.getPlayer().getPosition(),
                this.getEnemy().getPosition());

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(this.getEnemy().getPosition(), Direction.RIGHT)
                : Position.translateBy(this.getEnemy().getPosition(), Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(this.getEnemy().getPosition(), Direction.UP)
                : Position.translateBy(this.getEnemy().getPosition(), Direction.DOWN);

        Position offset = this.getEnemy().getPosition();

        if (plrDiff.getY() == 0 && game.canMoveTo(this.getEnemy(), moveX)) {
            offset = moveX;
        } else if (plrDiff.getX() == 0 && game.canMoveTo(this.getEnemy(), moveY)) {
            offset = moveY;
        } else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (game.canMoveTo(this.getEnemy(), moveX)) {
                offset = moveX;
            } else if (game.canMoveTo(this.getEnemy(), moveY)) {
                offset = moveY;
            } else {
                offset = this.getEnemy().getPosition();
            }
        } else {
            if (game.canMoveTo(this.getEnemy(), moveY)) {
                offset = moveY;
            } else if (game.canMoveTo(this.getEnemy(), moveX)) {
                offset = moveX;
            } else {
                offset = this.getEnemy().getPosition();
            }
        }

        return offset;
    }

}
