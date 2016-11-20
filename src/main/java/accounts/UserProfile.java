package accounts;

import helpers.PasswordHelper;

/**
 * Created by bbb1991 on 11/19/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Deprecated
public class UserProfile {
    private final String username;
    private final String password;
    private boolean isAdmin = false;
    private boolean isBlocked = false;

    public UserProfile(String username, String password) {
        this.username = username;
        this.password = PasswordHelper.getHash(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("Username: %s, password: %s", username, password);
    }
}
