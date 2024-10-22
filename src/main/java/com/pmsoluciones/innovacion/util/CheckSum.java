package com.pmsoluciones.innovacion.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class CheckSum {

	private CheckSum() {
		throw new IllegalStateException("Utility class");
	}

	// this method return the complete hash of the file
	// passed
	public static String checksum(MessageDigest digest, InputStream fis) throws IOException {
		// Get file input stream for reading the file content

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// read the data from file and update that data in
		// the message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}

		// close the input stream
		fis.close();

		// store the bytes returned by the digest() method
		byte[] bytes = digest.digest();

		// this array of bytes has bytes in decimal format
		// so we need to convert it into hexadecimal format

		// for this we create an object of StringBuilder
		// since it allows us to update the string i.e. its
		// mutable
		StringBuilder sb = new StringBuilder();

		// loop through the bytes array
		for (int i = 0; i < bytes.length; i++) {

			// the following line converts the decimal into
			// hexadecimal format and appends that to the
			// StringBuilder object
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// finally we return the complete hash
		return sb.toString();
	}

}
