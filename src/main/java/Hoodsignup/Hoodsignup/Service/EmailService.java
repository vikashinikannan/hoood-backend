package Hoodsignup.Hoodsignup.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetEmail(String toEmail, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset Request - HOOOD");
            message.setText("Hello,\n\n"
                    + "We received a request to reset your password.\n"
                    + "Click the link below to reset your password:\n\n"
                    + resetLink + "\n\n"
                    + "If you did not request this, please ignore this email.\n\n"
                    + "Best Regards,\n"
                    + "HOOOD Team");

            mailSender.send(message);
            System.out.println("✅ Password reset email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}

