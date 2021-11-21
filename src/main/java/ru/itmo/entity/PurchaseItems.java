package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//CREATE TABLE "purchase_items" (
//        "id" bigserial,
//        "price" bigint not null check(price > 0),
//        "item" bigint not null references items(id_item),
//        PRIMARY KEY ("id")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "purchase_items", schema = "public")
public class PurchaseItems {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    @JsonView(View.PurchaseItems.class)
    private Long id;

    @Column(name = "price", nullable = false, unique = true)
    @JsonView(View.PurchaseItems.class)
    private int price;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "item")
    @JsonView(View.PurchaseItems.class)
    private Item item;

    @Override
    public String toString() {
        return "PurchaseItems{" +
                "id=" + id +
                ", price=" + price +
                ", item=" + item +
                '}';
    }
}
