package com.googosoft.controller.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import com.googosoft.util.Validate;
/**
 * String类型转Date类型转换器
 * @author master
 */
public class CustomDateConverter implements Converter<String, Date>{
	
	public Date convert(String source) {
		try {
			if(Validate.noNull(source)){
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				return simpleDateFormat.parse(source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
