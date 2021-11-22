package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//CREATE TABLE "users" (
//        "id_user" bigserial ,
//        "username" varchar(250) not null unique,
//        "biography" text,
//        "first_name" varchar(250) not null,
//        "last_name" varchar(250) ,
//        "email" varchar(250) not null unique,
//        "pass" varchar(250) not null,
//        "registration_date" date not null,
//        PRIMARY KEY ("id_user")
//        );

@RequiredArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id_user")
    @JsonView(View.User.class)
    private Long id_user;

    @Column(name = "username", unique = true, nullable = false, length = 250)
    @JsonView({View.User.class, View.Creator.class, View.Series.class })
    private String username;

    @Lob
    @Column(name = "biography")
    private String biography;

    @Column(name = "first_name", nullable = false, length = 250)
    @JsonView(View.User.class)
    private String first_name;

    @Column(name = "last_name", length = 250)
    @JsonView(View.User.class)
    private String last_name;


    @Column(name = "email", unique = true, nullable = false, length = 250)
    @JsonView(View.User.class)
    private String email;


    @Column(name = "pass", nullable = false, length = 250)
    private String pass;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.User.class)
    private Date registration_date;

    @OneToOne(mappedBy = "creator")
    private Creator creator;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Article> articles;

    public User(String login, String pass) {
        this.username = login;
        this.first_name = login;
        this.email = login;
        this.pass = pass;
        this.registration_date = java.util.Calendar.getInstance().getTime();
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", biography='" + biography + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", registration_date=" + registration_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id_user != null && Objects.equals(id_user, user.id_user);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    @Transactional
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
