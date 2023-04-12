package repository;

import domain.PlayerBadge;
import domain.UserPlayerBadges;
import domain.dtos.BadgeDTO;

import java.util.List;

public interface IUserPlayerBadgesRepository extends IUserBadgesRepository<UserPlayerBadges,Integer> {
    List<BadgeDTO> getAllAchieved(String idUser);
}
