public class Item {
    private String name;
    private String author;
    private int imageResId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public Item(int imageResId, String name, String author) {
        this.imageResId = imageResId;
        this.name = name;
        this.author = author;
    }
}
