package com.seongmin.test.stream;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class MemoryByteArrayPrintStream extends PrintStream {

	public MemoryByteArrayPrintStream(OutputStream out) {
		super(out);
		
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#flush()
	 */
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		super.flush();
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#checkError()
	 */
	@Override
	public boolean checkError() {
		// TODO Auto-generated method stub
		return super.checkError();
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#setError()
	 */
	@Override
	protected void setError() {
		// TODO Auto-generated method stub
		super.setError();
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#write(int)
	 */
	@Override
	public void write(int b) {
		// TODO Auto-generated method stub
		super.write(b);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] buf, int off, int len) {
		// TODO Auto-generated method stub
		super.write(buf, off, len);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(boolean)
	 */
	@Override
	public void print(boolean b) {
		// TODO Auto-generated method stub
		super.print(b);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(char)
	 */
	@Override
	public void print(char c) {
		// TODO Auto-generated method stub
		super.print(c);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(int)
	 */
	@Override
	public void print(int i) {
		// TODO Auto-generated method stub
		super.print(i);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(long)
	 */
	@Override
	public void print(long l) {
		// TODO Auto-generated method stub
		super.print(l);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(float)
	 */
	@Override
	public void print(float f) {
		// TODO Auto-generated method stub
		super.print(f);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(double)
	 */
	@Override
	public void print(double d) {
		// TODO Auto-generated method stub
		super.print(d);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(char[])
	 */
	@Override
	public void print(char[] s) {
		// TODO Auto-generated method stub
		super.print(s);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(java.lang.String)
	 */
	@Override
	public void print(String s) {
		// TODO Auto-generated method stub
		super.print(s);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(java.lang.Object)
	 */
	@Override
	public void print(Object obj) {
		// TODO Auto-generated method stub
		super.print(obj);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println()
	 */
	@Override
	public void println() {
		// TODO Auto-generated method stub
		super.println();
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(boolean)
	 */
	@Override
	public void println(boolean x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(char)
	 */
	@Override
	public void println(char x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(int)
	 */
	@Override
	public void println(int x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(long)
	 */
	@Override
	public void println(long x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(float)
	 */
	@Override
	public void println(float x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(double)
	 */
	@Override
	public void println(double x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(char[])
	 */
	@Override
	public void println(char[] x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(java.lang.String)
	 */
	@Override
	public void println(String x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(java.lang.Object)
	 */
	@Override
	public void println(Object x) {
		// TODO Auto-generated method stub
		super.println(x);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#printf(java.lang.String, java.lang.Object[])
	 */
	@Override
	public PrintStream printf(String format, Object... args) {
		// TODO Auto-generated method stub
		return super.printf(format, args);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#printf(java.util.Locale, java.lang.String, java.lang.Object[])
	 */
	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		// TODO Auto-generated method stub
		return super.printf(l, format, args);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#format(java.lang.String, java.lang.Object[])
	 */
	@Override
	public PrintStream format(String format, Object... args) {
		// TODO Auto-generated method stub
		return super.format(format, args);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#format(java.util.Locale, java.lang.String, java.lang.Object[])
	 */
	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		// TODO Auto-generated method stub
		return super.format(l, format, args);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#append(java.lang.CharSequence)
	 */
	@Override
	public PrintStream append(CharSequence csq) {
		// TODO Auto-generated method stub
		return super.append(csq);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#append(java.lang.CharSequence, int, int)
	 */
	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		// TODO Auto-generated method stub
		return super.append(csq, start, end);
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#append(char)
	 */
	@Override
	public PrintStream append(char c) {
		// TODO Auto-generated method stub
		return super.append(c);
	}
	
	

}
