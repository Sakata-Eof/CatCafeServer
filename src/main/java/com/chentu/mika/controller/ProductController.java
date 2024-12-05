package com.chentu.mika.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.Product;
import com.chentu.mika.model.form.CatForm;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.CatService;
import com.chentu.mika.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @description:处理与产品相关的API请求
 */
@RestController
public class ProductController {

	@Value("${prop.upload-folder}")
	private String UPLOAD_FOLDER;
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/prdts")
	@ResponseBody
	public Result add(@RequestParam("productName") String name,
					  @RequestParam("productImage") MultipartFile image,
					  @RequestParam("productBrief") String brief,
					  @RequestParam("price") Double price)throws FileNotFoundException {
		Product product=new Product();

		product.setProductImage(""+image.getOriginalFilename().hashCode()+".jpg");
		product.setProductName(name);
		product.setPrice(price);
		product.setProductBrief(brief);

		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(
							UPLOAD_FOLDER+image.getOriginalFilename().hashCode()+".jpg")));
			out.write(image.getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Result.fail("上传失败");
		} catch (IOException e) {
			e.printStackTrace();
			return Result.fail("上传失败");
		}
		productService.save(product);
		return Result.success(null);
	}
	
	@DeleteMapping("/prdts/{productIDs}")
	public Result delete(@PathVariable("productIDs") String productIDs) {
		String[] split = productIDs.split(",");
		List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
		for (Integer i : list) {
			File file = new File(UPLOAD_FOLDER
					+productService
					.getOne(Wrappers.<Product>lambdaQuery()
							.eq(Product::getProductID, i)).getProductImage());
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
			}
			productService.remove(Wrappers.<Product>lambdaQuery()
					.eq(Product::getProductID, i));
		}
		return Result.success(null);
	}
	
	@GetMapping("/prdts/{productID}")
	public Result get(@PathVariable("productID") Integer productID) {
		Product product = productService.getOne(Wrappers.<Product>lambdaQuery()
				.eq(Product::getProductID, productID));
		return Result.success(product);
	}
	
	@PutMapping("/prdts")
	@ResponseBody
	public Result update(@RequestParam("productImage") MultipartFile image,
						 @RequestParam("productName") String name,
						 @RequestParam("price") Double price,
						 @RequestParam("productBrief") String brief,
						 @RequestParam("productID") Integer ID) throws FileNotFoundException {
		File file = new File(UPLOAD_FOLDER
				+productService
				.getOne(Wrappers.<Product>lambdaQuery()
						.eq(Product::getProductID, ID)).getProductImage());
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(
							UPLOAD_FOLDER+image.getOriginalFilename().hashCode()+".jpg")));
			out.write(image.getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Result.fail("上传失败");
		} catch (IOException e) {
			e.printStackTrace();
			return Result.fail("上传失败");
		}
		productService.update(Wrappers.<Product>lambdaUpdate()
				.eq(Product::getProductID, ID)
				.set(name!=null,Product::getProductName, name)
				.set(image!=null,Product::getProductImage, ""+image.getOriginalFilename().hashCode()+".jpg")
				.set(brief!=null,Product::getProductBrief, brief)
				.set(price!=null,Product::getPrice, price)
		);
		return Result.success(null);
	}
	
	@GetMapping("/prdts")
	public Result list() {
		return Result.success(productService.list());
	}
}
