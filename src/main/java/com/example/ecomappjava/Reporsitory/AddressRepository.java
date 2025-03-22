package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address save(Address address);
}
