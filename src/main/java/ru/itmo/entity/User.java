package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

@Getter
@Setter
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
    @JsonView(View.User.class)
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
}
