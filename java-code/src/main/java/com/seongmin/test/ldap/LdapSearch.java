package com.seongmin.test.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapSearch {
	public LdapSearch() {
	}

	// LDAP server에서 인증서 일련번호(serial)을 검색조건으로 하여 "attrIDs"에
	// 정의된 LDAP attribute들을 출력하는 예제
	public static void ldapSearchBySerial(String serial) {
		Hashtable env = new Hashtable(5, 0.75f);
		NamingEnumeration m_ne = null;
		// 검색된 결과 entry중 아래의 attribute들을 출력할 것임
		String[] attrIDs = { "cn", "serial", "mail", "uid" };
		// 파라미터로 넘어온 인증서 일련번호를 가지고 LDAP filter를 조립
		String filter = "(serial=" + serial + ")";
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			// LDAP 서버의 "프로토콜://IP:포트"를 설정
			// 아래는 ldap 프로토콜을 사용하는 127.0.0.1 서버의 7389포트로 접속하는 경우
			env.put(Context.PROVIDER_URL, "ldap://127.0.0.1:7389");
			DirContext dirCtx = new InitialDirContext(env);
			SearchControls constraints = new SearchControls();
			// 검색범위를 하위디렉토까지 검색(SUBTREE)
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			if (attrIDs != null)
				constraints.setReturningAttributes(attrIDs);
			// 검색을 시작할 BASE DN을 설정하고, 검색
			m_ne = dirCtx.search("c=kr", filter, constraints);
			dirCtx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SearchResult sr = null;
			while (m_ne.hasMoreElements()) {
				// System.out.println(m_ne.nextElement());
				sr = (SearchResult) m_ne.next();
				// dn출력시 BASE DN은 제외하고 출력된다.
				System.out.println(sr.getName() + "," + "c=kr");
				for (int i = 0; i < 4; i++) { // attrIDs member count
					System.out.println(i + ": " + attrIDs[i] + " : "
							+ (String) sr.getAttributes().get(attrIDs[i]).get());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ldapSearchBySerial("000000091812");
	} // main
} // class