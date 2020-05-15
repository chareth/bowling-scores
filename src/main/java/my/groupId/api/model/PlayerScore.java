package my.groupId.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Higher level class to contain the frames and related information
 * @author lannguyen
 */
public class PlayerScore {

    // the id of the player
    private final int playerId;

    // the sum of the frameScores
    private int runningTotal;
    
    // the most recent frame number updated
    private int lastUpdatedFrame;

    // this player's frames
    private final List<Frame> frames;

    public PlayerScore(int playerId, List<Frame> frames, int lastUpdatedFrame) {
        this.playerId = playerId;
        this.frames = frames;
        this.lastUpdatedFrame = lastUpdatedFrame;
    }

    /**
     * @return the runningTotal
     */
    @JsonProperty
    public int getRunningTotal() {
        return runningTotal;
    }

    /**
     * @param runningTotal the runningTotal to set
     */
    public void setRunningTotal(int runningTotal) {
        this.runningTotal = runningTotal;
    }

    /**
     * @return the frames
     */
    @JsonProperty
    public List<Frame> getFrames() {
        return frames;
    }

    /**
     * @return the playerId
     */
    @JsonProperty
    public int getPlayerId() {
        return playerId;
    }

    /**
     * @return the lastUpdatedFrame
     */
    public int getLastUpdatedFrame() {
        return lastUpdatedFrame;
    }

    /**
     * @param lastUpdatedFrame the lastUpdatedFrame to set
     */
    public void setLastUpdatedFrame(int lastUpdatedFrame) {
        this.lastUpdatedFrame = lastUpdatedFrame;
    }
}
