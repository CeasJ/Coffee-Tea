package com.backend.dream.repository;

import com.backend.dream.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    @Query("SELECT d FROM Discount d WHERE d.product.id = :idProduct")
    Optional<Discount> findByIDProduct(Long idProduct);
}