import com.ecommpay.sdk.Callback;
import com.ecommpay.sdk.ProcessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CallbackTest
{
    private Callback callback;

    @Before
    public void initTest() throws ProcessException
    {
        callback = new Callback(TestFixtures.callbackData, TestFixtures.getSignatureHandler());
    }

    @After
    public void afterTest()
    {
        callback = null;
    }

    @Test
    public void getPayment()
    {
        assertEquals(TestFixtures.getPaymentMap(), callback.getPayment());
    }

    @Test
    public void getPaymentStatus()
    {
        assertEquals(TestFixtures.paymentStatus, callback.getPaymentStatus());
    }

    @Test
    public void getPaymentId()
    {
        assertEquals(TestFixtures.paymentId, callback.getPaymentId());
    }

    @Test
    public void getSignature()
    {
        assertEquals(TestFixtures.paymentSignature, callback.getSignature());
    }

    @Test(expected = ProcessException.class)
    public void cantDecode() throws ProcessException
    {
        new Callback(TestFixtures.callbackDataInvalid, TestFixtures.getSignatureHandler());
    }

    @Test(expected = ProcessException.class)
    public void invalidSignature() throws ProcessException
    {
        new Callback(TestFixtures.callbackDataInvalidSignature, TestFixtures.getSignatureHandler());
    }

    @Test
    public void cantGetPaymentId() throws ProcessException
    {
        Callback callback = new Callback(TestFixtures.callbackDataWithoutPaymentId, TestFixtures.getSignatureHandler());
        assertEquals("", callback.getPaymentId());
    }
}
