package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.AddressDTO;
import com.example.spring_thymeleaf.entity.Address;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.repository.AddressRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void addAddress(AddressDTO addressDTO) {
        Employee employee = employeeRepository.findById(addressDTO.getEmployeeId()).orElseThrow(()->new RuntimeException("Employee Not Found"));
        Address address = modelMapper.map(addressDTO, Address.class);
       // address.setEmployee(employee);
        address.setId(null);
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Long id,AddressDTO addressDTO) {
        Address address = addressRepository.findById(id).orElseThrow(()->new RuntimeException("Address not found"));
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPinCode(addressDTO.getPinCode());
        addressRepository.save(address);
    }

   /* @Override
    public List<AddressDTO> getAllAddress() {
        return List.of();
    }*/

   /* @Override
    public AddressDTO getAddress(Long id) {
       // Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee Not Found"));
        Address address = addressRepository.findByEmployee_Id(id);
        if(address ==null){
            throw new RuntimeException("Address Not Found For This Employee");
        }
        return modelMapper.map(address,AddressDTO.class);
    }*/

   /* @Override
    public void deleteAddress(Long id) {

    }*/
}
