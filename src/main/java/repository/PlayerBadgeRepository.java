package repository;

import domain.PlayerBadge;

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

public class PlayerBadgeRepository implements IPlayerBadgeRepository{
    JdbcUtils dbutils;
    private static final Logger logger= LogManager.getLogger();

    public PlayerBadgeRepository(Properties props) {
        this.dbutils = new JdbcUtils(props);
    }

    @Override
    public List<PlayerBadge> getAll() {
        logger.traceEntry("return all player badges");
        Connection conn = dbutils.getConnection();
        List<PlayerBadge> badges= new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("select * from playerbadge")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int minqueststaken = resultSet.getInt("minqueststaken");
                    PlayerBadge badge = new PlayerBadge(id, name,minqueststaken);
                    logger.trace("found player badge " + badge);
                    badges.add(badge);
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println("Error DB" +se);
        }
        return badges;
    }
}
