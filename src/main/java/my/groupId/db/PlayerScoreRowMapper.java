package my.groupId.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import my.groupId.api.model.Frame;
import my.groupId.api.model.FrameType;
import my.groupId.api.model.PlayerScore;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

/**
 *
 * @author lannguyen
 */
public class PlayerScoreRowMapper implements RowMapper<PlayerScore> {

    @Override
    public PlayerScore map(ResultSet rs, StatementContext ctx) throws SQLException {
        List<Frame> frames = new ArrayList();
        PlayerScore playerScore = new PlayerScore(rs.getInt("playerid"), frames, rs.getInt("lastupdatedframe"));
        frames.add(new Frame(rs.getInt("framenumber"), 
                    rs.getInt("rollone"), 
                    rs.getInt("rolltwo"), 
                    rs.getInt("rollthree"), 
                    rs.getInt("framescore"),
                    FrameType.valueOf(rs.getString("frametype")), 
                    rs.getInt("subsequentrolladdsavail")));
        while(rs.next()) {
            Frame f = new Frame(rs.getInt("framenumber"), 
                    rs.getInt("rollone"), 
                    rs.getInt("rolltwo"), 
                    rs.getInt("rollthree"), 
                    rs.getInt("framescore"),
                    FrameType.valueOf(rs.getString("frametype")), 
                    rs.getInt("subsequentrolladdsavail"));
            frames.add(f);
        }
        playerScore.setRunningTotal(frames.stream().map(f -> f.getFrameScore()).reduce(0, (a,b) -> a + b));
        return playerScore;
    }
    
}
