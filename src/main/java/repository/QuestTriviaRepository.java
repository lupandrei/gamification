package repository;

import domain.QuestTrivia;
import domain.dtos.QuestDTO;
import exception.RepositoryException;
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

public class QuestTriviaRepository implements IQuestTriviaRepository{
    JdbcUtils dbutils;
    private static final Logger logger= LogManager.getLogger();

    public QuestTriviaRepository(Properties props) {
        this.dbutils = new JdbcUtils(props);
    }

    @Override
    public void add(QuestTrivia entity) {
        logger.traceEntry("saving entry {}", entity);
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("insert into questtrivia(question,answer,hint) Values (?,?,?)")){
            ps.setString(1, entity.getQuestion());
            ps.setString(2,entity.getAnswer());
            ps.setString(3, entity.getHint());
            int resultset= ps.executeUpdate();
            logger.trace("Saved  {} instances",resultset);
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB "+se);
        }
        logger.traceExit();
    }

    @Override
    public QuestTrivia findById(Integer integer) throws RepositoryException {
        logger.traceEntry("Find questTrivia with id {}",integer);
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("select * from questtrivia where id=?")) {
            ps.setInt(1, integer);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String question = resultSet.getString("question");
                    String answer = resultSet.getString("answer");
                    String hint = resultSet.getString("hint");
                    QuestTrivia questTrivia = new QuestTrivia(integer, question,answer, hint);
                    logger.trace("found quest " + questTrivia);
                    return questTrivia;
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB" +se);
        }
        return null;
    }

    @Override
    public Iterable<QuestTrivia> getAll() {
        logger.traceEntry("return all trivia quests");
        Connection conn = dbutils.getConnection();
        List<QuestTrivia> quests= new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("select * from questtrivia")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String question = resultSet.getString("question");
                    String answer = resultSet.getString("answer");
                    String hint = resultSet.getString("hint");
                    QuestTrivia questTrivia = new QuestTrivia(id, question,answer, hint);
                    logger.trace("found quest " + questTrivia);
                    quests.add(questTrivia);
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB" +se);
        }
        return quests;
    }

    @Override
    public void delete(int id) {
        logger.traceEntry("updating");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("delete from questtrivia where id=?")){
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
    public QuestTrivia getLastAdded() {
        logger.traceEntry("Find last quest");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("select * from questtrivia order by id desc limit 1")) {

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String question = resultSet.getString("question");
                    String answer = resultSet.getString("answer");
                    String hint = resultSet.getString("hint");
                    QuestTrivia questTrivia = new QuestTrivia(id, question,answer, hint);
                    logger.trace("found quest " + questTrivia);
                    return questTrivia;
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB" +se);
        }
        return null;
    }
}
