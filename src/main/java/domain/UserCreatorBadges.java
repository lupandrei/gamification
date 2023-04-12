package domain;

public class UserCreatorBadges {
    private int idUser;
    private int idBadge;

    private int achieved;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBadge() {
        return idBadge;
    }

    public void setIdBadge(int idBadge) {
        this.idBadge = idBadge;
    }

    public UserCreatorBadges(int idUser, int idBadge, int achieved) {
        this.idUser = idUser;
        this.idBadge = idBadge;
        this.achieved = achieved;
    }

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }
}
