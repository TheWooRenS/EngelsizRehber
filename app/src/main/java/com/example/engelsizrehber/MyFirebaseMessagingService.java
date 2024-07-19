package com.example.engelsizrehber;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Gelen bildirimi işleyin
        // Örnek: Bildirimi gösterme
        // String title = remoteMessage.getNotification().getTitle();
        // String body = remoteMessage.getNotification().getBody();
        // NotificationUtils.displayNotification(getApplicationContext(), title, body);
    }
}
