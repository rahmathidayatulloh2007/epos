package com.wo.epos.common.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

import com.wo.epos.common.dao.SessionFactoryConfigBased;

//assume this filter get called after authorization filter.
public class HibernateRequestFilter implements Filter {
	private static Logger log = Logger.getLogger(HibernateRequestFilter.class);
	private FilterConfig filterConfig;

	@Override
	public void destroy() {
		log.info("Destroying filter...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// HttpSession httpSession = ((HttpServletRequest)
		// request).getSession(false);
		SessionFactory sessionFactory = null;

		ServletContext context = filterConfig.getServletContext();
		SessionFactoryConfigBased configSessionFactory = (SessionFactoryConfigBased) context
				.getAttribute("configSessionFactory");

		if (configSessionFactory == null) {
			configSessionFactory = new SessionFactoryConfigBased();
			context.setAttribute("configSessionFactory", configSessionFactory);
		}

		try {
			// if (httpSession != null)
			// {
			log.debug("Starting a database transaction");

			sessionFactory = configSessionFactory.getSessionFactory();
			sessionFactory.getCurrentSession().setFlushMode(FlushMode.MANUAL);
			sessionFactory.getCurrentSession().beginTransaction();
			// hack to avoid NPE while entity has mapping with natural id not
			// PK. Menu.hbm has this filter def.
			//sessionFactory.getCurrentSession().enableFilter("myFilter");

			// Call the next filter (continue request processing)
			chain.doFilter(request, response);

			// Commit and cleanup
			if (sessionFactory != null
					&& sessionFactory.getCurrentSession().getTransaction()
							.isActive()) {
				log.debug("Committing the database transaction");
				sessionFactory.getCurrentSession().getTransaction().commit();
			}

			// }
			// else
			// {
			// allow no session, let accessControlListener handle auth
			// log.error("Session Time Out!");
			// request.getRequestDispatcher("/include/sessionTimeOut.jsp").forward(request,
			// response);
			// }
		} catch (StaleObjectStateException staleEx) {
			log.error("This interceptor does not implement optimistic concurrency control!");
			log.error("Your application will not work until you add compensation actions!");
			// Rollback, close everything, possibly compensate for any permanent
			// changes
			// during the conversation, and finally restart business
			// conversation. Maybe
			// give the user of the application a chance to merge some of his
			// work with
			// fresh data... what you do here depends on your applications
			// design.
			throw staleEx;
		} catch (Throwable ex) {
			// Rollback only
			ex.printStackTrace();
			try {
				if (sessionFactory != null
						&& sessionFactory.getCurrentSession().getTransaction()
								.isActive()) {
					log.debug("Trying to rollback database transaction after exception");
					sessionFactory.getCurrentSession().getTransaction()
							.rollback();
				}
			} catch (Throwable rbEx) {
				log.error("Could not rollback transaction after exception!",
						rbEx);
			}

			// Let others handle it... maybe another interceptor for exceptions?
			throw new ServletException(ex);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("Initializing filter...");
		this.filterConfig = filterConfig;
		log.info("Locale Country:" + Locale.getDefault().getCountry());
		log.info("Locale Language:" + Locale.getDefault().getLanguage());
	}

}
