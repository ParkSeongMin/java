package com.seongmin.test.annotation;

import java.lang.reflect.Method;

@UserAnnotation(number=1, text="d")
public class UserAnnotationSample {

	@UserAnnotation(number = 1)
	public static void main(String[] args) {
		UserAnnotationSample sample = new UserAnnotationSample();
		sample.checkAnnotations(UserAnnotationSample.class);
		
	}

	@UserAnnotation(number = 2)
	public void annotationSample1() {
	}

	@UserAnnotation(number = 3, text = "second")
	public void annotationSample2() {
	}

	@UserAnnotation(number = 4, text = "third")
	public void annotationSample3() {
	}

	public void checkAnnotations(Class useClass) {
		
		UserAnnotation classAnnotation = (UserAnnotation) useClass.getAnnotation(UserAnnotation.class);
		System.out.println("class annotation: number=" + classAnnotation.number() + " text=" + classAnnotation.text());
		
		Method[] methods = useClass.getDeclaredMethods();
		for (Method tempMethod : methods) {
			UserAnnotation annotation = tempMethod.getAnnotation(UserAnnotation.class);
			if (annotation != null) {
				int number = annotation.number();
				String text = annotation.text();
				System.out.println(tempMethod.getName() + "() : number=" + number + " text=" + text);
			} else {
				System.out.println(tempMethod.getName() + "() : annotation is null");
			}
		}
	}

}
