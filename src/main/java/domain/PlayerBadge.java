package domain;

public class PlayerBadge extends Badge{

    private int minQuestsTaken;
    public PlayerBadge(int id, String name, int minQuestsTaken) {
        super(id, name);
        this.minQuestsTaken = minQuestsTaken;
    }

    public int getMinQuestsTaken() {
        return minQuestsTaken;
    }

    public void setMinQuestsTaken(int minQuestsTaken) {
        this.minQuestsTaken = minQuestsTaken;
    }
}
