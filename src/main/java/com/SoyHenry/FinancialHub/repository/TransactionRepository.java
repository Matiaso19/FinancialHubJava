package com.SoyHenry.FinancialHub.repository;

import com.SoyHenry.FinancialHub.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE (:accountId IS NULL OR t.account.id = :accountId) " +
            "AND (:type IS NULL OR t.type = :type) " +
            "AND (:startDate IS NULL OR t.date >= :startDate) " +
            "AND (:endDate IS NULL OR t.date <= :endDate)")
    List<Transaction> findByFilters(@Param("accountId") Long accountId,
                                    @Param("type") String type,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

}
