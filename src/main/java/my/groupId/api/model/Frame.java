package my.groupId.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import static my.groupId.api.model.FrameType.OPEN;
import static my.groupId.api.model.FrameType.SPARE;
import static my.groupId.api.model.FrameType.STRIKE;

/**
 * Contains information about an individual frame for a player
 * @author lannguyen
 */
public class Frame {
    // this is a number from 1 to 10
    private final int frameNumber;
    
    // the number of pins knocked down in the first roll
    private int rollOne;
    
    // the number of pins knocked down in the second roll
    private int rollTwo;
    
    // this is a third roll only for the 10th frame if it is a STRIKE or SPARE
    private int rollThree;
    
    // at minimum, is the sum of the pins knocked down
    private int frameScore;
    
    // describes the frame based on the pins down for the frame (sum(rolls))
    private FrameType frameType;
    
    // keeps track of how many additional rolls were added to this frame
    private int subsequentRollAddsAvail;
    
    public Frame(int frameNumber) {
        this.frameNumber = frameNumber;
        this.rollOne = 0;
        this.rollTwo = 0;
        this.rollThree = 0;
        this.frameType = OPEN;
        this.subsequentRollAddsAvail = 0;
    }
    
    public Frame(int frameNumber, int rollOne, int rollTwo, int rollThree, int frameScore, FrameType frameType, int subsequentRollAddsAvail) {
        this.frameNumber = frameNumber;
        this.rollOne = rollOne;
        this.rollTwo = rollTwo;
        this.rollThree = rollThree;
        this.frameScore = frameScore;
        this.frameType = frameType;
        this.subsequentRollAddsAvail = subsequentRollAddsAvail;
    }
    
    
    /**
     * @return the frameNumber
     */
    @JsonProperty
    public int getFrameNumber() {
        return frameNumber;
    }


    /**
     * @return the rollOne
     */
    @JsonProperty
    public int getRollOne() {
        return rollOne;
    }

    /**
     * @param rollOne the rollOne to set
     */
    public void setRollOne(int rollOne) {
        if(rollOne > 10) {
            throw new IllegalStateException("Attempting to set a roll greater than 10 is invalid");
        }
        
        this.rollOne = rollOne;
        frameScore = rollOne;
        if(rollOne == 10) {
            frameType = STRIKE;
            subsequentRollAddsAvail = 2;
        } else {
            frameType = OPEN;
            subsequentRollAddsAvail = 0;
        }
    }

    /**
     * @return the rollTwo
     */
    @JsonProperty
    public int getRollTwo() {
        return rollTwo;
    }

    /**
     * @param rollTwo the rollTwo to set
     */
    public void setRollTwo(int rollTwo) {
        if(frameNumber != 10 && rollTwo > 10 - rollOne) {
            throw new IllegalStateException("Attempting to set a roll greater than the pins available to knock down is invalid");
        }
        
        this.rollTwo = rollTwo;
        frameScore = rollOne + rollTwo;
        
        if(!STRIKE.equals(frameType) && frameScore == 10) {
            frameType = SPARE;
            subsequentRollAddsAvail = 1;
        } else if(frameScore < 10){
            frameType = OPEN;
            subsequentRollAddsAvail = 0;
        }
    }

    /**
     * this roll should only be set for the 10th frame that is a  STRIKE or SPARE
     * @param rollThree the rollThree to set
     */
    public void setRollThree(int rollThree) {
        if(frameNumber == 10 && !OPEN.equals(frameType)) {
            this.rollThree = rollThree;
            frameScore = rollOne + rollTwo + rollThree;
        } else {
            throw new IllegalStateException("Attempting to set a roll 3 for this frame is invalid");
        }
    }
    
    /**
     * 
     * @return 
     */
    @JsonProperty
    public int getRollThree() {
        return rollThree;
    }
    /**
     * @return the frameScore
     */
    @JsonProperty
    public int getFrameScore() {
        return frameScore;
    }

    /**
     * This method should only be called for SPARE or STRIKE frames 
     * @param pins 
     */
    public void addToFrameScore(int pins) {
        if(subsequentRollAddsAvail > 0) {
            frameScore += pins;
            subsequentRollAddsAvail --;
        } else {
            throw new IllegalStateException("Attempting to add to a frame score when it is not applicable");
        }
        
    }
    
    /**
     * @return the frameType
     */
    public FrameType getFrameType() {
        return frameType;
    }

    /**
     * @return the subsequentRollAddsAvail
     */
    public int getSubsequentRollAddsAvail() {
        return subsequentRollAddsAvail;
    }

}
