package repository;

import domain.CreatorBadge;
import domain.UserCreatorBadges;
import domain.dtos.BadgeDTO;

import java.util.List;

public interface IUserCreatorBadgesRepository extends IUserBadgesRepository<UserCreatorBadges,Integer> {
    List<BadgeDTO> getAllAchieved(String idUser);
}
