package co.za.ria.controller;

import co.za.ria.exception.BankAccountException;
import co.za.ria.model.BankAccount;
import co.za.ria.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-app")
public class BankAccountController {

    private final BankAccountService service;

    @PostMapping("/createAccount")
    public ResponseEntity<BankAccount> createAccount(@Valid @RequestBody BankAccount account) throws BankAccountException {
        BankAccount createdAccount = service.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<BankAccount>> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccountById(id));
    }

    @GetMapping("/byAcc/{accountNumber}")
    public ResponseEntity<Optional<BankAccount>> getAccount(@PathVariable String accountNumber) throws BankAccountException {
        return ResponseEntity.ok(service.findByAccountNumber(accountNumber));
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @Valid @RequestBody BankAccount account) {
        try {
            Optional<BankAccount> updated = service.updateAccount(id, account);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
