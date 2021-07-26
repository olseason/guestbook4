package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Controller
public class GuestController {

	// 필드
	@Autowired
	private GuestbookDao guestbookDao;

	// 생성자

	// 메소드 - GS

	// 메소드 - 일반

	/*** addList ***/
	@RequestMapping(value = "/addList", method = { RequestMethod.GET, RequestMethod.POST })
	public String addList(Model model) {

		// 리스트 가져오기
		List<GuestbookVo> gList = guestbookDao.getGuestbookList();

		// model에 담기
		model.addAttribute("gList", gList);

		// addList.jsp 포워드
		return "/WEB-INF/views/addList.jsp";
	}

	/*** add ***/
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestbookVo guestbookVo) {

		// insert() 메소드 사용
		guestbookDao.insert(guestbookVo);

		// 리다이렉트
		return "redirect:addList";
	}

	/*** deleteForm ***/
	@RequestMapping(value = "/deleteForm/{no}", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm(@PathVariable("no") int no, Model model) {

		// model에 담기
		model.addAttribute("no", no);

		// deleteForm.jsp 포워드
		return "/WEB-INF/views/deleteForm.jsp";
	}

	/*** delete ***/
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@RequestParam("pw") String pw, @RequestParam("no") int no) {

		// delete() 메소드 사용
		guestbookDao.delete(no, pw);

		// 리다이렉트
		return "redirect:/addList";
	}

}
