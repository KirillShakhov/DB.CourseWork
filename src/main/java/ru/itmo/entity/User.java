package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "users", schema = "public")
public class User {
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Column(name = "id_user")
//    @JsonView({View.User.class, View.Creator.class})
//    private Long id_user;

    @Id
    @Column(name = "username", unique = true, nullable = false)
    @JsonView({View.User.class, View.Creator.class, View.Article.class, View.Bumpers.class, View.Car.class, View.Wheels.class})
    private String username;

    @Lob
    @Column(name = "biography")
    @JsonView(View.User.class)
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

    @OneToOne(mappedBy = "creator_user")
    @JsonView(View.User.class)
    private Creator creator_user;

    @Column(name = "balance", nullable = false)
    @JsonView(View.User.class)
    private int balance = 1000;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Item> items;

//    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private List<Article> articles;

    public User(String login, String pass) {
        this.username = login;
        this.first_name = login;
        this.email = login;
        this.pass = pass;
        this.registration_date = java.util.Calendar.getInstance().getTime();
    }

    public User() {

    }

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

    public Creator getCreator_user() {
        return creator_user;
    }

    public void setCreator_user(Creator creator_user) {
        this.creator_user = creator_user;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", biography='" + biography + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", registration_date=" + registration_date +
                '}';
    }
}
