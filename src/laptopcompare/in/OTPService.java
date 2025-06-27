package laptopcompare.in;

class OTPService {
    /**
     * Generates a random 6-digit OTP.
     * @return A String representing the 6-digit OTP.
     */
    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append((int) (Math.random() * 10)); // Appends a random digit (0-9)
        }
        return otp.toString();
    }
}