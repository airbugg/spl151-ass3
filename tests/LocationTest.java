import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    Location currentLocation;
    @Before
    public void setUp() throws Exception {
        currentLocation = new Location(2,3);

    }
    @Test
    public void testCalculateDistance() throws Exception {
        assertEquals(4.47,currentLocation.calculateDistance(new Location(4,7)),0.01);
    }
}