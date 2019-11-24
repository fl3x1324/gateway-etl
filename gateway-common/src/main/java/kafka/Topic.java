package kafka;

public enum Topic {
    TABLE_MAP("tableMap"),
    EXT_WRITE_ROW("extWriteRow"),
    EXT_UPDATE_ROW("extUpdateRow");

    public final String value;

    Topic(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
