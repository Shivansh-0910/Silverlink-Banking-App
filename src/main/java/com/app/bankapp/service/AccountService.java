package com.app.bankapp.service;


import com.app.bankapp.model.Account;
import com.app.bankapp.model.Transaction;
import com.app.bankapp.repository.AccountRepository;
import com.app.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account findAccountByUsername(String username){
        return accountRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Account not found"));
    }

    public Account registerAccount(String username,String password){
        if(accountRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already exists!");
        }
        Account account=new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    public void deposit(Account account,BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                amount,
                "Deposit",
                LocalDateTime.now(),
                account
        );
        transactionRepository.save(transaction);
    }

    public void withdraw(Account account,BigDecimal amount){
        if(amount.compareTo(account.getBalance())>0){
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        Transaction transaction = new Transaction(
                amount,
                "Withdraw",
                LocalDateTime.now(),
                account
        );
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(Account account){
            return transactionRepository.findByAccountId(account.getId());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Account account=findAccountByUsername(username);
        if(account==null){
            throw new UsernameNotFoundException("Username or Password not found!");
        }
        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),   // Username from account
                account.getPassword(),   // Password (BCrypt encoded) from account
                authorities()            // Authorities (roles/permissions) from account
        );
    }

    public Collection<? extends GrantedAuthority> authorities(){
        return Arrays.asList(new SimpleGrantedAuthority("User"));
    }

    public void transferAmount(Account fromAccount,String toUsername,BigDecimal amount){
        if(fromAccount.getBalance().compareTo(amount)<0){
            throw new RuntimeException("Insufficient Funds!");
        }

        Account toAccount=accountRepository.findByUsername(toUsername)
                .orElseThrow(()->new RuntimeException("Recepient Account not found!"));

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);

        Transaction debitTransaction=new Transaction(
                amount,
                "Transfer Out to "+toAccount.getUsername(),
                LocalDateTime.now(),
                fromAccount
        );
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction=new Transaction(
                amount,
                "Transfer from  "+fromAccount.getUsername(),
                LocalDateTime.now(),
                toAccount
        );

        transactionRepository.save(creditTransaction);

    }
}