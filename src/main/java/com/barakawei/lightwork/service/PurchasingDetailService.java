package com.barakawei.lightwork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.domain.PurchasingDetail;
/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Transactional
public interface PurchasingDetailService {

	
	void cretePurchasingDetail(PurchasingDetail purchasingDetail);


	PurchasingDetail findPurchasingDetailById(String id);


	void updatePurchasingDetail(PurchasingDetail purchasingDetail);


	void deletePurchasingDetailById(String id);
	
	
	Page<PurchasingDetail> findPurchasingDetails(Pageable pageable);

}
