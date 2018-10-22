package com.googosoft.controller.base;

import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/editor")
public class StencilsetRestResource {
	
	 @RequestMapping(value="/stencilset")
	  public @ResponseBody ObjectNode getStencilset() {
		ObjectMapper objectMapper = new ObjectMapper();
	    InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("spring/stencilset.json");
	    try {
			 return objectMapper.readValue(IOUtils.toString(stencilsetStream, "utf-8"), ObjectNode.class);
	    } catch (Exception e) {
	      throw new ActivitiException("Error while loading stencil set", e);
	    }
	  }
}
