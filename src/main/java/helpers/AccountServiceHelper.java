package helpers;

import accounts.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bbb1991 on 11/19/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class AccountServiceHelper {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    private volatile static AccountServiceHelper instance;
    private static final Object obj = new Object();

    public AccountServiceHelper() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();

        // TODO: 11/19/16 remove this mess
        loginToProfile.put("admin", new UserProfile("admin", PasswordHelper.getHash("admin")));
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getUsername(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    public static AccountServiceHelper getInstance() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new AccountServiceHelper();
                }
            }
        }

        return instance;
    }
}