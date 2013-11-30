package org.esblink.test;


import java.util.LinkedHashMap;
import java.util.List;

import org.esblink.module.basedata.biz.ICodeTableBiz;
import org.esblink.module.basedata.dao.ICodeTableDao;
import org.esblink.module.basedata.domain.CodeTable;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


/**
 * spring单元测试
 * 用于生成renderer需要的数据集
 * @author chenjh
 *
 */
public class testCodeTable {
	private static WebApplicationContext applicationContext;
	private static final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	 
	@BeforeClass
	public static void setUp() throws Exception {
		  helper.setUp();
		if(applicationContext==null){
			MockServletContext sc = new MockServletContext();	
			ContextLoader contextLoader = new ContextLoader();
//			sc.setContextPath("/beans.xml");
			sc.addInitParameter("contextConfigLocation", "beans.xml");
			contextLoader.initWebApplicationContext(sc);
			applicationContext=contextLoader.getCurrentWebApplicationContext();
		}	
//		DbInfo.showDbInfo();
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
//		System.exit(0);
		 helper.tearDown();
	}
	
	public WebApplicationContext getAppContext(){
		return applicationContext;
	}
	
	
	@Test
	public void testRun(){
		this.testSave();
		this.testFind();
		this.testAll();
		testDao();
	}
	
	
	@Test
	public void testFind(){
		System.out.println("-----testFind---");
		Object obj = this.getAppContext().getBean("codeTableBiz");
		ICodeTableBiz biz = (ICodeTableBiz)obj;
		CodeTable ct = biz.findCodeTable(1l);
		System.out.println("------ct="+ct);
		if(ct!=null){
			System.out.println(ct.getId()+" name="+ct.getCname());
		}
	}
	
	/**
	 * codeTableId重复性检查
	 */
	@Test
	public void testAll(){
		System.out.println("-----testAll---");
		Object obj = this.getAppContext().getBean("codeTableBiz");
		
		ICodeTableBiz biz = (ICodeTableBiz)obj;
		List<CodeTable> infoList = biz.codeTableAll();
		
		System.out.println("codeTableId  count="+infoList.size());
		
		
		
	}
	
	/**
	 * codeTableId重复性检查
	 */
	@Test
	public void testDao(){
		System.out.println("-----testDao---");
		Object obj = this.getAppContext().getBean("codeTableDao");
		
		ICodeTableDao dao = (ICodeTableDao)obj;
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String wherejql="code=?1";
		Object[] params=new Object[1];
		params[0]="test";
		List<CodeTable> infoList = dao.getScrollData(CodeTable.class, 1, 100, wherejql, params, orderby).getResultlist();
		
		System.out.println("codeTableId  count="+infoList.size());
		
		
		
	}
	
	@Test
	public void testSave(){
		System.out.println("-----testSave---");
		Object obj = this.getAppContext().getBean("codeTableBiz");
		ICodeTableBiz biz = (ICodeTableBiz)obj;
		CodeTable ct = new CodeTable();
		ct.setEname("test");
		ct.setCname("测试");
		ct.setCode("test");
		ct.setIfDel(0l);
		ct.setStatus(1l);
		ct.setCodeType("test");
		biz.saveCodeTable(ct);
		System.out.println(ct.getId());
		
		 ct = new CodeTable();
			ct.setEname("test2");
			ct.setCname("测试2");
			ct.setCode("test2");
			ct.setIfDel(0l);
			ct.setStatus(1l);
			ct.setCodeType("test");
			biz.saveCodeTable(ct);
			System.out.println(ct.getId());
	}
}
