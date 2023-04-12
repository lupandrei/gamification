package test;


import domain.QuestTrivia;
import domain.User;
import domain.dtos.BadgeDTO;
import domain.dtos.QuestDTO;
import exception.RepositoryException;
import exception.ServiceException;
import service.Service;

import java.util.List;

public class Test {
    private Service srv;

    public Test(Service srv) {
        this.srv = srv;
    }

    public  void run() throws RepositoryException, ServiceException {
        runTestLogin();
        runTestAddQuestTrivia();
        runTestDeleteQuestTrivia();
        runTestTop();
        runTestGetAllBadges();
        runTestGetAllTriviaQuests();
        runTestUpdateTokens();
        runTestUpdateQuestsCreated();
        runTestUpdateQuestsTaken();
        runTestFindQuestById();
    }
    private  void runTestLogin()throws RepositoryException, ServiceException{
        srv.loginUser("andrei","parola");
        assert true;
        try{
            srv.loginUser("andrei","incorecta");
            assert false;
        }
        catch(ServiceException se){
            assert se.getMessage().equals("Invalid password");
        }
        try{
            srv.loginUser("nonexistent","incorecta");
            assert false;
        }
        catch(RepositoryException se){
            assert se.getMessage().equals("User with given username does not exist");
        }
    }
    private void runTestTop() throws RepositoryException, ServiceException{
        List<User> top =srv.getUsersTop();
        for(int i=0;i<top.size()-1;i++){
            if(top.get(i).getScore() < top.get(i+1).getScore()){
                assert false;
            }
        }
        assert true;
    }
    private void runTestGetAllBadges()throws RepositoryException, ServiceException{
        List<BadgeDTO> badges = srv.getAllBadges("andrei");
        assert badges.size()==6;
    }
    private void runTestGetAllTriviaQuests()throws RepositoryException, ServiceException{
        List<QuestDTO> quests = srv.getAllTriviaQuests("andrei");
        assert quests.size()==3;
    }
    private void runTestFindQuestById()throws RepositoryException, ServiceException{
        QuestTrivia quest = srv.findQuestById(2);
        assert quest.getAnswer().equals("harper lee");
    }
    private void runTestUpdateTokens() throws RepositoryException, ServiceException{
        User user = srv.findUserById("andrei");
        int tokens = user.getScore();
        srv.updateTokens("andrei",tokens+1);
        user = srv.findUserById("andrei");
        assert tokens==user.getScore()-1;
    }
    private void runTestUpdateQuestsCreated() throws RepositoryException, ServiceException{
        User user = srv.findUserById("andrei");
        int quests = user.getQuestsCreated();
        srv.updateQuestsCreated("andrei",quests+1);
        user = srv.findUserById("andrei");
        assert quests==user.getQuestsCreated()-1;
    }
    private void runTestUpdateQuestsTaken() throws RepositoryException, ServiceException{
        User user = srv.findUserById("andrei");
        int quests = user.getQuestsTaken();
        srv.updateQuestsTaken("andrei",quests+1);
        user = srv.findUserById("andrei");
        assert quests==user.getQuestsTaken()-1;
    }
    private void runTestAddQuestTrivia(){
        srv.addQuestTrivia("question","answer","hint","andrei",50);
        QuestTrivia quest = srv.getLastAddedQuest();
        assert quest.getAnswer().equals("answer");
        assert quest.getQuestion().equals("question");
        assert quest.getHint().equals("hint");
    }
    private void runTestDeleteQuestTrivia(){
        QuestTrivia quest = srv.getLastAddedQuest();
        String answer = quest.getAnswer();
        srv.deleteQuest(quest.getId());
        quest = srv.getLastAddedQuest();
        assert !quest.getQuestion().equals(answer);
    }
}
