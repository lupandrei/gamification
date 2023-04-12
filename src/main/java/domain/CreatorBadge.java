package domain;

public class CreatorBadge extends Badge{

    private int minQuestsCreated;
    public CreatorBadge(int id, String name, int minQuestsCreated) {
        super(id, name);
        this.minQuestsCreated = minQuestsCreated;
    }

    public int getMinQuestsCreated() {
        return minQuestsCreated;
    }

    public void setMinQuestsCreated(int minQuestsCreated) {
        this.minQuestsCreated = minQuestsCreated;
    }
}
