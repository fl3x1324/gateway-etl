package tech.minkov.kafka;

import tech.minkov.data.MutationType;

public enum Topic {
    RECORD_INSERT("record_insert"),
    RECORD_UPDATE("record_update"),
    RECORD_DELETE("record_delete");

    private final String value;

    Topic(String value) {
        this.value = value;
    }

    public static Topic fromDataMutationType(MutationType type) {
        return switch (type) {
            case INSERTION -> RECORD_INSERT;
            case UPDATE -> RECORD_UPDATE;
            case DELETION -> RECORD_DELETE;
        };
    }

    @Override
    public String toString() {
        return this.value;
    }
}
