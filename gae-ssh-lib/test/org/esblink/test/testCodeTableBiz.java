package org.esblink.test;

import java.util.List;

import org.esblink.module.basedata.biz.ICodeTableBiz;
import org.esblink.module.basedata.biz.impl.CodeTableBizImpl;
import org.esblink.module.basedata.domain.CodeTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class testCodeTableBiz {
	private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	private ICodeTableBiz codeTableBiz;
	@Before
    public void setUp() {
        helper.setUp();
        this.codeTableBiz = new CodeTableBizImpl();
    }
 
   
    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void testInfo(){
    	System.out.println("----------aaa");
    }
    
    @Test
    public void testSave(){
    	CodeTable ct = new CodeTable();
    	ct.setCode("test");
    	ct.setCodeType("test");
    	ct.setIfDel(0l);
    	ct.setStatus(1l);
    	ct.setCname("test2");
    	ct.setDisplay(1l);
    	ct.setEname("test2");
    	this.codeTableBiz.saveCodeTable(ct);
    }
    
    @Test
    public void testCodeTableAll(){
    	List list=this.codeTableBiz.codeTableAll();
    	System.out.println("----------"+list.size());
    }

}
