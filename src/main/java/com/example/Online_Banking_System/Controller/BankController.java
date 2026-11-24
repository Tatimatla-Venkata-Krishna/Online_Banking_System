
package com.example.Online_Banking_System.Controller;


import com.example.Online_Banking_System.entiry.Customer;
import com.example.Online_Banking_System.entiry.Transactions;
import com.example.Online_Banking_System.repository.CustomerRepository;
import com.example.Online_Banking_System.repository.TransactionRepository;
import com.example.Online_Banking_System.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class BankController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String index(){
        return "login";
    }

    @GetMapping("/register")
    public String registor(@ModelAttribute Customer customer){
        String accno =customerService.accountNumber();
        customer.setAccountNo(accno);
        customer.setBalance(0.00);
        Customer c =customerRepository.save(customer);
        return "login";
    }
    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Customer customer = customerRepository.findAll().stream().filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password)).findFirst().orElse(null);
        if (customer==null){
            session.setAttribute("error","Invalid Email or password");
            return  "login";
        }
        List<Transactions> transactions = customerService.allTransactions(customer.getAccountNo());
        double dt= customerService.total(transactions,"CREDIT");
        double wt= customerService.total(transactions,"DEBIT");
        List<Transactions> revlist=new ArrayList<>(transactions);
        Collections.reverse(revlist);
        List<Transactions> smallRecords=revlist.stream().limit(4).collect(Collectors.toList());
        session.setAttribute(  "depositTotal",dt);
        session.setAttribute(  "withdrawTotal",wt);
        session.setAttribute("customer", customer);
        session.setAttribute("transactions", revlist);
        session.setAttribute("recentTransactions",smallRecords);
        return "dashbord";
    }
    @GetMapping("/deposit")
    public String deposit(@RequestParam double amount ,HttpSession session){
        List<Transactions> smallList =(List<Transactions>) session.getAttribute("recentTransactions");
        Customer c=(Customer) session.getAttribute("customer");
        List<Transactions> transactions =(List<Transactions>) session.getAttribute("transactions");
        customerService.insertTransaction(c,amount,"CREDIT");
        List<Transactions> t = customerService.allTransactions(c.getAccountNo());
        double dt= customerService.total(t,"CREDIT");
        double wt= customerService.total(t,"DEBIT");
        List<Transactions> revlist=new ArrayList<>(t);
        Collections.reverse(revlist);
        List<Transactions> smallRecords=revlist.stream().limit(4).collect(Collectors.toList());
        session.setAttribute("customer",c);
        session.setAttribute("transactions",revlist);
        session.setAttribute("recentTransactions",smallRecords);
        session.setAttribute(  "depositTotal",dt);
        session.setAttribute(  "withdrawTotal",wt);
        return"dashbord";
    }
    @GetMapping("/withdraw")
    public String withdraw(@RequestParam double amount,HttpSession session){
        List<Transactions> smallList =(List<Transactions>) session.getAttribute("recentTransactions");
        Customer c=(Customer) session.getAttribute("customer");
        List<Transactions> transactions =(List<Transactions>) session.getAttribute("transactions");

        customerService.insertTransaction(c,amount,"DEBIT");
        List<Transactions> t = customerService.allTransactions(c.getAccountNo());

        double dt= customerService.total(t,"CREDIT");
        double wt= customerService.total(t,"DEBIT");
        List<Transactions> revlist=new ArrayList<>(t);
        Collections.reverse(revlist);
        List<Transactions> smallRecords=revlist.stream().limit(4).collect(Collectors.toList());

        session.setAttribute("customer",c);
        session.setAttribute("transactions",revlist);
        session.setAttribute("recentTransactions",smallRecords);
        session.setAttribute(  "depositTotal",dt);
        session.setAttribute(  "withdrawTotal",wt);
        return"dashbord";
    }

    @GetMapping("/Trnsformoney")
    public String tranfor(@RequestParam String accountnumber,@RequestParam double amount,HttpSession session){
        List<Transactions> smallList =(List<Transactions>) session.getAttribute("recentTransactions");
        Customer c=(Customer) session.getAttribute("customer");
        List<Transactions> transactions =(List<Transactions>) session.getAttribute("transactions");

        int count =customerService.insertTransactionTransfer(c,amount,accountnumber,"DEBIT");

        List<Transactions> t = customerService.allTransactions(c.getAccountNo());

        double dt= customerService.total(t,"CREDIT");
        double wt= customerService.total(t,"DEBIT");

        List<Transactions> revlist=new ArrayList<>(t);
        Collections.reverse(revlist);
        List<Transactions> smallRecords=revlist.stream().limit(4).collect(Collectors.toList());

        session.setAttribute("customer",c);
        session.setAttribute("transactions",revlist);
        session.setAttribute("recentTransactions",smallRecords);
        session.setAttribute("depositTotal",dt);
        session.setAttribute("withdrawTotal",wt);
        return"dashbord";
    }

    @GetMapping("/logout")
    public  String logout(){
        return "login";
    }

}
