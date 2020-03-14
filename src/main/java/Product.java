public class Product {
    private String name;
    private int articleNumber;
    private int lastPurchasePrice;
    private int lastSalePrice;
    private int count = 0;

    public Product(String name, int articleNumber, int lastPurchasePrice, int lastSalePrice) {
        this.name = name;
        this.articleNumber = articleNumber;
        this.lastPurchasePrice = lastPurchasePrice;
        this.lastSalePrice = lastSalePrice;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public int getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(int lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public int getLastSalePrice() {
        return lastSalePrice;
    }

    public void setLastSalePrice(int lastSalePrice) {
        this.lastSalePrice = lastSalePrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return "наименование товара: " + this.name + ", артикул: " + this.articleNumber +
                ", цена последней закупки: " + this.lastPurchasePrice + ", цена последней продажи: " + this.lastSalePrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Product product = (Product) obj;
        return name == product.name;

    }
}
