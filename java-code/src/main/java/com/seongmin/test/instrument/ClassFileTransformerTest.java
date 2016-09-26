package com.seongmin.test.instrument;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class ClassFileTransformerTest implements ClassFileTransformer, Runnable {
	public final Instrumentation	instrumentation;

	public ClassFileTransformerTest(Instrumentation instrumentation) {

		this.instrumentation = instrumentation;

	}

	public static void premain(String agentArgument, Instrumentation instrumentation) {

		System.out.println("premain");

		ClassFileTransformerTest l = new ClassFileTransformerTest(instrumentation);

		instrumentation.addTransformer(l);

		Thread t = new Thread(l);

		t.start();

	}

	public byte[] transform(ClassLoader loader, String className,

	Class<?> classBeingRedefined, ProtectionDomain protectionDomain,

	byte[] classfileBuffer) throws IllegalClassFormatException {

		System.out.println("transform : " + className);

		return classfileBuffer;

	}

	public void hello() {

		System.out.println("안녕하세요");

	}

	public void run() {

		for (int i = 0; i < 10; i++) {

			try {

				Thread.sleep(1 * 1000);

				ClassPool pool = ClassPool.getDefault();

				CtClass cc = pool.get("instrument.ClassFileTransformerTest");

				cc.defrost();

				CtMethod m = cc.getDeclaredMethod("hello");

				m.setBody("{ System.out.println(\"오~! 변한다~ : " + i + "번째\"); }");

				ClassDefinition[] cd = new ClassDefinition[1];

				cd[0] = new ClassDefinition(ClassFileTransformerTest.class, cc.toBytecode());

				instrumentation.redefineClasses(cd);

			} catch (Exception e) {

				e.printStackTrace();

			}

			hello();

		}

	}

}
