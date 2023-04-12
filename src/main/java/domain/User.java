package domain;

public class User implements Entity<String>{
    private String username;
    private String password;
    private int score;
    private int questsCreated;
    private int questsTaken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password, int questsCreated, int questsTaken,int score) {
        this.username = username;
        this.password = password;
        this.questsCreated = questsCreated;
        this.questsTaken = questsTaken;
        this.score = score;
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public void setId(String s) {
        username=s;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getQuestsCreated() {
        return questsCreated;
    }

    public void setQuestsCreated(int questsCreated) {
        this.questsCreated = questsCreated;
    }

    public int getQuestsTaken() {
        return questsTaken;
    }

    public void setQuestsTaken(int questsTaken) {
        this.questsTaken = questsTaken;
    }
}
