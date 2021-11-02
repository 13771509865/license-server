package com.yozosoft.licenseserver.web;

import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.exception.LicenseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    HttpServletRequest request;

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        if(!userName.equals("yozosoft") || !password.equals("office2022")){
            throw new LicenseException(EnumResultCode.E_ACCOUNT_LOGIN_ERROR, HttpStatus.FORBIDDEN);
        }
        request.getSession().setAttribute("loginStatus", "true");
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        request.getSession().removeAttribute("loginStatus");
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }
}
