package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.entities.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewService {

    public List<Account> viewAllAccounts();


}
