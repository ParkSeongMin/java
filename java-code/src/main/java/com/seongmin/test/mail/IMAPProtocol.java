package com.seongmin.test.mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class IMAPProtocol implements MailProtocol {

	protected Session		session;

	protected Store			store;

	protected Folder		folder;

	private static String	SSL_FACTORY	= "javax.net.ssl.SSLSocketFactory";

	public void open(String host, String id, String passwd) throws MessagingException {
		
		
		
//		imap.gmail.com
		
		Properties props = new Properties();
		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.socketFactory.port", "993");
		session = Session.getInstance(props, null);
		URLName urlName = new URLName("imap", host, 993, "", id, passwd);
		
		
		
		store = new IMAPStore(session, urlName);
		store.connect();
		folder = store.getDefaultFolder().getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		
	}

	public void close() throws MessagingException {
		if (folder != null && folder.isOpen())
			folder.close(true);
		if (store != null && store.isConnected())
			store.close();
	}

	public Message getMessages(int msgNum) throws MessagingException {
		if (!folder.isOpen())
			throw new MessagingException("Already closed folder");
		return folder.getMessage(msgNum);
	}

	public Message[] getMessages() throws MessagingException {
		if (!folder.isOpen())
			throw new MessagingException("Already closed folder");
		return folder.getMessages();
	}

	public Message[] getRecentMessages(int count) throws MessagingException {
		if (!folder.isOpen())
			throw new MessagingException("Already closed folder");
		int folderSize = folder.getMessageCount();
		return folder.getMessages(folderSize - count + 1, folderSize);
	}

	public int getMessageCount() throws MessagingException {
		if (!folder.isOpen())
			throw new MessagingException("Already closed folder");
		return folder.getMessageCount();
	}

	public String getUID(Message msg) throws MessagingException {
		if (folder instanceof IMAPFolder) {
			return new Long(((IMAPFolder) folder).getUID(msg)).toString();
		} else {
			throw new MessagingException("Can not support");
		}
	}

	public Folder getFolder() throws MessagingException {
		if (!folder.isOpen())
			throw new MessagingException("Already closed folder");
		return folder;
	}

	public Store getStore() throws MessagingException {
		if (!store.isConnected())
			throw new MessagingException("Already closed store");
		return store;
	}
}
