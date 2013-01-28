package com.barakawei.lightwork.service;

import com.barakawei.lightwork.dao.GoodsDao;
import com.barakawei.lightwork.domain.Goods;
import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.domain.User;
import nl.bstoi.poiparser.core.ReadPoiParserException;
import nl.bstoi.poiparser.core.RequiredFieldPoiParserException;
import nl.bstoi.poiparser.core.strategy.annotation.AnnotatedPoiFileParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PurchasingWorkflowServiceTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected PurchasingWorkflowService pwService;

    @Autowired
    protected UserService userService;
    @Autowired
    protected GoodsDao goodsDao;

    @Before
    public void prepareTestData() {
        logger.info("before");
    }

    @Test
    public void testSync() {
        userService.synAllUserAndRoleToActiviti();
    }

    @Test
    public void testPoi() {
        // Initialize the AnnotatedPoiFileParser
        AnnotatedPoiFileParser<Goods> annotationExcelParser = new AnnotatedPoiFileParser<Goods>();
// Load excel fil
        File excelFile = new File("/Users/baraka/Downloads/plan.xls");
// Read the excel, with sheet name "Sheet3"
        List<Goods> testRows = null;
        try {
            testRows = annotationExcelParser.readExcelFile(excelFile, "Sheet1 - 1", Goods.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RequiredFieldPoiParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReadPoiParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("11");
    }

    @Test
    public void testStartWorkflow() {
//        User user = userService.findUserById("1");
//        List<PurchasingDetail> pds = new ArrayList<PurchasingDetail>();
//        Purchasing purchasing = new Purchasing();
//        PurchasingDetail pd = new PurchasingDetail();
//        pd.setStartTime(new Date());
//        pd.setUser(user);
//        pd.setId("1");
//        Goods goods = new Goods();
//        goods.setColor("red");
//        goodsDao.save(goods);
//        pd.setGoods(goods);
//        pds.add(pd);
//        purchasing.setId("1");
//        purchasing.setStartTime(new Date());
//        purchasing.setPds(pds);
//        pwService.startWorkflow(purchasing);
    }

    @After
    public void after() {
        logger.info("after");
    }

}
