package com.barakawei.lightwork.service.impl;

import com.barakawei.lightwork.dao.PurchasingDao;
import com.barakawei.lightwork.dao.PurchasingDetailDao;
import com.barakawei.lightwork.dao.UserDao;
import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.domain.SearchForm;
import com.barakawei.lightwork.service.PurchasingDetailService;
import com.barakawei.lightwork.service.PurchasingService;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qianxiaowei
 * @date 2013-1-9
 */

@Service
public class PurchasingDetailServiceImpl implements PurchasingDetailService {

    @Autowired
    private PurchasingDetailDao purchasingDetailDao;


    @Override
    public void cretePurchasingDetail(PurchasingDetail purchasingDetail) {
        purchasingDetailDao.save(purchasingDetail);
    }

    @Override
    public PurchasingDetail findPurchasingDetailById(String id) {

        return purchasingDetailDao.findOne(id);
    }

    @Override
    public void updatePurchasingDetail(PurchasingDetail purchasingDetail) {
        purchasingDetailDao.save(purchasingDetail);
    }

    @Override
    public void deletePurchasingDetailById(String id) {
        purchasingDetailDao.delete(id);
    }

    @Override
    public Page<PurchasingDetail> findPurchasingDetails(Pageable pageable) {
        return purchasingDetailDao.findAll(pageable);
    }
}
