package com.pmsoluciones.innovacion.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crypto{ //implements PasswordEncoder
	
private static final Logger logger = LoggerFactory.getLogger(Crypto.class);
	
	private String secret;
	private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
	private static SecretKeySpec secretKey;
	private static final Boolean TYPE_HOUR = true ;
	private static final Boolean TYPE_SECOND = false ;

	public Crypto() {
	}
	
	public Crypto(String secret) {
		super();
		this.secret = secret;
	}

	private String getSecret() {
		return secret;
	}

	private void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @param myKey
	 */
	private static void setKey(final String myKey) {
		MessageDigest sha = null;
		try {
		
			sha = MessageDigest.getInstance("SHA-256");
			byte[] key = myKey.getBytes("UTF-8");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey  = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param privateString
	 * @param skey
	 * @return
	 */
    public static String encrypt(String privateString, String skey){
    	try {
	    	setKey(skey);
	        byte[] iv = new byte[GCM_IV_LENGTH];
	        (new SecureRandom()).nextBytes(iv);
	
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
	
	        byte[] ciphertext = cipher.doFinal(privateString.getBytes("UTF8"));
	        byte[] encrypted = new byte[iv.length + ciphertext.length];
	        System.arraycopy(iv, 0, encrypted, 0, iv.length);
	        System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);
	
	        String encoded = Base64.getUrlEncoder().encodeToString(encrypted);
	
	        return new String(encoded).replace("+", "$");
        
	    } catch (Exception e) {
	    	logger.error("Error al momento de  encriptar: " + e.toString());
		}
		return null;
    }
    
    /**
     * 
     * @param encrypted
     * @param skey
     * @return
     */
    public static String decrypt(String encrypted, String skey){
    	try {
    		setKey(skey);
	    	encrypted = encrypted.replace("$", "+");
	        byte[] decoded = Base64.getUrlDecoder().decode(encrypted);
	
	        byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);
	
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
	
	        byte[] ciphertext = cipher.doFinal(decoded, GCM_IV_LENGTH, decoded.length - GCM_IV_LENGTH);
	
	        String result = new String(ciphertext, "UTF-8");
	        
	        return result;

    	} catch (Exception e) {
    		logger.error("Error al momento de desencriptar: " + e.toString());
		}
    	return null;
    }

	
	/**
	 * 
	 * @param stringTextCompare
	 * @param stringEncrypt
	 * @param secret
	 * @return
	 */
	public static boolean valide (String stringTextCompare, String stringEncrypt, final String secret) {
		String text = decrypt(stringEncrypt, secret);
		return stringTextCompare.equals(text);
	}
	
	
	/**
	 * @param text
	 * @param Hour
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	private static String parseString(String text, Integer time, Boolean typeTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if(typeTime) {
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + time);	
		}else {			
			cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + time);
		}
		String textUUID = text.toString() + "¥" +  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()).toString();
		return textUUID;
	}
	
	
	/**
	 * @param strToEncrypt
	 * @param secret
     * @return NULL | STRING
	 */
	public static String tokenOneHour ( String strToEncrypt, final String secret) {
		try {
			return encrypt(parseString(strToEncrypt,1 , TYPE_HOUR), secret);
		} catch (Exception e) {
			logger.error("Error de encrypting: " + e.toString());
		}
		return null;
	}
	
	/**
	 * @param strToEncrypt
	 * @param secret
     * @return NULL | STRING
	 */
	public static String tokenTime ( String strToEncrypt, Integer time, Boolean isHour, final String secret) {
		try {
			return encrypt(parseString(strToEncrypt, time, isHour), secret);
		} catch (Exception e) {
			logger.error("Error de encrypting: " + e.toString());
		}
		return null;
	}
	
	/** 
	 * @param decode
	 * @return STRING[]
	 */
	private static String decodeSplit (String decode, Integer position) {
		return decode.split("¥")[position];
	}
	
	/**
	 * @param strToDecrypt
	 * @param secret
	 * @return NULL | STRING
	 */
	public static String decryptTokenHour ( String strToDecrypt, final String secret) {
		
		try {
			String timestamp = decodeSplit(decrypt( strToDecrypt, secret ), 1);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date dateNow = cal.getTime();
			Date dateToken = new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime());
			
			if(dateNow.before(dateToken)) {
				return decodeSplit(decrypt( strToDecrypt, secret ), 0);
			}else  return null;
			
		} catch (Exception e) {
			logger.error("Error while decrypting: " + e.toString());
		}
		
		return null;
	}
	
//	@Override
//	public String encode(CharSequence rawPassword) {
//		return encrypt(rawPassword.toString(), getSecret());
//	}
//
//	@Override
//	public boolean matches(CharSequence rawPassword, String encodedPassword) {
//		String passdecoder = decrypt(encodedPassword, getSecret());
//		return rawPassword.toString().equals(passdecoder);
//	}
	
//	public static  void main (String args[]) {
//		String e = "kv1HEzia-RfGRGR_Phu6uRSv9WKeA9ySVOEyBKyPzG9AO487";
//		String k = "oi3WU1XSrYlc1crNmnFqyKfm5gav4d";
//		
//		System.out.println(decrypt(e, k));
//	}

}
