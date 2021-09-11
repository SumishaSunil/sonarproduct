package com.shopping.onlineshopping.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.onlineshopping.product.entity.SubscribedProd;

	public interface SubscribedProdRepository extends JpaRepository<SubscribedProd, String>{
		List<SubscribedProd> findByBuyerid(String buyerid);
	}

