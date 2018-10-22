package junitbase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//使用junit4进行测试  
@RunWith(SpringJUnit4ClassRunner.class) 
//加载配置文件
@ContextConfiguration({"classpath:spring/spring-mvc.xml","classpath:spring/spring-context*.xml"})   

//不加入这个注解配置，事务失效,也可在测试类加入。
//@Transactional  
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
public class BaseJunit4Test {

}
