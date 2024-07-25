package dungeonmania.entities.logical.activationStrategies;

import dungeonmania.Game;
import dungeonmania.entities.logical.Logical;

public class CoAndActivationStrategy extends ActivationStrategy {
    private int previousState;

    public CoAndActivationStrategy(Logical entity) {
        super(entity);
        this.previousState = 0;
    }

    @Override
    public boolean isActivated(Game game) {
        boolean result = this.activeConductors(game).size() - previousState > 1;
        this.previousState = this.activeConductors(game).size();
        return result;
    }
}
