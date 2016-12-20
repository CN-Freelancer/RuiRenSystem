package com.ai.platform.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.exception.RPCSystemException;
import com.ai.platform.modules.sys.dao.UserDao;
import com.ai.platform.modules.sys.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
//@ContextConfiguration({ "/dubbo/provider/dubbo-provider.xml" })
public class Test {

	@Autowired
	UserDao areaDao;
	
	//@Ignore
	@org.junit.Test
    public void testSSO() throws RPCSystemException{
		User ser = areaDao.getByNo("20043382");
		while(true){
			
		}
    }
	
	
}
