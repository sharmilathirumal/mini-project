package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.AddressDTO;
import com.example.spring_thymeleaf.entity.Address;
import com.example.spring_thymeleaf.service.AddressService;
import com.example.spring_thymeleaf.service.Impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
@CrossOrigin("*")
@EnableWebSecurity
@EnableMethodSecurity
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
    @PostMapping("/add")
    public String addAddress(@RequestBody AddressDTO address){
        addressService.addAddress(address);
        return "address added to the employee successfully";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
    @PutMapping("/update/{id}")
    public String updateAddress(@PathVariable Long id,@RequestBody AddressDTO addressDTO){
        addressService.updateAddress(addressDTO);
        return "details updated to the employee successfully";
    }

   /* @GetMapping("/get/{id}")
    public AddressDTO getAddress(@PathVariable Long id){
      return  addressService.getAddress(id);
    }*/
}
