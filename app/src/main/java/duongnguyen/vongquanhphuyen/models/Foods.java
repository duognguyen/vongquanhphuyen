package duongnguyen.vongquanhphuyen.models;

public class Foods {
    private String nameFood;
    private String descriptionFood;
    private String imageFood;

    public Foods(String nameFood, String descriptionFood, String imageFood) {
        this.nameFood = nameFood;
        this.descriptionFood = descriptionFood;
        this.imageFood = imageFood;
    }

    public Foods() {
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescriptionFood() {
        return descriptionFood;
    }

    public void setDescriptionFood(String descriptionFood) {
        this.descriptionFood = descriptionFood;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

}
