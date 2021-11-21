package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


//CREATE TABLE "bumpers" (
//        "id_bumper" bigserial ,
//        "name" varchar(250) not null,
//        "creator" bigint not null references creators(id_creators),
//        "color" bigint not null references colors(id_color),
//        "photo" varchar(250),
//        PRIMARY KEY ("id_bumper")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "bumpers", schema = "public")
public class Bumper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_bumper")
    @JsonView(View.Bumpers.class)
    private Long id_bumper;

    @Column(name = "name", nullable = false, length = 250)
    @JsonView(View.Bumpers.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator", nullable = false)
    @JsonView(View.Bumpers.class)
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "color", nullable = false)
    @JsonView(View.Bumpers.class)
    private Color color;

    @Column(name = "photo", length = 250)
    @JsonView(View.Bumpers.class)
    private String photo;

    @Override
    public String toString() {
        return "Bumpers{" +
                "id_bumper=" + id_bumper +
                ", name='" + name + '\'' +
                ", creator=" + creator +
                ", color=" + color +
                ", photo='" + photo + '\'' +
                '}';
    }
}
