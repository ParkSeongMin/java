package com.seongmin.test.aspectj.exception;

import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.Signature;

//public aspect ExceptionLoggingAspect {
@Aspect
public class ExceptionLoggingAspect {

//	before(Exception e): handler(Exception+) && args(e) {
//		System.err.println("Caught by aspect: " + e.toString());
//		e.printStackTrace();
//	}

	private Log	log					= LogFactory.getLog(this.getClass());
	private Map	loggedThrowables	= new WeakHashMap();

//	public pointcut scope(): within(nl.boplicity..*);
//
//	after() throwing(Throwable t): scope() {
//
//		logThrowable(t, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
//	}
//
//	before(Throwable t): handler(Exception+) && args(t) && scope() {
//		logThrowable(t, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
//	}
//	
//	before(Throwable t): handler(Exception+) && args(t) {
//
//		t.printStackTrace();
//		logThrowable(t, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
//	}

	protected synchronized void logThrowable(Throwable t, StaticPart location, StaticPart enclosing) {

		System.out.println(t + " : " + location + " : " + enclosing);

		if (!loggedThrowables.containsKey(t)) {
			loggedThrowables.put(t, null);

			Signature signature = location.getSignature();

			String source = signature.getDeclaringTypeName() + ":" + (enclosing.getSourceLocation().getLine());

			log.error("(a) " + source + " - " + t.toString(), t);
		}
	}
}
