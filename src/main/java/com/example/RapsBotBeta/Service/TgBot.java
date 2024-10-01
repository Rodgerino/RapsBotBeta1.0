package com.example.RapsBotBeta.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component

public class TgBot extends TelegramLongPollingBot {

    private final String creatorChatId = "6790348968"; // Замените на ваш Chat ID

    private boolean awaitingMessage = false;

    private static final Logger log = LoggerFactory.getLogger(TgBot.class);

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
                    try {
                        startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/sendMessage":
                    awaitingMessage = true;
                    sendMessage(chatId, "Отправь то,что бы хотел видеть в канале");
                    break;
                default:
                    if (awaitingMessage) {
                        sendMessage(Long.parseLong(creatorChatId), "Сообщение от пользователя " + chatId + ": " + messageText);
                        awaitingMessage = false; // Сброс состояния после получения сообщения
                        sendMessage(chatId, "Сообщение отправлено создателю.");
                    } else{
                        sendMessage(chatId, "Неизвестная команда. Используйте /start или /sendMessage.");
                    }
                    if (update.getMessage().hasPhoto()) {
                            handlePhoto(update.getMessage().getPhoto(), chatId);
                    }else if (update.getMessage().hasVideo()) {
                    handleVideo(update.getMessage().getVideo(), chatId);
                }
            }
        }

    }



    private void handlePhoto(List<PhotoSize> photos, long chatId) {
        if (awaitingMessage) {
            // Получаем самый большой размер фото
            PhotoSize photo = photos.stream().max((p1, p2) -> Integer.compare(p1.getFileSize(), p2.getFileSize())).orElse(null);
            if (photo != null) {
                sendPhotoToCreator(photo);
                awaitingMessage = false; // Сброс состояния после получения фото
                sendMessage(chatId, "Фото отправлено создателю.");
            }
        } else {
            sendMessage(chatId, "Неизвестная команда. Используйте /start или /await.");
        }
    }

    private void handleVideo(Video video, long chatId) {
        if (awaitingMessage) {
            sendVideoToCreator(video);
            awaitingMessage = false; // Сброс состояния после получения видео
            sendMessage(chatId, "Видео отправлено создателю.");
        } else {
            sendMessage(chatId, "Неизвестная команда. Используйте /start или /await.");
        }
    }

    private void sendVideoToCreator(Video video) {
        try {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(String.valueOf(creatorChatId));
            sendVideo.setVideo(video.getFileId()); // Отправляем видео по ID
            execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPhotoToCreator(PhotoSize photo) {
        try { SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(creatorChatId));
            sendPhoto.setPhoto(photo.getFileId(creatorChatId)); // Отправляем фото по ID
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    private void thirtyCommand(long chatId, String name){ //Ответ пользователю

    }


    private void secondCommand(long chatId, String name){ //Ответ пользователю

    }


    private void startCommandRecived(long chatId, String name) throws InterruptedException { //Ответ пользователю

        String answer = "Привет,"  + name +", приятно познакомиться!";
        String answer1 = "Я - бот предложка! Я больше ничего не умею кроме как принимать сообщения и отсылать их админу!";
        Thread.sleep(2000);
        String answer2 = "Чтобы отослать что-то в предложку, напиши: /sendMessege";
        log.info("Replied to user" + name);


        sendMessage(chatId, answer, answer1, answer2);
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

    private void sendMessage(long chatId, String textToSend, String textToSend2, String textToSend3){
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

        message.setText(textToSend3);
        try{
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошбика" + e.getMessage());
        }
    }
}