package com.laros.dtos.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
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


    public static class Builder {
        private String senderName;
        private String senderMail;
        private List<EmailMessage.Recipient> to = new ArrayList<>();
        private List<EmailMessage.Recipient> bcc = new ArrayList<>();
        private List<EmailMessage.Recipient> cc = new ArrayList<>();
        private String htmlContent;
        private String textContent;
        private String subject;
        private EmailMessage.ReplyTo replyTo;
        private List<EmailMessage.Attachment> attachment = new ArrayList<>();
        private Map<String, String> headers = new HashMap<>();
        private Long templateId;
        private Map<String, String> params = new HashMap<>();
        private List<EmailMessage.MessageVersion> messageVersions = new ArrayList<>();
        private List<String> tags = new ArrayList<>();
        private String scheduledAt;
        private String batchId;


        public Builder senderName(String name) {
            this.senderName = name;
            return this;
        }

        public Builder senderMail(String mail) {
            this.senderMail = mail;
            return this;
        }


        public Builder addTo(EmailMessage.Recipient recipient) {
            this.to.add(recipient);
            return this;
        }

        public Builder addBcc(EmailMessage.Recipient recipient) {
            this.bcc.add(recipient);
            return this;
        }

        public Builder addCc(EmailMessage.Recipient recipient) {
            this.cc.add(recipient);
            return this;
        }

        public Builder htmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
            return this;
        }

        public Builder textContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder replyTo(EmailMessage.ReplyTo replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        public Builder addAttachment(EmailMessage.Attachment attachment) {
            this.attachment.add(attachment);
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder templateId(Long templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder addParam(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder addMessageVersion(EmailMessage.MessageVersion messageVersion) {
            this.messageVersions.add(messageVersion);
            return this;
        }

        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public Builder scheduledAt(String scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public Builder batchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public EmailMessage build() {
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setSender(new Sender(this.senderName, this.senderMail));

            if (this.to.isEmpty()) {
                emailMessage.setTo(null);
            } else {
                emailMessage.setTo(this.to);
            }

            if (this.bcc.isEmpty()) {
                emailMessage.setBcc(null);
            } else {
                emailMessage.setBcc(this.bcc);
            }

            if (this.cc.isEmpty()) {
                emailMessage.setCc(null);
            } else {
                emailMessage.setCc(this.cc);
            }

            emailMessage.setHtmlContent(htmlContent);
            emailMessage.setTextContent(textContent);
            emailMessage.setSubject(subject);
            emailMessage.setReplyTo(replyTo);
            emailMessage.setScheduledAt(scheduledAt);
            emailMessage.setTemplateId(templateId);
            emailMessage.setBatchId(batchId);

            if (this.attachment.isEmpty()) {
                emailMessage.setAttachment(null);
            } else {
                emailMessage.setAttachment(this.attachment);
            }
            if (this.headers.isEmpty()) {
                emailMessage.setHeaders(null);
            } else {
                emailMessage.setHeaders(this.headers);
            }
            if (this.params.isEmpty()) {
                emailMessage.setParams(null);
            } else {
                emailMessage.setParams(this.params);
            }
            if (this.messageVersions.isEmpty()) {
                emailMessage.setMessageVersions(null);
            } else {
                emailMessage.setMessageVersions(this.messageVersions);
            }
            if (this.tags.isEmpty()) {
                emailMessage.setTags(null);
            } else {
                emailMessage.setTags(this.tags);
            }

            return emailMessage;
        }

    }
}
