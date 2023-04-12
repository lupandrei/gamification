package repository;

import domain.CreatorBadge;
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

public class CreatorBadgeRepository implements ICreatorBadgeRepository{

    JdbcUtils dbutils;
    private static final Logger logger= LogManager.getLogger();

    public CreatorBadgeRepository(Properties props) {
        this.dbutils = new JdbcUtils(props);
    }
    @Override
    public List<CreatorBadge> getAll() {
        logger.traceEntry("return all creator badges");
        Connection conn = dbutils.getConnection();
        List<CreatorBadge> badges= new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("select * from creatorbadge")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int minquestcreated = resultSet.getInt("minquestscreated");
                    CreatorBadge badge = new CreatorBadge(id, name,minquestcreated);
                    logger.trace("found creator badge " + badge);
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
