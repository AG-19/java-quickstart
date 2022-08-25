package com.blogspot.adapttoaem;

import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import org.apache.jackrabbit.commons.JcrUtils;

public class JCRUtils {
	static Session session = null;

	public static Session getJCRSession () {
		try {
			//Create a connection to the AEM JCR repository running on local host
			Repository repository = JcrUtils.getRepository("http://localhost:4502/crx/server");
			//Create a Session instance
			session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public static NodeIterator executeSQL2Query (String queryString) throws InvalidQueryException, RepositoryException {
		Query query = getJCRSession().getWorkspace().getQueryManager().createQuery(queryString, Query.JCR_SQL2);
		QueryResult result = query.execute();

		return result.getNodes();
	}
}
