//package com.SoyHenry.FinancialHub.controller;
//
//import com.SoyHenry.FinancialHub.dto.AccountDtoResponse;
//import com.SoyHenry.FinancialHub.entities.Account;
//import com.SoyHenry.FinancialHub.service.ViewServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
////@RequestMapping("/account-list")
//public class ViewController {
//
//    @Autowired
//    private ViewServiceImpl viewService;
//
//@GetMapping("/account-list")
//    public String listAccounts(Model model){
//    List<AccountDtoResponse> accounts = viewService.viewAllAccounts();
//    model.addAttribute("accounts", accounts);
//    return "accounts-list";
//}
//
//}
