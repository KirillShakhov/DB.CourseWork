package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//CREATE TABLE "articles" (
//        "id_articles" bigserial ,
//        "title" varchar(250) not null,
//        "car" bigint not null references cars(id_car),
//        "text" text not null,
//        "author" bigint not null references users(id_user),
//        "create_date" date not null,
//        PRIMARY KEY ("id_articles")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "articles", schema = "public")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_articles")
    @JsonView(View.Article.class)
    private Long id_articles;

    @Column(name = "title", nullable = false, length = 250)
    @JsonView(View.Article.class)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car", nullable = false)
    @JsonView(View.Article.class)
    private Car car;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author", nullable = false)
    @JsonView(View.Article.class)
    private User author;

    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.Article.class)
    private Date create_date;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonView(View.Comment.class)
    private List<Comment> replies;

    public Article(String title, String text, User author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id_articles=" + id_articles +
                ", title='" + title + '\'' +
                ", car=" + car +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", create_date=" + create_date +
                '}';
    }
}
