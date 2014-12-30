import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class RunnableClerkTest {

    private Assets assets;
    private ArrayBlockingQueue<RentalRequest> requests;
    private int numOfRequests;

    @Before
    public void setUp() throws Exception {
        requests = new ArrayBlockingQueue<RentalRequest>(10);
    }

    @Test
    public void testRun() throws Exception {

    }
}