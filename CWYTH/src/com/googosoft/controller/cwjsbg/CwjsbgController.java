package com.googosoft.controller.cwjsbg;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.googosoft.service.cwjsbg.CwjsbgService;

@Controller
@RequestMapping(value = "/cwjs")
public class CwjsbgController {
//	@Resource(name="cwjsbgservice")
//	private CwjsbgService cwjsbgservice;
	
	@RequestMapping("/jsbglist")
	public String jsbglist() {
		return "cwjs/jsbglist";
	}
	@RequestMapping("/cwbglist")
	public String cwbglist() {
		return "cwjs/cwbglist";
	}
	
}
