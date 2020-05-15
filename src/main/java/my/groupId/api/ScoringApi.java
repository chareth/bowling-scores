package my.groupId.api;

import my.groupId.api.model.PlayerScore;

/**
 *
 * @author lannguyen
 */
public interface ScoringApi {
    
    PlayerScore getPlayerScore(int playerId);
    
    PlayerScore createPlayerScore(int playerId);
    
    PlayerScore updatePlayerScore(int playerId, int frameNumber, int rollNumber, int pinsDown);
    
}
