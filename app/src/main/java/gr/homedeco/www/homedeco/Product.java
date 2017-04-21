package gr.homedeco.www.homedeco;


public class Product {

    private int productID, weight, stock, categoryID, subCategoryID, orderID;
    private double price, discountPrice;
    private String  SKU, name, description, shortDescription, thumbnail, image;

    public Product() {
    }

    public Product(int productID) {
        this.productID = productID;
    }

    public Product(int productID, int weight, int stock, int categoryID, int subCategoryID, int orderID, double price,
                   double discountPrice, String SKU, String name, String description,
                   String shortDescription, String thumbnail, String image) {
        this.productID = productID;
        this.weight = weight;
        this.stock = stock;
        this.categoryID = categoryID;
        this.subCategoryID = subCategoryID;
        this.orderID = orderID;
        this.price = price;
        this.discountPrice = discountPrice;
        this.SKU = SKU;
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.thumbnail = thumbnail;
        this.image = image;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(int subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
