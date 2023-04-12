package domain.dtos;

public class QuestDTO {
    private int id;
    private String type;
    private String creator;
    private int reward;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public QuestDTO(int id, String type, String creator, int reward) {
        this.id = id;
        this.type = type;
        this.creator = creator;
        this.reward = reward;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
