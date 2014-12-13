import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssetContentTest {

    private AssetContent content;
    @Before
    public void setUp() throws Exception {
        this.content = new AssetContent("stove",2);

    }

    @Test
    public void testReduceHealth() throws Exception {
        content.reduceHealth(25);
        assertEquals(true, content.isHealthy());
        content.reduceHealth(50);
        assertEquals(false, content.isHealthy());
    }

    @Test
    public void testHeal() throws Exception {
        content.reduceHealth(50);
        content.Heal();
        assertEquals(true, content.isHealthy());

    }
}