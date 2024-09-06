package com.example.RapsBotBetaZOV.Service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component

public class TgBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "RaspBotBetaTest1Bot";
    }

    @Override
    public String getBotToken() {
        return "7545317596:AAFZHu0ORjLTp56BNxuuUf9bsGFq7rwnvWE";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                        startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                case "Что ты умеешь?":
                        secondCommand(chatId, update.getMessage().getChat().getFirstName());
                        break;
                case "что ты умеешь?":
                    secondCommand(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "что ты умеешь":
                    secondCommand(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default: sendMessage(chatId, "Прости, я умею пока немного :(");
            }

        }

    }



    private void secondCommand(long chatId, String name){ //Ответ пользователю

        String answer = "Я не умею ничего, но у меня большой потенциал!";

        sendMessage(chatId, answer);
    }

    private void startCommandRecived(long chatId, String name){ //Ответ пользователю

        String answer = "Привет,"  + name +", приятно познакомиться!";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
