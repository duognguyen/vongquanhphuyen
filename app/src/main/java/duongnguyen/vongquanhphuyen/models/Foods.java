package duongnguyen.vongquanhphuyen.models;

public class Foods {
    private String nameFood;
    private String descriptionFood;
    private String imageFood;
    private String[] restaurantIds;

    public Foods(String nameFood, String descriptionFood, String imageFood, String[] restaurantIds) {
        this.nameFood = nameFood;
        this.descriptionFood = descriptionFood;
        this.imageFood = imageFood;
        this.restaurantIds = restaurantIds;
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

    public String[] getRestaurantIds() {
        return restaurantIds;
    }

    public void setRestaurantIds(String[] restaurantIds) {
        this.restaurantIds = restaurantIds;
    }
}
