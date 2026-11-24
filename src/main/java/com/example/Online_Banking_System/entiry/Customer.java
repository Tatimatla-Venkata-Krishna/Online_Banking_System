package com.example.Online_Banking_System.entiry;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    private String accountNo;
    private String name;
    private String dob;
    private String gender;
    private String email;
    private String accountType;
    private String password;
    private String phno;
    private double balance;
}
