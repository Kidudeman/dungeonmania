package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.util.Position;
import java.util.List;

public class ExitGoal implements Goal {
    @Override
    public boolean achieved(Game game) {
        if (!game.isPlayerDefined())
            return false;
        Position pos = game.getPlayerPosition();
        List<Exit> es = game.getEntities(Exit.class);
        if (es == null || es.size() == 0)
            return false;
        return es.stream().map(Entity::getPosition).anyMatch(pos::equals);
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":exit";
    }
}
