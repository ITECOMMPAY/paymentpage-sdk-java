import com.ecommpay.sdk.SignatureHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.*;

public class SignatureHandlerTest
{
    private SignatureHandler signatureHandler;
    private String json =
        "{" +
            "\"payment\": {" +
                "\"id\": \"test.test-payment\"," +
                "\"status\": \"success\"," +
                "\"valid\": true" +
            "}," +
            "\"some-data\": 1234," +
            "\"errors\": [" +
                "{" +
                    "\"code\": 22565," +
                    "\"message\": \"Failure message\"" +
                "}" +
            "]" +
        "}";

    @Before
    public void initTest()
    {
        signatureHandler = new SignatureHandler("qwerty");
    }

    @After
    public void afterTest()
    {
        signatureHandler = null;
    }

    @Test
    public void setSortParams() throws IllegalAccessException, NoSuchFieldException
    {
        Field sortParams = SignatureHandler.class.getDeclaredField("sortParams");
        sortParams.setAccessible(true);

        assertEquals(signatureHandler, signatureHandler.setSortParams(false));
        boolean sortParamsFalse = Boolean.valueOf(sortParams.get(signatureHandler).toString());
        assertFalse(sortParamsFalse);

        assertEquals(signatureHandler, signatureHandler.setSortParams(true));
        boolean sortParamsBool = Boolean.valueOf(sortParams.get(signatureHandler).toString());
        assertTrue(sortParamsBool);
    }

    @Test
    public void check() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Map paymentParams = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        assertTrue(signatureHandler.check("xtj79Dt9X6pVnHw0GWSgISRMRp9PopymK7yZjHAedHpNAuFiOqddEXZ/JNCsVP3Gm1Urv6IEqDJjXkPqj+J7rg==", paymentParams));
    }

    @Test
    public void sign()
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map paymentParams = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
            String sing = signatureHandler.sign(paymentParams);
            assertEquals("xtj79Dt9X6pVnHw0GWSgISRMRp9PopymK7yZjHAedHpNAuFiOqddEXZ/JNCsVP3Gm1Urv6IEqDJjXkPqj+J7rg==", sing);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void signException() throws IOException, IllegalAccessException, NoSuchFieldException
    {
        Field field = signatureHandler.getClass().getDeclaredField("ALGORITHM");
        field.setAccessible(true);
        field.set(signatureHandler, "512");

        ObjectMapper mapper = new ObjectMapper();
        Map paymentParams = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        signatureHandler.sign(paymentParams);

        field.set(signatureHandler, "HmacSHA512");
    }
}