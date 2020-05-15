package my.groupId.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import my.groupId.api.model.Frame;
import my.groupId.api.model.PlayerScore;
import my.groupId.db.PlayerScoreDao;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author lannguyen
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoringServiceTest {

    @Mock
    private final PlayerScoreDao dao = Mockito.mock(PlayerScoreDao.class);

    private final ScoringService instance = new ScoringService(dao);

    private PlayerScore player;

    private List<Frame> frames;

    @Before
    public void setup() {
        frames = new ArrayList();
        player = new PlayerScore(1, frames, 0);
    }

    /**
     * test scoring of an open frame
     */
    @Test
    public void openFrame() {
        System.out.println("test open frame scoring");

        frames.add(new Frame(1));
        frames.get(0).setRollOne(3);
        instance.applyPinsToSparesAndStrikes(1, 3, player);

        frames.get(0).setRollTwo(3);
        instance.applyPinsToSparesAndStrikes(1, 3, player);
        assertEquals(6, frames.get(0).getFrameScore());
    }

    /**
     * test scoring of a spare then open frame
     */
    @Test
    public void spareOpenFrame() {
        System.out.println("test spare, then open frame scoring");

        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        frames.add(f1);
        frames.add(f2);

        // frame one is a spare; rolls 5, 5
        f1.setRollOne(5);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);
        f1.setRollTwo(5);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollTwo(), player);

        // frame two is open; rolls 3, 3
        f2.setRollOne(3);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        f2.setRollTwo(3);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollTwo(), player);

        assertEquals(13, f1.getFrameScore());
        assertEquals(6, f2.getFrameScore());

    }

    /**
     * test scoring of a spare, spare then open frame
     */
    @Test
    public void spareSpareOpen() {
        System.out.println("test spare, spare, open frame scoring");

        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        Frame f3 = new Frame(3);
        frames.add(f1);
        frames.add(f2);
        frames.add(f3);

        // frame one is a spare; rolls 5, 5
        f1.setRollOne(5);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);
        f1.setRollTwo(5);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollTwo(), player);

        // frame two is spare; rolls 6, 4
        f2.setRollOne(6);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        f2.setRollTwo(4);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollTwo(), player);

        // frame three is open; rolls 0, 3
        f3.setRollOne(0);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollOne(), player);

        f3.setRollTwo(3);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollTwo(), player);

        assertEquals(16, f1.getFrameScore());
        assertEquals(10, f2.getFrameScore());
        assertEquals(3, f3.getFrameScore());

    }

    /**
     * test scoring of a strike then open frame
     */
    @Test
    public void strikeOpen() {
        System.out.println("test strike, then open frame scoring");

        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        frames.add(f1);
        frames.add(f2);

        // frame one is a strike; rolls 10
        f1.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);

        // frame two is open; rolls 0, 4
        f2.setRollOne(0);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        f2.setRollTwo(4);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollTwo(), player);

        assertEquals(14, f1.getFrameScore());
        assertEquals(4, f2.getFrameScore());

    }

    /**
     * test scoring of a strike, strike, then open
     */
    @Test
    public void strikeStrikeOpen() {
        System.out.println("test strike, strike, then open frame scoring");
        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        Frame f3 = new Frame(3);
        frames.addAll(Arrays.asList(f1, f2, f3));

        // frame one is a strike; rolls 10
        f1.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);

        // frame two is strike; rolls 10
        f2.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        // frame three is open; rolls 2, 3
        f3.setRollOne(2);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollOne(), player);

        f3.setRollTwo(3);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollTwo(), player);

        assertEquals(22, f1.getFrameScore());
        assertEquals(15, f2.getFrameScore());
        assertEquals(5, f3.getFrameScore());
    }

    /**
     * test scoring of a strike, strike, then spare
     */
    @Test
    public void strikeStrikeSpare() {
        System.out.println("test strike, strike, then spare frame scoring");
        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        Frame f3 = new Frame(3);
        frames.addAll(Arrays.asList(f1, f2, f3));

        // frame one is a strike; rolls 10
        f1.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);

        // frame two is strike; rolls 10
        f2.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        // frame three is spare; rolls 2, 8
        f3.setRollOne(2);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollOne(), player);

        f3.setRollTwo(8);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollTwo(), player);

        assertEquals(22, f1.getFrameScore());
        assertEquals(20, f2.getFrameScore());
        assertEquals(10, f3.getFrameScore());
    }

    /**
     * test scoring of a strike, spare, then open
     */
    @Test
    public void strikeSpareOpen() {
        System.out.println("test strike, spare, then open frame scoring");
        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        Frame f3 = new Frame(3);
        frames.addAll(Arrays.asList(f1, f2, f3));

        // frame one is a strike; rolls 10
        f1.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);

        // frame two is spare; rolls 0, 10
        f2.setRollOne(0);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        f2.setRollTwo(10);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollTwo(), player);

        // frame three is open; rolls 2, 7
        f3.setRollOne(2);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollOne(), player);

        f3.setRollTwo(7);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollTwo(), player);

        assertEquals(20, f1.getFrameScore());
        assertEquals(12, f2.getFrameScore());
        assertEquals(9, f3.getFrameScore());
    }

    /**
     * test scoring of a spare, strike, then open
     */
    @Test
    public void spareStrikeOpen() {
        System.out.println("test spare, strike, then open frame scoring");
        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        Frame f3 = new Frame(3);
        frames.addAll(Arrays.asList(f1, f2, f3));

        // frame one is a spare; rolls 7, 3
        f1.setRollOne(7);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);

        f1.setRollTwo(3);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollTwo(), player);

        // frame two is strike; rolls 10
        f2.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        // frame three is open; rolls 2, 7
        f3.setRollOne(2);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollOne(), player);

        f3.setRollTwo(7);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollTwo(), player);

        assertEquals(20, f1.getFrameScore());
        assertEquals(19, f2.getFrameScore());
        assertEquals(9, f3.getFrameScore());
    }

    /**
     * test scoring of three strikes
     */
    @Test
    public void strikeStrikeStrike() {
        System.out.println("test three strike scoring");
        Frame f1 = new Frame(1);
        Frame f2 = new Frame(2);
        Frame f3 = new Frame(3);
        frames.addAll(Arrays.asList(f1, f2, f3));

        // frame one is a strike
        f1.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f1.getFrameNumber(), f1.getRollOne(), player);
        assertEquals(10, f1.getFrameScore());

        // frame two is strike
        f2.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f2.getFrameNumber(), f2.getRollOne(), player);

        // frame three is strike
        f3.setRollOne(10);
        instance.applyPinsToSparesAndStrikes(f3.getFrameNumber(), f3.getRollOne(), player);

        assertEquals(30, f1.getFrameScore());
        assertEquals(20, f2.getFrameScore());
        assertEquals(10, f3.getFrameScore());
    }

    /**
     * test scoring of ten strikes
     */
    @Test
    public void perfectGame() {
        System.out.println("test ten strike scoring");
        for (int i = 0; i < 10; i++) {
            Frame frame = new Frame(i + 1);
            frame.setRollOne(10);
            frames.add(frame);
            instance.applyPinsToSparesAndStrikes(frame.getFrameNumber(), frame.getRollOne(), player);
        }
        frames.get(9).setRollTwo(10);
        instance.applyPinsToSparesAndStrikes(10, frames.get(9).getRollTwo(), player);
        frames.get(9).setRollThree(10);
        assertEquals(30, frames.get(9).getFrameScore());

        instance.applyPinsToSparesAndStrikes(10, frames.get(9).getRollThree(), player);
        assertEquals(30, frames.get(9).getFrameScore());

        assertEquals(300, frames.stream().map(f -> f.getFrameScore()).reduce(0, (a,b)-> a+b).intValue());
    }

}
