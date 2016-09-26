package com.seongmin.test.mail;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

public class MailTest {

	public static void main(String[] args) {
		try {
			readEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readEmail() throws Exception {
		IMAPMailAgent mailagent = new IMAPMailAgent("imap.gmail.com", "google id", "pw");
		mailagent.open();
		
		Message[] msg = mailagent.getUnReadMessages();
		
		for(Message m : msg) {
			
			printMsssage(m);
			
		}
		
		
//		mailagent.createFolder("newFolder");
//		Message[] msg = mailagent.getUnReadMessages();
//		
//		mailagent.getStore().getFolder("INBOX").
//		
//		for(Message m : msg) {
//			System.out.println("MsgNum: "+m.getMessageNumber());
//			System.out.println("UID: "+mailagent.getUID(m));
//			
//			Address[] in = m.getFrom();
//            for (Address address : in) {
//                System.out.println("FROM:" + address.toString());
//            }
//            Multipart mp = (Multipart) m.getContent();
//            BodyPart bp = mp.getBodyPart(0);
//            System.out.println("SENT DATE:" + m.getSentDate());
//            System.out.println("SUBJECT:" + m.getSubject());
//            System.out.println("CONTENT:" + ((MimeMultipart)bp.getContent()).getBodyPart(0).getContent());
//			
////			System.out.println("headers");
////			Enumeration allHeaders = m.getAllHeaders();
////			while(allHeaders.hasMoreElements()) {
////				System.out.println(allHeaders.nextElement());
////			}
//			
//			System.out.println("recipients");
//			Address[] allRecipients = m.getAllRecipients();
//			if(allRecipients != null) {
//				for(int i=0; i<allRecipients.length; i++) {
//					System.out.println(allRecipients[i] + "=" + allRecipients[i].getType());
//				}
//			}
//			
//			String contentType = m.getContentType();
//			System.out.println("contentType="+contentType);
//			
//			System.out.println("getDescription="+m.getDescription());
//			
////			mailagent.setSeenFlag(m);
//		}
////		mailagent.moveMessage(msg, mailagent.getDefaultFolder(), mailagent.getFolder("newFolder"));
		mailagent.close();
	}

	
	 private static void printMsssage(Message message) throws Exception {
	      String replyTo = null, subject;
	      Object content;
	      java.util.Date sentDate;
	      Address[] a=null;
	      
	      if ((a = message.getFrom()) != null)
	         replyTo = a[0].toString();
	      
	      subject  = message.getSubject();
	      sentDate = message.getSentDate();
	      DataHandler dataHandler = message.getDataHandler();
	      String contentType = dataHandler.getContentType();
	      
	      System.out.println("contentType="+contentType);
	      
	      System.out.println("=====================================================");
	      System.out.println("보낸사람:"+replyTo);
	      System.out.println("보낸시각:"+sentDate);
	      System.out.println("주제:"+subject);
	      System.out.println("-----------------------------------------------------");
	      
	      if ( (contentType.indexOf("text/html") != -1) || (contentType.indexOf("text/plain") != -1) ) {
	         BufferedReader br = new BufferedReader(new InputStreamReader(dataHandler.getInputStream()));
	         char[] buff = new char[512];
	         int len;
	         while ( (len = br.read(buff)) != -1) {
	            System.out.print(new String(buff, 0, len));
	         }
	         br.close();
	      } else if(contentType.indexOf("multipart") >= 0) {
	    	  Multipart mp = (Multipart) message.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + message.getSentDate());
            System.out.println("SUBJECT:" + message.getSubject());
            System.out.println("CONTENT:" + ((MimeMultipart)bp.getContent()).getBodyPart(0).getContent());
            System.out.println("CONTENT:" + ((MimeMultipart)bp.getContent()).getBodyPart(1).getContent());
            
            throw new RuntimeException("test");
	      }
	      System.out.println("\n=====================================================\n");
	   }
	
	
}
