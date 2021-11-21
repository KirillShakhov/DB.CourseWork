package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


//CREATE TABLE "comments" (
//        "id_comments" bigserial primary key ,
//        "reply" bigint references comments(id_comments) ON DELETE CASCADE,
//        "article" bigint check(car is null) references articles(id_articles),
//        "car" bigint check(article is null) references cars(id_car),
//        "text" text not null,
//        "author" bigint not null references users(id_user),
//        "create_date" date not null
//        );


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "comments", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_comments")
    @JsonView(View.Comment.class)
    private Long id_comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply")
    @JsonView(View.Comment.class)
    private Comment reply;

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonView(View.Comment.class)
    private List<Comment> replies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article")
    @JsonView(View.Comment.class)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car")
    @JsonView(View.Comment.class)
    private Car car;

    @Lob
    @Column(name = "text")
    @JsonView(View.Comment.class)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author", nullable = false)
    @JsonView(View.Article.class)
    private User author;

    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.Article.class)
    private Date create_date;

}
