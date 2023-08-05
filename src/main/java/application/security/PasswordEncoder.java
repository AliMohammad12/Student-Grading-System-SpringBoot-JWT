package application.security;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncoder {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 4;
    private static final int ITERATIONS = 3;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;
    public static String encodePassword(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, SALT_LENGTH, HASH_LENGTH);
        try {
            String hashedPassword = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
            return hashedPassword;
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
}