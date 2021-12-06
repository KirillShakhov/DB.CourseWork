package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//CREATE TABLE "contracts" (
//        "id_contract" bigserial ,
//        "from_user" bigint not null references users(id_user),
//        "to_user" bigint references users(id_user),
//        "from_money" int not null check(from_money >= 0),
//        "to_money" int not null check(from_money >= 0),
//        "is_closed" boolean default false,
//        "closing_date" date not null,
//        "closing_time" time default '00:00',
//        PRIMARY KEY ("id_contract")
//        );


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "contracts", schema = "public")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_contract")
    @JsonView(View.Contract.class)
    private Long id_contract;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "from_user", nullable = false)
    @JsonView(View.Contract.class)
    private User from_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    @JsonView(View.Contract.class)
    private User to_user;

    @Column(name = "from_money", nullable = false)
    @JsonView(View.Contract.class)
    private int from_money;

    @Column(name = "to_money", nullable = false)
    @JsonView(View.Contract.class)
    private int to_money;

    @Column(name = "is_closed", nullable = false)
    @JsonView(View.Contract.class)
    private boolean is_closed = false;

    @Column(name = "closing_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonView(View.Contract.class)
    private Date closing_date;

    @Column(name = "closing_time")
    @JsonView(View.Contract.class)
    private Time closing_time = new Time(0);

    @OneToOne(mappedBy = "contract", optional = true)
    private Auction auction;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public Contract(User from_user, User to_user, Integer from_money, Integer to_money) {
        this.from_user = from_user;
        this.to_user = to_user;
        this.from_money = from_money;
        this.to_money = to_money;
    }

    public Contract(User from_user, Integer from_money, Integer to_money) {
        this.from_user = from_user;
        this.from_money = from_money;
        this.to_money = to_money;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id_contract=" + id_contract +
                ", from_user=" + from_user +
                ", to_user=" + to_user +
                ", from_money=" + from_money +
                ", to_money=" + to_money +
                ", is_closed=" + is_closed +
                ", closing_date=" + closing_date +
                ", closing_time=" + closing_time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contract contract = (Contract) o;
        return id_contract != null && Objects.equals(id_contract, contract.id_contract);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
