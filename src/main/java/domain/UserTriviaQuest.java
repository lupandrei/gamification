package domain;

public class UserTriviaQuest {
    private String idUser;
    private int idTriviaQuest;
    private int reward;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdTriviaQuest() {
        return idTriviaQuest;
    }

    public void setIdTriviaQuest(int idTriviaQuest) {
        this.idTriviaQuest = idTriviaQuest;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public UserTriviaQuest(String idUser, int idTriviaQuest, int reward) {
        this.idUser = idUser;
        this.idTriviaQuest = idTriviaQuest;
        this.reward = reward;
    }
}
