package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


//CREATE TABLE "series" (
//        "id_series" bigserial ,
//        "name" varchar(250) not null,
//        "description" text,
//        "date_of_start" date not null,
//        "date_of_finish" date,
//        PRIMARY KEY ("id_series")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "series", schema = "public")
public class Series {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_series")
    @JsonView(View.Series.class)
    private Long id_series;

    @Column(name = "name", nullable = false, length = 250)
    @JsonView(View.Series.class)
    private String name;

    @Lob
    @Column(name = "description")
    @JsonView(View.Series.class)
    private String description;

    @Column(name = "date_of_start", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.Series.class)
    private Date date_of_start;

    @Column(name = "date_of_finish")
    @Temporal(TemporalType.DATE)
    @JsonView(View.Series.class)
    private Date date_of_finish;

    @Override
    public String toString() {
        return "Series{" +
                "id_series=" + id_series +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date_of_start=" + date_of_start +
                ", date_of_finish=" + date_of_finish +
                '}';
    }
}
