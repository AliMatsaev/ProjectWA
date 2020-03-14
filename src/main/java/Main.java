import com.google.gson.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Программа складского учета запущена.\n");
        System.out.println("Для работы со складами(добавление/перемещение/списание) нужно сначала создать товар(ы) и склад(ы).\n");
        Menu menu = new Menu();
        menu.mainMenu();
    }
}
