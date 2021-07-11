package com.sm.app.web.controller;

import com.sm.app.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.web.bind.annotation.*;
//
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    FtpService ftpService;

	@GetMapping
	public String uploadDifferentExtensionFile() {
        ftpService.uploadFile();
		return "succes";
	}

}