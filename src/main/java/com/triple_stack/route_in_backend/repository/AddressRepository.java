package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Address;
import com.triple_stack.route_in_backend.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddressRepository {
    @Autowired
    private AddressMapper addressMapper;

    public int addAddress(Address address) {
        return addressMapper.addAddress(address);
    }

    public int changeAddress(Address address) {
        return addressMapper.changeAddress(address);
    }

    public Optional<Address> getAddressByUserId(Integer userId) {
        return addressMapper.getAddressByUserId(userId);
    }
}
