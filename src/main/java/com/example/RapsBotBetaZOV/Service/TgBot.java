package com.example.RapsBotBetaZOV.Service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

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

    private void sendButtons(String chatId) {
        // Создание кнопок
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("Кнопка 1").callbackData("button1").build());
        row.add(InlineKeyboardButton.builder().text("Кнопка 2").callbackData("button2").build());

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons);

        // Отправка сообщения с кнопками
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите опцию:");
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
                case "Мне нужны кнопки":
                        thirtyCommand(chatId, update.getMessage().getChat().getFirstName());
                        sendButtons(String.valueOf(chatId));
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


    private void thirtyCommand(long chatId, String name){ //Ответ пользователю

        String answer2 = "Это кнопки для вида";

        sendMessage(chatId, answer2);
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
