package Model.chatbot;

public enum Trigger {

    WELCOME("welcome"),
    EXIST_USER("exist_user"),
    NOT_EXIST_USER("not_exist_user");

    private String id;

    Trigger(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
