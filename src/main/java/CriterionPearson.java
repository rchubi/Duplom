import org.rosuda.JRI.Rengine;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Roman on 09.06.2017.
 */
public class CriterionPearson extends JPanel{

    private static final String PATH_LEFT_FILE = "C:\\Pearson_Herbert_Schildt_-_Java_The_Complete_Reference.txt";
    private static final String PATH_RIGHT_FILE = "C:\\Pearson_Bruce_Eckel_-_Thinking_In_Java.txt";

    private static final double NUMBER_INTERVALS = 10;
    private static final int NUMBER_PORTIONS = 102;
    private static final double XI_TEORETICHNE = 77.93;

    double TEORETUCHA_IMOVIRNIST_POPADANIA = 0.1;

    private final JTextArea jTextAreaRightPearson;
    private final JTextArea jTextAreaLeftPearson;

    DistributionOfGroups DofG = new DistributionOfGroups();
    private Rengine rengine = new Rengine(new String[]{"--no-save"}, false, null);   // Екземпляр класу для R

    double[][] union_vubirka_both_text = new double[102][8];
    double[][] argumentsRozpodily = new double[102][8];

    double[] min_union_vubirka = new double[8];
    double[] max_union_vubirka = new double[8];

//    int[] elements_intervals = new int[10];

    double[] seredniaKilkist    = new double[8];
    double[] vubirkovaDuspercia = new double[8];
//    double[] xiKvadrEmpiriche   = new double[8];
    ArrayList<Double> xiKvadrEmpirichne = new ArrayList<>();


    // Число попадань на певний інтервал
    List<ArrayList<Integer>> intervals = new ArrayList<>();
    ArrayList<Integer> interval_0 = new ArrayList<>();
    ArrayList<Integer> interval_1 = new ArrayList<>();
    ArrayList<Integer> interval_2 = new ArrayList<>();
    ArrayList<Integer> interval_3 = new ArrayList<>();
    ArrayList<Integer> interval_4 = new ArrayList<>();
    ArrayList<Integer> interval_5 = new ArrayList<>();
    ArrayList<Integer> interval_6 = new ArrayList<>();
    ArrayList<Integer> interval_7 = new ArrayList<>();

    //число кроків у кожному новому обєднаному інтервалі
    List<ArrayList<Integer>> limits = new ArrayList<>();
    ArrayList<Integer> limits_0 = new ArrayList<>();
    ArrayList<Integer> limits_1 = new ArrayList<>();
    ArrayList<Integer> limits_2 = new ArrayList<>();
    ArrayList<Integer> limits_3 = new ArrayList<>();
    ArrayList<Integer> limits_4 = new ArrayList<>();
    ArrayList<Integer> limits_5 = new ArrayList<>();
    ArrayList<Integer> limits_6 = new ArrayList<>();
    ArrayList<Integer> limits_7 = new ArrayList<>();

    List<ArrayList<Double>> teoretuchnaImovirnist = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_0 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_1 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_2 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_3 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_4 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_5 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_6 = new ArrayList<>();
    ArrayList<Double> teoretuchnaImovirnist_7 = new ArrayList<>();




    CriterionPearson(double v){
        jTextAreaLeftPearson = new JTextArea(38, 60);
        jTextAreaRightPearson = new JTextArea(38, 60);

        JScrollPane scrollPaneFilesLeft = new JScrollPane(jTextAreaLeftPearson);
        JScrollPane scrollPaneFilesRight = new JScrollPane(jTextAreaRightPearson);

        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();

        panelLeft.add(scrollPaneFilesLeft);
        panelRight.add(scrollPaneFilesRight);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPane.setOneTouchExpandable(false);
        splitPane.setEnabled(false);


        JButton buttonTranscriptionLeft = new JButton("Перевірка на нормальність критерієм Пірсона лівий текст");
        JButton buttonTranscriptionRight = new JButton("Перевірка на нормальність критерієм Пірсона правий текст");

        buttonTranscriptionLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonTranscriptionRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.add(buttonTranscriptionLeft);
        this.add(buttonTranscriptionRight);


        buttonTranscriptionRight.addActionListener((e) -> {
            criteriyPirsona();
//            method();
//            openFile(jTextAreaRightPearson,PATH_RIGHT_FILE);
        });

        buttonTranscriptionLeft.addActionListener((e) -> {
//            criteriyPirsona();
//            openFile(jTextAreaLeftPearson,PATH_LEFT_FILE);
        });

        JPanel jPanel = new JPanel();

        jPanel.add(buttonTranscriptionLeft, BorderLayout.NORTH);
        jPanel.add(buttonTranscriptionRight, BorderLayout.NORTH);

        jPanel.add(splitPane, BorderLayout.SOUTH);

        this.add(jPanel);
        this.add(splitPane);

    }

    void criteriyPirsona(){
//  середня кількість фонем одного типу разом у всіх порціях двох текстів
        seredniaKilkistFonemOneGroupTwoText();
        System.out.println("\n\n\n");
        vubirkovaDuspercia();
        unionTwoArrays();
        argumentsRozpodily();
        unionMinMax();
        inizializationList();
        numberElementsInInterval();
        teoretuchnaImovirnist();
        xiKvadr();
    }

    void seredniaKilkistFonemOneGroupTwoText(){
        for (int group = 0; group < 8; group++) {

            seredniaKilkist[group] = (sumFonemOneGroup(DofG.sum_left, group) + sumFonemOneGroup(DofG.sum_right, group)) / 102;
            System.out.println("SeredniaKilkist " + seredniaKilkist[group]);
        }
    }

        double sumFonemOneGroup(double seredne_znachenia[][], int group){
            double seredne = 0;
            for (int portion = 0; portion < 51; portion++) {
                seredne = seredne + seredne_znachenia[portion][group];
            }
            System.out.println("seredne " + seredne);
    //        System.out.println("s   " + seredne);
            return seredne;
        }


    void vubirkovaDuspercia(){
        for (int group = 0; group < 8; group++) {

            vubirkovaDuspercia[group] = ((sumFonemOneGroupSquare(DofG.sum_left, group)
                    + sumFonemOneGroupSquare(DofG.sum_right, group)) / 102) - Math.pow(seredniaKilkist[group],2);
            System.out.println("VubirkovaDuspercia " + vubirkovaDuspercia[group]);
        }
    }

        double sumFonemOneGroupSquare(double seredne_znachenia[][], int group){
            double seredne = 0;
            for (int portion = 0; portion < 51; portion++) {
                seredne = seredne + Math.pow(seredne_znachenia[portion][group],2);
            }
            System.out.println("seredne2 " + seredne);

            return seredne;
        }

    // обєднуємо дві вибірки в один масив
    void unionTwoArrays() {
//        System.arraycopy(DofG.sum_left, 0, union_vubirka_both_text, 0,DofG.sum_left.length);
//        System.arraycopy(DofG.sum_right, 0, union_vubirka_both_text, DofG.sum_left.length, DofG.sum_right.length);
        for (int i=0; i < DofG.sum_left.length; i++) {
            for (int j = 0; j < DofG.sum_left[i].length; j++) {
                union_vubirka_both_text[i][j] = DofG.sum_left[i][j];
            }
        }
        for (int i=0; i < DofG.sum_right.length; i++) {
            for (int j = 0; j < DofG.sum_right[i].length; j++) {
                union_vubirka_both_text[i+51][j] = DofG.sum_right[i][j];
            }
        }
    }

    private void argumentsRozpodily() {
        for (int group = 0; group < 8; group++) {
            for (int portion = 0; portion < NUMBER_PORTIONS; portion++) {
                argumentsRozpodily[portion][group] = (union_vubirka_both_text[portion][group] - seredniaKilkist[group]) / Math.sqrt(vubirkovaDuspercia[group]);
            }
        }

        System.out.println("***************************************************");
        for(int p=0; p < NUMBER_PORTIONS; p++){
            System.out.println("-----------------------Порція " + (p+1));
            for (int g = 0; g < 8; g++) {
//                System.out.print(name_groups[g] + "- ");
                System.out.print(argumentsRozpodily[p][g] /**1000/sfp(portions, p)*/ + "\t");
            }

            System.out.println();
        }
        System.out.println("***************************************************");
    }

    // знаходимо min i max у фонемах кожного типу в об'єднаній вибірці
    void unionMinMax() {
        double min_max;
        for (int i=0; i < min_union_vubirka.length; i++){
            min_max = argumentsRozpodily[0][i];
            for (int j=0; j < argumentsRozpodily.length; j++) {
                if(min_max > argumentsRozpodily[j][i]) min_max = argumentsRozpodily[j][i];
            }
            min_union_vubirka[i] = min_max;
        }

        for (int i=0; i < max_union_vubirka.length; i++){
            min_max = argumentsRozpodily[0][i];
            for (int j=0; j < argumentsRozpodily.length; j++) {
                if(min_max < argumentsRozpodily[j][i]) min_max = argumentsRozpodily[j][i];
            }
            max_union_vubirka[i] = min_max;
        }

        for (int i=0; i < max_union_vubirka.length; i++) {

            System.out.println("min                   max");
            System.out.println(min_union_vubirka[i] + "   " + max_union_vubirka[i]);
        }
    }

    void inizializationList() {
        intervals.add(0, interval_0);
        intervals.add(1, interval_1);
        intervals.add(2, interval_2);
        intervals.add(3, interval_3);
        intervals.add(4, interval_4);
        intervals.add(5, interval_5);
        intervals.add(6, interval_6);
        intervals.add(7, interval_7);

        limits.add(0, limits_0);
        limits.add(1, limits_1);
        limits.add(2, limits_2);
        limits.add(3, limits_3);
        limits.add(4, limits_4);
        limits.add(5, limits_5);
        limits.add(6, limits_6);
        limits.add(7, limits_7);


        System.out.println("limits ++++++++++++++++++++  ");
        for (int i=0; i < 8; i++) {
            for (int j = 0; j < NUMBER_INTERVALS; j++) {
                limits.get(i).add(j, 1);
            }

            System.out.println(limits.get(1));
        }

        teoretuchnaImovirnist.add(0, teoretuchnaImovirnist_0);
        teoretuchnaImovirnist.add(1, teoretuchnaImovirnist_1);
        teoretuchnaImovirnist.add(2, teoretuchnaImovirnist_2);
        teoretuchnaImovirnist.add(3, teoretuchnaImovirnist_3);
        teoretuchnaImovirnist.add(4, teoretuchnaImovirnist_4);
        teoretuchnaImovirnist.add(5, teoretuchnaImovirnist_5);
        teoretuchnaImovirnist.add(6, teoretuchnaImovirnist_6);
        teoretuchnaImovirnist.add(7, teoretuchnaImovirnist_7);
    }

    void numberElementsInInterval() {
        double start_range, end_range;
        int number;

        for (int i=0; i < 8; i++){
            start_range = min_union_vubirka[i];
            end_range = start_range + stepSpace(min_union_vubirka[i], max_union_vubirka[i]);

            for (int k = 0; k < NUMBER_INTERVALS; k++) {

                if (k == NUMBER_INTERVALS-1) { end_range = max_union_vubirka[i] + 1;
                } else { end_range = end_range + stepSpace(min_union_vubirka[i], max_union_vubirka[i]); }
                number = 0;

                for (int j = 0; j < argumentsRozpodily.length; j++) {
                    if ((start_range <= argumentsRozpodily[j][i]) && (argumentsRozpodily[j][i] < end_range)) {
                        number++;
                    }
                }

                intervals.get(i).add(k, number);
                start_range = end_range;
            }
        }

        System.out.println("intervals ++++++++++++++++++++  ");
        System.out.println(intervals);
//        System.out.println("limits ++++++++++++++++++++  ");
//        System.out.println(limits.get(1));
        System.out.println("union intervals=============================");
        unionIntervals();
    }

        double stepSpace(double min, double max) {
            double h = (max - min)/NUMBER_INTERVALS;
            return h;
        }

        void unionIntervals() {
            for (int i=0; i < 8; i++) {
                for (int j=0; j < intervals.get(i).size(); j++ ) {
                    while (intervals.get(i).get(j) <5) {
                        if (intervals.get(i).indexOf(intervals.get(i).get(j)) == intervals.get(i).size()-1){
                            intervals.get(i).set(j-1, intervals.get(i).get(j-1) + intervals.get(i).remove(j));
                            limits.get(i).set(j-1, limits.get(i).get(j-1) + limits.get(i).remove(j));
                            break;
                        } else {
                            intervals.get(i).set(j, intervals.get(i).get(j) + intervals.get(i).remove(j + 1));
                            limits.get(i).set(j, limits.get(i).get(j) + limits.get(i).remove(j+1));
                        }
                    }
                }
                System.out.println(intervals.get(i));
                System.out.println("union limits +++++++++++++++");
                System.out.println(limits.get(i));
            }
        }


    void teoretuchnaImovirnist() {
        unionTeoretImovirnist();

        double temprery;

        for (int group = 0; group < min_union_vubirka.length; group++) {
            int numberStep_first = 0;
            int numberStep_second = 0;

            for (int k = 0; k < limits.get(group).size(); k++) {
                numberStep_first = numberStep_first + limits.get(group).get(k);
                temprery = teoretichnaImovirnistR(znahodzeniaArgumentuTeorImovirnosti(group, numberStep_first)) -
                            teoretichnaImovirnistR(znahodzeniaArgumentuTeorImovirnosti(group, numberStep_second));

                teoretuchnaImovirnist.get(group).add(k, temprery);
                numberStep_second = numberStep_first;
            }

            System.out.println("teoretuchnaImovirnist ----------------------");
            System.out.println(teoretuchnaImovirnist.get(group));
            System.out.println("union intervals 111111111111111111111111111111111=============================");
            System.out.println(intervals.get(group));
        }
    }

        void unionTeoretImovirnist() {
            double temprery;
            for (int group = 0; group < min_union_vubirka.length; group++) {
                int numberStep_first = 0;
                int numberStep_second = 0;

                for (int k = 0; k < limits.get(group).size(); k++) {
                    numberStep_first = numberStep_first + limits.get(group).get(k);
                    temprery = teoretichnaImovirnistR(znahodzeniaArgumentuTeorImovirnosti(group, numberStep_first)) -
                            teoretichnaImovirnistR(znahodzeniaArgumentuTeorImovirnosti(group, numberStep_second));

                    if (temprery * NUMBER_PORTIONS < 5) {
                        if (intervals.get(group).indexOf(intervals.get(group).get(k)) == intervals.get(group).size()-1){
                            intervals.get(group).set(k-1, intervals.get(group).get(k-1) + intervals.get(group).remove(k));
                            limits.get(group).set(k-1, limits.get(group).get(k-1) + limits.get(group).remove(k));
                            break;
                        } else {
                            intervals.get(group).set(k, intervals.get(group).get(k) + intervals.get(group).remove(k + 1));
                            limits.get(group).set(k, limits.get(group).get(k) + limits.get(group).remove(k+1));
                        }

//                        teoretuchnaImovirnist();
                    }

//                    teoretuchnaImovirnist.get(group).add(k, temprery);
                    numberStep_second = numberStep_first;
                }

//                System.out.println("teoretuchnaImovirnist ----------------------");
//                System.out.println(teoretuchnaImovirnist.get(group));
//                System.out.println("union intervals 111111111111111111111111111111111=============================");
//                System.out.println(intervals.get(group));
            }
        }

        //Teoretichna imovirnist on R
        public double teoretichnaImovirnistR(double argument) {
            rengine.eval("argument = " + argument);
            rengine.eval("teorImovirnist = pnorm(argument)");

            return rengine.eval("teorImovirnist").asDouble();
        }

        double znahodzeniaArgumentuTeorImovirnosti(int group, int numberStep){
            double argument;
            argument = min_union_vubirka[group] + numberStep * stepSpace(min_union_vubirka[group], max_union_vubirka[group]);
            System.out.println("i- " + group + "   numberStep- " + numberStep + "   argument - "  + argument);
            return argument;
        }

    void xiKvadr() {
        double temp;
        for (int i=0; i < intervals.size(); i++) {
            temp = 0;
            for (int j=0; j < intervals.get(i).size(); j++ ) {
                temp = temp + (Math.pow(intervals.get(i).get(j) - NUMBER_PORTIONS * teoretuchnaImovirnist.get(i).get(j), 2)/
                        NUMBER_PORTIONS * teoretuchnaImovirnist.get(i).get(j));
            }
            xiKvadrEmpirichne.add(i, temp);
        }
        System.out.println("xiKvadrEmpiriche ////////////////////////////// ");
//        System.out.println(xiKvadrEmpirichne);
        checkXiKvadr();
    }

    void checkXiKvadr() {
            for (int i=0; i < xiKvadrEmpirichne.size(); i++) {
                System.out.print(xiKvadrEmpirichne.get(i));

                if (xiKvadrEmpirichne.get(i) > XI_TEORETICHNE) { System.out.println("     NEODNORIDNE" ); }
                else { System.out.println("     ODNORIDNE");
                }
            }
        }

    void openFile(JTextArea textArea, String pathFile) {
        BufferedReader in = null;
        try {

            in = new BufferedReader(new FileReader(pathFile));
            for(;;) {
                String str = in.readLine();
                if ( str == null )
                    break;
                textArea.append(str + "\n");
                textArea.setEditable(false);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if ( in != null ) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    void method() {
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        CrSt cs = new CrSt();
        System.out.println();
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
    }

}
