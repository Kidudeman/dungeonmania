package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemyGoal implements Goal {
    private final int enemiesKilledRequirement;

    public EnemyGoal(int enemiesKilledRequirement) {
        this.enemiesKilledRequirement = enemiesKilledRequirement;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getEntities(ZombieToastSpawner.class).size() == 0
                && game.getEnemiesKilledByPlayer() == this.enemiesKilledRequirement;
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":enemies";
    }

}
