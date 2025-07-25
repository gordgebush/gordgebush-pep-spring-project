package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;



@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        // Validate messageText
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("message text cannot be blank or longer than 255 characters");
        }

        // Validate postedBy (account must exist)
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("postedby must exist to a valid account");
        }

        // Save message
        return messageRepository.save(message);
    }

    public List<Message> getAlMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageByID(int id){
        return messageRepository.findById(id);
    }

    public int deleteMessageByID(int messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById((Integer.valueOf(messageId)));
            return 1;
        }
        return 0;
    }   

    public int updateMessage(int messageId, String text){
        Optional<Message> opMessage = messageRepository.findById(messageId);
        if(opMessage.isPresent() && text != null && !text.isBlank() && text.length() < 255){
            Message message = opMessage.get();
            message.setMessageText(text);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByAccount(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }

}
