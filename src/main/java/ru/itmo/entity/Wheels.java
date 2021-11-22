package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


//CREATE TABLE "wheels" (
//        "id_wheel" bigserial ,
//        "name" varchar(250) not null,
//        "creator" bigint not null references creators(id_creators),
//        "adhesion_coefficient" decimal,
//        "disk_color" bigint not null references colors(id_color),
//        "photo" varchar(250),
//        PRIMARY KEY ("id_wheel")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "wheels", schema = "public")
public class Wheels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_wheel")
    @JsonView(View.Wheels.class)
    private Long id_wheel;

    @Column(name = "name", nullable = false, length = 250)
    @JsonView(View.Wheels.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator", nullable = false)
    @JsonView(View.Wheels.class)
    private Creator creator;

    @Column(name = "adhesion_coefficient")
    @JsonView(View.Wheels.class)
    private float adhesion_coefficient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disk_color", nullable = false)
    @JsonView(View.Wheels.class)
    private Color disk_color;

    @Column(name = "photo", length = 250)
    @JsonView(View.Wheels.class)
    private String photo;

    public Wheels(Creator user, String name, int cc, String photo, Color color) {
        this.creator = user;
        this.name = name;
        this.adhesion_coefficient = cc;
        this.disk_color = color;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Wheels{" +
                "id_wheel=" + id_wheel +
                ", name='" + name + '\'' +
                ", creator=" + creator +
                ", adhesion_coefficient=" + adhesion_coefficient +
                ", disk_color=" + disk_color +
                ", photo='" + photo + '\'' +
                '}';
    }
}
