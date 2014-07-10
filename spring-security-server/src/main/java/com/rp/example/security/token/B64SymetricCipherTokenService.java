package com.rp.example.security.token;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.springcryptoutils.core.cipher.symmetric.Cipherer;
import com.springcryptoutils.core.signature.Signer;
import com.springcryptoutils.core.signature.Verifier;

/**
 * Encrypted tokens used in this implementation are constructed like this (in that order): 
 * - serialize the Token object into byte array using java serialization
 * - sign the serialized object using provided signer
 * - add the resulting signature to the serialized version of the token
 * - encrypt the result using the provided symmetric cipher (hard-coded initialization vector for now)
 * - encode the encrypted token+signature in base64
 * 
 * It is up to the application to define wath exactly is the token.
 * 
 * @author richard
 *
 */
// Subclass for the signature feature ?
@Service
@Primary
public class B64SymetricCipherTokenService implements TokenService {

	@Autowired
    @Qualifier("encrypter")
    private Cipherer encrypter;

    @Autowired
    @Qualifier("decrypter")
    private Cipherer decrypter;
	
    @Autowired
    @Qualifier("secretKey")
    private java.security.Key key;
    
    @Autowired
    private Signer signer;
    
    @Autowired
    private Verifier verifier;
    
    // base64 encoded initialization vector
    private static final byte[] iv = new byte[] {79,127,9,42,55,84,27,96,53,123,99,32,3,61,1,77};
	
	@Override
	public String encrypt(Token token) {
		byte[] serializedToken;
		try {
			serializedToken = serialize(token);
			byte[] signature = signer.sign(serializedToken);
			String encodedSignature = Base64.encodeBase64String(signature);
			
			ByteArrayOutputStream baos= new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(token);
			oos.writeObject(encodedSignature);
			serializedToken = baos.toByteArray();
		} catch (Exception e) {
			// TODO Custom exception
			throw new RuntimeException(e);
		}
		
		byte[] encryptedToken = encrypter.encrypt(key.getEncoded(), iv, serializedToken);
		// let not the token available in memory ...
		byte fill = 0x0;
		Arrays.fill(serializedToken, fill);
		return Base64.encodeBase64String(encryptedToken);
	}

	@Override
	public <T extends Token> T decrypt(String b64EncryptedToken) {
		byte[] encryptedToken = Base64.decodeBase64(b64EncryptedToken);
		byte[] decryptedToken = decrypter.encrypt(key.getEncoded(), iv, encryptedToken);
		try {
			ByteArrayInputStream bais=new ByteArrayInputStream(decryptedToken);
			ObjectInputStream ois = new ObjectInputStream(bais);
			@SuppressWarnings("unchecked")
			T token = (T) ois.readObject();
			String encodedSignature = (String)ois.readObject();
			verifySignature(token, encodedSignature);
			return token;
		} catch (Exception e) {
			// TODO Custom exception
			throw new RuntimeException(e);
		}
	}

	private <T extends Token> void verifySignature(T token,
			String encodedSignature) throws IOException {
		byte[] signature = Base64.decodeBase64(encodedSignature);
		
		byte[] serializedToken = serialize(token);
		verifier.verify(serializedToken, signature);
	}

	private <T extends Token> byte[] serialize(T token) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(token);
		byte[] serializedToken = bos.toByteArray();
		return serializedToken;
	}

}
