package mate.academy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AuthenticationUtil {

    private static final int SEED_SIZE = 16;
    private static final String HASHING_ALGORITHM = "SHA-512";

    private AuthenticationUtil() {
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] seed = new byte[SEED_SIZE];
        random.nextBytes(seed);
        return seed;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASHING_ALGORITHM);
            digest.digest(password.getBytes(StandardCharsets.UTF_8));
            digest.update(salt);
            StringBuilder builder = new StringBuilder();
            for (byte b : digest.digest()) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("Unsupported algorithm: " + HASHING_ALGORITHM, e);
        }
    }
}
