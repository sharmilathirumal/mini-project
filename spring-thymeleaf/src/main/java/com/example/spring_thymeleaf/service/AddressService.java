package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    void addAddress(AddressDTO addressDTO);
    void updateAddress(AddressDTO addressDTO);
    AddressDTO getAddressByEmployeeId(Long id);
    // List<AddressDTO> getAllAddress();//i don't think it will be used in live since based on employee only anyone can see the address
    //AddressDTO getAddress(Long id);
   // void deleteAddress(Long id);//same we can update the address but delete addres won't happen
}
