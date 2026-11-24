package com.example.Online_Banking_System.repository;

import com.example.Online_Banking_System.entiry.Transactions;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Long> {
}
