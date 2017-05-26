package model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "product.all", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "product.id", query = "SELECT p FROM Product p WHERE p.id = :id")
})

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Double price;
    @Enumerated(EnumType.STRING)
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
