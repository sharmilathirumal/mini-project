package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.AddressDTO;
import com.example.spring_thymeleaf.entity.Address;
import com.example.spring_thymeleaf.repository.AddressRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        //Employee employee = employeeRepository.findById(addressDTO.getEmployeeId()).orElseThrow(()->new RuntimeException("Employee Not Found"));
        Address address = modelMapper.map(addressDTO, Address.class);
       // address.setEmployee(employee);
        address.setId(null);
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressDTO.getId()).orElseThrow(()->new IllegalStateException("Address not found"));
        boolean isChanged =false;
        if(!Objects.equals(addressDTO.getHouseNumber(),address.getHouseNumber())){
            address.setHouseNumber(addressDTO.getHouseNumber());
            isChanged =true;
        }
        if(!Objects.equals(addressDTO.getStreet(),address.getStreet())){
            address.setStreet(addressDTO.getStreet());
            isChanged =true;
        }
        if (!Objects.equals(addressDTO.getCity(),address.getCity())){
            address.setCity(addressDTO.getCity());
            isChanged =true;
        }
        if(!Objects.equals(addressDTO.getState(),address.getState())){
            address.setState(addressDTO.getState());
            isChanged =true;
        }
        if(!Objects.equals(addressDTO.getCountry(),address.getCountry())){
            address.setCountry(addressDTO.getCountry());
            isChanged =true;
        }
        if(!Objects.equals(addressDTO.getPinCode(),address.getPinCode())){
            address.setPinCode(addressDTO.getPinCode());
            isChanged =true;
        }
        if(isChanged){
            addressRepository.save(address);
        }
    }

   /* @Override
    public List<AddressDTO> getAllAddress() {
        return List.of();
    }*/

   @Override
   public AddressDTO getAddressByEmployeeId(Long employeeId) {
       // Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee Not Found"));
        Optional<Address> address = addressRepository.findById(employeeRepository.findAddressIdByEmployeeId(employeeId));
        if(address.isPresent()){
            return modelMapper.map(address,AddressDTO.class);
        }
       throw new IllegalStateException("Address Not Found For This Employee");
    }

   /* @Override
    public void deleteAddress(Long id) {

    }*/
}
