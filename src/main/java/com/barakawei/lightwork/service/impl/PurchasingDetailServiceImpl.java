package com.barakawei.lightwork.service.impl;

import com.barakawei.lightwork.dao.PurchasingDetailDao;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.service.PurchasingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
