package com.coffeereview.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coffeereview.domain.Criteria;
import com.coffeereview.domain.MenuVO;
import com.coffeereview.domain.PageDTO;
import com.coffeereview.service.MenuService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
* @packageName   : com.coffeereview.controller
* @fileName      : MenuController.java
* @author        : SeongPyo Jo
* @date          : 2020.10.29
* @description   : Cafe 기능과 관련된 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2020.10.29        SeongPyo Jo       최초 생성
* 2020.11.10        SeongPyo Jo       카페 메뉴 조회를 위한 getCafeMenu 추가
* 2020.11.10        SeongPyo Jo       클래스 및 메쏘드 이름 변경(cafe -> menu)
* 2020.11.23        SeongPyo Jo       메뉴 이미지 출력을 위한 getFile 메쏘드 추가
* 2020.11.23        SeongPyo Jo       getFile 메쏘드 파라미터 이름 변경 (fileName -> menuName)
* 2020.11.23        SeongPyo Jo       getCafeMenu 모델 파라미터 이름 변경 (menu -> menuInfo)
* 2020.11.23        SeongPyo Jo       getMenuList 파라미터 추가 (cafe)
* 2020.11.23        SeongPyo Jo       getMenuList 페이징 처리 메쏘드로 변환
* 2020.11.23        SeongPyo Jo       getCafeMenu -> getMenuInfo으로 변경 (파리미터 변경 포함)
* 2020.11.23        SeongPyo Jo       getMenuList, getMenuInfo 방식 변경으로 인한 파라미터에 Criteria 추가
* 2020.11.23        SeongPyo Jo       getMenuList 메쏘드에 각 카페별 메뉴의 총 개수를 구하는 기능 추가
* 2020.11.27        SeongPyo Jo		  list와 info에서 검색 기능을 사용하기 위해 getMenuList에 ModelAttribute 추가
* 2020.11.29        SeongPyo Jo       list와 info에서 amount를 컨트롤러에서 설정하도록 변경
* 2020.12.29        SeongPyo Jo       REST 방식으로 변환 (getMenuList, getMenuInfo)
* 2021.01.09        SeongPyo Jo       REST 방식 주석 처리 (getMenuList, getMenuInfo)
*/

@Controller
//@RestController
@Log4j
@RequestMapping("/menu/*")
@AllArgsConstructor
public class MenuController {
	
	private MenuService service;
	
	/*
	@GetMapping("/list")
	public void getMenuList(@RequestParam("cafe") String cafe, Model model) {
		
		log.info("/list");
		model.addAttribute("list", service.getMenuList(cafe));
		
	}
	*/
	
	@GetMapping("/list")
	public void getMenuList(@ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("list: " + cri);
		
		// 페이지당 출력 개수
		cri.setAmount(12);
		model.addAttribute("list", service.getMenuList(cri));
		//model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		
	}
	
	/*
	@GetMapping(value="/list/{cafe}/{pageNum}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> getMenuList(@PathVariable("cafe") String cafe, @PathVariable("pageNum") int pageNum, @ModelAttribute("cri") Criteria cri) {
		
		Map<String, Object> map = new HashMap<>();
		
		log.info("list: " + cri);
		
		// 페이지당 출력 개수
		cri.setAmount(12);
		
		map.put("list", service.getMenuList(cri));
		
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		map.put("pageMaker", new PageDTO(cri, total));
		
		return new ResponseEntity<>(map, HttpStatus.OK);
		
	}
	*/
	
	@GetMapping("/info")
	public void getMenuInfo(@RequestParam("mno") Long mno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/info");
		
		// 페이지당 출력 개수
		cri.setAmount(12);
		model.addAttribute("menuInfo", service.getMenu(mno));
		
	}
	
	/*
	@GetMapping(value="/info/{cafe}/{mno}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MenuVO> getMenuInfo(@PathVariable("cafe") String cafe, @PathVariable("mno") Long mno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/info");
		
		// 페이지당 출력 개수
		cri.setAmount(12);

		return new ResponseEntity<>(service.getMenu(mno), HttpStatus.OK);
		
	}
	*/
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String menuName) {
		
		log.info("fileName : " + menuName);
		
		File file = new File("c:\\upload\\" + menuName + ".jpg");
		
		log.info("file: " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
}
