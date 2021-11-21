package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


//CREATE TABLE "photo" (
//        "id_photo" bigserial ,
//        "car" bigint not null references cars(id_car),
//        "creator" bigint not null references user(id_user),
//        "url_photo" varchar(255) not null,
//        "alt_description" varchar(250),
//        PRIMARY KEY ("id")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "colors", schema = "public")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_photo")
    @JsonView(View.Photo.class)
    private Long id_photo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car", nullable = false)
    @JsonView(View.Photo.class)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator", nullable = false)
    @JsonView(View.Photo.class)
    private User user;

    @Column(name = "url_photo", nullable = false)
    @JsonView(View.Photo.class)
    private String url_photo;

    @Column(name = "alt_description")
    @JsonView(View.Photo.class)
    private String alt_description;

    @Override
    public String toString() {
        return "Photo{" +
                "id_photo=" + id_photo +
                ", car=" + car +
                ", user=" + user +
                ", url_photo='" + url_photo + '\'' +
                ", alt_description='" + alt_description + '\'' +
                '}';
    }
}
