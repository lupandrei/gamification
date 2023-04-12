package repository;

import domain.QuestTrivia;
import domain.dtos.QuestDTO;

import java.util.List;

public interface IQuestTriviaRepository extends repository<QuestTrivia, Integer> {
    void delete(int id);

    QuestTrivia getLastAdded();
}
