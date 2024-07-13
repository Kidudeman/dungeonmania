package dungeonmania.goals;

public abstract class CompositeGoal implements Goal {
    private final Goal goal1;
    private final Goal goal2;

    public CompositeGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public Goal getGoal1() {
        return goal1;
    }

    public Goal getGoal2() {
        return goal2;
    }
}
