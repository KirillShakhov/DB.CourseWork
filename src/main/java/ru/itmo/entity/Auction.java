package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//CREATE TABLE "auctions" (
//        "contract" bigint not null references contracts(id_contract),
//        "last_bet_size" int check(last_bet_size> 0),
//        "last_customer" bigint
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "auctions", schema = "public")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonView(View.Auction.class)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract", nullable = false)
    @JsonView(View.Auction.class)
    private Contract contract;

    @Column(name = "last_bet_size")
    @JsonView(View.Auction.class)
    private int last_bet_size;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "last_customer")
    @JsonView(View.Auction.class)
    private User last_customer;

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", contract=" + contract +
                ", last_bet_size=" + last_bet_size +
                ", last_customer=" + last_customer +
                '}';
    }
}
