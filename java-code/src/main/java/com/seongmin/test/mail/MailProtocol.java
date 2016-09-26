package com.seongmin.test.mail;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

public interface MailProtocol {

	void open(String host, String id, String passwd) throws MessagingException;

	void close() throws MessagingException;

	Message getMessages(int msgNum) throws MessagingException;

	Message[] getMessages() throws MessagingException;

	Message[] getRecentMessages(int count) throws MessagingException;

	int getMessageCount() throws MessagingException;

	String getUID(Message msg) throws MessagingException;

	Folder getFolder() throws MessagingException;

	Store getStore() throws MessagingException;

}
