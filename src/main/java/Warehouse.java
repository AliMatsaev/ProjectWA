import java.util.ArrayList;

public class Warehouse {
    private String name;
    private ArrayList<Product> products;

    public Warehouse(String name, ArrayList<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String toString() {
        return "наименование склада: " + this.name;
    }
}
