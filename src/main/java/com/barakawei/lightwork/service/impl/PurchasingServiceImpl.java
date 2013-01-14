package com.barakawei.lightwork.service.impl;

import com.barakawei.lightwork.dao.PurchasingDetailDao;
import com.barakawei.lightwork.dao.UserDao;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.domain.SearchForm;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.barakawei.lightwork.dao.PurchasingDao;
import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.service.PurchasingService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qianxiaowei
 * @date 2013-1-9
 */

@Service
public class PurchasingServiceImpl implements PurchasingService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private PurchasingDao purchasingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PurchasingDetailDao purchasingDetailDao;

	
	
	@Override
	public void createPurchasing(Purchasing purchasing) {
        purchasing.setPlanningUser(userDao.findOne("3"));
        purchasing.setStartTime(new Date());
        purchasingDao.save(purchasing);
        for(PurchasingDetail pd: purchasing.getPds()){
            String businessKey = pd.getId();
            identityService.setAuthenticatedUserId(purchasing.getPlanningUser().getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("purchasing-flow", businessKey);
            String processInstanceId = processInstance.getId();
            pd.setProcessInstanceId(processInstanceId);
            pd.setPurchasing(purchasing);
            purchasingDetailDao.save(pd);
        }
	}

	/* (non-Javadoc)
	 * @see com.barakawei.lightwork.service.PurchasingService#findPurchasingById(java.lang.String)
	 */
	@Override
	public Purchasing findPurchasingById(String id) {
		return purchasingDao.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.barakawei.lightwork.service.PurchasingService#updatePurchasing(com.barakawei.lightwork.domain.Purchasing)
	 */
	@Override
	public void updatePurchasing(Purchasing purchasing) {
        purchasingDao.save(purchasing);

	}

	/* (non-Javadoc)
	 * @see com.barakawei.lightwork.service.PurchasingService#deletePurchasingById(java.lang.String)
	 */
	@Override
	public void deletePurchasingById(String id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.barakawei.lightwork.service.PurchasingService#findPurchasings(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Purchasing> findPurchasings(SearchForm sf) {
        Pageable pageable = new PageRequest(sf.getPageNum(),sf.getNumPerPage());
        return purchasingDao.findAll(getWhereClause(sf),pageable);
	}

    private Specification<Purchasing> getWhereClause(final SearchForm sf) {
        return new Specification<Purchasing>() {
            @Override
            public Predicate toPredicate(Root<Purchasing> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Map<String,String> search = sf.getSearchMap();
                for(String key:search.keySet()){
                    String value = search.get(key);
                    if (StringUtils.isNotBlank(value)) {
                        predicate.getExpressions().add(cb.like(root.<String>get(key), "%" + value + "%"));

                    }
                }
                query.where(predicate);
                return null;
            }
        };
    }
}
