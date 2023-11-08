package com.backend.dream.service;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Discount;

import java.util.List;

public interface DiscountService {
    DiscountDTO createDiscount(DiscountDTO discount);
    DiscountDTO getDiscountByProductId(Long id);

    Double getDiscountPercentByProductId(Long idProduct);

//    void deleteDiscount(Long discountId);
    List<DiscountDTO> findAll();

    DiscountDTO update(DiscountDTO discountDTO);

    void delete(Long id);
}
