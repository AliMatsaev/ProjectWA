import java.io.FileWriter;
import java.io.IOException;


public class Document {
    private int number;

    public Document(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void entranceOrSelling(int number, String name, int count, int price, String type, String warehouse) {
        FileWriter writer;

        if (type.equals("entrance")) {
            try {
                writer = new FileWriter("document№" + number + ".txt");
                String string = "document of entrance №" + number + "\n" + "product: " + name + "\n"
                        + "count: " + count + "\n" + "purchase price: " + price + "\n" + "warehouse: " + warehouse;
                writer.write(string);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Возникла ошибка записи документа в файл. Попробуйте еще раз.");
            }
        } else {
            try {
                writer = new FileWriter("document№" + number + ".txt");
                String string = "document of selling №" + number + "\n" + "product: " + name + "\n"
                        + "count: " + count + "\n" + "sale price: " + price + "\n" + "warehouse: " + warehouse;
                writer.write(string);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Возникла ошибка записи документа в файл. Попробуйте еще раз.");
            }
        }
    }


    public void transfer(int number, String name, int count, String warehouseFrom, String warehouseTo) {
        try {
            FileWriter writer = new FileWriter("document№" + number + ".txt");
            String string = "document of transfer №" + number + "\n" + "product: " + name + "\n"
                    + "count: " + count + "\n" + "warehouse from: " + warehouseFrom + "\n" + "warehouse to: " + warehouseTo;
            writer.write(string);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка записи документа в файл. Попробуйте еще раз.");
        }
    }
}
