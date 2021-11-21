package ru.itmo.entity.old;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.itmo.entity.View;

import javax.persistence.*;


@Data
@Entity
@Table(name = "tokens", schema = "public")
public class Token {
    @Id
    @Column(name = "token")
    @JsonView(View.Token.class)
    private String token;
    @Column(name = "isActive")
    @JsonView(View.Token.class)
    private boolean isActive;
    @JsonView(View.Token.class)
    private String user_name;
    public Token() {

    }

    public Token(String token, String user_name) {
        this.token = token;
        this.user_name = user_name;
        this.isActive = true;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
