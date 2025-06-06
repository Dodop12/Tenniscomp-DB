package tenniscomp.utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtils {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    private PasswordUtils() {
        
    }

    public static String hashPassword(final char[] password, final byte[] salt) throws Exception {
        final var spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        final var skf = SecretKeyFactory.getInstance(ALGORITHM);
        final byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static byte[] generateSalt() {
        final SecureRandom random = new SecureRandom();
        final byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password with a generated salt and returns the combined result
     * @param password the plain text password
     * @return the combined salt and hash (format: salt:hash)
     */
    public static String hashPasswordWithSalt(final String password) {
        try {
            final byte[] salt = generateSalt();
            final String hash = hashPassword(password.toCharArray(), salt);
            
            // Return salt and hash encoded as base64, separated by colon
            return Base64.getEncoder().encodeToString(salt) + ":" + hash;
        } catch (final Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies a password against a stored hash
     * @param password the plain text password to verify
     * @param storedHash the stored hash (format: salt:hash)
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(final String password, final String storedHash) {
        try {
            // Split the stored hash into salt and hash parts
            final String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            final byte[] salt = Base64.getDecoder().decode(parts[0]);
            final String storedHashPart = parts[1];
            
            // Hash the provided password with the same salt
            final String testHash = hashPassword(password.toCharArray(), salt);
            
            // Compare the hashes using constant-time comparison
            return constantTimeEquals(storedHashPart, testHash);
        } catch (final Exception e) {
            return false;
        }
    }

    // Constant-time string comparison to prevent timing attacks
    private static boolean constantTimeEquals(final String a, final String b) {
        if (a.length() != b.length()) {
            return false;
        }
        
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}