package com.chentu.mika.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.form.CatForm;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.CatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @description:与猫相关的API请求
 */
@RestController
public class CatController {

	@Value("${prop.upload-folder}")
	private String UPLOAD_FOLDER;


	@Autowired
	CatService catService;
	
	@PostMapping("/cats")
	@ResponseBody
	public Result add(@RequestParam("catImage") MultipartFile image,
					  @RequestParam("catName") String name,
					  @RequestParam("catAge") Integer age,
					  @RequestParam("catBrief") String brief,
					  @RequestParam("catSex") Boolean sex,
					  @RequestParam("catState") Boolean state) throws FileNotFoundException {
		
		Cat cat = new Cat();
		int index= Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf(".");
		String suffix=image.getOriginalFilename().substring(index);//获取后缀名
		//使用随机生成的UUID做文件名
		String fileName = UUID.randomUUID().toString().replace("-","")+suffix;
		cat.setCatImage(fileName);//数据库里存文件名
		cat.setCatName(name);
		cat.setCatAge(age);
		cat.setCatBrief(brief);
		cat.setCatSex(sex?1:0);
		cat.setCatState(state?1:0);
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(
							UPLOAD_FOLDER+fileName)));
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
		catService.save(cat);
		return Result.success(null);
	}
	
	@DeleteMapping("/cats/{catIDs}")
	public Result del(@PathVariable("catIDs") String catIDs) {
		String[] split = catIDs.split(",");
		List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
		for (Integer i : list) {
			File file = new File(UPLOAD_FOLDER
					+catService.getOne(
							Wrappers.<Cat>lambdaQuery()
									.eq(Cat::getCatID, i)
			).getCatImage());
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
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
	@ResponseBody
	public Result update(@RequestParam("catImage") MultipartFile image,
						 @RequestParam("catName") String name,
						 @RequestParam("catAge") Integer age,
						 @RequestParam("catBrief") String brief,
						 @RequestParam("catSex") Boolean sex,
						 @RequestParam("catState") Boolean state,
						 @RequestParam("catID") Integer ID) throws FileNotFoundException {
		File file = new File(UPLOAD_FOLDER
				+catService
				.getOne(Wrappers.<Cat>lambdaQuery()
				.eq(Cat::getCatID, ID)).getCatImage());
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		int index= Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf(".");
		String suffix=image.getOriginalFilename().substring(index);//获取后缀名
		//使用随机生成的UUID做文件名
		String fileName = UUID.randomUUID().toString().replace("-","")+suffix;
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(
							UPLOAD_FOLDER+fileName)));
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
		catService.update(Wrappers.<Cat>lambdaUpdate()
				.eq(Cat::getCatID, ID)
				.set(name!=null,Cat::getCatName, name)
				.set(image!=null,Cat::getCatImage, fileName)
				.set(age!=null,Cat::getCatAge, age)
				.set(brief!=null,Cat::getCatBrief, brief)
				.set(sex!=null,Cat::getCatSex, sex?1:0)
				.set(state!=null,Cat::getCatState, state?1:0)
		);
		return Result.success(null);
	}
	
	@GetMapping("/cats")
	public Result list() {
		List<Cat> catList = catService.list();
		List<CatRequest> reqList = new ArrayList<>();
		for (Cat cat : catList) {
			CatRequest catRequest = new CatRequest();
			if(cat.getCatState()==1){
				catRequest.setCatState(true);
			}
			else{
				catRequest.setCatState(false);
			}
			if (cat.getCatSex() == 1) {
				catRequest.setCatSex(true);
			}
			else {
				catRequest.setCatSex(false);
			}
			catRequest.setCatAge(cat.getCatAge());
			catRequest.setCatName(cat.getCatName());
			catRequest.setCatBrief(cat.getCatBrief());
			catRequest.setCatID(cat.getCatID());
			catRequest.setCatImage(cat.getCatImage());
			reqList.add(catRequest);
		}
		return Result.success(reqList);
	}
}




class CatRequest {
	private Integer catID;

	private String catName;

	private String catImage;

	private Integer catAge;

	private String catBrief;

	private boolean catSex;

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

	public boolean isCatSex() {
		return catSex;
	}

	public void setCatSex(boolean catSex) {
		this.catSex = catSex;
	}

	public void setCatBrief(String catBrief) {
		this.catBrief = catBrief;
	}

	public boolean isCatState() {
		return catState;
	}

	public void setCatState(boolean catState) {
		this.catState = catState;
	}


}