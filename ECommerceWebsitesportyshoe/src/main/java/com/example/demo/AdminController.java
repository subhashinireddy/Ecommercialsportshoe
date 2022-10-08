package com.example.demo;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AdminController {
	Logger log=Logger.getAnonymousLogger();
			@Autowired ProductDAO dao;
			
			
			
	@RequestMapping("/")
	public ModelAndView loadfrontpage(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	log.info("in request mapping /");
	mv.setViewName("index.jsp");
	log.info("loaded index page");
	return mv;
	}
	@RequestMapping("/admin")
	public ModelAndView loadfadminpage(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	log.info("in request mapping /admin");
	List<Product> list=dao.getAll(); 
	mv.setViewName("displayproduct.jsp");
	mv.addObject("list",list);
	log.info("\"displayproduct.jsp");
	return mv;
}
	
	@RequestMapping("/add")
	public ModelAndView loadfaddpage(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	log.info("in request mapping /add");
	mv.setViewName("insert.jsp");
		return mv;
	}
		
	@RequestMapping("/insert")
	public ModelAndView insert(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	Product p=new Product();
	p.setName(request.getParameter("name"));
	p.setCost(Double.parseDouble(request.getParameter("cost")));
	Product ss=dao.insert(p);
	if(ss!=null) {
	mv.setViewName("display.jsp");
	}
	else {
		mv.setViewName("fail");
	}
	return mv;
	}

	@RequestMapping("/getall")
	public ModelAndView getall(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	List<Product> list=dao.getAll();
	mv.setViewName("displayproduct.jsp");
	mv.addObject("list", list);
	return mv;
	}
	@RequestMapping("/edit")
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	System.out.println("inside edit");
	Product p=new Product();
	int newId=Integer.parseInt(request.getParameter("id"));
	p.setId(newId);
	p.setName(request.getParameter("name"));
	p.setCost(Double.parseDouble(request.getParameter("cost")));
	dao.updatebyname(p);
	List<Product> list=dao.getAll();
	mv.setViewName("displayproduct.jsp");
	mv.addObject("list", list);
	return mv;

	}
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=new ModelAndView();
		System.out.println("inside edit");
		Product p=new Product();
		int deleteId=Integer.parseInt(request.getParameter("id"));
		dao.delete(deleteId);
		List<Product> listAfterDelete=dao.getAll();
		mv.setViewName("displaysproduct.jsp");
		mv.addObject("list", listAfterDelete);
		return mv;
	}
	@RequestMapping("/user")
	public ModelAndView loadfuserpage(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	log.info("in request mapping /user");
	List<Product> list=dao.getAll(); 
	mv.setViewName("display.jsp");
	log.info("load user page");
	return mv;
	
	}
	@RequestMapping("/login")
	public ModelAndView loginaction(HttpServletRequest request,HttpServletResponse response) {
	ModelAndView mv=new ModelAndView();
	log.info("inside the request mapping /login ");
	String user=request.getParameter("user");
	String password=request.getParameter("password");
	log.info("recieved the data of user and password");
		if((repo.findByUser(user)!=null)&&(repo.findByPassword(password)!=null)) {
				log.info(repo.findByUser(user)+"   "+repo.findByPassword(password));
         Optional<Product> obj1=(repo.findByUser(user));
         Optional<Product> obj2=(repo.findByPassword(password));
         if(obj1.isPresent()&&obj2.isPresent()) {
        if(obj1.equals(obj2)) {
        log.info("login is success");
        mv.setViewName("display.jsp");
        mv.addObject("userid",user);
        log.info("control passed to display.jsp");
    }
    else {
        mv.setViewName("fail.jsp");
        log.info("control passed to fail.jsp");
    }
         }
         else {
                mv.setViewName("fail.jsp");
                log.info("control passed to fail.jsp");
            }
         
    }else {
        mv.setViewName("fail.jsp");
        log.info("control passed to fail.jsp");
    }
}
}
	




