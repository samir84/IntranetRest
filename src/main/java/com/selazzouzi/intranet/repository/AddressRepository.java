package com.selazzouzi.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selazzouzi.intranet.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
