package com.zj.caoshangfei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

//@Configuration
public class ExceptionConfiguration {

    @Autowired
    private JavaMailSender mailSender;

    @ControllerAdvice
    public class GlobalDefaultExceptionHandler {

        public static final String DEFAULT_ERROR_VIEW = "error/5xx.html";

        @Autowired
        ExceptionConfiguration config;

        @Value("${spring.profiles.active}")
        private String activeProfile;

        @Value("${spring.mail.username}")
        private String from;

        private String[] recipients = new String[]{"jerry.chen@fuwo.com", "jin.zhang@fuwo.com"};

        @ExceptionHandler(value = Exception.class)
        public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

            String uri = req.getRequestURI();
            String debug = getContent(req, e);

            if ("prod".equals(activeProfile)) {
                config.sendAdminMail("[Xgt] Exception Message: " + e.getMessage(), debug, from, recipients);
            }

            if (AnnotationUtils.findAnnotation
                    (e.getClass(), ResponseStatus.class) != null)
                throw e;

            if (uri.startsWith("/api")) {
                throw e;
            }

            ModelAndView mav = new ModelAndView();
            mav.setViewName(DEFAULT_ERROR_VIEW);
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

            if (!"prod".equals(activeProfile)) {
                mav.addObject("debug", debug);
                e.printStackTrace();
            }

            return mav;
        }


        private String getContent(HttpServletRequest req, Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("[General] \n");
            sb.append("  Path: " + req.getRequestURL() + (req.getQueryString() != null ? "?" + req.getQueryString() : "") + "\n");

            sb.append("\n");
            sb.append("[Header] \n");
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = req.getHeader(name);
                sb.append("  " + name + ": " + value + "\n");
            }

            sb.append("\n");
            sb.append("[Cookies] \n");
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : req.getCookies()) {
                    sb.append("  " + cookie.getName() + "=" + cookie.getValue() + " ");
                    sb.append("[path: " + cookie.getPath() + ", ");
                    sb.append("domain: " + cookie.getDomain() + ", ");
                    sb.append("maxAge: " + cookie.getMaxAge() + ", ");
                    sb.append("version: " + cookie.getVersion() + ", ");
                    sb.append("secure: " + cookie.getSecure() + ", ");
                    sb.append("comment: " + cookie.getComment() + "]");
                    sb.append("\n");
                }
            }

            sb.append("\n");
            sb.append("[Session] \n");
            HttpSession session = req.getSession(false);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    Object value = session.getAttribute(name);
                    sb.append("  " + name + "=" + value + "\n");
                }
            }

            sb.append("\n");
            sb.append("[Remote] \n");
            sb.append("  Address: " + req.getRemoteAddr() + "\n");
            sb.append("  Host: " + req.getRemoteHost() + "\n");
            sb.append("  Port: " + req.getRemotePort() + "\n");

            sb.append("\n");
            sb.append("[Exception] \n");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            sb.append("  StackTrace: " + sw.toString());

            return sb.toString();
        }

    }

    @Async
    public void sendAdminMail(String subject, String content, String from, String... recipients) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipients);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
