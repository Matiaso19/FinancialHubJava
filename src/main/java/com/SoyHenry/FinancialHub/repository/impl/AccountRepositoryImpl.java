//package com.SoyHenry.FinancialHub.repository.impl;
//
//import com.SoyHenry.FinancialHub.entities.Account;
//import com.SoyHenry.FinancialHub.repository.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class AccountRepositoryImpl implements AccountRepository {
//
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    @Override
//    public List<Account> getAll() {
//        String sql = "SELECT * FROM accounts";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));
//    }
//
//    @Override
//    public int save(Account account) {
//        String sql = "INSERT INTO accounts (accountHolderName, balance, openingDate) VALUES(?,?,?)";
//        return jdbcTemplate.update(sql, account.getAccountHolderName(), account.getBalance(), account.getOpeningDate());
//    }
//
//    @Override
//    public int delete(Long id) {
//        String sql = "DELETE FROM accounts WHERE id = ?";
//        return jdbcTemplate.update(sql, id);
//    }
//
//    @Override
//    public Account getAccountById(Long id) {
//        String sql = "SELECT * FROM accounts WHERE id = ?";
//        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Account.class), id);
//    }
//
//    @Override
//    public int update(Account account) {
//        return 0;
//    }
//}
