package com.coffeereview.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

/**
* @packageName	: com.coffeereview.domain
* @fileName		: UserVO.java
* @author		: Goonoo Jang
* @date			: 2020.11.21
* @description	:
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2020.11.21        Goonoo Jang       최초 생성
* 2020.11.29		Goonoo Jang		  password, authList 추가
* 2020.12.01		Goonoo Jang		  enabled 추가
*/
@Data // Lombok을 이용하여 생성자, getter/setter, toString()등을 만들어내는 Annotation
public class UserVO {
	
	private String uid;
	private String password;
	private String name;
	private String nickname;
	private String age;
	private Character gender;
	private String phonenum;
	private String email;
	private String address;
	private boolean enabled;
	
	private Date regdate;
	private List<AuthVO> authList;

}
