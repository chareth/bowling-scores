package my.groupId.api.model;

/**
 * Enum used to describe the type of frame
 * @author lannguyen
 */
public enum FrameType {
    
    // a frame is OPEN when the number of pins down is less than 10
    OPEN, 
    
    // a frame is SPARE when the number of pins down is 10 in two rolls
    SPARE, 
    
    // a frame is STRIKE when the number of pins down is 10 in the first roll
    STRIKE;
}
