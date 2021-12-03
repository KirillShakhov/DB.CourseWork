package ru.itmo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//CREATE TABLE "items" (
//        "id_item" bigserial,
//        "car" bigint check((wheels is null and bumper is null) or car is null) references cars(id_car),
//        "wheels" bigint check((car is null and bumper is null) or wheel is null) references wheels(id_wheel),
//        "bumper" bigint check((car is null and wheels is null) or bumper is null) references bumpers(id_bumper),
//        "description" text,
//        "real_photo" varchar(250),
//        "owner" bigint not null references users(id_user),
//        PRIMARY KEY ("id_item")
//        );

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_item")
    @JsonView(View.Item.class)
    private Long id_item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car")
    @JsonView(View.Item.class)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wheels")
    @JsonView(View.Item.class)
    private Wheels wheels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bumper")
    @JsonView(View.Item.class)
    private Bumper bumper;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "real_photo", length = 250)
    @JsonView(View.Item.class)
    private String real_photo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner", nullable = false)
    @JsonView(View.Item.class)
    private User owner;

    @OneToOne(mappedBy = "item")
    private PurchaseItems purchase_items;

    public Item(User user, String description, String real_photo) {
        this.owner = user;
        this.description = description;
        this.real_photo = real_photo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id_item=" + id_item +
                ", car=" + car +
                ", wheels=" + wheels +
                ", bumper=" + bumper +
                ", description='" + description + '\'' +
                ", real_photo='" + real_photo + '\'' +
                ", owner=" + owner +
                '}';
    }
}
