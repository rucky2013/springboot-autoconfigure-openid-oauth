package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Notice;
import com.alexbt.appdirect.notifications.model.enu.NoticeType;
import com.alexbt.appdirect.notifications.util.Validate;

public class NoticeBuilder {

    private Notice notice = new Notice();

    public static NoticeBuilder builder() {
        return new NoticeBuilder();
    }

    public Notice get() {
        return notice;
    }

    public NoticeBuilder type(NoticeType type) {
        Validate.notNull(type);
        notice.setType(type);
        return this;
    }

    public NoticeBuilder message(String message) {
        Validate.notEmpty(message);
        notice.setMessage(message);
        return this;
    }

}
