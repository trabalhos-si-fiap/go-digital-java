package com.laroz.dtos.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailMessage {
    private Sender sender;
    private List<Recipient> to;
    private List<Recipient> bcc;
    private List<Recipient> cc;
    private String htmlContent;
    private String textContent;
    private String subject;
    private ReplyTo replyTo;
    private List<Attachment> attachment;
    private Map<String, String> headers;
    private Long templateId;
    private Map<String, String> params;
    private List<MessageVersion> messageVersions;
    private List<String> tags;
    private String scheduledAt;
    private String batchId;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sender {
        private String name;
        private String email;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recipient {
        private String name;
        private String email;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReplyTo {
        private String email;
        private String name;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attachment {
        private String url;
        private String content;
        private String name;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageVersion {
        private List<Recipient> to;
        private List<Recipient> bcc;
        private List<Recipient> cc;
        private String htmlContent;
        private String textContent;
        private String subject;
        private ReplyTo replyTo;
        private Map<String, String> params;
    }
}
