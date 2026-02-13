package com.felipfad.fin_project.service;

import com.felipfad.fin_project.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    
    private Map<Long, Account> accounts = new HashMap<>();
    private Long nextId = 1L;
    
    public AccountService() {
        initializeMockedData();
    }
    
    private void initializeMockedData() {
        accounts.put(1L, new Account(1L, "John Doe", "john@example.com", 5000.0, "ACTIVE"));
        accounts.put(2L, new Account(2L, "Jane Smith", "jane@example.com", 3500.0, "ACTIVE"));
        accounts.put(3L, new Account(3L, "Bob Johnson", "bob@example.com", 2000.0, "INACTIVE"));
        nextId = 4L;
    }
    
    // CREATE
    public Account createAccount(Account account) {
        account.setId(nextId);
        accounts.put(nextId, account);
        nextId++;
        return account;
    }
    
    // READ ALL
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    
    // READ BY ID
    public Optional<Account> getAccountById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }
    
    // UPDATE
    public Optional<Account> updateAccount(Long id, Account updatedAccount) {
        if (accounts.containsKey(id)) {
            updatedAccount.setId(id);
            accounts.put(id, updatedAccount);
            return Optional.of(updatedAccount);
        }
        return Optional.empty();
    }
    
    // DELETE
    public boolean deleteAccount(Long id) {
        return accounts.remove(id) != null;
    }
}
