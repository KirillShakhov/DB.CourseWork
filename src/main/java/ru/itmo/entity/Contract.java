package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


@Entity
@Table(name = "contracts", schema = "public")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_contract")
    @JsonView(View.Contract.class)
    private Long id_contract;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

//    @OneToOne(mappedBy = "last_customer", optional = true)
//    private Auction auction;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public Contract(){

    }

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

    public Long getId_contract() {
        return id_contract;
    }

    public void setId_contract(Long id_contract) {
        this.id_contract = id_contract;
    }

    public User getFrom_user() {
        return from_user;
    }

    public void setFrom_user(User from_user) {
        this.from_user = from_user;
    }

    public User getTo_user() {
        return to_user;
    }

    public void setTo_user(User to_user) {
        this.to_user = to_user;
    }

    public int getFrom_money() {
        return from_money;
    }

    public void setFrom_money(int from_money) {
        this.from_money = from_money;
    }

    public int getTo_money() {
        return to_money;
    }

    public void setTo_money(int to_money) {
        this.to_money = to_money;
    }

    public boolean isIs_closed() {
        return is_closed;
    }

    public void setIs_closed(boolean is_closed) {
        this.is_closed = is_closed;
    }

    public Date getClosing_date() {
        return closing_date;
    }

    public void setClosing_date(Date closing_date) {
        this.closing_date = closing_date;
    }

    public Time getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(Time closing_time) {
        this.closing_time = closing_time;
    }
//
//    public Auction getAuction() {
//        return auction;
//    }
//
//    public void setAuction(Auction auction) {
//        this.auction = auction;
//    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
}
