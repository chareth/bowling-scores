package my.groupId.db;

import my.groupId.api.model.Frame;
import my.groupId.api.model.PlayerScore;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author lannguyen
 */
public interface PlayerScoreDao {

    @SqlUpdate("insert into playerscores (playerid) "
            + "values (:playerId)")
    void insertPlayerScore(@Bind("playerId") int playerId);

    @SqlUpdate("insert into frames (playerid, framenumber) "
            + "values (:playerId, :frameNumber)")
    void insertFrame(@Bind("playerId") int playerId, @Bind("frameNumber") int frameNumber);

    @SqlQuery("select * from frames f "
            + "join playerscores p on p.playerid=f.playerid "
            + "where f.playerid=:playerId "
            + "order by framenumber asc;")
    @RegisterRowMapper(PlayerScoreRowMapper.class)
    PlayerScore get(@Bind("playerId") int playerId);

    @SqlUpdate("UPDATE frames SET rollone=:frame.rollOne,"
            + "rolltwo=:frame.rollTwo,"
            + "rollthree=:frame.rollThree,"
            + "framescore=:frame.frameScore,"
            + "frametype=:frame.frameType,"
            + "subsequentrolladdsavail=:frame.subsequentRollAddsAvail "
            + "where playerid=:playerId AND framenumber=:frame.frameNumber")
    void updateFrame(@BindBean("frame") Frame frame, @Bind("playerId") int playerId);

    @SqlUpdate("UPDATE playerscores SET lastupdatedframe=:lastUpdatedFrame "
            + "where playerid=:playerId")
    void updateLastUpdatedFrame(@Bind("playerId") int playerId, @Bind("lastUpdatedFrame") int lastUpdatedFrame);
}
