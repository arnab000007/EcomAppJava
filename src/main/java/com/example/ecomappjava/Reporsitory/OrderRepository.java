package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.EcomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<EcomOrder, Long> {
    EcomOrder save(EcomOrder order);
    Optional<EcomOrder> findEcomOrderById(Long id);
    List<EcomOrder> findAllByUser_Id(Long id);
}
