package duongnguyen.vongquanhphuyen.models;

public class Comment {
    private String content;
    private String destinationId;
    private String timestamp;
    private String userAvatar;
    private String userId;
    private String userName;

    public Comment() {}

    public Comment(String content, String destinationId, String timestamp, String userAvatar, String userId, String userName) {
        this.content = content;
        this.destinationId = destinationId;
        this.timestamp = timestamp;
        this.userAvatar = userAvatar;
        this.userId = userId;
        this.userName = userName;
    }

    // Getter và Setter cho tất cả các trường
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getDestinationId() { return destinationId; }
    public void setDestinationId(String destinationId) { this.destinationId = destinationId; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}