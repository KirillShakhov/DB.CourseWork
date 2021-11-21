package ru.itmo.entity.old;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.itmo.entity.View;

import javax.persistence.*;


@Data
@Entity
@Table(name = "payments", schema = "public")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    @JsonView(View.Payment.class)
    private String cardNumber;
    private String month;
    private String year;
    private String cvv;

    public Payment(String firstName, String lastName, String cardNumber, String month, String year, String cvv) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public Payment() {

    }
}
