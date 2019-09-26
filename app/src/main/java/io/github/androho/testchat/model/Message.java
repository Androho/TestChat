package io.github.androho.testchat.model;

import java.util.Date;

/**
 * Message class with properties <b>textMessage</b> , <b>userMail</b>,
 * <b>userPhotoUrl<b/>, <b>timeMessage</b>,<b>isSend</b>.
 *
 * @version 2.1
 * @autor Vol Peterburg
 */
public class Message {

    /**
     * Field textMessage
     */
    private String textMessage;
    /**
     * Field userMail
     */
    private String userMail;
    /**
     * Field userPhotoUrl
     */
    private String userPhotoUrl;
    /**
     * Field timeMessage
     */
    private long timeMessage;
    /**
     * Field isSend
     */
    private boolean isSend;

    /**
     * Constructor - creating a new object with specific values
     *
     * @param textMessage  - text message
     * @param userMail     - mail user
     * @param userPhotoUrl - url user pic
     * @param isSend       - sending
     * @see Message#Message(String, String, String, boolean)
     */
    public Message(String textMessage, String userMail, String userPhotoUrl, boolean isSend) {
        this.textMessage = textMessage;
        this.userMail = userMail;
        this.isSend = isSend;
        this.userPhotoUrl = userPhotoUrl;
        timeMessage = new Date().getTime();
    }


    public Message() {
    }

    /**
     * Function to get field value {@link Message#textMessage}
     *
     * @return returns message text
     */
    public String getTextMessage() {
        return textMessage;
    }

    /**
     * Text message determination procedure{@link Message#textMessage}
     *
     * @param textMessage - Text message
     */
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    /**
     * Function to get field value {@link Message#userMail}
     *
     * @return returns mail user
     */
    public String getUserMail() {
        return userMail;
    }

    /**
     * User mail determination procedure{@link Message#userMail}
     *
     * @param userMail - mail user
     */
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    /**
     * Function to get field value {@link Message#timeMessage}
     *
     * @return returns time message
     */
    public long getTimeMessage() {
        return timeMessage;
    }

    /**
     * Time Message determination procedure{@link Message#timeMessage}
     *
     * @param timeMessage - mail user
     */
    public void setTimeMessage(long timeMessage) {
        this.timeMessage = timeMessage;
    }

    /**
     * Function to get field value {@link Message#userPhotoUrl}
     *
     * @return returns url user pic
     */
    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    /**
     * User photo URL determination procedure{@link Message#userPhotoUrl}
     *
     * @param userPhotoUrl - URL from user pic
     */
    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    /**
     * Function to get field value {@link Message#isSend()}
     *
     * @return returns boolean is send message
     */
    public boolean isSend() {
        return isSend;
    }

    /**
     * Sending determination procedure{@link Message#isSend()}
     *
     * @param isSend - boolean is send message
     */
    public void setSend(boolean send) {
        isSend = send;
    }

}
