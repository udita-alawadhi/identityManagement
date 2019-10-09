package com.cg.ibs.im.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;

public class CustomerDaoImpl implements CustomerDao {

	private static Map<String, CustomerBean> customerDao = new HashMap<String, CustomerBean>();

	@Override
	public boolean saveCustomer(CustomerBean customer) throws IBSCustomException {
		boolean result = false;
		if (customer != null) {
			customerDao.put(customer.getUci(), customer);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.customerNotPresent);
		}
		return result;
	}

	@Override
	public CustomerBean getCustomerDetails(String uci) throws IBSCustomException {
		CustomerBean newCustomer = null;
		for (Entry<String, CustomerBean> entry : customerDao.entrySet()) {
			if (entry.getKey().equals(uci)) {
				newCustomer = entry.getValue();
				break;
			}
		}
		if (newCustomer == null) {
			throw new IBSCustomException(IBSException.applicantNotFound);
		}
		return newCustomer;
	}

	@Override
	public Set<String> getAllCustomers() {
		return new TreeSet<String>(customerDao.keySet());
	}

	public CustomerBean getCustomerByApplicantId(long applicantId) {
		CustomerBean newCustomer = new CustomerBean();
		for (Entry<String, CustomerBean> entry : customerDao.entrySet()) {
			long appId = entry.getValue().getApplicant().getApplicantId();
			if (appId == applicantId) {
				newCustomer = entry.getValue();
				break;
			}
		}
		return newCustomer;
	}

}