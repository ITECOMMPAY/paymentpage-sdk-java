import com.ecommpay.sdk.Callback;
import com.ecommpay.sdk.Gate;
import com.ecommpay.sdk.ProcessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GateTest
{
    private Gate gate;

    @Before
    public void initTest()
    {
        gate = new Gate(TestFixtures.secret);
    }

    @After
    public void afterTest()
    {
        gate = null;
    }

    @Test
    public void setBaseUrl()
    {
        assertEquals(gate, gate.setBaseUrl(TestFixtures.testUrl));
    }

    @Test
    public void getPurchasePaymentPageUrl()
    {
        assertEquals(gate, gate.setBaseUrl(TestFixtures.baseUrl));
        assertEquals(TestFixtures.baseUrl.concat(TestFixtures.compareParams), gate.getPurchasePaymentPageUrl(TestFixtures.getPayment()));
    }

    @Test
    public void handleCallback() throws ProcessException
    {
        assertEquals(Callback.class, gate.handleCallback(TestFixtures.callbackData).getClass());
    }
}
