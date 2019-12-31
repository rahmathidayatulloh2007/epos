package com.wo.epos.common.dao;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@ManagedBean(name="configSessionFactory")
@ApplicationScoped
public class SessionFactoryConfigBased {
	private SessionFactory sessionFactory;
	
	public SessionFactoryConfigBased() {
		sessionFactory = new Configuration().configure()
			.buildSessionFactory();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
