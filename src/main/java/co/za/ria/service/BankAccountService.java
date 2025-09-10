package co.za.ria.service;

import co.za.ria.exception.BankAccountException;
import co.za.ria.model.BankAccount;
import co.za.ria.repository.BankAccountRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {

    private final BankAccountRepository repository;

    public BankAccount createAccount(BankAccount account) throws BankAccountException {

        if (account == null) {
            throw new BankAccountException("Account cannot be null");
        }

        if (account.getAccountHolderName() == null || account.getAccountHolderName().isBlank()) {
            throw new ValidationException("Account holder name is required");
        }

        if (account.getAccountNumber() == null || account.getAccountNumber().isBlank()) {
            throw new ValidationException("Account number is required");
        }

        if (repository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new DataIntegrityViolationException("Account number already exists: " + account.getAccountNumber());
        }

        if (account.getBalance() == null) {
            throw new ValidationException("Initial balance must be zero or greater");
        }

        return repository.save(account);
    }


    public Optional<BankAccount> getAccountById(Long id) {
        try {
            return repository.findById(id);
        }catch (NullPointerException e){
            log.error("Could not get the account by id");
            return Optional.empty();
        }
    }

    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        try {
            return repository.findByAccountNumber(accountNumber);
        }catch (NullPointerException e){
            log.error("Could not get the account by account number");
            return Optional.empty();
        }
    }


    @Transactional
    public Optional<BankAccount> updateAccount(Long id, BankAccount updatedAccount) {

        if (id == null) {
            throw new IllegalArgumentException("Invalid account ID: " + id);
        }

        if (updatedAccount == null) {
            throw new IllegalArgumentException("Updated account cannot be null");
        }

        if(updatedAccount.getAccountHolderName() != null || updatedAccount.getBalance() != null || updatedAccount.getIsAccountActive()){
            Optional<BankAccount> existingAccount = repository.findById(id);

            return existingAccount.map(account -> {
                account.setBalance(updatedAccount.getBalance());
                account.setIsAccountActive(updatedAccount.getIsAccountActive());
               repository.save(account);
                return account;
            });
        }else {
            throw new RuntimeException("Account not found with id: " + id);
        }
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }
}