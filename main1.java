//  *Получить исходную json строку из файла, используя FileReader или Scanner
//Дана json строка вида:
//String json = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
//"{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
//"{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";
//
//Задача написать метод(ы), который распарсить строку и выдаст ответ вида:
//Студент Иванов получил 5 по предмету Математика.
//Студент Петрова получил 4 по предмету Информатика.
//Студент Краснов получил 5 по предмету Физика.
//
//Используйте StringBuilder для подготовки ответа. Далее создайте метод, который запишет
//результат работы в файл. Обработайте исключения и запишите ошибки в лог файл с помощью Logger.
//

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class main1 {

    static Logger logger;
    public static void main(String[] args) {
        String filePath = "src/main/java/org/example/text2_1.txt";
        createLogger();
        System.out.println(parsingStringJson(readFile(filePath)));
        writeToFile(parsingStringJson(readFile(filePath)));
        closeLogger();
    }

    private static void closeLogger(){
        for (Handler handler: logger.getHandlers()){
            handler.close();
        }
    }

    private static void createLogger(){
        logger = logger.getAnonymousLogger();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("src/main/java/org/example/log2_1.txt", true);
            logger.addHandler(fileHandler);
        } catch (IOException e){
            e.printStackTrace();
        }
        SimpleFormatter formatter = new SimpleFormatter();
        if (fileHandler != null){
            fileHandler.setFormatter(formatter);
        }
    }

    static String readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    static String parsingStringJson(String readFile) {
        String filePath = "src/main/java/org/example/text2_1.txt";
        String string = new String(readFile(filePath));
        String stringTest = string.replace("[", "");
        string = stringTest.replace("]", "");
        stringTest = string.replace("{", "");
        string = stringTest.replace("}", "");
        stringTest = string.replace("\\", "");
        string = stringTest.replace("\"", "");
        stringTest = string.replace(";", "");
        string = Arrays.toString(stringTest.split(","));
        stringTest = Arrays.toString(string.split(":"));
        string = stringTest.replace("фамилия", "Студент");
        stringTest = string.replace("оценка", "получил");
        string = stringTest.replace("предмет", "по предмету");
        stringTest = string.toString();
        string = stringTest.replace(",", "");
        stringTest = string.replace("[", "");
        string = stringTest.replace("]", "");
        return string.toString();
    }

    static void writeToFile(String parsingStringJson) {
        String filePath = "src/main/java/org/example/text2_1.txt";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(parsingStringJson(readFile(filePath)));
            writer.write("\n");
            writer.flush();
            logger.info("Запись успешна");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Не удалось выполнить запись");
        }
    }
}