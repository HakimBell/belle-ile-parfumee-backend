package com.belleileperfumee.belle_ile_parfumee.repository;

import com.belleileperfumee.belle_ile_parfumee.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLine.OrderLineId> {

    List<OrderLine> findById_CommandNumber(String commandNumber);

    List<OrderLine> findById_ProductCode(String productCode);
}
