package com.manish.jobportal.models;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String recipientId;

    private Map<String, String> message;

    private String type;

    private boolean read;

    private LocalDateTime timeStamp;

    // Explicit setter for read (needed in NotificationService)
    public void setRead(boolean read) {
        this.read = read;
    }

    // Optional: manual builder if Lombok @Builder doesn't work
    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public static class NotificationBuilder {
        private String id;
        private String recipientId;
        private Map<String, String> message;
        private String type;
        private boolean read;
        private LocalDateTime timeStamp;

        public NotificationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder recipientId(String recipientId) {
            this.recipientId = recipientId;
            return this;
        }

        public NotificationBuilder message(Map<String, String> message) {
            this.message = message;
            return this;
        }

        public NotificationBuilder type(String type) {
            this.type = type;
            return this;
        }

        public NotificationBuilder read(boolean read) {
            this.read = read;
            return this;
        }

        public NotificationBuilder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Notification build() {
            return new Notification(id, recipientId, message, type, read, timeStamp);
        }
    }
}
