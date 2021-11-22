package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


//CREATE TABLE "cars" (
//        "id_car" bigserial ,
//        "name" varchar(250),
//        "creator" bigint not null references creators(id_creators),
//        "series" bigint references series(id_series),
//        "first_color" bigint references colors(id_color),
//        "second_color" bigint references colors(id_color),
//        "wheels" bigint references wheels(id_wheel),
//        "bumper" bigint references bumpers(id_bumper),
//        PRIMARY KEY ("id_car")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "cars", schema = "public")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_car")
    @JsonView(View.Car.class)
    private Long id_car;

    @Column(name = "name", nullable = false, length = 250)
    @JsonView(View.Car.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator", nullable = false)
    @JsonView(View.Car.class)
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series")
    @JsonView(View.Car.class)
    private Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_color")
    @JsonView(View.Car.class)
    private Color first_color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_color")
    @JsonView(View.Car.class)
    private Color second_color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wheels")
    @JsonView(View.Car.class)
    private Wheels wheels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bumper")
    @JsonView(View.Car.class)
    private Bumper bumper;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonView(View.Comment.class)
    private List<Comment> replies;

    public Car(Creator creator, String name, Series series) {
        this.creator = creator;
        this.name = name;
        this.series = series;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id_car=" + id_car +
                ", name='" + name + '\'' +
                ", creator=" + creator +
                ", series=" + series +
                ", first_color=" + first_color +
                ", second_color=" + second_color +
                ", wheels=" + wheels +
                ", bumper=" + bumper +
                '}';
    }
}
