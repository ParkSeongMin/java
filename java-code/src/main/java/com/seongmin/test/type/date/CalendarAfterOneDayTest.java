package com.seongmin.test.type.date;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;
import org.junit.Test;

public class CalendarAfterOneDayTest {

	public static void main(String[] args) {

		
//		CalendarAfterOneDayTest.shouldGetAfterOneDayJoda();
//		CalendarAfterOneDayTest.shouldGetAfterOneDayCalendar();
		
		testIncreateDay(4);
		
	}
	
	public static void testIncreateDay(int increaseYear) {
		
		String pattern = "yyyy.MM.dd";
		Chronology chrono = GregorianChronology.getInstance();
		LocalDate theDay = new LocalDate(1952, 1, 1, chrono);
		
		LocalDate finishDay = new LocalDate(1952, 6, 17, chrono);
		
		int doLoopCount = 365 * increaseYear;
		
		LocalDate currentDay = theDay;
		for(int i=0; i<doLoopCount; i++) {
			currentDay = currentDay.plusDays(1);
			
			System.out.println(currentDay.monthOfYear().getLocalDate().toString("MM"));
			
			if(currentDay.isAfter(finishDay)) {
				break;
			}
					
			System.out.println("currentDay="+currentDay.toString(pattern));
		}
		
		
	}
	

	// 예제1, 2: 1일 후 구하기
	public static void shouldGetAfterOneDayJoda() {
		Chronology chrono = GregorianChronology.getInstance();
		LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
		String pattern = "yyyy.MM.dd";
		System.out.println("theDay="+theDay.toString(pattern));
		assertEquals("1582.10.04", theDay.toString(pattern));
		
		

		LocalDate nextDay = theDay.plusDays(1);
		System.out.println("nextDay="+nextDay.toString(pattern));
		assertEquals("1582.10.05", nextDay.toString(pattern));
		
	}

	
	public static void shouldGetAfterOneDayCalendar() {

		// 1582년 10월 4일의 다음 날은?
		// <예제 1>에서는 UTC(Universal Time Coordinated, 세계협정시) 시간대를 기준으로 1582년 10월
		// 4일에 하루를 더한 날짜가 10월 5일인 것을 테스트하고 있다. JUnit과 Fest Assertions 라이브러리[1]를
		// 활용했다.

		TimeZone utc = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(utc);
		calendar.set(1582, Calendar.OCTOBER, 4);
		String pattern = "yyyy.MM.dd";
		String theDay = toString(calendar, pattern, utc);

		System.out.println("theDay="+theDay);
		assertEquals("1582.10.04", theDay);

		/*
		 * 1582년에서 실종된 10일은 그레고리력을 처음 적용하면서 율리우스력에 의해 그동안 누적된 오차를 교정하기 위해서 건너뛴
		 * 기간이다. 태양의 황경이 0도가 되는 춘분이 1582년에는 10일 정도 어긋나게 되었다. 교황 그레고리우스 13세는 더
		 * 정교한 그레고리력을 1582년 10월 15일에 처음 적용했고, 10월 5 ~ 14일의 날짜는 그 해 달력에서 제외시켰다.
		 * 율리우스력은 4년마다 윤년을 두지만, 그레고리력에서는 4년마다 윤년을 두되 매 100번째 해는 윤년이 아니고, 매 400번째
		 * 해는 윤년이라는 차이가 있다.
		 */

		calendar.add(Calendar.DATE, 1); // number of days to add
		String nextDay = toString(calendar, pattern, utc);
		// assertEquals("1582.10.05", theDay);
		System.out.println("nextDay="+nextDay);
		assertEquals("1582.10.15", nextDay); // 위와같은 이유로 1일 뒤는 15일 이다.

	}
	
	private static String toString(Calendar calendar, String pattern, TimeZone zone) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(zone);
		return format.format(calendar.getTime());
	}
	
}
