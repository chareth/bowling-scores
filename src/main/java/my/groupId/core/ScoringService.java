package my.groupId.core;

import java.util.ArrayList;
import java.util.List;
import my.groupId.api.ScoringApi;
import my.groupId.api.model.Frame;
import my.groupId.api.model.FrameType;
import my.groupId.api.model.PlayerScore;
import my.groupId.db.PlayerScoreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interacts with the database to return the information or perform the action
 * requested
 *
 * @author lannguyen
 */
public class ScoringService implements ScoringApi {

    private static final Logger log = LoggerFactory.getLogger(ScoringService.class);

    private final PlayerScoreDao dao;

    public ScoringService(PlayerScoreDao dao) {
        this.dao = dao;
    }

    @Override
    public PlayerScore getPlayerScore(int playerId) {
        return dao.get(playerId);
    }

    @Override
    public PlayerScore createPlayerScore(int playerId) {
        if (dao.get(playerId) == null) {
            dao.insertPlayerScore(playerId);
            for (int i = 1; i < 11; i++) {
                dao.insertFrame(playerId, i);
            }
        } else {
            log.info("playerId={} exists in the database already. Returning existing record", playerId);
        }

        return dao.get(playerId);
    }

    @Override
    public PlayerScore updatePlayerScore(int playerId, int frameNumber, int rollNumber, int pinsDown) {
        PlayerScore playerScore = dao.get(playerId);

        // frameNumber to update must be same as or greater than the latest frameNumber
        if (playerScore.getLastUpdatedFrame() == frameNumber || playerScore.getLastUpdatedFrame() + 1 == frameNumber) {
            playerScore.setLastUpdatedFrame(frameNumber);
            dao.updateLastUpdatedFrame(playerId, frameNumber);
            Frame frameToUpdate = playerScore.getFrames().stream().filter(f -> f.getFrameNumber() == frameNumber).findFirst().get();
            
            switch (rollNumber) {
                case 1:
                    frameToUpdate.setRollOne(pinsDown);
                    break;
                case 2:
                    frameToUpdate.setRollTwo(pinsDown);
                    break;
                case 3:
                    frameToUpdate.setRollThree(pinsDown);
                    break;
                default:
                    break;
            }

            dao.updateFrame(frameToUpdate, playerId);

            applyPinsToSparesAndStrikes(frameNumber, pinsDown, playerScore);

            return dao.get(playerId);
        } else {
            throw new IllegalStateException("Attempting to set a frame out of sequence");
        }

    }

    /**
     * This method applies the pins down for the frame to previous frames if
     * applicable
     *
     * @param frameNumber the current frame
     * @param pinsDown the pins knocked down for the frame
     * @param player the playerScore containing the list of frames
     */
    public void applyPinsToSparesAndStrikes(int frameNumber, int pinsDown, PlayerScore player) {
        for (Frame frame : getApplicableFrames(frameNumber, player.getFrames())) {
            frame.addToFrameScore(pinsDown);
            dao.updateFrame(frame, player.getPlayerId());
        }

    }

    /**
     * Get the frames where this roll should be added to the frameScore. These
     * would be previous frames that are STRIKEs or SPAREs with
     * subsequentRollAddsAvail > 0
     */
    private List<Frame> getApplicableFrames(int frameNumber, List<Frame> frames) {
        List<Frame> result = new ArrayList();
        if (frameNumber > 1) {
            Frame prevFrame = frames.stream().filter((Frame f) -> f.getFrameNumber() == frameNumber - 1).findFirst().get();
            if (prevFrame.getSubsequentRollAddsAvail() > 0 && (FrameType.STRIKE.equals(prevFrame.getFrameType()) || FrameType.SPARE.equals(prevFrame.getFrameType()))) {
                result.add(prevFrame);
            }
        }
        if (frameNumber > 2) {
            Frame prevPrevFrame = frames.stream().filter((Frame f) -> f.getFrameNumber() == frameNumber - 2).findFirst().get();
            if (prevPrevFrame.getSubsequentRollAddsAvail() > 0 && (FrameType.STRIKE.equals(prevPrevFrame.getFrameType()) || FrameType.SPARE.equals(prevPrevFrame.getFrameType()))) {
                result.add(prevPrevFrame);
            }
        }
        return result;
    }

}
