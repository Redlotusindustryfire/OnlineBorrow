package com.attjl.online_borrow.repository;

import com.attjl.online_borrow.entity.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCartRepository extends JpaRepository<ShopCart,Integer> {

    List<ShopCart> findByUsername(String username);
}
