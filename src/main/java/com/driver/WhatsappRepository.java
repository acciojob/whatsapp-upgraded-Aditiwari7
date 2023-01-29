package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {

    private Map<Group, List<User>> groupUserMap;
    private Map<Group, List<Message>> groupMessageMap;
    private Map<Group, User> groupAdminMap;
    private Map<String, User> usersMap;
    private int groupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupUserMap = new HashMap<>();
        this.groupMessageMap = new HashMap<>();
        this.groupAdminMap = new HashMap<>();
        this.usersMap = new HashMap<>();
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
        Group newGroup = new Group("Group"+this.groupCount, users2.size());
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
        List<Message> messageList = new ArrayList<>();

        if(groupMessageMap.containsKey(group)){
            messageList = groupMessageMap.get(group);
        }

        messageList.add(message);
        groupMessageMap.put(group, messageList);

        return messageList.size();
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
        int total = 0;

        if(groupAdminMap.containsValue(user)){
            throw new Exception("Cannot remove admin");
        }else{
            for(Group group : groupUserMap.keySet()){
                List<User> user1 = groupUserMap.get(group);
                if(user1.contains(user)){
                    groupUserMap.remove(group);
                    total = groupUserMap.size() + groupMessageMap.size();
                }else{
                    throw new Exception("User not found");
                }
            }
        }

        return total + this.messageId;
    }


}
