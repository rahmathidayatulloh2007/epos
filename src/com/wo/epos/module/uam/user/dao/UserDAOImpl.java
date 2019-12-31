package com.wo.epos.module.uam.user.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.bean.EncryptMd5;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;
import com.wo.epos.module.uam.user.model.User;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "userDAO")
@ViewScoped
public class UserDAOImpl extends GenericDAOHibernate<User, Long> implements UserDAO, Serializable {

	private static final long serialVersionUID = 1161408141758534022L;

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<UserVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, boolean sortOrder) throws Exception {
		Criteria criteria = getSession().createCriteria(User.class);
		decorateCriteria(criteria, searchCriteria);

		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<UserVO> voList = new ArrayList<UserVO>();
		if (criteria.list().size() > 0) {
			for (int i = 0; i < criteria.list().size(); i++) {
				User user = (User) criteria.list().get(i);
				UserVO userVo = new UserVO();

				userVo.setUserId(user.getUserId());
				userVo.setUserCode(user.getUserCode());
				userVo.setUserPassword(user.getUserPasswd());
				userVo.setFirstLoginFlag(user.getFirstLoginFlag());
				userVo.setLoginFlag(user.getLoginFlag());
				userVo.setActiveFlag(user.getActiveFlag());
				userVo.setPrevChngPasswd(user.getPrevChngPasswd());
				userVo.setPrevLogin(user.getPrevLogin());

				userVo.setCompanyId(user.getCompanyId());
				if (user.getCompany() != null) {
					userVo.setCompanyName(user.getCompany().getCompanyName());
				}

				userVo.setEmployeeId(user.getEmployeeId());
				if (user.getEmployee() != null) {
					userVo.setEmployeeName(user.getEmployee().getFullName());
					userVo.setEmployeeNo(user.getEmployee().getEmployeeNo());
				}

				userVo.setRoleId(user.getRoleId());
				if (user.getRole() != null) {
					userVo.setRoleName(user.getRole().getRoleName());
				}

				voList.add(userVo);
			}
		}

		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		Criteria criteria = getSession().createCriteria(User.class);
		decorateCriteria(criteria, searchCriteria);

		Integer results = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}

	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit, List<? extends SearchObject> searchCriteria) {
		crit.createAlias("role", "role", CriteriaSpecification.INNER_JOIN);
		crit.createAlias("company", "company", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("employee", "employee", CriteriaSpecification.INNER_JOIN);

		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
					String sSplit = searchVal.getSearchValueAsString().trim();
					crit.add(Restrictions.or(Restrictions.ilike("userCode", sSplit, MatchMode.ANYWHERE),
							Restrictions.or(Restrictions.ilike("employee.employeeNo", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("employee.fullName", sSplit, MatchMode.ANYWHERE),
											Restrictions.ilike("role.roleName", sSplit, MatchMode.ANYWHERE)))));
				}

				if (searchVal.getSearchColumn().compareTo("companyId") == 0) {
					crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
				}

				if (searchVal.getSearchColumn().compareTo("userCode") == 0) {
					crit.add(Restrictions.ilike("userCode", searchVal.getSearchValueAsString().trim(),
							MatchMode.ANYWHERE));
				}

				if (searchVal.getSearchColumn().compareTo("employeeNo") == 0) {
					crit.add(Restrictions.ilike("employee.employeeNo", searchVal.getSearchValueAsString().trim(),
							MatchMode.ANYWHERE));
				}

				if (searchVal.getSearchColumn().compareTo("employeeName") == 0) {
					crit.add(Restrictions.ilike("employee.fullName", searchVal.getSearchValueAsString().trim(),
							MatchMode.ANYWHERE));
				}

				if (searchVal.getSearchColumn().compareTo("roleId") == 0) {
					crit.add(Restrictions.eq("role.roleId", searchVal.getSearchValue()));
				}

			}
		}

		crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public UserVO getUserNamePassword(String userCode, String password) {
		Criteria criteria = getSession().createCriteria(User.class);

		criteria.add(Restrictions.eq("userCode", userCode));
		criteria.add(Restrictions.eq("userPasswd", EncryptMd5.crypt(password)));

		UserVO userVo = null;
		if (criteria.list().size() > 0) {
			for (int i = 0; i < criteria.list().size(); i++) {
				User user = (User) criteria.list().get(i);
				userVo = new UserVO();

				userVo.setUserId(user.getUserId());
				userVo.setUserCode(user.getUserCode());
				userVo.setUserPassword(user.getUserPasswd());
				userVo.setFirstLoginFlag(user.getFirstLoginFlag());
				userVo.setLoginFlag(user.getLoginFlag());
				userVo.setActiveFlag(user.getActiveFlag());
				userVo.setPrevChngPasswd(user.getPrevChngPasswd());
				userVo.setPrevLogin(user.getPrevLogin());

				userVo.setCompanyId(user.getCompanyId());
				if (user.getCompany() != null) {
					userVo.setCompanyName(user.getCompany().getCompanyName());
				}

				userVo.setEmployeeId(user.getEmployeeId());
				if (user.getEmployee() != null) {
					userVo.setEmployeeName(user.getEmployee().getFullName());
					userVo.setEmployeeNo(user.getEmployee().getEmployeeNo());
					OutletEmpVO outletEmpVO = dataOutletEmp(user.getEmployeeId());
					if (outletEmpVO != null) {
						userVo.setOutletId(outletEmpVO.getOutletId());
						userVo.setOutletName(outletEmpVO.getOutletName());
						userVo.setPicFlag(outletEmpVO.getPicFlag());
					}
				}

				userVo.setRoleId(user.getRoleId());
				if (user.getRole() != null) {
					userVo.setRoleName(user.getRole().getRoleName());
				}

			}
		}

		return userVo;
	}

	private OutletEmpVO dataOutletEmp(Long employeeId) {
		OutletEmpVO outletEmpVO = null;

		Criteria criteria = getSession().createCriteria(OutletEmp.class);
		criteria.createAlias("employee", "employee", CriteriaSpecification.INNER_JOIN);

		criteria.add(Restrictions.eq("employee.employeeId", employeeId));

		if (criteria.list().size() > 0) {
			for (int i = 0; i < criteria.list().size(); i++) {
				OutletEmp outletEmp = (OutletEmp) criteria.list().get(0);
				outletEmpVO = new OutletEmpVO();

				outletEmpVO.setEmployeeId(outletEmp.getEmployeeId());
				outletEmpVO.setOutletEmpId(outletEmp.getOutletEmpId());
				outletEmpVO.setOutletId(outletEmp.getOutletId());
				if (outletEmp.getOutlet() != null) {
					outletEmpVO.setOutletName(outletEmp.getOutlet().getOutletName());
				}

				outletEmpVO.setPicFlag(outletEmp.getPicFlag());
			}

		}

		return outletEmpVO;
	}

	@Override
	public UserVO dataUserEmp(Long employeeId) {
		// TODO Auto-generated method stub
		UserVO userVO = null;

		Criteria criteria = getSession().createCriteria(User.class);
		criteria.createAlias("employee", "employee", CriteriaSpecification.INNER_JOIN);

		criteria.add(Restrictions.eq("employee.employeeId", employeeId));
		if (criteria.list().size() > 0) {
			for (int i = 0; i < criteria.list().size(); i++) {
				User user = (User) criteria.list().get(0);
				userVO = new UserVO();
				
				userVO.setUserId(user.getUserId());
				userVO.setRoleId(user.getRoleId());
				userVO.setEmployeeId(user.getEmployeeId());
				userVO.setRoleName(user.getRole().getRoleName());
			}
		}

		return userVO;
	}

}
