package com.example.RapsBotBeta.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component

public class TgBot extends TelegramLongPollingBot {


    private static final Logger log = LoggerFactory.getLogger(TgBot.class);

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
        row.add(InlineKeyboardButton.builder().text("Курс Euro").callbackData("button1").build());
        row.add(InlineKeyboardButton.builder().text("Курс $").callbackData("button2").build());

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons);

        // Отправка сообщения с кнопками
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбери опцию:");
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            if (callbackData.equals("button1")) {
                // Действие для кнопки 1
                try {
                    execute(new SendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Курс евро: 100 рублей 16 копеек"));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button2")) {
                // Действие для кнопки 2
                try {
                    execute(new SendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Курс доллара: 90 рублей 28 копеек"));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start":
                    startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
                    sendButtons(String.valueOf(chatId));
                    break;
                case "/info":
                    sendButtons(String.valueOf(chatId));
                    break;
                default: sendMessage(chatId, "Прости, я умею пока немного(",
                        "Попробуй команду /info!");
            }

        }

    }



    private void thirtyCommand(long chatId, String name){ //Ответ пользователю

    }


    private void secondCommand(long chatId, String name){ //Ответ пользователю

    }


    private void startCommandRecived(long chatId, String name){ //Ответ пользователю

        String answer = "Привет,"  + name +", приятно познакомиться!";
        String answer1 = "Я пока нахожусь на бета-тесте, но могу предложить тебе пару опций";
        log.info("Replied to user" + name);


        sendMessage(chatId, answer, answer1);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошбика" + e.getMessage());
        }
    }

    private void sendMessage(long chatId, String textToSend, String textToSend2){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));


        message.setText(textToSend);
        try{
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошбика" + e.getMessage());
        }


        message.setText(textToSend2);
        try{
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошбика" + e.getMessage());
        }
    }
}