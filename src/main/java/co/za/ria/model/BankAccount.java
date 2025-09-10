package co.za.ria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String accountHolderName;

    @Column(unique = true)
    @NotNull
    private String accountNumber;

    @NotNull
    private String bankName;

    @NotNull
    @Positive(message = "Balance must be positive")
    private BigDecimal balance;

    @NotNull
    private Boolean isAccountActive;

}
