import org.junit.Test;
import org.junit.Assert;

/**
 * Class represents a test class for Clock.getEarlier()
 * It verfies all the return statements contained in the method
 */
public class GetEarlierTest {
    
    /**
     * Verifies that a.m. is considered earlier than p.m.
     */
    @Test
    public void amIsEarlierThanPm() {
        Clock c1 = new Clock(10, 43, "a.m.");
        Clock c2 = new Clock(4, 21, "p.m.");

        Assert.assertEquals(c1, Clock.getEarlier(c1, c2));
    }

    /**
     * Verifies that p.m. is considered later than a.m.
     */
    @Test
    public void pmIsLaterThanAm() {
        Clock c1 = new Clock(10, 33, "p.m.");
        Clock c2 = new Clock(2, 33, "a.m.");

        Assert.assertEquals(c2, Clock.getEarlier(c1, c2));
    }

    /**
     * Verifies that 12 is earlier than non 12
     */
    @Test
    public void twelveIsEarlierThanNonTwelve() {
        Clock c1 = new Clock(12, 21, "a.m");
        Clock c2 = new Clock(1,10,"a.m.");

        Assert.assertEquals(c1, Clock.getEarlier(c1, c2));
    }

    /**
     * Verifies that a non 12 is later than a 12
     */
    @Test
    public void nonTwelveIsNotEarlierThanTwelve() {
        Clock c1 = new Clock(3,44,"p.m");
        Clock c2 = new Clock(12, 34, "p.m.");

        Assert.assertEquals(c2, Clock.getEarlier(c1, c2));
    }

    /**
     * Verifies that a lower hour is earlier
     */
    @Test
    public void lowerHourIsEarlier() {
        Clock c1 = new Clock(3,32,"a.m");
        Clock c2 = new Clock(4, 21, "a.m.");

        Assert.assertEquals(c1, Clock.getEarlier(c1, c2));
    }

    /**
     * Verifies that a higher hour is later
     */
    @Test
    public void higherHourIsLater() {
        Clock c1 = new Clock(6,21,"p.m.");
        Clock c2 = new Clock(2,43,"p.m.");

        Assert.assertEquals(c2, Clock.getEarlier(c1, c2));
    }
    
    /**
     * Verifies that a lower minute is earlier
     */
    @Test
    public void lowerMinuteIsEarlier() {
        Clock c1 = new Clock(4,2,"a.m.");
        Clock c2 = new Clock(4,5,"a.m.");

        Assert.assertEquals(c1, Clock.getEarlier(c1, c2));
    }
    
    /**
     * Verifies that a higher minute is considered later
     */
    @Test
    public void higherMinuteIsLater() {
        Clock c1 = new Clock(11,43,"p.m.");
        Clock c2 = new Clock(11,32,"p.m.");

        Assert.assertEquals(c2, Clock.getEarlier(c1, c2));
    }

    /**
     * Verifies that if both clocks have exactly the same time, then one of the clocks time is returned
     */
    @Test
    public void sameTimesReturnSameTimes() {
        Clock c1 = new Clock(4,32,"a.m.");
        Clock c2 = new Clock(4,32,"a.m.");

        Assert.assertEquals(c1, Clock.getEarlier(c1, c2));

    }
}
