package com.shopping.onlineshopping.product.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopping.onlineshopping.product.dto.SubscribedProdDTO;
import com.shopping.onlineshopping.product.entity.SubscribedProd;
import com.shopping.onlineshopping.product.exception.ShopingException;
import com.shopping.onlineshopping.product.repository.SubscribedProdRepository;

@Service
public class SubscribedProdService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SubscribedProdRepository subscribedprodrepo;
	
	//Get subscribed product of buyer
	public List<SubscribedProdDTO> getByBuyerid(@PathVariable String buyerid) throws ShopingException{

		logger.info("Products for the buyer with id {}", buyerid);

		Iterable<SubscribedProd> sub = subscribedprodrepo.findByBuyerid(buyerid);
		List<SubscribedProdDTO> subscribedprodDTO = new ArrayList<SubscribedProdDTO>();

		sub.forEach(subs -> {
			subscribedprodDTO.add(SubscribedProdDTO.valueOf(subs));
		});
		if(subscribedprodDTO.isEmpty())
			throw new ShopingException("Service.EMPTY_BUYER_PRODUCTS");
		return subscribedprodDTO;
	}
	
	//Get all subscribed products
	public List<SubscribedProdDTO> getAllSubProduct() throws ShopingException{

		Iterable<SubscribedProd> subs = subscribedprodrepo.findAll();
		List<SubscribedProdDTO> subscribedprodDTOs = new ArrayList<>();

		subs.forEach(subpro -> {
			SubscribedProdDTO subscribedprodDTO = SubscribedProdDTO.valueOf(subpro);
			subscribedprodDTOs.add(subscribedprodDTO);
		});
		if(subscribedprodDTOs.isEmpty())
			throw new ShopingException("Service.NO_SUBSCRIBED_PRODUCTS");
		logger.info("Subscribed Product Details : {}", subscribedprodDTOs);
		return subscribedprodDTOs;
	}
	
	public void createSubscribedProduct(SubscribedProdDTO subscribeProdDTO) throws ShopingException {
		logger.info("Creation request for customer {} with data {}", subscribeProdDTO);
		SubscribedProd subscribe = null;
//		if(subscribeProdDTO.getBuyerid() != null) {
//			subscribe = subscribeProdDTO.createEntity();	
//		}
//		System.out.println("wishlist" + subscribe);
		subscribe = subscribeProdDTO.createEntity();	
		if (subscribe != null) {
			subscribedprodrepo.save(subscribe);
		} else {
			throw new ShopingException("Service.NO_WISHLIST");
		}

	}
	
}
