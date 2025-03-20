package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account account){
        Account acc = accountService.register(account);
        if (acc == null){//status 400
            return ResponseEntity.status(400).body(null);
        }
        else if (acc.getUsername() == null){
            return ResponseEntity.status(409).body(null);
        }
        else return ResponseEntity.status(200).body(acc);
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        Account a = accountService.login(account);
        if (a == null) return ResponseEntity.status(401).body(null);
        else return ResponseEntity.status(200).body(a);
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> newMessage(@RequestBody Message message){
        Message m = messageService.newMessage(message);
        if (m != null) return ResponseEntity.status(200).body(m);
        else return ResponseEntity.status(400).body(null);
    }

    @GetMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return new ResponseEntity<>(messageService.getMessageById(messageId), HttpStatus.OK);
    }

    @DeleteMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        int confirm = messageService.deleteMessage(messageId);
        if (confirm == 1) {
            return ResponseEntity.status(200).body(confirm);
        }
        else {
            return ResponseEntity.status(200).body(null);
        }
    }

    @PatchMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message){
        int confirm = messageService.updateMessage(messageId, message.getMessageText());
        if (confirm == 1) {
            return ResponseEntity.status(200).body(confirm);
        }
        else {
            return ResponseEntity.status(400).body(confirm);
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody List<Message> getAllMessagesByAccountId(@PathVariable int accountId){
        return messageService.getMessagesByAccountId(accountId);
    }    
}
