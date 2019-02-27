import com.ecommpay.sdk.PaymentPage;
import com.ecommpay.sdk.SignatureHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    @Test(expected = InvocationTargetException.class)
    public void encode() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException
    {
        Field field = paymentPage.getClass().getDeclaredField("CHARSET");
        field.setAccessible(true);
        field.set(paymentPage, "");

        Method method = paymentPage.getClass().getDeclaredMethod("encode", Object.class);
        method.setAccessible(true);
        method.invoke(paymentPage, "test/test");
    }
}
