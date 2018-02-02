package gr.homedeco.www.homedeco.product;

import java.util.ArrayList;

public class CustomProduct {

    private Integer id, categoryId;
    private double price;
    private String name, part, image;
    private ArrayList<CPart> cParts;

    public CustomProduct() {
        this.cParts = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /* Custom Product Part */

    public class CPart {
        private Integer id;
        private double price;

        public CPart() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public CPart createCPart() {
        return new CPart();
    }

    public void addCPart(CPart p) {
        this.cParts.add(p);
    }

    public ArrayList<CPart> getCParts() {
        return this.cParts;
    }

    public void clearCustomProduct() {
        categoryId = 0;
        cParts = new ArrayList<>();
    }
}
