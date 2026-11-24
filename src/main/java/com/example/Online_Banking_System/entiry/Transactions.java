
package com.example.Online_Banking_System.entiry;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private double amount;
    private LocalDateTime transactionDate;
    private String accountNumber;
    private String status;

}
