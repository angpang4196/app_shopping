package org.edupoll.controller;

import org.edupoll.exception.IsAdminException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.model.dto.request.ProductRegistrationRequest;
import org.edupoll.model.dto.response.ProductListResponse;
import org.edupoll.model.dto.response.ProductResponse;
import org.edupoll.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	// 전체 상품 목록 불러오기 (전체 이용가능)
	@GetMapping
	public ResponseEntity<?> getProductsInfo(@RequestParam(defaultValue = "남성") String productMainType,
			@RequestParam(defaultValue = "상의") String productSubType, @RequestParam(defaultValue = "1") int page)
			throws NotFoundProductException {

		ProductListResponse list = productService.allItems(productMainType, productSubType, page);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// 특정 상품 자세히 제공 해 주는 API (전체 이용가능)
	@GetMapping("/{productId}")
	public ResponseEntity<?> readSpecificProductHandle(@PathVariable Long productId) throws NotFoundProductException {
		ProductWrapper one = productService.getSpecificProduct(productId);

		ProductResponse response = new ProductResponse(one);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 상품 등록 (권한 필요)
	@PostMapping
	public ResponseEntity<?> newProductHandle(@AuthenticationPrincipal String principal, ProductRegistrationRequest req)
			throws IsAdminException {
		productService.create(principal, req);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteProductHandle(@AuthenticationPrincipal String userId, @PathVariable Long productId) throws IsAdminException, NotFoundProductException {
		productService.delete(userId, productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
