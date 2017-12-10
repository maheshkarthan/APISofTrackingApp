package com.helmet.util;

public class OTPGenerator {

	private static String otpString;

	public static String generateOTP(int length) {

		otpString = new String();

		for (int i = 0; i < length; i++) {

			otpString = otpString + generateRandom();
		}

		return otpString;
	}

	private static int generateRandom() {

		return (int) (Math.random() * 10);

	}
}
