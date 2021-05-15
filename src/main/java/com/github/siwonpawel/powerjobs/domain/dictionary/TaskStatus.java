package com.github.siwonpawel.powerjobs.domain.dictionary;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

public enum TaskStatus {

    PREPARED("task.status.prepared"),
    RUNNING("task.status.running"),
    FINISHED("task.status.finished");

    public static final String RESOURCEBUNDLE_TASK_STATUS_RESOURCE = "resourcebundle.taskStatusResource";

    private final String resourceValue;

    TaskStatus(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public String getDisplayValue() {
        return getDisplayValue(LocaleContextHolder.getLocale());
    }

    public String getDisplayValue(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCEBUNDLE_TASK_STATUS_RESOURCE, locale);
        return bundle.getString(resourceValue);
    }
}
