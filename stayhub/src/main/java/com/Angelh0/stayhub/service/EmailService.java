package com.Angelh0.stayhub.service;

public interface EmailService {
    void sendEmailHtml(String to, String subject, String htmlContent);
}
