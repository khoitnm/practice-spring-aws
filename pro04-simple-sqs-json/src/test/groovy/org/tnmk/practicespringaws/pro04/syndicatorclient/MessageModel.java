package org.tnmk.practicespringaws.pro04.syndicatorclient;

public class MessageModel {
    private Integer oidPropertyClient; // PID == oidPropertyClient
    private Integer oidIdsClient; // IDS == Account
    private String messageId;

    public Integer getOidPropertyClient() {
        return oidPropertyClient;
    }

    public void setOidPropertyClient(Integer oidPropertyClient) {
        this.oidPropertyClient = oidPropertyClient;
    }

    public Integer getOidIdsClient() {
        return oidIdsClient;
    }

    public void setOidIdsClient(Integer oidIdsClient) {
        this.oidIdsClient = oidIdsClient;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "oidPropertyClient=" + oidPropertyClient +
                ", oidIdsClient=" + oidIdsClient +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
