package dbService.dataSets;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class UserDataSet {
    private long id;
    private String username;
    private String password;

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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return username;
    }
}
