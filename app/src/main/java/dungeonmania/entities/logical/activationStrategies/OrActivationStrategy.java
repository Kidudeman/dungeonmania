package dungeonmania.entities.logical.activationStrategies;

import dungeonmania.Game;
import dungeonmania.entities.logical.Logical;

public class OrActivationStrategy extends ActivationStrategy {
    public OrActivationStrategy(Logical entity) {
        super(entity);
    }

    @Override
    public boolean isActivated(Game game) {
        return this.activeConductors(game).size() > 0;
    }

}
