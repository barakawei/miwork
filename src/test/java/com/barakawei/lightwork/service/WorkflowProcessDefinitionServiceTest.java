package com.barakawei.lightwork.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class WorkflowProcessDefinitionServiceTest{

  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  protected RuntimeService runtimeService;

  @Autowired
  protected RepositoryService repositoryService;

  @Autowired
  protected HistoryService historyService;

  @Autowired
  protected WorkflowProcessDefinitionService wpdService;

  @Before
  public void prepareTestData() {
    logger.info("before");
  }

  @Test
  public void testDeploySingleProcess() throws Exception{
		ResourceLoader resourceLoader = new DefaultResourceLoader();
        wpdService.deploySingleProcess(resourceLoader,"purchasing");
  }

  @After
  public void after() {
    logger.info("after");
  }
}
