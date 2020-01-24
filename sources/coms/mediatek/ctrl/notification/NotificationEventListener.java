package coms.mediatek.ctrl.notification;

public interface NotificationEventListener {
    void clearAllNotificationData();

    void notifyBlockListChanged(String str);

    void notifyNotificationActionOperate(String str, String str2);

    void notifyNotificationDeleted(String str);
}
