package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


//
//CREATE TABLE "creators" (
//        "id_creators" bigserial,
//        "creator" bigint not null references users(id_user),
//        "creation_date" date not null,
//        PRIMARY KEY ("id_creators")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "creators", schema = "public")
public class Creator {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_creators")
    @JsonView(View.Creator.class)
    private Long id_creators;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    @JsonView(View.Creator.class)
    private User creator;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.Creator.class)
    private Date creation_date;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Wheels> wheels;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Bumper> bumpers;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Car> cars ;

    public Creator(User user) {
        this.creator = user;
        this.creation_date = java.util.Calendar.getInstance().getTime();
    }


    @Override
    public String toString() {
        return "Creator{" +
                "id_creators=" + id_creators +
                ", creator=" + creator +
                ", creation_date=" + creation_date +
                '}';
    }
}
