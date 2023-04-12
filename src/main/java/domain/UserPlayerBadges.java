package domain;

public class UserPlayerBadges {
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

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }

    public UserPlayerBadges(int idUser, int idBadge, int achieved) {
        this.idUser = idUser;
        this.idBadge = idBadge;
        this.achieved = achieved;
    }
}
