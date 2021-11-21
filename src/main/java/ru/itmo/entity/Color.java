package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


//CREATE TABLE "colors" (
//        "id_color" bigserial ,
//        "name" varchar(250) not null unique,
//        "hex" varchar(7) not null unique,
//        "r" int not null check(r >= 0 and r <= 255),
//        "g" int not null check(g >= 0 and g <= 255),
//        "b" int not null check(b >= 0 and b <= 255),
//        PRIMARY KEY ("id_color")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "colors", schema = "public")
public class Color {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_color")
    @JsonView(View.Color.class)
    private Long id_color;

    @Column(name = "name", nullable = false, unique = true, length = 250)
    @JsonView(View.Color.class)
    private String name;

    @Column(name = "hex", nullable = false, unique = true, length = 7)
    @JsonView(View.Color.class)
    private String hex;

    @Column(name = "r", nullable = false, unique = true)
    @JsonView(View.Color.class)
    private int r;

    @Column(name = "g", nullable = false, unique = true)
    @JsonView(View.Color.class)
    private int g;

    @Column(name = "b", nullable = false, unique = true)
    @JsonView(View.Color.class)
    private int b;

    @Override
    public String toString() {
        return "Color{" +
                "id_color=" + id_color +
                ", name='" + name + '\'' +
                ", hex='" + hex + '\'' +
                ", r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
