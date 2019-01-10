package hello.model;

public class GreetingData {

    private final long id;
    private final String content;

    public GreetingData(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
