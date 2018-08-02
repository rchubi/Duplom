package ConnectInternet;

/**
 * Created by Roman on 07.06.2017.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolRead {
    /*
            {{'p', 'b', 'm', 'f', 'v', 'w'},
            {'r', 'θ', 'ð', 'd', 'n', 's', 'z', 't', 'l', 'tʃ', 'dʒ', 'ʃ'},
            {'j'},
            {'k', 'g', 'ŋ'},
            {'m', 'n', 'ŋ'},
            {'w', 'r', 'l', 'j'},
            {'θ', 'ð', 's', 'z', 'ʃ', 'f', 'v', 'h', 'ʒ'},
            {'tʃ', 'dʒ', 'k', 'g', 'p', 'b', 'd', 't', 'm', 'n', 'ŋ'},};

     */
    private static final char[][] PHONEM_GROUP = {
            {112, 98, 109, 102, 118, 119},
            {114, 952, 240, 100, 110, 115, 122, 116, 108, 116, 100, 643},     // замість фонем tʃ і dʒ ми написали фонеми t і d. Якщо фонема співпадає з t або d, а наступна ʃ і ʒ відповідно, то ми рахуємо це як фонеми tʃ і dʒ відповідно.
            {106},
            {107, 103, 331},
            {109, 110, 331},
            {119, 114, 108, 106},
            {952, 240, 115, 122, 643, 102, 118, 104, 658},
            {116, 100, 107, 103, 112, 98, 100, 116, 109, 110, 331},};

    private static int [][] phonem_number = {
            {0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0},
            {0,0,0},
            {0,0,0},
            {0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0}};

    private static List<Character> chars = new ArrayList<>();

    private static List<Character> portion_1 = new ArrayList<>();

    List[] portion = {};

    public static void main(String[] args) {
        BufferedReader reader = null;
//        double d=0.0;
        double p =0;
        double np=0;
        char q= 116;
        try {
            reader = new BufferedReader(new FileReader(new File("C:\\Transcription_Herbert_Schildt.txt")));

            int c;
            while (( c = reader.read())!= -1) {
                char symbol = (char) c;

//                System.out.println("code  " + (int)symbol + "   " + symbol);
                if (symbol == q){
                    p++;
                }
                chars.add(symbol);

//                d++;
            }
//            System.out.println(chars.toString());
            numberOfPhonems();
            for (int i =0 ; i < PHONEM_GROUP.length; i++ ) {
                for (int a = 0; a < PHONEM_GROUP[i].length; a++){
                    System.out.print(PHONEM_GROUP[i][a] + "   ");
                }
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

////        System.out.println("d: " + d);
//        System.out.println("q  "  +p);
//        System.out.println(q);
//        System.out.println(np);
//        System.out.println(chars.get(1));


    }

    static void  numberOfPhonems() {
            double number_phonem_text = 0;

            for (int group = 0; group < PHONEM_GROUP.length; group++) {
                System.out.println("PHONEM_GROUP.length " + PHONEM_GROUP[group].length);
                for (int phonem = 0; phonem < PHONEM_GROUP[group].length ; phonem++) {

                    for (int d = 0; d < chars.size(); d++) {
                        if ((chars.get(d) == 100) && (chars.get(d+1) == 658)){
                            phonem_number[1][10]++;
                            phonem_number[7][1]++;
                            d++;
                            number_phonem_text++;

                        } else if ((chars.get(d) == 116) && (chars.get(d+1) == 643)) {
                            phonem_number[1][9]++;
                            phonem_number[7][0]++;
                            d++;
                            number_phonem_text++;

                        } else if (PHONEM_GROUP[group][phonem] == chars.get(d)) {
                            phonem_number[group][phonem]++;
                            number_phonem_text++;
                        }

                    }
                    System.out.println(PHONEM_GROUP[group][phonem] + ": " + phonem_number[group][phonem]);


                }
            }

        System.out.println("Кількість фонем: " + number_phonem_text);
        System.out.println(PHONEM_GROUP[1][11] + " " + PHONEM_GROUP[7][1]);
    }


}

