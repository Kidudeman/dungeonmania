package dungeonmania.mvp.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SceptreTest {
    @Test
    @Tag("17-1")
    @DisplayName("Test sceptre can be built using wood, key")
    public void buildSceptreWoodKey() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreWoodKey", "c_SceptreTest");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up Key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("17-2")
    @DisplayName("Test sceptre can be built using wood, treasure")
    public void buildSceptreWoodTreasure() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreWoodTreasure", "c_SceptreTest");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up Arrow x2 (wood should be used first)
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Pick up Treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Pick up Key (treasure should be used first)
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("17-3")
    @DisplayName("Test sceptre can be built using arrow, key")
    public void buildSceptreArrowKey() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreArrowKey", "c_SceptreTest");

        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Arrow x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Pick up Key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("17-4")
    @DisplayName("Test sceptre can be built using arrow, treasure")
    public void buildSceptreArrowTreasure() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreArrowTreasure", "c_SceptreTest");

        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Arrow x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Pick up Key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("17-5")
    @DisplayName("Test sceptre is on list of buildables that the player can currently build")
    public void onBuildables() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_onBuildables", "c_SceptreTest");

        List<String> buildables = new ArrayList<>();
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Sceptre added to buildables list
        buildables.add("sceptre");
        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

        // Build Sceptre
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Sceptre disappears from buildables list
        buildables.remove("sceptre");
        assertEquals(buildables, res.getBuildables());
    }

    @Test
    @Tag("17-6")
    @DisplayName("Test the effects of the sceptre only last for a limited time")
    public void sceptreDuration() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_sceptreDuration", "c_SceptreTest");

        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(new Position(2, 1), getPlayerPos(res));
        assertEquals(new Position(9, 1), getMercPos(res));

        // Pick up Key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(new Position(3, 1), getPlayerPos(res));
        assertEquals(new Position(8, 1), getMercPos(res));

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(7, 1), getMercPos(res));

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(new Position(6, 1), getMercPos(res));

        // use sceptre
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(5, 1), getMercPos(res));

        // meet mercenary, shouldn't have any battles
        assertEquals(1, TestUtils.getEntities(res, "mercenary").size());
        assertEquals(0, res.getBattles().size());
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(5, 1), getMercPos(res));

        // merc no longer brainwashed and battle
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        assertEquals(1, res.getBattles().size());
        assertTrue(res.getBattles().get(0).getRounds().size() >= 1);
        assertEquals(0, res.getBattles().get(0).getBattleItems().size());
    }

    // Test when the effects of a 2nd sceptre is 'queued'
    // and will take place the tick following the previous sceptre effects wearing off
    @Test
    @Tag("17-7")
    @DisplayName("Test when the effects of a 2nd sceptre is 'queued'")
    public void sceptreQueuing() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_sceptreQueuing", "c_SceptreTest");

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());
        assertEquals(new Position(3, 1), getPlayerPos(res));
        assertEquals(new Position(16, 1), getMercPos(res));

        // Pick up Treasure x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getPlayerPos(res));
        assertEquals(new Position(14, 1), getMercPos(res));

        // Pick up SunStone x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(7, 1), getPlayerPos(res));
        assertEquals(new Position(12, 1), getMercPos(res));

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(2, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(new Position(10, 1), getMercPos(res));

        // use sceptre
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));
        assertEquals(new Position(7, 1), getPlayerPos(res));
        assertEquals(new Position(8, 1), getMercPos(res));

        // meet mercenary, shouldn't have any battles
        assertEquals(1, TestUtils.getEntities(res, "mercenary").size());
        assertEquals(0, res.getBattles().size());
        assertEquals(new Position(7, 1), getPlayerPos(res));
        assertEquals(new Position(8, 1), getMercPos(res));

        // new sceptre activates and merc remains brainwashed
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        assertEquals(0, res.getBattles().size());

        // merc no longer brainwashed and battle
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        assertEquals(1, res.getBattles().size());
        assertTrue(res.getBattles().get(0).getRounds().size() >= 1);
        assertEquals(0, res.getBattles().get(0).getBattleItems().size());
    }

    @Test
    @Tag("17-8")
    @DisplayName("Test the all mercs are brainwashed")
    public void sceptreMultipleMercs() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_multipleMercs", "c_SceptreTest");

        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(new Position(2, 1), getPlayerPos(res));
        assertEquals(new Position(4, 6), getMercPos(res));
        assertEquals(new Position(9, 1), getMercPosmMult(res));

        // Pick up Key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(new Position(3, 1), getPlayerPos(res));
        assertEquals(new Position(4, 5), getMercPos(res));
        assertEquals(new Position(8, 1), getMercPosmMult(res));

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(4, 4), getMercPos(res));
        assertEquals(new Position(7, 1), getMercPosmMult(res));

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(new Position(4, 3), getMercPos(res));
        assertEquals(new Position(6, 1), getMercPosmMult(res));

        // use sceptre
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(5, 1), getMercPos(res));
        assertEquals(new Position(4, 2), getMercPosmMult(res));

        // meet mercs, shouldn't have any battles
        assertEquals(2, TestUtils.getEntities(res, "mercenary").size());
        assertEquals(0, res.getBattles().size());
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(5, 1), getMercPos(res));
        assertEquals(new Position(4, 2), getMercPosmMult(res));

        // mercs no longer brainwashed and battle
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, res.getBattles().size());
        assertTrue(res.getBattles().get(0).getRounds().size() >= 1);
        assertEquals(0, res.getBattles().get(0).getBattleItems().size());
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }

    private Position getMercPosmMult(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(1).getPosition();
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }

}
