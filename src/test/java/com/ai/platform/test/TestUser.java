package com.ai.platform.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.platform.common.config.Global;
import com.ai.platform.modules.sys.dao.OfficeDao;
import com.ai.platform.modules.sys.dao.UserDao;
import com.ai.platform.modules.sys.entity.Office;
import com.ai.platform.modules.sys.entity.User;
import com.ai.platform.modules.sys.service.SystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class TestUser {
	@Autowired
	UserDao userDao;
	@Autowired
	OfficeDao officeDao;
	

	@org.junit.Test
	public void run() {
		try {
			String encoding = "UTF-8";
			File file = new File("/Users/meteor/Downloads/ftp/user.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int i=0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					++i;
					
						String[] userInfo = lineTxt.split("\\t");
						
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
	public class Inner { 
        public void print(String[] userInfo,int i) { 
        	try {
        	User user = new User();
			user.setNo(userInfo[0]);
			if (!userInfo[1].isEmpty() && userInfo[1].length() >= 1) {
				user.setLoginName(userInfo[1]);
				user.setPassword(SystemService.entryptPassword(Global.getPasswordRule()));
			}
			user.setName(userInfo[2]);
			user.setEmail(userInfo[3]);
			user.setMobile(userInfo[4]);

			user.setLoginFlag("1");

			// 验证公司编码
			if (!userInfo[5].isEmpty()) {
				Office company = new Office();
				company.setCode(userInfo[5]);
				List<Office> companyList = officeDao.find(company);
				if (!companyList.isEmpty()) {
					company = companyList.get(0);
					user.setCompany(company);
				}
			}
			// 验证部门编码
			if (!userInfo[6].isEmpty()) {
				Office office = new Office();
				office.setCode(userInfo[6]);
				List<Office> officeList = officeDao.find(office);
				if (!officeList.isEmpty()) {
					office = officeList.get(0);
					user.setOffice(office);
				}
			}
			user.setDelFlag(userInfo[7]);
			
			user.setId(362092+i+"");
			
			user.setCreateDate(new Date());
			user.setUpdateDate(new Date());
			user.setTenantId(Global.getTenantID());

			User findUser = userDao.getByNo(userInfo[0]);
			if (findUser != null) {
				findUser.setNo(userInfo[0]);
				findUser.setLoginName(userInfo[1]);
				findUser.setName(userInfo[2]);
				findUser.setEmail(userInfo[3]);
				findUser.setMobile(userInfo[4]);
				// 验证公司编码
				findUser.setCompany(user.getCompany());
				findUser.setOffice(user.getOffice());
				findUser.setDelFlag(userInfo[7]);
				userDao.saveImportUser(findUser);
			} else {
				userDao.saveImportUser(user);
			}
			System.out.println("NO:" + userInfo[0] + ",Name:" + userInfo[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
        } 
    } 
}
