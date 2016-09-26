package com.seongmin.test.type.date;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

//http://helloworld.naver.com/helloworld/textyle/645609
public class CalendarTest {

	@Test
	public void shouldGetAfterOneDay() {

		// 1582년 10월 4일의 다음 날은?
		// <예제 1>에서는 UTC(Universal Time Coordinated, 세계협정시) 시간대를 기준으로 1582년 10월
		// 4일에 하루를 더한 날짜가 10월 5일인 것을 테스트하고 있다. JUnit과 Fest Assertions 라이브러리[1]를
		// 활용했다.

		TimeZone utc = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(utc);
		calendar.set(1582, Calendar.OCTOBER, 4);
		String pattern = "yyyy.MM.dd";
		String theDay = toString(calendar, pattern, utc);

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
		assertEquals("1582.10.15", nextDay); // 위와같은 이유로 1일 뒤는 15일 이다.

	}

	@Test
	public void shouldGetAfterOneHour() {
		// 'Asia/Seoul' 시간대에서 '1988.05.07 23:00'의 1시간 후가 '1988.05.08 00:00'임을
		// 테스트하고 있다.

		TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");

		Calendar calendar = Calendar.getInstance(seoul);
		calendar.set(1988, Calendar.MAY, 7, 23, 0);
		String pattern = "yyyy.MM.dd HH:mm";
		String theTime = toString(calendar, pattern, seoul);
		assertEquals("1988.05.07 23:00", theTime);

		// 이상하게도 1시간 후는 5월 8일 새벽 1시이다. 이는 그 시기에 서울에 적용된 일광절약시간제(Daylight Saving
		// Time), 즉 서머타임 때문이다. 서머타임이 시작되는 시점에서는 1시간을 건너뛴다. 해당 시간대가 서머타임 적용
		// 시간대인지는 TimeZone.inDaylightTime() 메서드로 확인할 수 있다. <예제 3>의 마지막 2줄을 다음과
		// 같이 바꾸면 테스트를 통과하고, 이 시간대에 일어난 일을 좀 더 잘 설명할 수 있다.

		calendar.add(Calendar.HOUR_OF_DAY, 1);
		String after1Hour = toString(calendar, pattern, seoul);
		// assertEquals("1988.05.08 00:00", after1Hour);
		assertTrue(seoul.inDaylightTime(calendar.getTime()));
		assertEquals("1988.05.08 01:00", after1Hour);

	}

	@Test
	public void shouldGetAfterOneMinute() {

		// '1961.08.09 23:59'의 1분 후가 '1961.08.10 00:00'임을 테스트하고 있다.
		TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
		Calendar calendar = Calendar.getInstance(seoul);
		calendar.set(1961, Calendar.AUGUST, 9, 23, 59);
		String pattern = "yyyy.MM.dd HH:mm";
		String theTime = toString(calendar, pattern, seoul);
		assertEquals("1961.08.09 23:59", theTime);

		calendar.add(Calendar.MINUTE, 1);
		String after1Minute = toString(calendar, pattern, seoul);
		// 신기하게도 23시 59분의 1분 후는 0시 30분이다. 다음과 같이 마지막 줄을 수정하면 테스트를 통과할 수 있다.
		// assertEquals("1961.08.10 00:00", after1Minute);

		// 1961년 8월 10일은 대한민국의 표준시가 UTC+8:30에서 현재와 같은 UTC+9:00로 변경된 시점이다. 일제 강점기
		// 동안 UTC+9:00이었던 표준시가 해방 이후 1954년에 UTC+8:30으로 바뀌었다가 1961년에 다시
		// UTC+9:00으로 바뀐다.[8] 이 표준시 변경 때문에 30분을 건너뛰게 된 것이다.

		// 1961년 당시 최고 권력 기관이었던 국가재건최고회의는 표준시를 일본과 동일하게 바꾸기로 결정했다. 최근에는 일제의 잔재에서
		// 벗어난다는 의미에서 다시 한 번 표준시를 바꾸자는 의견도 나오고 있다.[9] 우리나라 근현대사와 관련이 있는 예제라고 할
		// 만하다.
		assertEquals("1961.08.10 00:30", after1Minute);

	}

	@Test
	public void shouldGetAfterTwoSecond() {
		// UTC(협정세계시) '2012.06.30 23:59:59'의 2초 후가 '2012.07.01 00:00:01'인 것을
		// 테스트하고 있다.
		TimeZone utc = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(utc);
		calendar.set(2012, Calendar.JUNE, 30, 23, 59, 59);
		String pattern = "yyyy.MM.dd HH:mm:ss";
		String theTime = toString(calendar, pattern, utc);
		assertEquals("2012.06.30 23:59:59", theTime);

		calendar.add(Calendar.SECOND, 2);
		String afterTwoSeconds = toString(calendar, pattern, utc);
		assertEquals("2012.07.01 00:00:01", afterTwoSeconds);

		// 지금까지의 예제와는 다르게 위의 테스트는 잘 통과한다. 별로 특별할 것이 없다면 이번 예제는 왜 넣었을까? 2012년 6월
		// 30일은 가장 최근에 '윤초'가 적용된 때이다. 즉 <예제 7>의 결과는 윤년이나 서머타임과는 달리 Java에서 윤초가
		// Calendar 연산에 적용되지 않는다는 것을 보여 준다.
	}

	private String toString(Calendar calendar, String pattern, TimeZone zone) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(zone);
		return format.format(calendar.getTime());
	}

}
