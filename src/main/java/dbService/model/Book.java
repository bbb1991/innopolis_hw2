package dbService.model;

import javax.persistence.*;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Entity
//@Table(name = "books")
public class Book {

    /**
     * ID книги в БД
     */
    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * Название книги
     */
//    @Column(name = "title")
    @Column
    private String title;

    /**
     * Информация об авторе, логин
     */
//    @Column(name = "author")
//    @ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name = "author_id", referencedColumnName = "id")
//    @ForeignKey()
//    @Column
    @Column
    private Long author;

    /**
     * Содержание книги
     */
//    @Column(name = "content")
    @Column
    private String content;

    public Book() {
    }

    public Book(long id, String title, long author, String content) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.content = content;
    }

    public Book(String title, long author, String content) {
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
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }
}
