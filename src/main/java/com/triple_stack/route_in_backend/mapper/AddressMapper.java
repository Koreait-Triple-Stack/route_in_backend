package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Address;
import com.triple_stack.route_in_backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AddressMapper {
    Optional<Address> getAddressByUserId(Integer userId);
    int addAddress(Address address);
    int changeAddress(Address address);
}
