package my.groupId.resources;

import com.codahale.metrics.annotation.Timed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import my.groupId.api.model.PlayerScore;
import my.groupId.api.ScoringApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Endpoint to update, create or get a player's scores
 *
 * @author lannguyen
 */
@Path("/scores/{playerId}")
@Produces(MediaType.APPLICATION_JSON)
public class ScoreResource {

    private static final Logger log = LoggerFactory.getLogger(ScoreResource.class);

    private final ScoringApi api;

    public ScoreResource(ScoringApi api) {
        this.api = api;
    }

    /**
     * Sets up the records in the database for that player. If the player
     * already exists in the database, this will return the existing records.
     *
     * @param playerId the playerId of the player to create the records for
     * @return the PlayerScore for the playerId
     */
    @POST
    @Timed
    public PlayerScore createPlayerScore(@PathParam("playerId") int playerId) {
        log.info("Received request to create a player where playerId={}", playerId);
        return api.createPlayerScore(playerId);
    }

    /**
     * Fetches and returns the playerScore for the playerId If the playerId does
     * not exist in the database, the response will be empty
     *
     * @param playerId the playerId to get the PlayerScore for
     * @return the PlayerScore corresponding to the playerId
     */
    @GET
    @Timed
    public PlayerScore getPlayerScore(@PathParam("playerId") int playerId) {
        log.info("Received request to get the playerScore where playerId={}", playerId);
        PlayerScore playerScore = api.getPlayerScore(playerId);
        return playerScore;
    }

    /**
     * Updates the PlayerScore given the frame scoring information and the
     * playerId. Updates to frames cannot be done out of order
     *
     * @param playerId the playerId whose records to update
     * @param frameNumber the frameNumber to update
     * @param rollNumber which roll are these changes for
     * @param pinsDown the number of pins knocked down for the roll
     * @return the updated player score
     */
    @PUT
    @Timed
    public PlayerScore updatePlayerScore(@PathParam("playerId") int playerId,
            @QueryParam("frameNumber") int frameNumber,
            @QueryParam("rollNumber") int rollNumber,
            @QueryParam("pinsDown") int pinsDown) {

        log.info("Received request to update the playerScore where playerId={}, "
                + "frameNumber={}, rollNumber={}, pinsDown={}", playerId, frameNumber, rollNumber, pinsDown);

        // basic validation on the inputs
        if (frameNumber > 10 || frameNumber < 1
                || rollNumber > 3 || rollNumber < 1
                || pinsDown > 10 || pinsDown < 0) {
            log.error("Input(s) for updating score were out of bounds");
            throw new WebApplicationException("Input out of bounds");
        }

        try {
            return api.updatePlayerScore(playerId, frameNumber, rollNumber, pinsDown);
        } catch (IllegalStateException e) {
            throw new WebApplicationException(e.getMessage());
        }

    }

}
