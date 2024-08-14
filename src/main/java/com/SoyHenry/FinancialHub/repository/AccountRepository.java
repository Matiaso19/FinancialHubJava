package com.SoyHenry.FinancialHub.repository;


import com.SoyHenry.FinancialHub.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository  extends JpaRepository<Account, Long> {

//    List<Account> getAll ();
//    int save(Account account);
//    int delete(Long id);
//    Account getAccountById(Long id);
//    int update(Account account);


}
