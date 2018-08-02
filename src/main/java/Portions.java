/**
 * Created by Roman on 27.06.2017.
 */

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Portions {
    /*                              {0    1    2    3    4    5    6    7    8    9    10   11   12   13  14    15    16    17   18   19   20   21   22   23   24 }
                                   {'p', 'b', 'm', 'f', 'v', 'w', 'r', 'θ', 'ð', 'd', 'n', 's', 'z', 't', 'l', 'tʃ', 'dʒ', 'ʃ', 'j', 'k', 'g', 'ŋ', 'z', 'h', 'ʒ'}; */
    private  final char[] PHONEMS = {112, 98, 109, 102, 118, 119, 114, 952, 240, 100, 110, 115, 122, 116, 108,  116,  100, 643, 106, 107, 103, 331, 122, 104, 658};
    private  int[] phonems_number = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

//    private static int portions[][] = new int[51][25];

    private int portions[][] = new int[51][25];
//            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//    };


    private List<Character> chars = new ArrayList<>();
    private List<Character> text = new ArrayList<>();

    private static ArrayList<Character> portion_1 = new ArrayList<>();
    private static ArrayList<Character> portion_2 = new ArrayList<>();
    private static ArrayList<Character> portion_3 = new ArrayList<>();
    private static ArrayList<Character> portion_4 = new ArrayList<>();
    private static ArrayList<Character> portion_5 = new ArrayList<>();
    private static ArrayList<Character> portion_6 = new ArrayList<>();
    private static ArrayList<Character> portion_7 = new ArrayList<>();
    private static ArrayList<Character> portion_8 = new ArrayList<>();
    private static ArrayList<Character> portion_9 = new ArrayList<>();
    private static ArrayList<Character> portion_10 = new ArrayList<>();
    private static ArrayList<Character> portion_11 = new ArrayList<>();
    private static ArrayList<Character> portion_12 = new ArrayList<>();
    private static ArrayList<Character> portion_13 = new ArrayList<>();
    private static ArrayList<Character> portion_14 = new ArrayList<>();


//    private static ArrayList<Character>[] portion = new ArrayList[]{portion_1, portion_2, portion_3, portion_4,};
//
//    Portions() {
//        try {
//            Arrays.fill(portions, 0);
//        } catch (Exception ex) {
//            for (int i = 0; i<portions.length; i++) {
//                for (int a=0; a< portions[i].length; a++){
//
//                    System.out.print(portions[i][a] + "   ");
//
//                }
//                System.out.println();
//            }
//        }
//
//    }

    public static void main(String[] args) {

        Portions portions = new Portions();
        portions.readTranscription();
        PanelTranscription panelTranscription = new PanelTranscription();
//        System.out.println(panelTranscription.fileLeft);
    }

    void readTranscription() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File("C:\\Transcription_Herbert_Schildt.txt")));

            int c;
            while ((c = reader.read()) != -1) {
                char symbol = (char) c;


//  System.out.println(text.toString());
                for (char PHONEM : PHONEMS) {
                    if (symbol == PHONEM)
                        chars.add(symbol);       //   вибірка приголосних фонем
                }
            }
            System.out.println(chars.toString());
            System.out.println("size " + chars.size());
            System.out.println(chars.indexOf('g'));


//  перевірка на кількість фонем для вибірки
            if (chars.size() <= 51000) {
                errorDialog();
            }

//  формування порцій
            creationPortions();

//  запис у файл
//            writerFile();


            int suuum = 0;
            for (int i = 0; i< portions.length; i++) {
                System.out.println("Порція " + (i+1));
                int t = 0;
                for (int a=0; a < portions[i].length; a++){
                    if (a == 16) {
                        System.out.print("'" + "dʒ" + "'" + "- " + portions[i][a] + "\t\t");
                    } else if (a == 15) {
                        System.out.print("'" + "tʃ" + "'" + "- " + portions[i][a] + "\t\t");
                    } else {
                        System.out.print("'" + PHONEMS[a] + "'" + "- " + portions[i][a] + "\t\t");
                        t = t + portions[i][a];
                    }
                }

                suuum = suuum + t;
                System.out.println(t);
                System.out.println(suuum);
                System.out.println();
            }


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    void errorDialog() {
        String message = "У тексті приголосних фонем " + chars.size() + "." +
                "\nДля побудови вибірки необхідно не менше 51 000 приголосних фонем. " +
                "\nБудь ласка додайте більше фонем тексту.";
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    void  creationPortions() {
        int portion = 0;
        int number_phonem = 0;

        for (int d = 0; d < chars.size(); d++) {
            if (portion >= 51) {
                System.out.println("end");
                return;
            } else {
                creationOnePortion(d, portion);
            }

            if ((number_phonem != 0) && (number_phonem % 1000) == 0) {
                portions[portion][16] = portions[portion][16] - portions[portion][9];
                portions[portion][15] = portions[portion][15] - portions[portion][13];
                portion++;
                System.out.println(portion);

            }
            number_phonem++;
        }
    }


    void creationOnePortion(int d, int portion) {
        for (int phonem = 0; phonem < PHONEMS.length; phonem++) {

            if ((chars.get(d) == 100) && (chars.get(d + 1) == 658)) {
                portions[portion][16]++;
                d++;
            } else if ((chars.get(d) == 116) && (chars.get(d + 1) == 643)) {
                portions[portion][15]++;
                d++;
            } else if (chars.get(d) == PHONEMS[phonem]) {
                portions[portion][phonem]++;
            }
        }
    }

    void writerFile() {
        File file = new File("txt.txt");
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));

            writer.println("Кількість приголосних фонем у тексті - " + chars.size());

            writer.println();
            writer.println();
            writer.println();

            for (int i = 0; i< portions.length; i++) {
                writer.println("Порція " + (i+1));
                for (int a=0; a < portions[i].length; a++){
                    if (a == 16) {
                        writer.print("'" + "dʒ" + "'" + "- " + portions[i][a] + "\t\t");
                    } else if (a == 15) {
                        writer.print("'" + "tʃ" + "'" + "- " + portions[i][a] + "\t\t");
                    } else {
                        writer.print("'" + PHONEMS[a] + "'" + "- " + portions[i][a] + "\t\t");
                    }
                }

                writer.println();
                writer.println();
                writer.println();

            }

            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}





