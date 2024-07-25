package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.exceptions.InvalidActionException;

import dungeonmania.util.Position;

public class SnakeTest {
    @Test
    @Tag("16-1")
    @DisplayName("Test Snake moves towards Treasure")
    public void testSnakeMovesShortestPathWhenThereisFood() {
        //                                          Wall    Wall   Wall    Wall    Wall    Wall
        // T       P1       P2      P3    P4      S4      S3    S2      S1      .      Wall
        //                                          Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_movesShortestPathWhenThereIsFood", "c_snakeTests");

        assertEquals(new Position(8, 1), getSnakeHeadPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getSnakeHeadPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getSnakeHeadPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-2")
    @DisplayName("Test Snake cannot move through walls when not invisible")
    public void testSnakeCannotMoveThroughWallsWhenNotInvisible() {
        //                  Wall     Wall    Wall
        // P1       P2      Wall      S1     Wall
        //                  Wall     Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_cannotMoveThroughWallsWhenNotInvisible", "c_snakeTests");

        Position startingPos = getSnakeHeadPos(res);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(startingPos, getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test snake can move through closed doors")
    public void testSnakeCanOverlapClosedDoor() {
        // T                Wall     Door    Wall
        // P1       P2      Wall      S1     Wall
        // Key              Wall     Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapClosedDoor", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 1), getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-4")
    @DisplayName("Test snake can move through open doors")
    public void testSnakeCanOverlapOpenDoor() {
        //   1         2       3      4       5       6       7
        // 1 T                Wall     Wall    Wall    Wall    Wall
        // 2 P1       Key     Door      S3      S2      S1     Wall
        // 3                  Wall     Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapOpenDoor", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 2), getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-5")
    @DisplayName("Test snake can move through mercenary")
    public void testSnakeCanOverlapMercenary() {
        //   1         2       3      4       5       6       7
        // 1 T                Wall     Wall    Wall    Wall    Wall
        // 2          Key     Door      M       S2      S1     Wall
        // 3 P1               Wall     Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapMercenary", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(4, 2), getSnakeHeadPos(res));
        assertEquals(new Position(4, 2), TestUtils.getEntities(res, "mercenary").get(0).getPosition());

    }

    @Test
    @Tag("16-6")
    @DisplayName("Test snake can move through spider")
    public void testSnakeCanOverlapSpider() {
        //   1         2       3      4       5
        // 1Boul     Boul    Boul
        // 2 T                Boul     Spid     S2
        // 3 P1               Boul     Boul    Boul
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapSpider", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 2), getSnakeHeadPos(res));
        assertEquals(new Position(4, 2), TestUtils.getEntities(res, "spider").get(0).getPosition());
    }

    @Test
    @Tag("16-7")
    @DisplayName("Test snake can move through boulder")
    public void testSnakeCanOverlapBoulder() {
        //   1         2       3      4       5
        // 1Boul     Boul    Boul
        // 2 T                Boul      S1     Boul
        // 3 P1               Boul     Boul    Boul
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapBoulder", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 2), getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-8")
    @DisplayName("Test snake can move through switch")
    public void testSnakeCanOverlapSwitch() {
        //   1         2       3      4       5
        // 1Wall     Wall    Wall
        // 2 T                Swit      S1     Wall
        // 3 P1               Wall     Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapSwitch", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 2), getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-9")
    @DisplayName("Test snake can move through exit")
    public void testSnakeCanOverlapExit() {
        //   1         2       3      4       5
        // 1Wall     Wall    Wall
        // 2 T                Exit      S1     Wall
        // 3 P1               Wall     Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapExit", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 2), getSnakeHeadPos(res));
    }

    @Test
    @Tag("16-10")
    @DisplayName("Test snake can move through Portal")
    public void testSnakeCanTeleport() {
        //   1         2       3      4       5
        // 1 Port             Wall     Wall    Wall
        // 2 T                Port      S1     Wall
        // 3 P1               Wall     Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canTeleport", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        assertEquals(new Position(1, 4), getSnakeHeadPos(res));
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(1, 2), new Position(1, 3)),
                getAllSnakeBodiesPos(res));
    }

    @Test
    @Tag("16-11")
    @DisplayName("Test snake can move through ZombieToast")
    public void testSnakeCanOverlapZombieToast() {
        //   1         2       3      4       5         6
        // 1 Key      Wall    Wall     Wall     Wall     Wall
        // 2 T        Door     Z       Door     Snak     Wall
        // 3 P1       Wall    Wall     Wall     Wall     Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapZombieToast", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        assertEquals(new Position(3, 2), getSnakeHeadPos(res));
        assertEquals(new Position(3, 2), TestUtils.getEntities(res, "zombie_toast").get(0).getPosition());

        res = dmc.tick(Direction.UP);
        assertEquals(new Position(2, 2), getSnakeHeadPos(res));

    }

    @Test
    @Tag("16-12")
    @DisplayName("Test snake can move through ZombieToast Spawner")
    public void testSnakeCanOverlapZombieToastSpawner() {
        //   1         2       3      4       5         6
        // 1 Key      Wall    Wall     Wall     Wall     Wall
        // 2 T        Door    ZTS      Door     Snak     Wall
        // 3 P1       Wall    Wall     Wall     Wall     Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapZombieToastSpawner", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        assertEquals(new Position(3, 2), getSnakeHeadPos(res));
        assertEquals(new Position(3, 2), TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getPosition());

        res = dmc.tick(Direction.UP);
        assertEquals(new Position(2, 2), getSnakeHeadPos(res));

    }

    @Test
    @Tag("16-13")
    @DisplayName("Test snake can consume Treasure")
    public void testSnakeConsumesTreasure() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesTreasure", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "treasure"));

        res = dmc.tick(Direction.UP);
        assertEquals(2, res.getBattles().get(0).getInitialEnemyHealth());
    }

    @Test
    @Tag("16-14")
    @DisplayName("Test snake can consume Key")
    public void testSnakeConsumesKey() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesKey", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "key"));

        res = dmc.tick(Direction.UP);
        assertEquals(2, res.getBattles().get(0).getInitialEnemyHealth());

    }

    @Test
    @Tag("16-15")
    @DisplayName("Test snake can move onto Invincibility Potion")
    public void testSnakeConsumesInvincibilityPotion() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesInvincibilityPotion", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "invincibility_potion"));
    }

    @Test
    @Tag("16-16")
    @DisplayName("Test snake can move onto Invisibility Potion")
    public void testSnakeConsumesInvisibilityPotion() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesInvisibilityPotion", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "invisibility_potion"));
    }

    @Test
    @Tag("16-17")
    @DisplayName("Test snake can move onto arrow")
    public void testSnakeConsumesArrow() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesArrow", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "arrow"));

        res = dmc.tick(Direction.UP);
        assertEquals(-0.4, res.getBattles().get(0).getRounds().get(0).getDeltaCharacterHealth());
    }

    @Test
    @Tag("16-18")
    @DisplayName("Test snake can move onto wood")
    public void testSnakeCanOverlapWood() {
        //   1         2       3
        // 1
        // 2           W       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapWood", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(new Position(2, 2), TestUtils.getEntities(res, "wood").get(0).getPosition());

    }

    @Test
    @Tag("16-19")
    @DisplayName("Test snake can move onto bomb")
    public void testSnakeCanMoveOntoBomb() {
        //   1         2       3
        // 1
        // 2           W       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapBomb", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(new Position(2, 2), TestUtils.getEntities(res, "bomb").get(0).getPosition());
    }

    @Test
    @Tag("16-20")
    @DisplayName("Test snake can move onto sword")
    public void testSnakeCanMoveOntoSword() {
        //   1         2       3
        // 1
        // 2           W       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapSword", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(new Position(2, 2), TestUtils.getEntities(res, "sword").get(0).getPosition());
    }

    @Test
    @Tag("16-21")
    @DisplayName("Test snake can overlap wall after consuming the invisibility potion")
    public void testCanOverlapWallWhenInvisible() {
        //
        //       T       Wall      Invis       S1
        //       P1       P2         P3
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapWallWhenInvisible", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(3, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(2, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(1, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(0, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(0, 3).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(0, 4).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(0, 5).equals(getSnakeHeadPos(res)));
    }

    @Test
    @Tag("16-22")
    @DisplayName("Test snake moves over another snake head after collecting the invisibility potion")
    public void testSnakeMovesOverOtherSnakesWhenInvisible() {
        //                                     5       6      7      8        9       10
        // 0                                                         S
        // 1                                     Invis
        // 2                                                         T
        // 3
        // 4
        // 5                                 Wall    Wall   Wall    Wall    Wall    Wall
        // 6 P                               Wall     S      T       T       T      Wall
        // 7                                 Wall    Wall   Wall    Wall    Wall    Wall
        // 8                                                         T
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_canOverlapOtherSnakeWhenInvisible", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 1), new Position(7, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.LEFT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 2), new Position(8, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 3), new Position(9, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.LEFT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 4), new Position(9, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 5), new Position(9, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.LEFT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 6), new Position(9, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 7), new Position(9, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.LEFT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 8), new Position(9, 6)),
                getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.LEFT);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 8), new Position(9, 6)),
                getAllSnakeHeadsPos(res));
    }

    @Test
    @Tag("16-23")
    @DisplayName("Test snake hibernates when there is no food")
    public void testSnakeHibernatesWhenThereIsNoFood() {
        //
        //       P1       P2      T       S1
        //
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_hibernatesWhenThereIsNoFood", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(3, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(3, 2).equals(getSnakeHeadPos(res)));
    }

    @Test
    @Tag("16-24")
    @DisplayName("Test snake moves around a wall to get to the treasure")
    public void testEvadesWallWhenThereIsTreasure() {
        //                         Wall      S2
        // T      P1       P2      Wall      S1
        //                         Wall      S2
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_evadesWallWhenThereIsTreasure", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(4, 1).equals(getSnakeHeadPos(res)) || new Position(4, 3).equals(getSnakeHeadPos(res)));
    }

    @Test
    @Tag("16-25")
    @DisplayName("Test whole snake is killed when snake head is destroyed in battle and not invincible")
    public void testEntireSnakeKilledWhenHeadIsDestroyedAndNotInvincible() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_snakeHeadDestroyedWhenNotInvincible", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(3, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(2, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(0, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        assertTrue(getAllSnakeBodies(res).isEmpty());
        assertTrue(getAllSnakeHeads(res).isEmpty());

    }

    @Test
    @Tag("16-26")
    @DisplayName("Test whole snake is killed when snake head is destroyed in battle and not invincible")
    public void testEntireSnakeKilledWhenHeadIsDestroyedAndInvincible() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_snakeHeadDestroyedWhenNotInvincible", "c_snakeTests");

        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(3, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(2, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(1, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(0, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);

        assertTrue(getAllSnakeBodies(res).isEmpty());
        assertTrue(getAllSnakeHeads(res).isEmpty());
    }

    @Test
    @Tag("16-27")
    @DisplayName("Test right nodes of body of snake is killed when body node is destroyed in battle")
    public void testDestroyBodyNodeWhenNotInvincible() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_destroyBodyNodeNotInvincible", "c_snakeTests");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(3, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(2, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(1, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(0, 2).equals(getSnakeHeadPos(res)));

        res = dmc.tick(Direction.DOWN);

        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(0, 2)), getAllSnakeHeadsPos(res));
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(1, 2)), getAllSnakeBodiesPos(res));
    }

    @Test
    @Tag("16-28")
    @DisplayName("Test invincible snake gets body destroyed in battle")
    public void testDestroySnakeBodyWhenInvincible() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_destroySnakeBodyWhenInvincible", "c_snakeTests");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(6, 3)), getAllSnakeHeadsPos(res));
    }

    @Test
    @Tag("16-29")
    @DisplayName("Test invincible snake gets destroyed part by part in battle")
    public void testDestroyHibernatingInvincibleSnake() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_hibernatingInvincibleSnakeGetsDestroyed", "c_snakeTests");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        assertListAreEqualIgnoringOrder(Arrays.asList(), getAllSnakeHeadsPos(res));
        assertListAreEqualIgnoringOrder(Arrays.asList(), getAllSnakeBodiesPos(res));
    }

    @Test
    @Tag("16-30")
    @DisplayName("Test invincible snake cannot move onto itself when not invincible")
    public void testSnakeCannotMoveOntoItselfWhenNotInvisible() {
        // 1           2       3        4        5        6        7       8        9
        // 1          Wall    Wall     Wall     Wall     Wall     Wall    Wall     Wall
        // 2          Wall     T        T        T       Snak              T       Wall
        // 3 P1       Wall    Wall     Wall     Wall     Wall     Wall    Wall     Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_cannotMoveOntoItselfWhenNotInvisible", "c_snakeTests");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(3, 2)), getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.DOWN);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(3, 2)), getAllSnakeHeadsPos(res));
    }

    @Test
    @Tag("16-31")
    @DisplayName("Test invincible snake cannot move onto itself when invisible")
    public void testSnakeCannotMoveOntoItselfWhenInvisible() {
        // 1           2       3        4        5        6        7       8        9
        // 1          Wall    Wall     Wall     Wall     Wall     Wall    Wall     Wall
        // 2          Wall     T        T       Invis    Snak              T       Wall
        // 3 P1       Wall    Wall     Wall     Wall     Wall     Wall    Wall     Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_cannotMoveOntoItselfWhenInvisible", "c_snakeTests");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(3, 2)), getAllSnakeHeadsPos(res));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(8, 2)), getAllSnakeHeadsPos(res));
    }

    @Test
    @Tag("16-32")
    @DisplayName("Test snake body gets treasure buffs")
    public void testSnakeBodyIsBuffedWhenConsumedTreasure() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesTreasure", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "treasure"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(2, res.getBattles().get(0).getInitialEnemyHealth());
    }

    @Test
    @Tag("16-32")
    @DisplayName("Test snake body gets treasure buffs")
    public void testSnakeBodyIsBuffedWhenConsumedKey() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesKey", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "treasure"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(2, res.getBattles().get(0).getInitialEnemyHealth());
    }

    @Test
    @Tag("16-33")
    @DisplayName("Test snake body gets treasure buffs")
    public void testSnakeBodyIsBuffedWhenConsumedArrow() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesArrow", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "treasure"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(-0.4, res.getBattles().get(0).getRounds().get(0).getDeltaCharacterHealth());
    }

    @Test
    @Tag("16-34")
    @DisplayName("Test snake cannot move through other snakes when not invisible")
    public void testSnakeCannotOverlapOtherSnakeWhenNotInvisible() {
        //   1         2       3
        // 1                   T
        // 2          Wall
        // 3 P1        S2      S1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_cannotOverlapOtherSnakeWhenNotInvisible", "c_snakeTests");
        res = dmc.tick(Direction.UP);
        assertListAreEqualIgnoringOrder(Arrays.asList(new Position(1, 3), new Position(3, 2)),
                getAllSnakeHeadsPos(res));
    }

    @Test
    @Tag("16-35")
    @DisplayName("Test snake body parts can teleport through portals")
    public void testSnakeBodyCanTeleport() {
        //   1         2         3         4         5
        // 1
        // 2 S1        T         T         T         P
        // 3 P1
        // 4
        // 5           P
        // 6
        // 7
        // 8                     T
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_bodyCanTeleport", "c_snakeBuffTest");
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        assertTrue(new Position(3, 8).equals(getSnakeHeadPos(res)));
    }

    @Test
    @Tag("16-36")
    @DisplayName("Test snake takes damage when attacked by a player")
    public void testSnakeTakesDamageWhenAttackedByPlayer() {
        //   1         2       3
        // 1
        // 2           T       S1
        // 3 P1
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_consumesArrow", "c_snakeBuffTest");

        res = dmc.tick(Direction.RIGHT);

        assertEquals(new Position(2, 2), getSnakeHeadPos(res));
        assertEquals(Arrays.asList(), TestUtils.getEntities(res, "treasure"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertEquals(-2, res.getBattles().get(0).getRounds().get(0).getDeltaEnemyHealth());
    }

    @Test
    @Tag("16-37")
    @DisplayName("Test snake killed when bomb explodes")
    public void testSnakeKilledWhenBombExplodes() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_killedWhenBombExplodes", "c_snakeTests");

        // Activate Switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up Bomb
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        // Check Bomb exploded with radius 2
        //
        //                 Boulder/Switch        Wall            Wall
        //                Bomb                   Treasure
        //
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(0, TestUtils.getEntities(res, "snake_head").size());
        assertEquals(0, TestUtils.getEntities(res, "snake_body").size());

    }

    @Test
    @Tag("16-38")
    @DisplayName("Test snake body killed when bomb explodes")
    public void testSnakeBodyDestroyedWhenBombExplodes() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest_bodyDestroyedWhenBombExplodes", "c_bombTest_placeBombRadius2");

        // Activate Switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up Bomb
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());
        res = dmc.tick(Direction.RIGHT);

        // Check Bomb exploded with radius 2
        //
        //                 Boulder/Switch        Wall            Wall
        //                Bomb                   Treasure
        //
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(1, TestUtils.getEntities(res, "snake_head").size());
        assertEquals(Arrays.asList(new Position(7, 2)), getAllSnakeBodiesPos(res));

    }

    private Position getSnakeHeadPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "snake_head").get(0).getPosition();
    }

    private List<EntityResponse> getAllSnakeBodies(DungeonResponse res) {
        return TestUtils.getEntities(res, "snake_body");
    }

    private List<EntityResponse> getAllSnakeHeads(DungeonResponse res) {
        return TestUtils.getEntities(res, "snake_head");
    }

    private List<Position> getAllSnakeHeadsPos(DungeonResponse res) {
        return TestUtils.getEntityPositions(res, "snake_head");
    }

    private List<Position> getAllSnakeBodiesPos(DungeonResponse res) {
        return TestUtils.getEntityPositions(res, "snake_body");
    }

    public static void assertListAreEqualIgnoringOrder(List<?> a, List<?> b) {
        // containsAll both ways is important to handle dupes
        assertTrue(a.size() == b.size() && a.containsAll(b) && b.containsAll(a));
    }

}
