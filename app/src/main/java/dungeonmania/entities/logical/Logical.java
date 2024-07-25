package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.logical.activationStrategies.ActivationStrategy;
import dungeonmania.util.Position;

public abstract class Logical extends Entity {
    private ActivationStrategy activationStrategy;

    public Logical(Position position) {
        super(position);
    }

    public abstract void onActivated(Game game);

    public boolean runActivationStrategy(Game game) {
        return this.activationStrategy.isActivated(game);
    }

    public void setActivationStrategy(ActivationStrategy activationStrategy) {
        this.activationStrategy = activationStrategy;
    }

    public boolean isActivationStrategySet() {
        return this.activationStrategy != null;
    }
}
