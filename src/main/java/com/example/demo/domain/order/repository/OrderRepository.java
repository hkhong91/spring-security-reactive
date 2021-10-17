package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.document.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;

public interface OrderRepository extends ReactiveMongoRepository<Order, String>,
    ReactiveQuerydslPredicateExecutor<Order> {

}
