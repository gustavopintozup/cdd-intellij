package com.cdd.service;

import com.example.demo.utils.RealtimeState;
import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ClientNotificationService {
    private static ClientNotificationService instance;
    public Project project;
//    public static NotificationGroup notificationGroup = new NotificationGroup("NotificationClient.Notify", NotificationDisplayType.BALLOON, false);
//    public static NotificationGroup balloonGroup = new NotificationGroup("NotificationClient.Notify", NotificationDisplayType.STICKY_BALLOON, false);
    private Notification notification;

    private ClientNotificationService() {
    }

    public static ClientNotificationService getInstance() {
        if (instance == null) {
            instance = new ClientNotificationService();
        }
        return instance;
    }

    public void notify(int currentComplexity, int limitOfComplexity) {
        if (RealtimeState.getInstance().isLimitExceededNotification()) {
            if(this.notification != null)
                this.notification.expire();

            this.notification = new Notification("Plugins Suggestion", "<span style='font-size: 12px;'>Cognitive load limit was exceeded</span>", "<strong>Current cognitive load is <span style='color: #BA6F25;'>" + currentComplexity + "</span> and the limit allowed in this project is  <span style='color: #589DF6;'>" + limitOfComplexity + "</span> </strong><br /><strong>It's time to restructure your code.</strong>", NotificationType.ERROR);
//            var action = new NotificationAction.Simple(() -> "Open rules", (anActionEvent, notification1) -> {
//            }, "");
//            this.notification.addAction(action);

            this.notification.setCollapseActionsDirection(Notification.CollapseActionsDirection.KEEP_RIGHTMOST);
            this.notification.notify(project);
        }
    }

    public void clear() {
        NotificationsManager mgr = NotificationsManager.getNotificationsManager();
        Arrays.stream(mgr.getNotificationsOfType(Notification.class, project)).forEach(Notification::expire);
    }


    public void popup(String content) {
        BalloonBuilder balloonBuilder = JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(content, MessageType.INFO, null);
        Balloon balloon = balloonBuilder.setFadeoutTime(TimeUnit.SECONDS.toMillis(1)).createBalloon();
        Balloon.Position pos = Balloon.Position.above;
        balloon.show(RelativePoint.fromScreen(new Point(0, 0)), pos);

    }


}