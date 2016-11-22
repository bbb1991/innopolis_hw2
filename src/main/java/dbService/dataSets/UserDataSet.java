package dbService.dataSets;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public final class UserDataSet {
    private long id;
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isBlocked;

    public UserDataSet(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDataSet(String username, String password) {
//        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return username;
    }
}
