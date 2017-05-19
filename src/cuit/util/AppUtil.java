package cuit.util;

import cuit.service.CommentService;
import cuit.service.MessageService;
import cuit.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Esong on 2017/4/9.
 */
public class AppUtil {
    private static ApplicationContext applicationContext = null;
    private static UserService userService = null;
    private static MessageService messageService = null;
    private static CommentService commentService = null;
    public static ApplicationContext getAppContext() {
        if (applicationContext == null){
            applicationContext = new ClassPathXmlApplicationContext("spring/config/BeanLocation.xml");
        }
        return applicationContext;
    }

    public static UserService getUserService(){
        if (userService == null){
            userService = (UserService) getAppContext().getBean("userService");
        }
        return userService;
    }

    public static MessageService getMessageService(){
        if (messageService == null){
            messageService = (MessageService)getAppContext().getBean("messageService");
        }
        return messageService;
    }
    public static CommentService getCommentService(){
        if (commentService == null){
            commentService = (CommentService)getAppContext().getBean("commentService");
        }
        return commentService;
    }
}
