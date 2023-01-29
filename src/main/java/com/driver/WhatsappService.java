package com.driver;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile){
        if(whatsappRepository.MobileExist(mobile)){
            throw new RuntimeException();
        }

        whatsappRepository.createUserDB(name, mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> user){
        if(user.size() == 2){
            return whatsappRepository.createChatDB(user);
        }
        return whatsappRepository.createGroupDB(user);
    }

    public int createMessage(String content){
        return whatsappRepository.createMessageDB(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(whatsappRepository.checkGroup(group) == false){
            throw new Exception("Group does not exist");
        }
        else if(whatsappRepository.checkSender(sender, group) == false){
            throw new Exception("You are not allowed to send message");
        }

        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(whatsappRepository.checkGroup(group) == false){
            throw new Exception("Group does not exist");
        } else if (whatsappRepository.checkApprover(approver, group) == false) {
            throw new Exception("Approver does not have rights");
        } else if (whatsappRepository.checkSender(user, group) == false) {
            throw new Exception("User is not a participant");
        }

        whatsappRepository.changeAdminDB(approver, group, user);
        return "SUCCESS";
    }

    public int removeUser(User user) throws Exception {
        return whatsappRepository.removeUser(user);
    }
}
