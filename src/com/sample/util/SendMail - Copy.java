
package com.sample.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;

import com.sample.test.DriverScript;

//import jdk.nashorn.internal.runtime.regexp.joni.Config;

public class SendMail {
	public static Properties CONFIG;
	public static FileInputStream fs;
	public static String emailID;
	public static boolean flash = false;

	public boolean execute(String env, String branch, String module, String browser, String jobID,
			String emailDeliver, String to_email) throws Exception {

		System.out.println(System.getProperty("user.dir") + "//src//com//sample//config//" + branch + ".properties");
		fs = new FileInputStream(
				System.getProperty("user.dir") + "//src//com//sample//config//" + branch + ".properties");
		CONFIG = new Properties();
		CONFIG.load(fs);

		String[] to = null;
		String[] cc = {}, bcc = {};
		if (emailDeliver.equalsIgnoreCase("YES")) {
			to = to_email.split(",");
			to = CONFIG.getProperty("to").split(";");
			cc = CONFIG.getProperty("cc").split(";");

		} else if (emailDeliver.equalsIgnoreCase("NO")) {
			to = "timirtrivedi1979@gmail.com".split(";");
			cc = "hriday04@gmail.com".split(";");

		} else
			System.out.println("Please check the parameter you passed to trigger the mail");

		String subject = CONFIG.getProperty("subject");

		String bodyMessage = CONFIG.getProperty("bodyMessage");

		// Sender's email ID needs to be mentioned
		String from = CONFIG.getProperty("InternetAddress");

		String host = CONFIG.getProperty("smtp");

		String port = CONFIG.getProperty("port");

		// Get system properties
		Properties properties = System.getProperties();

		// String port = String.valueOf(i);

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port); // or 587
		properties.setProperty("mail.user", "testautomationever@gmail.com");
		properties.setProperty("mail.password", "Synoverge@123");
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CONFIG.getProperty("InternetAddress"),
						CONFIG.getProperty("InternetPassword"));
			}

		};

		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);
		Session session = Session.getInstance(properties, auth);

		try {
			MimeMessage message = new MimeMessage(session);

			// Create a default MimeMessage object.
			MimeBodyPart message1 = new MimeBodyPart();
			MimeBodyPart message3 = new MimeBodyPart();
			MimeBodyPart message2 = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			for (int i = 0; i < to.length; i++) {

				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));

			}

			for (int i = 0; i < cc.length; i++) {

				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));

			}

			for (int i = 0; i < bcc.length; i++) {

				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));

			}

			String branch1 = CONFIG.getProperty("branch");
			if (branch.length() >= 16) {
				branch = CONFIG.getProperty("branch").substring(0, 16);
			} else {
				branch = CONFIG.getProperty("branch");
			}
			// message.setSubject("FINISHED Auto run of ["+module+"] on ["+"
			// Browser-"+browser+"]");
			// String subject=CONFIG.getProperty("subject");
			message.setSubject(CONFIG.getProperty("subject"));

			// Now set the actual message

			/*
			 * message1.setText(bodyMessage+"\n"+"Environment="+env+"\n"+
			 * "Branch="+Executor.release+"\n"+"Module="+module+"\n"+"Build id="
			 * +DriverScript.buildNumber+"\n"+"Browser="+browser+"\n"+"Jenkins JOB ID="
			 * +jobID
			 * +"\n\n"+"Pass Test Cases= "+ReportUtil.totalpass+"\n"+"Fail Test Cases= "
			 * +ReportUtil.totalfail+"\n\n"+"Regards"+"\n"+"Automation Team");
			 */

			/*
			 * message1.setText(bodyMessage+"\n"+"Environment="+env+"\n"+
			 * "Module="+module+"Browser="+browser
			 * +"\n\n"+"Pass Test Cases= "+ReportUtil1.totalpass+"\n"+"Fail Test Cases= "
			 * +ReportUtil1.totalfail+"\n\n"+"Regards"+"\n"+ReportUtil1.author);
			 */

			// message1.setText("Please refer attached execution file");

			message1.setText(bodyMessage + "\n\n" + "Execution Environment=" + branch + "\n\n"
			// +"Module="+module+"\n\n"
					+ "Current Build Number=" + CONFIG.getProperty("buildNumber") + "\n\n" + "Browser="
					+ CONFIG.getProperty("browserType") + "\n\n" + "Execution Start Date=" + ReportUtil1.startDate
					+ "\n\n" + "Execution End Date=" + ReportUtil1.endDate + "\n\n"
					/*
					 * +"Total Pass="+ReportUtil1.totalpass+"\n\n"
					 * +"Total Fail="+ReportUtil1.totalfail+"\n\n"
					 * +"Total Skip="+ReportUtil1.totalskip+"\n\n"
					 */

					+ "Regards" + "\n" + "Synoverge Automation Team");
			// message.add

			StringWriter writer = new StringWriter();
			IOUtils.copy(new FileInputStream(new File(ReportUtil1.report_FolderName + "\\"
					+ ReportUtil1.result_FolderName + "\\" + ReportUtil1.currentTestSuite + ".html")), writer);
			message3.setContent(writer.toString(), "text/html");

			String filename = System.getProperty("user.dir") + "//src//com//sample//config//"
					+ DriverScript.currentTestSuite + ".xlsx";// change accordingly
			System.out.println("file name is" + filename);
			DataSource source = new FileDataSource(filename);
			message2.setDataHandler(new DataHandler(source));
			message2.setFileName(DriverScript.currentTestSuite + ".xlsx");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(message1);
			multipart.addBodyPart(message2);
			multipart.addBodyPart(message3);
			message.setContent(multipart);

			
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean startExecute(String module, String browser, String emailDeliver, String to_email, String author)
			throws Exception {

		System.out.println(System.getProperty("user.dir") + "//config//config.properties");
		fs = new FileInputStream(System.getProperty("user.dir") + "//config//config.properties");
		CONFIG = new Properties();
		CONFIG.load(fs);
		String[] to = null;
		String[] cc = {}, bcc = {};
		if (emailDeliver.equalsIgnoreCase("YES")) {
			// to_email=to_email;
			to = to_email.split(",");
//	 			to=CONFIG.getProperty("to").split(";");
//		 		cc=CONFIG.getProperty("cc").split(";");
		} else if (emailDeliver.equalsIgnoreCase("NO")) {
			to = "testautomationever@gmail.com".split(";");
		} else
			System.out.println("Please check the parameter you passed to trigger the mail");

		String subject = CONFIG.getProperty("subject");

		String bodyMessage = CONFIG.getProperty("startbodyMessage");

		// Sender's email ID needs to be mentioned
		String from = CONFIG.getProperty("InternetAddress");

		String host = CONFIG.getProperty("smtp");

		String port = CONFIG.getProperty("port");

		// Get system properties
		Properties properties = System.getProperties();

		// String port = String.valueOf(i);

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port); // or 587
		properties.setProperty("mail.user", "testautomationever@gmail.com");
		properties.setProperty("mail.password", "synoverge@123");
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CONFIG.getProperty("InternetAddress"),
						CONFIG.getProperty("InternetPassword"));
			}

		};

		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);
		Session session = Session.getInstance(properties, auth);
		try {
			MimeMessage message = new MimeMessage(session);

			// Create a default MimeMessage object.
			MimeBodyPart message1 = new MimeBodyPart();

			MimeBodyPart message2 = new MimeBodyPart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			for (int i = 0; i < to.length; i++) {

				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));

			}

			for (int i = 0; i < cc.length; i++) {

				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));

			}

			for (int i = 0; i < bcc.length; i++) {

				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));

			}

			String currBranchName = CONFIG.getProperty("branch");
			if (currBranchName.length() >= 16) {
				currBranchName = CONFIG.getProperty("branch").substring(0, 16);
			} else {
				currBranchName = CONFIG.getProperty("branch");
			}
			message.setSubject("STARTED Auto run of [" + module + "] on [" + " Browser-" + browser + "]");
			String env = CONFIG.getProperty("URL").toString();
			// Now set the actual message
			message1.setText(bodyMessage + "\n" + "Environment=" + env + "\n" + "Module=" + module + "Browser="
					+ browser + "\n\n" + "Regards" + "\n" + author);
			// message.add

			/*
			 * String filename =
			 * System.getProperty("user.dir")+"//config//config.properties";//change
			 * accordingly DataSource source = new FileDataSource(filename);
			 * message2.setDataHandler(new DataHandler(source));
			 * message2.setFileName(filename);
			 */

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(message1);
			// multipart.addBodyPart(message2);

			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return true;
		} catch (Exception e) {
			System.out.println("Message sent failed");
			e.printStackTrace();
			return false;
		}

	}

	private static void addAttachment(Multipart multipart, String filename) {
		DataSource source = new FileDataSource(filename);
		BodyPart messageBodyPart = new MimeBodyPart();
		try {
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
