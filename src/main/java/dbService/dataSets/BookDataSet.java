package dbService.dataSets;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BookDataSet {
    private long id;
    private String title;
    private long authorId;
    private String author;
    private String content;

    public BookDataSet(long id, String title, long authorId, String content) {
        this.title = title;
        this.authorId = authorId;
        this.id = id;
        this.content = content;
    }

    public BookDataSet(String title, long authorId, String content) {
        this.title = title;
        this.authorId = authorId;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("%d: '%s'", authorId, title.toUpperCase());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
