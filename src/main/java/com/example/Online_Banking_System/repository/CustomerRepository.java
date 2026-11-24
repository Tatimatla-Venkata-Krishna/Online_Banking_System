package com.example.Online_Banking_System.repository;

import com.example.Online_Banking_System.entiry.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {

    Customer findByAccountNo(String accountNo);
}
