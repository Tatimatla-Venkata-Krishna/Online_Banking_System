package com.example.Online_Banking_System.service;

import com.example.Online_Banking_System.entiry.Customer;
import com.example.Online_Banking_System.entiry.Transactions;
import com.example.Online_Banking_System.repository.CustomerRepository;
import com.example.Online_Banking_System.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        // Generate 12-digit account number
        accountNumber.append("10");
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
    public String accountNumber() {

        List<Customer> allCustomers = customerRepository.findAll();

        String accno;

        while (true) {
            accno = CustomerService.generateAccountNumber();

            String finalAccno = accno;

            boolean exists = allCustomers.stream()
                    .anyMatch(customer -> finalAccno.equals(customer.getAccountNo()));

            if (!exists) {
                return accno;
            }
        }
    }

    public List<Transactions> allTransactions(String acno){
        List<Transactions> all =transactionRepository.findAll();
        return all.stream().filter(t->t.getAccountNumber().equals(acno)).toList();
    }

    public double total(List<Transactions> list,String type){
        return list.stream().filter(t->t.getType().equalsIgnoreCase(type)).mapToDouble(t -> t.getAmount()).sum();
    }

    public void transactionDetailes(Customer c,double amount,String type,String status){
        customerRepository.save(c);
        Transactions transactions= new Transactions();
        transactions.setAccountNumber(c.getAccountNo());
        transactions.setAmount(amount);
        transactions.setTransactionDate(LocalDateTime.now());
        transactions.setType(type);
        transactions.setStatus(status);
        transactionRepository.save(transactions);
    }
    public void insertTransaction(Customer c,double amount,String type){
        if (type.equals("CREDIT")) {
            c.setBalance(c.getBalance() + amount);
            transactionDetailes(c,amount,type,"SUCCESSFUL");
        }
        else if (c.getBalance()>=amount){
            c.setBalance(c.getBalance() - amount);
            customerRepository.save(c);
            transactionDetailes(c,amount,type,"SUCCESSFUL");
        }else {
            transactionDetailes(c,amount,type,"FAILED");
        }

    }
    public int insertTransactionTransfer(Customer c,double amount,String accno,String type){
        Customer receiver= customerRepository.findByAccountNo(accno);

        if (c.getBalance()>=amount) {
            c.setBalance(c.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            customerRepository.save(c);
            customerRepository.save(receiver);
            transactionDetailes(c, amount, type, "SUCCESSFUL");
            transactionDetailes(receiver, amount, "CREDIT", "SUCCESSFUL");
            return  0;
        }
        else {
            transactionDetailes(c, amount, type, "FAILED");
            transactionDetailes(receiver, amount, "CREDIT", "FAILED");
            return  1;
        }

    }



}
