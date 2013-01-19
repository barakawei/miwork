package com.barakawei.lightwork.service;

import com.barakawei.lightwork.dao.GoodsDao;
import com.barakawei.lightwork.domain.Goods;
import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.domain.User;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
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
    public void testSync(){
        userService.synAllUserAndRoleToActiviti();
    }
    @Test
    public void testStartWorkflow(){
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
