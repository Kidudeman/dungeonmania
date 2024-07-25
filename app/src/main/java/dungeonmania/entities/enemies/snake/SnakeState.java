package dungeonmania.entities.enemies.snake;

public class SnakeState {
    private boolean isInvincible;
    private boolean isInvisible;
    private boolean isHibernating;

    public SnakeState() {
        isInvincible = false;
        isInvisible = false;
        isHibernating = false;
    }

    public SnakeState(boolean isInvincible, boolean isInvisible, boolean isHibernating) {
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
        this.isHibernating = isHibernating;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public boolean isHibernating() {
        return isHibernating;
    }

    public void becomeInvincible() {
        isInvincible = true;
        endHibernation();
    }

    public void becomeInvisible() {
        isInvisible = true;
    }

    public void enterHibernation() {
        isHibernating = true;
    }

    public void endHibernation() {
        isHibernating = false;
    }
}
