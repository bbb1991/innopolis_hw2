package dbService.dataSets;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BookDataSet {

    /**
     * ID книги в БД
     */
    private long id;


    /**
     * Название книги
     */
    private String title;


    /**
     * Информация об авторе, логин
     */
    private String author;

    /**
     * Содержание книги
     */
    private String content;


    public BookDataSet(long id, String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.content = content;
    }

    public BookDataSet(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return String.format("%s: '%s'", author, title.toUpperCase());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



}
