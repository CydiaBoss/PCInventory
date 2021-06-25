package com.andrew.inv.manage.db;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.andrew.inv.manage.C;

/**
 * This class will handle encryption and decyption
 * 
 * @author andre
 *
 */
public class Security {

	/**
	 * Obtains the Secret Key from the password using AES
	 * 
	 * @param password
	 * The password
	 * @param salt
	 * Random stuff
	 * 
	 * @return
	 * The secret key
	 * 
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKey getKey(char[] password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		// Get the Secret Key Generator
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		// Key Specification
	    KeySpec spec = new PBEKeySpec(password, salt, C.KEY_ITERATE_NUM, 256);
	    // Make Key for AES
	    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	    return secret;
	}

	/**
	 * Generates an Initialization Vector
	 * 
	 * @return
	 * The Initialization Vector
	 */
	public static IvParameterSpec generateIV() {
		// Make a byte array with random bits
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	
	/**
	 * Generates Salt
	 * 
	 * @return
	 * The Initialization Vector
	 */
	public static byte[] generateSalt() {
		// Make a byte array with random bits
	    byte[] salt = new byte[C.SALT_SIZE];
	    new SecureRandom().nextBytes(salt);
	    return salt;
	}
	
	/**
	 * Encrypts a string
	 * 
	 * @param input
	 * The plain text
	 * @param key
	 * The secret key
	 * @param iv
	 * The IV
	 * 
	 * @return
	 * The cipher text
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(byte[] input, SecretKey key, IvParameterSpec iv) throws InvalidKeyException {
		// CipherText
		byte[] cipherText = null;
		try {
			// Use AES with Padding
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// Start
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    // Encrypt
		    cipherText = cipher.doFinal(input);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {}
	    return cipherText;
	}
	
	/**
	 * Decrypts a string
	 * 
	 * @param cipherText
	 * The cipher test
	 * @param key
	 * The Key
	 * @param iv
	 * The IV
	 * 
	 * @return
	 * The plain text
	 * 
	 * @throws InvalidKeyException
	 */
	public static byte[] decrypt(byte[] cipherText, SecretKey key, IvParameterSpec iv) throws InvalidKeyException {
		// Plain text
	    byte[] plainText = null;
		try {
	    	// Use AES
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    // Start
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			// Decrypt
		    plainText = cipher.doFinal(cipherText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {}
		return plainText;
	}
	
	/**
	 * Encodes a byte array into a string
	 * 
	 * @param data
	 * The byte array
	 * 
	 * @return
	 * The string
	 */
	public static String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	/**
	 * Decodes an encoded string into a byte array
	 * 
	 * @param data
	 * The encoded string
	 * 
	 * @return
	 * The byte array
	 */
	public static byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
}
