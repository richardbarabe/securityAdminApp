package com.rp.example.spring.config;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.springcryptoutils.core.cipher.Mode;
import com.springcryptoutils.core.cipher.symmetric.Cipherer;
import com.springcryptoutils.core.cipher.symmetric.CiphererImpl;
import com.springcryptoutils.core.key.PrivateKeyException;
import com.springcryptoutils.core.key.PublicKeyException;
import com.springcryptoutils.core.signature.Signer;
import com.springcryptoutils.core.signature.SignerImpl;
import com.springcryptoutils.core.signature.Verifier;
import com.springcryptoutils.core.signature.VerifierImpl;

@Configuration("cryptoUtilsConfig")
public class CryptoUtilsConfig {

	private static final String SIGNATURE_ALGORYTHM = "SHA256withRSA";
	private static final String TOKEN_PRIVATE_KEY_PASSWORD = "tokenSignatureKeyPass";
	private static final String TOKEN_PRIVATE_KEY_ALIAS = "tokenSignature";
	private static final String TOKEN_SECRET_KEY_PASSWORD = "testKeyPass";
	private static final String TOKEN_SECRET_KEY_ALIAS = "testAESKey";
	private static final String TOKEN_CIPHER_ALGORYTHM = "aes/CBC/PKCS5Padding";
	private static final String TOKEN_KEY_ALGORYTHM = "aes";

	private static final String SECURITY_PROVIDER = "BC";

	private static final String KEYSTORE_PASSWORD = "mystorepass";
	private static final String KEYSTORE_LOCATION = "aes-128-keystore.jck";
	private static final String KEYSTORE_TYPE = "JCEKS";

	@PostConstruct
	public void init() {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Bean(name = "encrypter")
	public Cipherer tokenEncrypter() {
		CiphererImpl cipher = new CiphererImpl();
		cipher.setCipherAlgorithm(TOKEN_CIPHER_ALGORYTHM);
		cipher.setKeyAlgorithm(TOKEN_KEY_ALGORYTHM);
		cipher.setProvider(SECURITY_PROVIDER);
		cipher.setMode(Mode.ENCRYPT);
		return cipher;
	}

	@Bean(name = "decrypter")
	public Cipherer tokenDencrypter() {
		CiphererImpl cipher = new CiphererImpl();
		cipher.setCipherAlgorithm(TOKEN_CIPHER_ALGORYTHM);
		cipher.setKeyAlgorithm(TOKEN_KEY_ALGORYTHM);
		cipher.setProvider(SECURITY_PROVIDER);
		cipher.setMode(Mode.DECRYPT);
		return cipher;
	}
	
	@Bean(name="signer")
	public Signer signer() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, CertificateException, NoSuchProviderException, IOException {
		SignerImpl signer = new SignerImpl();
		signer.setPrivateKey(signaturePrivateKey());
		signer.setProvider(SECURITY_PROVIDER);
		signer.setAlgorithm(SIGNATURE_ALGORYTHM);
		return signer;
	}
	
	@Bean(name="verifier")
	public Verifier verifier() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, CertificateException, NoSuchProviderException, IOException {
		VerifierImpl verifier = new VerifierImpl();
		verifier.setPublicKey(signaturePublicKey());
		verifier.setProvider(SECURITY_PROVIDER);
		verifier.setAlgorithm(SIGNATURE_ALGORYTHM);
		return verifier;
	}
	
	@Bean(name="signaturePublicKey")
	public PublicKey signaturePublicKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, IOException {
		Certificate certificate = tokenKeystore().getCertificate(TOKEN_PRIVATE_KEY_ALIAS);

        if (certificate == null) {
            throw new PublicKeyException("no such public key with alias: " + TOKEN_PRIVATE_KEY_ALIAS);
        }

        return certificate.getPublicKey();
	}
	
	@Bean(name = "signaturePrivateKey")
	public PrivateKey signaturePrivateKey() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, CertificateException, NoSuchProviderException, IOException {
		KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) tokenKeystore()
				.getEntry(
						TOKEN_PRIVATE_KEY_ALIAS,
						new KeyStore.PasswordProtection(TOKEN_PRIVATE_KEY_PASSWORD
								.toCharArray()));

		if (privateKeyEntry == null) {
			throw new PrivateKeyException("no such private key with alias: "
					+ TOKEN_PRIVATE_KEY_ALIAS);
		}

		return privateKeyEntry.getPrivateKey();
	}

	@Bean(name = "secretKey")
	public Key secretKey() throws UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException,
			NoSuchProviderException, IOException {
		return tokenKeystore().getKey(TOKEN_SECRET_KEY_ALIAS,
				TOKEN_SECRET_KEY_PASSWORD.toCharArray());
	}

	@Bean(name = "keystore")
	public KeyStore tokenKeystore() throws NoSuchAlgorithmException,
			CertificateException, IOException, KeyStoreException,
			NoSuchProviderException {
		KeyStore keystore = KeyStore.getInstance(KEYSTORE_TYPE);
		Resource location = new ClassPathResource(KEYSTORE_LOCATION);
		keystore.load(location.getInputStream(),
				KEYSTORE_PASSWORD.toCharArray());
		return keystore;
	}

}
