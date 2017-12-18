package pl.edu.agh.iosr.microservices.crud.model;

public class Note {

    private Long id;
    private String author;
    private String title;
    private String text;

    public Note() {
        this("", "", "");
    }

    public Note(String author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Note setId(Long id) {
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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text +
                '}';
    }
}
