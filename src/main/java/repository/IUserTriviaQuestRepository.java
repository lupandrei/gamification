package repository;

import domain.UserTriviaQuest;
import domain.dtos.QuestDTO;

import java.util.List;

public interface IUserTriviaQuestRepository {
    List<QuestDTO> getAllQuests(String idUser);

    void delete(int id);
    void add(UserTriviaQuest userTriviaQuest);
}
