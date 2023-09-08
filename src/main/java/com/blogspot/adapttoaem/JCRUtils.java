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

	private static Session session = null;

	public static void getJCRSession () {
		try {
			if (session == null) {
				Repository repository = JcrUtils.getRepository("http://localhost:4503/crx/server");
				session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static NodeIterator executeSQL2Query (String queryString) throws InvalidQueryException, RepositoryException {
		getJCRSession();
		Query query = session.getWorkspace().getQueryManager().createQuery(queryString, Query.JCR_SQL2);
		QueryResult result = query.execute();

		return result.getNodes();
	}
	
	public static void closeJCRSession () {
		session.logout();
	}
}
