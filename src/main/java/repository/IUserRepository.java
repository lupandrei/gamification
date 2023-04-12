package repository;

import domain.User;

import java.util.List;

public interface IUserRepository extends repository<User, String> {
    List<User> getTopUsers();
    void updateTokens(String username, int tokens);

    void updateQuestsTaken(String username,int taken);

    void updateQuestsCreated(String username,int created);

    int findUserIdDB(String username);
}
