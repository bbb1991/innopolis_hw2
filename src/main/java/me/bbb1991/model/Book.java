package me.bbb1991.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by bbb1991 on 12/8/16.
 * Модель книги
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 * @version 1.0
 */
@Entity
@Table(name = "books")
public class Book {

    /**
     * ID, по которому будет хранится книга в БД
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    /**
     * Название книги
     */
    @Column
    private String title;

    /**
     * Автор книги
     */
    @ManyToOne
    private User author;

    /**
     * Дата создания
     */
    @Column
    private Date date;

    /**
     * Содержание книги
     */
    @Column
    private String content;

    /**
     * Пометка, можно ли показывать книгу читателям или все же ходе написания, то есть черновик
     */
    @Column
    private boolean draft;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Comment> comments;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String context) {
        this.content = context;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", date=" + date +
                ", context='" + content + '\'' +
                '}';
    }
}