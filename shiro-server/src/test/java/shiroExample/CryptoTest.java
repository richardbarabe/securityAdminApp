package shiroExample;

import static org.junit.Assert.*;

import java.security.Security;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCipherer;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/CryptoTest-config.xml")
public class CryptoTest {
	@Autowired
    @Qualifier("encrypter")
    private Base64EncodedCipherer encrypter;

    @Autowired
    @Qualifier("decrypter")
    private Base64EncodedCipherer decrypter;
	
    @Autowired
    @Qualifier("secretKey")
    private java.security.Key key;
    
    // base64 encoded initialization vector
    private static final String iv = Base64.encodeBase64String(new byte[] {79,127,9,42,55,84,27,96,53,123,99,32,3,61,1,77});

    @BeforeClass
    public static void init() {
    	Security.addProvider(new BouncyCastleProvider());
    }
    
	@Test
	public void test() {
		String secretMessage = "a secret message";
		String b64encryptedMessage = encrypter.encrypt(Base64.encodeBase64String(key.getEncoded()), iv, secretMessage);
        String decryptedMessage = decrypter.encrypt(Base64.encodeBase64String(key.getEncoded()), iv, b64encryptedMessage);
		
        assertNotEquals(b64encryptedMessage, secretMessage);
        assertEquals(secretMessage, decryptedMessage);
	}
}
