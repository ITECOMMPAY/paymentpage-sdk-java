import com.ecommpay.sdk.PaymentPage;
import com.ecommpay.sdk.SignatureHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaymentPageTest
{
    private PaymentPage paymentPage;

    @Before
    public void initTest()
    {
        paymentPage = new PaymentPage(new SignatureHandler(TestFixtures.secret));
    }

    @After
    public void afterTest()
    {
        paymentPage = null;
    }

    @Test
    public void setBaseUrl()
    {
        assertEquals(paymentPage, paymentPage.setBaseUrl(TestFixtures.testUrl));
        assertEquals(TestFixtures.testUrl.concat(TestFixtures.compareParams), paymentPage.getUrl(TestFixtures.getPayment()));
    }

    @Test
    public void getUrl()
    {
        assertEquals(TestFixtures.baseUrl.concat(TestFixtures.compareParams), paymentPage.getUrl(TestFixtures.getPayment()));
    }
}
