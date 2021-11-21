package ru.itmo.entity.old;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.itmo.entity.View;

import javax.persistence.*;

@Data
@Entity
@Table(name = "accounts", schema = "public")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.Account.class)
    private Long id;
    @Column(name = "login")
    @JsonView(View.Account.class)
    private String login;
    @Column(name = "pass")
    @JsonView(View.Account.class)
    private String pass;
    @Column(name = "cookies")
    @JsonView(View.Account.class)
    private String cookies;

    public Account() {

    }
}
