package com.myorg.email;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController 
{
    @Autowired
    private JavaMailSender sender;

    @PostMapping("/sendemail")
    String home(@Valid @RequestBody EmailRequest emailRequest) 
    {
        try 
        {
            sendEmail(emailRequest.getEmailAddress(), emailRequest.getSubject(), emailRequest.getBodyText());
            return "Email Sent!";
        }
		catch(Exception ex) 
		{
            return "Error in sending email: "+ex;
        }
    }

    private void sendEmail(String address, String subject, String text) throws Exception
	{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(address);
        helper.setText(text);
        helper.setSubject(subject);
		sender.send(message);
    }
}
