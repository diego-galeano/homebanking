package com.mindhub.homebanking2.repository;

import com.mindhub.homebanking2.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
}
