package repository;

import java.util.List;

public interface IUserBadgesRepository<E,ID>{
    void update(String idUser, ID id);
}
