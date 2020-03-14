import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//
public class Menu {
    public static HashMap<String, Product> allCreatedProducts = new HashMap<>();
    public static HashMap<String, Warehouse> allCreatedWarehouses = new HashMap<>();
    public static HashMap<Integer, Document> allCreatedDocuments = new HashMap<>();
    public static Scanner scanner = new Scanner(System.in);
    public static Gson gson = new Gson();

    public void mainMenu() {
        boolean exit = true;
        while (exit) {
            System.out.println("Для работы с программой введите цифру, соответствующую нужной Вам команде:");
            System.out.println("1. Создание в базе товара");
            System.out.println("2. Создание в базе склада");
            System.out.println("3. Просмотр товара по имени");
            System.out.println("4. Просмотр склада");
            System.out.println("5. Редактирование товара");
            System.out.println("6. Удаление товара из базы");
            System.out.println("7. Удаление склада из базы");
            System.out.println("8. Отчет существующих товаров");
            System.out.println("9. Создание и просмотр документа поступления на склад");
            System.out.println("10. Создание и просмотр документа списания со склада");
            System.out.println("11. Создание и просмотр документа перемещения между складами");
            System.out.println("12. Отчет остатков товара по складам");
            System.out.println("13. Выход из программы");

            int choise = 0;
            while (true) {
                try {
                    choise = Integer.parseInt(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Возникла ошибка ввода. Пожалуйста, повторите команду.\n");
                    break;
                }
            }
            switch (choise) {
                case 1:
                    createProduct(allCreatedProducts);
                    break;
                case 2:
                    createWarehouse(allCreatedWarehouses);
                    break;
                case 3:
                    showProduct(allCreatedProducts);
                    break;
                case 4:
                    showWarehouse(allCreatedWarehouses);
                    break;
                case 5:
                    editProduct(allCreatedProducts);
                    break;
                case 6:
                    removeProduct(allCreatedProducts);
                    break;
                case 7:
                    removeWarehouse(allCreatedWarehouses);
                    break;
                case 8:
                    showAllProducts(allCreatedProducts);
                    break;
                case 9:
                    doEntrance();
                    break;
                case 10:
                    doSelling();
                    break;
                case 11:
                    doTransfer();
                    break;
                case 12:
                    getReport();
                    break;
                case 13:
                    exit = false;
                    scanner.close();
                    break;
                default:
                    System.out.println("Возможно вы ввели некорректную команду, попробуйте снова.\n");
            }
        }
    }

    public void createProduct(HashMap<String, Product> allCreatedProducts) {
        try {
            System.out.println("Для создания товара введите его параметры через запятую в следующем порядке и формате:");
            System.out.println("наименование товара, артикул, цена последней закупки, цена последней продажи");

            String string = scanner.nextLine();
            String[] array = string.split(",");

            Product product = new Product(array[0], Integer.parseInt(array[1].trim()), Integer.parseInt(array[2].trim()), Integer.parseInt(array[3].trim()));

            FileWriter writer = new FileWriter(product.getName() + ".json");
            String temporary = gson.toJson(product);
            writer.write(temporary);
            writer.flush();
            writer.close();
            allCreatedProducts.put(product.getName(), product);

            System.out.println("Товар успешно создан в базе.\n");
            System.out.println(product.toString());

        } catch (Exception e) {
            System.out.println("Не удалось создать товар, скорее всего возникла ошибка ввода. Попробробуйте не ставить пробел после запятой.\n");
        }
    }

    public void createWarehouse(HashMap<String, Warehouse> allCreatedWarehouses) {
        System.out.println("Для создания склада введите его наименование буквами:");

        String string = scanner.nextLine();

        if (string.isEmpty() || string.trim().isEmpty()) {
            System.out.println("Не удалось создать склад. Скорее всего вы ввели некорректное наименование" +
                    " или случайно ввели пустую строку. Попробуйте снова.\n");
        }

        Warehouse warehouse = new Warehouse(string, new ArrayList<Product>());
        allCreatedWarehouses.put(warehouse.getName(), warehouse);
        System.out.println("Склад " + warehouse.getName() + " успешно создан в базе.\n");
    }

    public void showProduct(HashMap<String, Product> allCreatedProducts) {
        if (allCreatedProducts.isEmpty()) {
            System.out.println("В базе нет товаров, сначала создайте в базе товар(ы).\n");
            return;
        }

        System.out.println("Введите точное наименование интересующего Вас товара:");
        String string = scanner.nextLine();

        if (allCreatedProducts.containsKey(string)) {
            System.out.print("Информация о товаре: ");
            System.out.println(allCreatedProducts.get(string).toString());
        } else {
            System.out.println("Такого товара не существует, убедитесь в правильности наименования или создайте новый товар.\n");
        }
    }

    public void showWarehouse(HashMap<String, Warehouse> allCreatedWarehouses) {
        if (allCreatedWarehouses.isEmpty()) {
            System.out.println("В базе нет складов, сначала создайте в базе склад(ы).\n");
            return;
        }

        System.out.println("Введите точное наименование интересующего Вас склада:");
        String string = scanner.nextLine();

        if (allCreatedWarehouses.containsKey(string)) {
            System.out.print("Информация о складе: ");
            System.out.println(allCreatedWarehouses.get(string).toString());
            System.out.println("Ниже список названий всех созданных в базе складов:");
            allCreatedWarehouses.values().forEach(warehouse -> System.out.print(warehouse.getName() + ","));
            System.out.println("Для просмотра содержащихся на складе товаров выберете в основном меню \"Отчет остатков товара на складах\".\n");
        } else {
            System.out.println("Такого склада не существует, убедитесь в правильности наименования или создайте новый склад.\n");
        }
    }

    public void editProduct(HashMap<String, Product> allCreatedProducts) {
        if (allCreatedProducts.isEmpty()) {
            System.out.println("В базе нет созданных товаров, сначала создайте товар(ы) в базе.\n");
            return;
        }

        System.out.println("Введите наименование товара, который хотите отредактировать:");
        String string = scanner.nextLine();

        if (allCreatedProducts.containsKey(string)) {
            System.out.println("На текущий момент у товара заданы следующие параметры: ");
            System.out.println(allCreatedProducts.get(string).toString());
            System.out.println("Для редактирования товара введите его новые параметры через запятую в следующем порядке и формате:");
            System.out.println("наименование товара, артикул, цена последней закупки, цена последней продажи");

            try {
                String newString = scanner.nextLine();
                String[] array = newString.split(",");
                allCreatedProducts.remove(string, allCreatedProducts.get(string));

                Product product = new Product(array[0], Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3]));
                allCreatedProducts.put(product.getName(), product);
                System.out.println("Товар успешно изменен.\n");
            }
            catch (Exception e) {
                System.out.println("Не удалось изменить товар, скорее всего возникла ошибка ввода. Попробробуйте снова.\n");
            }
        } else {
            System.out.println("Такого товара не существует в базе, проверьте правильность введенного наименования.\n");
        }
    }

    public void removeProduct(HashMap<String, Product> allCreatedProducts) {
        if (allCreatedProducts.isEmpty()) {
            System.out.println("В базе нет созданных товаров, сначала создайте товар(ы) в базе.\n");
            return;
        }

        System.out.println("Введите наименование товара, который нужно удалить:");
        String string = scanner.nextLine();

        if (allCreatedProducts.remove(string, allCreatedProducts.get(string))) {
            System.out.println("Товар успешно удален из базы.\n");
        } else {
            System.out.println("Не удалось удалить товар из базы. Проверьте правильность введенных данных.\n");
        }
    }

    public void removeWarehouse(HashMap<String, Warehouse> allCreatedWarehouses) {
        if (allCreatedWarehouses.isEmpty()) {
            System.out.println("В базе нет созданных складов, сначала создайте склад(ы) в базе.\n");
            return;
        }

        System.out.println("Введите наименование склада, который нужно удалить:");
        String string = scanner.nextLine();

        if (allCreatedWarehouses.remove(string, allCreatedWarehouses.get(string))) {
            System.out.println("Склад успешно удален из базы.\n");
        } else {
            System.out.println("Не удалось удалить склад из базы. Проверьте правильность введенных данных.\n");
        }
    }

    public void showAllProducts(HashMap<String, Product> allCreatedProducts) {
        if (!allCreatedProducts.isEmpty()) {
            try {
                FileWriter writer = new FileWriter("generalListOfProducts.json");
                writer.write(gson.toJson(allCreatedProducts, new TypeToken<HashMap<String, Product>>() {}.getType()));
                writer.flush();
                writer.close();
                System.out.println("Файл с информацией загрузился в папку проекта.");
                System.out.println("Общий список имеющихся в базе товаров: ");
                allCreatedProducts.values().forEach(product -> System.out.println(product.toString()));
                System.out.println("\n" + "Введите название интересующего Вас товара:");
                String string = scanner.nextLine();

                if (allCreatedProducts.containsKey(string)) {
                    FileWriter fw = new FileWriter(string + ".json");
                    fw.write(gson.toJson(allCreatedProducts.get(string)));
                    fw.flush();
                    fw.close();
                    System.out.println("Информация о товаре:");
                    System.out.println(allCreatedProducts.get(string));
                    System.out.println("Файл с информацией загрузился в папку проекта.");
                } else {
                    System.out.println("Такого товара не существует, проверьте правильность ввода.");
                }
            } catch (IOException e) {
                System.out.println("Не удалось создать файл с отчетом. Повторите попытку.");
            }
        } else {
            System.out.println("В базе нет созданных товаров, сначала создайте товар(ы) в базе.\n");
        }
    }

    public void doEntrance() {
        if (allCreatedWarehouses.isEmpty() && allCreatedProducts.isEmpty()) {
            System.out.println("В базе нет складов или товаров. Проверьте, чтобы был создан хотя бы 1 склад и 1 товар.\n");
            return;
        }

        try {
            System.out.println("Введите параметры создаваемого документа поступления на склад через запятую в формате:");
            System.out.println("название склада, название товара, количество товара(цифра), номер документа, цена закупки\n");
            String string = scanner.nextLine();
            String[] array = string.split(",");

            Product product = allCreatedProducts.get(array[1]);
            product.setCount(Integer.parseInt(array[2]));
            product.setLastPurchasePrice(Integer.parseInt(array[4]));
            allCreatedWarehouses.get(array[0]).getProducts().add(product);
            Document document = new Document(Integer.parseInt(array[3]));
            String type = "entrance";
            document.entranceOrSelling(document.getNumber(), product.getName(), product.getCount(), product.getLastPurchasePrice(), type, array[0]);
            allCreatedDocuments.put(document.getNumber(), document);
            System.out.println("Поступление на склад прошло успешно. Документ прихода загружен в папку проекта.\n");
        } catch (Exception e) {
            System.out.println("Не удалось создать документ поступления. Скорее всего возникли ошибки ввода данных. Попробробуйте не ставить пробел после запятой.\n");
        }
    }

    public void doSelling() {
        if (allCreatedWarehouses.isEmpty() && allCreatedProducts.isEmpty()) {
            System.out.println("В базе нет складов или товаров. Проверьте, чтобы был создан хотя бы 1 склад и 1 товар.");
            return;
        }

        try {
            System.out.println("Введите параметры создаваемого документа списания со склада через запятую в формате:");
            System.out.println("название склада, название товара, количество товара(цифра), номер документа, цена продажи\n");
            String string = scanner.nextLine();
            String[] array = string.split(",");

            Product product = allCreatedProducts.get(array[1]);
            int i = allCreatedWarehouses.get(array[0]).getProducts().indexOf(product);
            int oldCount = allCreatedWarehouses.get(array[0]).getProducts().get(i).getCount();
            int newCount = Integer.parseInt(array[2]);
            if (oldCount < newCount) {
                System.out.println("Вы пытаетесь списать товаров больше, чем есть на складе. Проверьте правильность введенных данных.\n");
                return;
            }

            allCreatedWarehouses.get(array[0]).getProducts().remove(i);
            product.setCount(oldCount - newCount);
            product.setLastSalePrice(Integer.parseInt(array[4]));
            allCreatedWarehouses.get(array[0]).getProducts().add(product);

            Document document = new Document(Integer.parseInt(array[3]));
            String type = "selling";
            document.entranceOrSelling(document.getNumber(), product.getName(), newCount, product.getLastSalePrice(), type, array[0]);
            allCreatedDocuments.put(document.getNumber(), document);
            System.out.println("Списание со склада прошло успешно. Документ списания загружен в папку проекта.\n");
        } catch (Exception e) {
            System.out.println("Не удалось создать документ поступления. Скорее всего возникли ошибки ввода данных. Попробробуйте не ставить пробел после запятой.\n");
        }
    }

    public void doTransfer() {
        if (allCreatedWarehouses.size() < 2 && allCreatedProducts.isEmpty()) {
            System.out.println("В базе нет складов или товаров. Проверьте, чтобы было создано минимум 2 склада и 1 товар.");
            return;
        }
        try {
            System.out.println("Введите параметры создаваемого документа перемещения между складами через запятую в формате:");
            System.out.println("Откуда перемещать(название склада), куда перемещать(название склада), название перемещаемого товара, количество  товара(цифра), номер документа\n");
            String string = scanner.nextLine();
            String[] array= string.split(",");

            Product product = allCreatedProducts.get(array[2]);
            int indexFrom = allCreatedWarehouses.get(array[0]).getProducts().indexOf(product);
            int indexTo = allCreatedWarehouses.get(array[1]).getProducts().indexOf(product);
            int oldCount = allCreatedWarehouses.get(array[0]).getProducts().get(indexFrom).getCount();
            int newCount = Integer.parseInt(array[3]);
            if (oldCount < newCount) {
                System.out.println("Вы пытаетесь переместить товаров больше, чем есть на складе. Проверьте правильность введенных данных. Попробробуйте не ставить пробел после запятой.\n");
                return;
            }

            if (allCreatedWarehouses.get(array[1]).getProducts().contains(product)) {
                int count = allCreatedWarehouses.get(array[1]).getProducts().get(indexTo).getCount();
                allCreatedWarehouses.get(array[1]).getProducts().get(indexTo).setCount(count + newCount);
            } else {
                product.setCount(newCount);
                allCreatedWarehouses.get(array[1]).getProducts().add(product);
            }

            if (newCount == oldCount) {
                allCreatedWarehouses.get(array[0]).getProducts().remove(indexFrom);
            } else {
                allCreatedWarehouses.get(array[0]).getProducts().get(indexFrom).setCount(oldCount - newCount);
            }

            Document document = new Document(Integer.parseInt(array[4]));
            allCreatedDocuments.put(document.getNumber(), document);
            document.transfer(document.getNumber(), product.getName(), newCount, array[0], array[1]);
            System.out.println("Перемещение между складами прошло успешно. Документ перемещения загружен в папку проекта.\n");
        } catch (Exception e) {
            System.out.println("Не удалось создать документ поступления. Скорее всего возникли ошибки ввода данных. Попробробуйте не ставить пробел после запятой. \n");
        }
    }

    public void getReport() {
        if (allCreatedWarehouses.isEmpty()) {
            System.out.println("В базе нет складов, сначала создайте в базе склад(ы).\n");
            return;
        }

        System.out.println("Введите название нужного склада:");
        try {
            String string = scanner.nextLine();
            if (allCreatedWarehouses.containsKey(string)) {
                FileWriter writer = new FileWriter(string + "_balance.json");
                writer.write(gson.toJson(allCreatedWarehouses.get(string).getProducts(), new TypeToken<ArrayList<Product>>() {}.getType()));
                writer.flush();
                writer.close();
                System.out.println("Остаток товаров на складе " + string + ":");
                allCreatedWarehouses.get(string).getProducts().forEach(product -> System.out.println(product.toString()));
                System.out.println("Отчет остатков товара на складе " + string + " был загружен в папку проекта.");
            } else {
                System.out.println("Не удалось найти такой склад, проверьте правильность ввода данных.\n");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка ввода, проверьте правильность ввода данных.\n");
        }
    }
}
