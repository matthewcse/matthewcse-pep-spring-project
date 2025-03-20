package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;



@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    
    public Message newMessage(Message message){
        Optional<Account> a = accountRepository.findById(message.getPostedBy());
        if (a.isPresent() && message.getMessageText().length() > 0 && message.getMessageText().length() <= 255){
            Message m = messageRepository.save(message);
            return m;
        }
        else return null;
    }
    
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        Optional<Message> m = messageRepository.findById(messageId);
        if (m.isPresent()){
            Message me = m.get();
            return me;
        } 
        else return null;
    }

    public int deleteMessage(int messageId){
        Optional<Message> m = messageRepository.findById(messageId);
        if (m.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        else return 0;
    }

    public int updateMessage(int messageId, String message){
        Optional<Message> m = messageRepository.findById(messageId);
        if (m.isPresent()){
            Message mess = m.get();
            if (message.length() > 0 && message.length() <= 255){
                mess.setMessageText(message);
                return 1;
            }
            else return 0;
        }
        else return 0;
    }

    public List<Message> getMessagesByAccountId(int accountId){
        //Optional<Account> a = accountRepository.findById(accountId);
        // if (a.isPresent()){
        return messageRepository.findByPostedBy(accountId);
        // }   
        // else return new List<Message>(); 
    }
}
