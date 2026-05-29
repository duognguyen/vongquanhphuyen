package duongnguyen.vongquanhphuyen.models;

public class NoteItem {
    private String title;
    private String content;
    private String icon;
    private String category;

    public NoteItem() {
    }

    public NoteItem(String title, String content, String icon, String category) {
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
