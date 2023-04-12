package service;

import domain.*;
import domain.dtos.BadgeDTO;
import domain.dtos.QuestDTO;
import exception.RepositoryException;
import exception.ServiceException;
import observer.observable;
import observer.observer;
import repository.*;

import java.util.ArrayList;
import java.util.List;

public class Service implements observable {

    private IUserRepository userRepository;
    private List<observer> obs;
    private ICreatorBadgeRepository creatorBadgeRepository;
    private IPlayerBadgeRepository playerBadgeRepository;
    private IUserPlayerBadgesRepository userPlayerBadgesRepository;
    private IUserCreatorBadgesRepository userCreatorBadgesRepository;

    private IQuestTriviaRepository questTriviaRepository;

    private IUserTriviaQuestRepository iUserTriviaQuestRepository;

    public Service(IUserRepository userRepository, ICreatorBadgeRepository creatorBadgeRepository, IPlayerBadgeRepository playerBadgeRepository, IUserPlayerBadgesRepository userPlayerBadgesRepository, IUserCreatorBadgesRepository userCreatorBadgesRepository, IQuestTriviaRepository questTriviaRepository, IUserTriviaQuestRepository iUserTriviaQuestRepository) {
        this.userRepository = userRepository;
        this.creatorBadgeRepository = creatorBadgeRepository;
        this.playerBadgeRepository = playerBadgeRepository;
        this.userPlayerBadgesRepository = userPlayerBadgesRepository;
        this.userCreatorBadgesRepository = userCreatorBadgesRepository;
        this.questTriviaRepository = questTriviaRepository;
        this.iUserTriviaQuestRepository = iUserTriviaQuestRepository;
        this.obs=new ArrayList<>();
    }

    public User loginUser(String username, String password) throws RepositoryException, ServiceException {
            User user = userRepository.findById(username);
            if(user.getPassword().equals(password)){
                return user;
            }
            throw new ServiceException("Invalid password");
    }

    public List<User> getUsersTop() {
        return userRepository.getTopUsers();
    }
    public void updateBadges(){
        Iterable<User> users = userRepository.getAll();
        List<PlayerBadge> playerBadges = playerBadgeRepository.getAll();
        List<CreatorBadge> creatorBadges = creatorBadgeRepository.getAll();
        for(User user:users){
            for(PlayerBadge playerBadge:playerBadges){
                if(user.getQuestsTaken()>playerBadge.getMinQuestsTaken()){
                    userPlayerBadgesRepository.update(user.getId(), playerBadge.getId());
                }
            }
            for(CreatorBadge creatorBadge:creatorBadges){
                if(user.getQuestsTaken()>creatorBadge.getMinQuestsCreated()){
                    userCreatorBadgesRepository.update(user.getId(), creatorBadge.getId());
                }
            }
        }
    }
    public List<BadgeDTO> getAllBadges(String idUser){
        List<BadgeDTO>userBadges= userPlayerBadgesRepository.getAllAchieved(idUser);
        for(BadgeDTO badgeDTO: userCreatorBadgesRepository.getAllAchieved(idUser)){
            userBadges.add(badgeDTO);
        }
        return userBadges;
    }
    public List<QuestDTO> getAllTriviaQuests(String idUser){
        return iUserTriviaQuestRepository.getAllQuests(idUser);
    }
    public QuestTrivia findQuestById(int id) {
        try{
            return questTriviaRepository.findById(id);
        }
        catch(RepositoryException re){

        }
        return null;
    }

    public void updateTokens(String id, int reward) {
        userRepository.updateTokens(id,reward);
        notifyObservers();
    }
    public void updateQuestsCreated(String id, int created){
        userRepository.updateQuestsCreated(id,created);
        notifyObservers();
    }
    public void updateQuestsTaken(String id, int taken){
        userRepository.updateQuestsTaken(id,taken);
        notifyObservers();
    }

    public void deleteQuest(Integer id) {
        questTriviaRepository.delete(id);
        iUserTriviaQuestRepository.delete(id);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for(observer o:obs){
            o.update();
        }
    }

    @Override
    public void addObserver(observer o) {
        obs.add(o);
    }
    public User findUserById(String username){
        try{
            return userRepository.findById(username);
        }
        catch(RepositoryException re){
            System.err.println(re.getMessage());
        }
        return null;
    }
    public void addQuestTrivia(String question,String answer,String hint,String idUser,int reward){
        QuestTrivia questTrivia = new QuestTrivia(0,question,answer,hint);
        questTriviaRepository.add(questTrivia);
        questTrivia =questTriviaRepository.getLastAdded();
        UserTriviaQuest userTriviaQuest = new UserTriviaQuest(idUser,questTrivia.getId(),reward);
        iUserTriviaQuestRepository.add(userTriviaQuest);
        notifyObservers();
    }
    public QuestTrivia getLastAddedQuest(){
        return questTriviaRepository.getLastAdded();
    }

}
