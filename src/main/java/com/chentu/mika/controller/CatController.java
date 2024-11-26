package com.chentu.mika.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.form.CatForm;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.CatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @description:与猫相关的API请求
 */
@RestController
public class CatController {
	
	@Autowired
	CatService catService;
	
	@PostMapping("/cats")
	@ResponseBody
	public Result add(@RequestParam("catImage") MultipartFile image,
					  @RequestParam("catName") String name,
					  @RequestParam("catAge")Integer age) {
		
		Cat cat = new Cat();

		cat.setCatImage(""+image.getOriginalFilename().hashCode());
		cat.setCatName(name);
		cat.setCatAge(age);

		//错的
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
		/*List<Cat> catList = catService.list();
		CatRequest catRequest = new CatRequest();
		List<CatRequest> reqList = new ArrayList<>();
		Iterator<CatRequest> iterator = reqList.iterator();
		for (Cat cat : catList) {
			if(cat.getCatState()==1){
				catRequest = iterator.next();
				catRequest.setCatState(true);
			}
			else{

			}
			catRequest.setCatID(cat.getCatID());
			catRequest.setCatImage("src/main/resources/image/"+cat.getCatImage());
		}*/
		return Result.success(catService.list());
	}
}




class CatRequest {
	private Integer catID;

	private String catName;

	private String catImage;

	private Integer catAge;

	private String catBrief;

	private Integer catSex;

	private boolean catState;
	public Integer getCatID() {
		return catID;
	}

	public void setCatID(Integer catID) {
		this.catID = catID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatImage() {
		return catImage;
	}

	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}

	public Integer getCatAge() {
		return catAge;
	}

	public void setCatAge(Integer catAge) {
		this.catAge = catAge;
	}

	public String getCatBrief() {
		return catBrief;
	}

	public void setCatBrief(String catBrief) {
		this.catBrief = catBrief;
	}

	public Integer getCatSex() {
		return catSex;
	}

	public void setCatSex(Integer catSex) {
		this.catSex = catSex;
	}

	public boolean isCatState() {
		return catState;
	}

	public void setCatState(boolean catState) {
		this.catState = catState;
	}


}