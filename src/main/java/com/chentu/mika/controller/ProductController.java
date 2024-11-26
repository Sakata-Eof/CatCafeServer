package com.chentu.mika.controller;

import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.Product;
import com.chentu.mika.model.form.CatForm;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.CatService;
import com.chentu.mika.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @description:处理与产品相关的API请求
 */
@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/prdts")
	public Result add(@RequestBody Product product) {
		Assert.notNull(product, "参数不能为空");
		Assert.notNull(product.getProductName(), "商品名称不能为空");
		Assert.notNull(product.getPrice(), "商品价格不能为空");
		productService.save(product);
		return Result.success(null);
	}
	
	@DeleteMapping("/prdts/{productIDs}")
	public Result delete(@PathVariable("productIDs") String productIDs) {
		productService.removeByIds(Arrays.asList(productIDs.split(",")));
		return Result.success(null);
	}
	
	@GetMapping("/prdts/{productID}")
	public Result get(@PathVariable("productID") Integer productID) {
		Product product = productService.getById(productID);
		return Result.success(product);
	}
	
	@PutMapping("/prdts")
	public Result update(@RequestBody Product product) {
		productService.updateById(product);
		return Result.success(null);
	}
	
	@GetMapping("/prdts")
	public Result list() {
		return Result.success(productService.list());
	}
}
