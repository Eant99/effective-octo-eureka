package com.googosoft.rybxx;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import junitbase.BaseJunit4Test;

public class rybTest extends BaseJunit4Test {
	
    //自动注入,默认按名称	
  //@Resource
    //标明是测试方法
	@Test
    //标明此方法需使用事务
  //@Transactional
    //标明使用完此方法后事务不回滚,true时为回滚
  //@Rollback(false)    
	public void selectTest()
	{ 
		System.out.println("222222");          
	}  
}
