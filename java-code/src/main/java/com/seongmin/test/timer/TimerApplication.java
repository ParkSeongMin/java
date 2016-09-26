package com.seongmin.test.timer;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

class ReportGenerator extends TimerTask {

	private String	threadName	= null;

	public void run() {
		System.out.println("[" + getThreadName() + "]Generating report");
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @param threadName
	 *            the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

}

public class TimerApplication {

	public static void main(String[] args) {

		for (int i = 0; i < 3; i++) {
			Thread t = new Thread() {
				public void run() {
					TimerApplication main = new TimerApplication();
					main.startSchedule(Thread.currentThread().getName());
				}
			};
			t.start();
		}

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void startSchedule(String threadName) {
		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		// Schedule to run every Sunday in midnight
		ReportGenerator generator = new ReportGenerator();
		generator.setThreadName(threadName);

		int period = (int)(Math.random()*10)+1;
		System.out.println("period : " + period);
		timer.schedule(generator, date.getTime(),
		// 1000 * 60 * 60 * 24 * 7
				(long)(1000 * period));
		
	}

}