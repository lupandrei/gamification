package repository;

import domain.UserTriviaQuest;
import domain.dtos.QuestDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserTriviaQuestRepository implements IUserTriviaQuestRepository{

    JdbcUtils dbutils;
    private static final Logger logger= LogManager.getLogger();

    public UserTriviaQuestRepository(Properties props) {
        this.dbutils=new JdbcUtils(props);
    }
    @Override
    public List<QuestDTO> getAllQuests(String idUser) {
        logger.traceEntry("get all guests");
        Connection connection = dbutils.getConnection();
        List<QuestDTO> quests = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("Select qt.id, u.username,utq.reward from usertriviaquest utq " +
                "inner join User u on u.username = utq.iduser " +
                "inner join questtrivia qt on qt.id=utq.idtriviaquest " +
                "where utq.iduser != ?")){
            ps.setString(1,idUser);
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    int reward = resultSet.getInt("reward");
                    QuestDTO questDTO = new QuestDTO(id, "trivia",username,reward);
                    logger.trace("found quest " + questDTO);
                    quests.add(questDTO);
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println(se);
        }
        logger.traceExit();
        return quests;
    }

    @Override
    public void delete(int id) {
        logger.traceEntry("deleting");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("delete from usertriviaquest where idtriviaquest=?")){
            ps.setInt(1, id);
            int resultset= ps.executeUpdate();
            logger.trace("deleted  {} instances",resultset);
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB "+se);
        }
        logger.traceExit();
    }

    @Override
    public void add(UserTriviaQuest userTriviaQuest) {
        logger.traceEntry("saving entry {}", userTriviaQuest);
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("insert into usertriviaquest(iduser,idtriviaquest,reward) Values (?,?,?)")){
            ps.setString(1, userTriviaQuest.getIdUser());
            ps.setInt(2,userTriviaQuest.getIdTriviaQuest());
            ps.setInt(3, userTriviaQuest.getReward());
            int resultset= ps.executeUpdate();
            logger.trace("Saved  {} instances",resultset);
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB "+se);
        }
        logger.traceExit();
    }

}
