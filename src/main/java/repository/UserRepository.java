package repository;

import domain.User;
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

public class UserRepository implements IUserRepository{
    JdbcUtils dbutils;
    private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props) {
        this.dbutils=new JdbcUtils(props);
    }

    @Override
    public void add(User entity) {

    }

    @Override
    public User findById(String s) throws RepositoryException {
        logger.traceEntry("find admin with username +" +s);
        Connection connection = dbutils.getConnection();
        try(PreparedStatement ps = connection.prepareStatement("Select * from user where username=?")){
            ps.setString(1,s);
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    String password = resultSet.getString("password");
                    int questsCreated = resultSet.getInt("questscreated");
                    int questsTaken = resultSet.getInt("queststaken");
                    int score = resultSet.getInt("score");
                    User user = new User(s, password, questsCreated, questsTaken,score);
                    logger.trace("found user " + user);
                    return user;
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println(se);
        }
        logger.traceExit();
        throw new RepositoryException("User with given username does not exist");
    }


    @Override
    public Iterable<User> getAll() {
        logger.traceEntry("get all users");
        Connection connection = dbutils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("Select * from user")){
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    int questsCreated = resultSet.getInt("questscreated");
                    int questsTaken = resultSet.getInt("queststaken");
                    int score = resultSet.getInt("score");

                    User user = new User(username, password, questsCreated, questsTaken,score);
                    logger.trace("found user " + user);
                    users.add(user);
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println(se);
        }
        logger.traceExit();
        return users;
    }

    @Override
    public List<User> getTopUsers() {
        logger.traceEntry("get top users");
        Connection connection = dbutils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("Select * from user ORDER BY score DESC")){
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    int questsCreated = resultSet.getInt("questscreated");
                    int questsTaken = resultSet.getInt("queststaken");
                    int score = resultSet.getInt("score");

                    User user = new User(username, password, questsCreated, questsTaken,score);
                    logger.trace("found user " + user);
                    users.add(user);
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println(se);
        }
        logger.traceExit();
        return users;
    }

    @Override
    public void updateTokens(String username, int tokens) {
        logger.traceEntry("updating");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("update user set score=? where username=?")){
            ps.setInt(1, tokens);
            ps.setString(2,username);
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
    public void updateQuestsTaken(String username, int taken) {
        logger.traceEntry("updating");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("update user set queststaken=? where username=?")){
            ps.setInt(1, taken);
            ps.setString(2,username);
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
    public void updateQuestsCreated(String username, int created) {
        logger.traceEntry("updating");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("update user set questscreated=? where username=?")){
            ps.setInt(1, created);
            ps.setString(2,username);
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
    public int findUserIdDB(String username) {
        logger.traceEntry("find user with username +" +username);
        Connection connection = dbutils.getConnection();
        try(PreparedStatement ps = connection.prepareStatement("Select * from user where username=?")){
            ps.setString(1,username);
            try(ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    logger.trace("found user " + id);
                    return id;
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println(se);
        }
        logger.traceExit();
       return 0;
    }
}
