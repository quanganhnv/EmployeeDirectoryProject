package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {
    private JavaMailSender mailSender;

    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/sign-in")
    public ModelAndView getSignInPage() {
        ModelAndView mav = new ModelAndView("/sign-in");
        return mav;
    }

    @GetMapping("/")
    public ModelAndView getDefault(){
        ModelAndView mav = new ModelAndView("/employee/index");
        return mav;
    }

    @GetMapping("/index")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/403")
    public String getAccessDeniedPage(){
        return "403";
    }

    @GetMapping("/page-recover-pw")
    public ModelAndView forgotPassword(){
        ModelAndView mav = new ModelAndView("page-recover-pw");
        mav.addObject("messages", "Enter your email address and we'll send you an email with instructions to reset your password.");
        return mav;
    }

    @PostMapping("/page-recover-pw")
    public ModelAndView resetPWwithMail(HttpServletRequest request, ModelAndView mav){
        String email = request.getParameter("email");
        mav = new ModelAndView("/page-recover-pw");
        String token = RandomString.make(30);

        try {
            accountService.updateRememberToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            mav.addObject("messages", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            mav.addObject("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            mav.addObject("error", "Error while sending email");
        }
        return mav;
    }

    @GetMapping("/change_pw")
    public ModelAndView showChangePasswordForm(@Param(value = "token") String token, ModelAndView mav) {
        mav = new ModelAndView("change_pw");
        Account account = accountService.getByRememberToken(token);
        mav.addObject("token", token);

        if (account == null) {
            mav.addObject("messages", "Invalid Token");
            return mav;
        }

        return mav;
    }

    @PostMapping("/change_pw")
    public ModelAndView processChangePassword(HttpServletRequest request, ModelAndView mav) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.getByRememberToken(token);
        mav.addObject("title", "Reset your password");

        if (account == null) {
            mav.addObject("message", "Invalid Token");
            return mav;
        } else {
            accountService.updatePassword(account, password);

            mav.addObject("message", "You have successfully changed your password.");
        }

        return mav;
    }

    public static class Utility {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
