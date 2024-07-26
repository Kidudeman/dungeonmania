package dungeonmania.mvp.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SunStoneTest {
    @Test
    @Tag("16-1")
    @DisplayName("Test player can pick up a SunStone and add to inventory")
    public void pickUpSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_pickUp", "c_SunStoneTest_pickUp");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());

    }

    @Test
    @Tag("16-2")
    @DisplayName("Test player can use a SunStone to open and walk through a door")
    public void useSunStoneWalkThroughOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_useSunStoneWalkThroughDoor",
                "c_SunStoneTest_useSunStoneWalkThroughDoor");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // walk through door and check sun_stone is still in inventory
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test doors remain open and the player can move through the door")
    public void doorRemainsOpen() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_useSunStoneWalkThroughDoor",
                "c_SunStoneTest_useSunStoneWalkThroughDoor");

        // pick up key
        res = dmc.tick(Direction.RIGHT);

        // open door
        res = dmc.tick(Direction.RIGHT);

        // player keeps sun_stone and can move freely through door
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.LEFT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("16-4")
    @DisplayName("Test building a shield with SunStone")
    public void buildShieldWithSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithSunStone",
                "c_SunStoneTest_useSunStoneWalkThroughDoor");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Wood disappears but SunStone remains in inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("16-5")
    @DisplayName("Test building a shield with Treasure and not using SunStone")
    public void buildShieldWithTreasureNotSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithTreasureNotSunStone",
                "c_SunStoneTest_useSunStoneWalkThroughDoor");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Wood and Treasure disappear but SunStone remains in inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("16-6")
    @DisplayName("Test building a shield with Key and not using SunStone")
    public void buildShieldWithKeyNotSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithKeyNotSunStone",
                "c_SunStoneTest_useSunStoneWalkThroughDoor");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Wood and Key disappear but SunStone remains in inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("16-7")
    @DisplayName("Testing a mercenary cannot be bribed with SunStone")
    public void bribeAmount() {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      M4       M3       M2     M1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_cannotBribeMercs", "c_SunStoneTest_cannotBribeMercs");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(7, 1), getMercPos(res));

        // attempt bribe but fail
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getMercPos(res));

        // achieve bribe and SunStone remains
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

    }

    @Test
    @Tag("16-8")
    @DisplayName("Test achieving a basic treasure goal with SunStone")
    public void treasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_treasureGoalAchieved", "c_basicGoalsTest_treasure");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }

}
