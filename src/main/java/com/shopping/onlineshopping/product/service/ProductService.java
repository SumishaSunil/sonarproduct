package com.shopping.onlineshopping.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopping.onlineshopping.product.dto.ProductDTO;
import com.shopping.onlineshopping.product.entity.Product;
import com.shopping.onlineshopping.product.exception.ShopingException;
import com.shopping.onlineshopping.product.repository.ProductRepository;
import com.shopping.onlineshopping.product.repository.SubscribedProdRepository;

@Service
public class ProductService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductRepository productrepo;

	@Autowired
	SubscribedProdRepository subscribedprodrepo;

	// Get products by name
	public List<ProductDTO> getProductByName(@PathVariable String productname) throws ShopingException {

		logger.info("Product request with name {}", productname);

		Iterable<Product> product = productrepo.findByproductname(productname);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new ShopingException("Service.PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	//Get products by category
	public List<ProductDTO> getProductBycategory(@PathVariable String category) throws ShopingException {
		logger.info("Product request of category {}", category);
		Iterable<Product> product = productrepo.findBycategory(category);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new ShopingException("Service.PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	//Get products by seller id
	public List<ProductDTO> getProductBySellerId(@PathVariable String sellerid) throws ShopingException {
		logger.info("Product request of seller {}", sellerid);
		Iterable<Product> product = productrepo.findBysellerid(sellerid);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new ShopingException("Service.PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	//Get all product details
	public List<ProductDTO> getAllProduct() throws ShopingException {

		Iterable<Product> products = productrepo.findAll();
		List<ProductDTO> productDTOs = new ArrayList<>();

		products.forEach(product -> {
			ProductDTO productDTO = ProductDTO.valueOf(product);
			productDTOs.add(productDTO);
		});
		if (productDTOs.isEmpty())
			throw new ShopingException("Service.PRODUCTS_NOT_FOUND");
		logger.info("Product Details : {}", productDTOs);
		return productDTOs;
	}

	//Update stock
	public Product updateProductStock(Product product, String prodid) throws ShopingException{
        Product existingProduct = productrepo.findById(prodid).orElse(null);
        if(existingProduct != null) {
            existingProduct.setStock(product.getStock());
            return productrepo.save(existingProduct); 
        }else {
        	throw new ShopingException("Service.PRODUCTS_NOT_FOUND");
        }
    
    }
	
	//Get product by id
	public ProductDTO getByProdid(String prodid) throws ShopingException{
		logger.info("Profile request for customer {}", prodid);
		Product pro = productrepo.findByProdid(prodid);
		if(pro != null) {
			ProductDTO proDTO = ProductDTO.valueOf(pro);
			logger.info("Profile for customer : {}", proDTO);
			return proDTO;
		} else{
			throw new ShopingException("Service.PRODUCTS_NOT_FOUND");
		}
	}
	
	//Add product
	public void saveProduct(ProductDTO productDTO) throws ShopingException {
		logger.info("Adding product with data {}", productDTO);
		Product product = productDTO.createProduct();
		productrepo.save(product);
	}

	//Delete product
	public void deleteProduct(String productid) throws ShopingException {
		Optional<Product> product = productrepo.findById(productid);
		product.orElseThrow(() -> new ShopingException("Service.Seller_NOT_FOUND"));
		productrepo.deleteById(productid);
	}

}
