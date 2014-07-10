package com.example.security.token;
/**
 * This interface defines method signatures to encrypt and decrypt 
 * a token.
 * @author richard
 */
public interface TokenService {
	public <T extends Token> String encrypt(T token);
	public <T extends Token> T decrypt(String encryptedToken);
}
