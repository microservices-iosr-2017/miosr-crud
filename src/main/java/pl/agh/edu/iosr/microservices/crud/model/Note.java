package pl.agh.edu.iosr.microservices.crud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Note {
    @Id
    private String id;

    private String author;
    private String title;
    private String text;
    private Date date;

    public Note() {
        this.date = new Date();
    }

    public Note(String author, String title, String text, Date date) {
        this.author = author;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public Note setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Note setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Note setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
