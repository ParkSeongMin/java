package com.seongmin.test.type.date;

import static org.junit.Assert.*;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.GregorianChronology;
import org.junit.Test;

public class JodaTimeTest {

	/*
LocalDate, DateTime 등으로 지역 시간과 시간대가 지정된 시간을 구분했다. LocalDate와 LocalTime으로 날짜와 시간을 별도의 클래스로 구분할 수도 있다.
plusDays, plusMinutes, plusSeconds 등 단위별 날짜 연산 메서드를 LocalDate, DateTime 클래스에서 지원한다. 메서드가 호출된 객체의 상태를 바꾸지 않고 새로운 객체를 반환한다. 불변 객체이다.
월의 int 값과 명칭이 일치한다. 1월은 int 값 1이다.
GregorianChronology를 썼을 때는 1582년 10월을 특별하게 취급하지는 않는다. GJChronology를 사용하면 JDK의 GregorianCalendar와 같이 10월 4일 다음 날이 10월 15일로 나온다.
서머타임 기간이면 DateTimeZone.isStandardOffset() 메서드의 반환값이 false이다.
13월 같이 잘못 된 월이 넘어가면 객체 생성 시점에서 IllegalFieldValueException을 던진다.
요일 상수는 일관되게 사용한다.
잘못 된 시간대 ID 지정에는 IllegalArguementException을 던진다.
	*/
	
	@Test
	// 예제1, 2: 1일 후 구하기
	public void shouldGetAfterOneDay() {
		Chronology chrono = GregorianChronology.getInstance();
		LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
		String pattern = "yyyy.MM.dd";
		assertEquals("1582.10.04", theDay.toString(pattern));

		LocalDate nextDay = theDay.plusDays(1);
		assertEquals("1582.10.05", nextDay.toString(pattern));
	}

	// 예제1, 2: 1일 후 구하기.
	@Test
	public void shouldGetAfterOneDayWithGJChronology() {
		Chronology chrono = GJChronology.getInstance();
		LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
		String pattern = "yyyy.MM.dd";
		assertEquals("1582.10.04", theDay.toString(pattern));

		LocalDate nextDay = theDay.plusDays(1);
		assertEquals("1582.10.15", nextDay.toString(pattern));
	}

	@Test
	// 예제3, 4: 1시간 후 구하기
	public void shouldGetAfterOneHour() {
		DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
		DateTime theTime = new DateTime(1988, 5, 7, 23, 0, seoul);
		String pattern = "yyyy.MM.dd HH:mm";
		assertEquals("1988.05.07 23:00", theTime.toString(pattern));
		assertTrue(seoul.isStandardOffset(theTime.getMillis()));

		DateTime after1Hour = theTime.plusHours(1);
		assertEquals("1988.05.08 01:00", after1Hour.toString(pattern));
		assertTrue(seoul.isStandardOffset(after1Hour.getMillis()));
		
	}
	
	
	

	@Test
	// 예제 5, 6: 1분 후 구하기
	public void shouldGetAfterOneMinute() {
		DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
		DateTime theTime = new DateTime(1961, 8, 9, 23, 59, seoul);
		String pattern = "yyyy.MM.dd HH:mm";
		assertEquals("1961.08.09 23:59", theTime.toString(pattern));

		DateTime after1Minute = theTime.plusMinutes(1);
		assertEquals("1961.08.10 00:30", after1Minute.toString(pattern));
	}

	@Test
	// 예제 7: 2초 후 구하기
	public void shouldGetAfterTwoSecond() {
		DateTimeZone utc = DateTimeZone.forID("UTC");
		DateTime theTime = new DateTime(2012, 6, 30, 23, 59, 59, utc);
		String pattern = "yyyy.MM.dd HH:mm:ss";
		assertEquals("2012.06.30 23:59:59", theTime.toString(pattern));

		DateTime after2Seconds = theTime.plusSeconds(2);
		assertEquals("2012.07.01 00:00:01", after2Seconds.toString(pattern));
	}

	@Test
	// 예제 12: 1999년 12월 31일을 지정하는 코드
	public void shouldGetDate() {
		LocalDate theDay = new LocalDate(1999, 12, 31);

		assertEquals(1999, theDay.getYear());
		assertEquals(12, theDay.getMonthOfYear());
		assertEquals(31, theDay.getDayOfMonth());
	}

	@Test(expected = IllegalFieldValueException.class)
	// 예제 12 : 1999년 12월 31일을 지정하는 코드의 실수
	public void shouldNotAcceptWrongMonth() {
		new LocalDate(1999, 13, 31);
	}

	@Test
	// 예제 13: 요일 확인하기
	public void shouldGetDayOfWeek() {
		LocalDate theDay = new LocalDate(2014, 1, 1);

		int dayOfWeek = theDay.getDayOfWeek();
		assertEquals(DateTimeConstants.WEDNESDAY, dayOfWeek);
		assertEquals(3, dayOfWeek);
	}

	@Test(expected = IllegalArgumentException.class)
	// 예제 14: 잘못 지정한 시간대 ID
	public void shouldThrowExceptionWhenWrongTimeZoneId() {
		DateTimeZone.forID("Seoul/Asia");
	}
}