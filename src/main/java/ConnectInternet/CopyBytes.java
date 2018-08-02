package ConnectInternet;

/**
 * Created by Roman on 07.06.2017.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes {
    public static void main(String[] args) throws IOException {

        //створюємо об'єктні змінні, які посилатимуться на наші потоки
        FileInputStream in = null;
        FileOutputStream out = null;
        double d=0.0;
        // При помилках читання/запису можуть генеруватися винятки, тож потрібно перехопити їх
        // Наприклад, помилка може виникнути, при відсутності файлу first.txt у вказаному місці
        try {

            // створюємо вхідний і вихідний потік
            // файл first.txt повинен вже існувати
            // якщо second.txt не буде існувати,
            // то буде створений при спробі запису
            in = new FileInputStream("c:\\Transcription_Herbert_Schildt.txt");
            out = new FileOutputStream("d:\\second.txt");
            int c;

            //Допоки з файлу first.txt не буде прочинато всі байти,
            //читаємо байти з файлу first.txt і записуємо даний байт у second.txt
            //якщо потік не повертає -1(не досягнено кінець файлу),
            //то копіюємо наступний байт
            while ((c = in.read()) != -1) {
                out.write(c);
                d++;
                System.out.println((char)c);
            }
        } finally { //дії коли не знайдено файли
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        System.out.println(d);
    }
}