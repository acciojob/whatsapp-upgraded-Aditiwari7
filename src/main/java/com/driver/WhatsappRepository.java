package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {

    private Map<Group, List<User>> groupUserMap;
    private Map<Group, List<Message>> groupMessageMap;
    private Map<Group, User> groupAdminMap;
    private Map<String, User> usersMap;
    private Map<Message, User> senderMap;
    private int groupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupUserMap = new HashMap<>();
        this.groupMessageMap = new HashMap<>();
        this.groupAdminMap = new HashMap<>();
        this.usersMap = new HashMap<>();
        this.senderMap = new HashMap<>();
        this.groupCount = 0;
        this.messageId = 0;
    }

    public boolean MobileExist(String mobile){
        if(usersMap.containsKey(mobile)){
            return true;
        }
        return false;
    }
    public void createUserDB(String name, String mobile){
        usersMap.put(mobile, new User(name, mobile));
    }

    public Group createChatDB(List<User> users1){
        Group newGroup = new Group(users1.get(1).getName(), 2);
        groupUserMap.put(newGroup, users1);
        groupAdminMap.put(newGroup, users1.get(0));
        return newGroup;
    }
    public Group createGroupDB(List<User> users2){
        this.groupCount++;
        Group newGroup = new Group("Group "+this.groupCount, users2.size());
        groupUserMap.put(newGroup, users2);
        groupAdminMap.put(newGroup, users2.get(0));
        return newGroup;
    }

    public int createMessageDB(String content){
        this.messageId++;
        Message message = new Message(this.messageId, content, new Date());
        return this.messageId;
    }
    public boolean checkGroup(Group group){
        if(groupUserMap.containsKey(group)){
            return true;
        }
        return false;
    }
    public boolean checkSender(User sender, Group group){
        List<User> users = groupUserMap.get(group);
        if(users.contains(sender)){
            return true;
        }
        return false;
    }
    public int sendMessage(Message message, User sender, Group group){
        List<Message> messages = new ArrayList<>();

        if(groupMessageMap.containsKey(group)){
            messages = groupMessageMap.get(group);
        }

        messages.add(message);
        groupMessageMap.put(group, messages);
        senderMap.put(message, sender);

        return messages.size();
    }

    public boolean checkApprover(User approver, Group group){
        List<User> users = groupUserMap.get(group);
        if(users.get(0) == approver){
            return true;
        }
        return false;
    }

    public void changeAdminDB(User approver, Group group, User user){
        groupAdminMap.put(group, user);
    }

    public int removeUser(User user) throws Exception{
        boolean userFound = false;
        Group currGroup = null;

        for(Group group : groupUserMap.keySet()){
            List<User> users = groupUserMap.get(group);

            for(User currUser : users){
                if(currUser.equals(user)){
                    if(groupAdminMap.get(group).equals(user)){
                        throw new Exception("Cannot remove admin");
                    }

                    currGroup = group;
                    userFound = true;
                    break;
                }
            }
            if(userFound)
                break;
        }

        if(!userFound){
            throw new Exception("User not found");
        }

        List<User> users = groupUserMap.get(currGroup);
        if(users.contains(user)){
            users.remove(user);
        }
        groupUserMap.put(currGroup, users);

        for(Message message : senderMap.keySet()){
            if(senderMap.get(message).equals(user))
                continue;

            senderMap.put(message, senderMap.get(message));
        }

        return groupUserMap.get(currGroup).size() + groupMessageMap.get(currGroup).size() + senderMap.size();
    }


}
