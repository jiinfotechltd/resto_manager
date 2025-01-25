package com.jiinfotech.restomanager.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByTableId(long tableId);

    Optional<Order> findByIdAndIsPaid(Long orderId, boolean b);
}

