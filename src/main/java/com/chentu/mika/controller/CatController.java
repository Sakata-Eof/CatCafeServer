package com.chentu.mika.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.form.CatForm;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.CatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @description:与猫相关的API请求
 */
@RestController
public class CatController {
	
	@Autowired
	CatService catService;
	
	@PostMapping("/cats")
	public Result add(@RequestBody CatForm form) {
		
		Cat cat = new Cat();
		BeanUtils.copyProperties(form, cat);
		cat.setCatState(form.getCatState() ? 1 : 0);
		catService.save(cat);
		return Result.success(null);
	}
	
	@DeleteMapping("/cats/{catIDs}")
	public Result del(@PathVariable("catIDs") String catIDs) {
		String[] split = catIDs.split(",");
		List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
		catService.remove(Wrappers.<Cat>lambdaQuery()
				.in(Cat::getCatID, list)
		);
		return Result.success(null);
	}
	
	@GetMapping("/cats/{catID}")
	public Result get(@PathVariable("catID") Integer catID) {
		Cat cat = catService.getOne(Wrappers.<Cat>lambdaQuery()
				.eq(Cat::getCatID, catID));
		return Result.success(cat);
	}
	
	@PutMapping("/cats")
	public Result update(@RequestBody CatForm form) {
		Cat cat = new Cat();
		BeanUtils.copyProperties(form, cat);
		catService.update(Wrappers.<Cat>lambdaUpdate()
				.eq(Cat::getCatID, form.getCatId())
				.set(cat.getCatName()!=null,Cat::getCatName, cat.getCatName())
				.set(cat.getCatImage()!=null,Cat::getCatImage, cat.getCatImage())
				.set(cat.getCatAge()!=null,Cat::getCatAge, cat.getCatAge())
				.set(cat.getCatBrief()!=null,Cat::getCatBrief, cat.getCatBrief())
				.set(cat.getCatSex()!=null,Cat::getCatSex, cat.getCatSex())
				.set(cat.getCatState()!=null,Cat::getCatState, cat.getCatState())
		);
		return Result.success(null);
	}
	
	@GetMapping("/cats")
	public Result list() {
		return Result.success(catService.list());
	}
}
