package repository;

import domain.CreatorBadge;
import domain.PlayerBadge;
import domain.User;
import domain.UserCreatorBadges;
import domain.dtos.BadgeDTO;
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

public class UserCreatorBadgesRepository implements IUserCreatorBadgesRepository{
    JdbcUtils dbutils;
    private static final Logger logger= LogManager.getLogger();
    public UserCreatorBadgesRepository(Properties props) {
        this.dbutils = new JdbcUtils(props);
    }


    @Override
    public void update(String idUser, Integer integer) {
        logger.traceEntry("updating");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("update usercreatorbadges set achieved=1 where iduser=? and idbadge=?")){
            ps.setString(1, idUser);
            ps.setInt(2,integer);
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
    public List<BadgeDTO> getAllAchieved(String idUser) {
        logger.traceEntry("get all achieved creator badges");
        Connection connection = dbutils.getConnection();
        List<BadgeDTO> badges = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("Select cb.name as name from usercreatorbadges ub " +
                " inner join creatorbadge cb on cb.id=ub.idbadge " +
                " where ub.achieved=1 and ub.iduser=?")){
            ps.setString(1,idUser);
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");


                    BadgeDTO badgeDTO = new BadgeDTO(name,"creator");
                    logger.trace("found badge " + badgeDTO);
                    badges.add(badgeDTO);
                }
            }
        }
        catch(SQLException se){
            logger.error(se);
            System.err.println(se);
        }
        logger.traceExit();
        return badges;
    }
}
