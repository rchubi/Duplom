import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Roman on 09.06.2017.
 */
public class CriterionStudent extends JPanel {

    double[] x_text_ser = new double[8];
    double[] y_text_ser = new double[8];

    double[] s_x_kvadr = new double[8];
    double[] s_y_kvadr = new double[8];

    double[] statistic_criteriy = new double[8];

    static final double CRITERIY_ST = 1.96;
    static final int NUMBER_PORTIONS = 51;

//    private static final String PATH_FILE = "С:\\Criterion_Student.txt";

    private final JTextArea jTextAreaStudent;
//    String student = "\n\n\n\n\n\n\n\n \t\t\t\t\tЗначення t-розподілу Стьюдента:\n" +
//            " \n" +
//            " \t\t\t\t\tГубні: 3.0   Різниця істотна\n" +
//            " \t\t\t\t\tПередньоязикові: 2.7   Різниця істотна\n" +
//            " \t\t\t\t\tСередньоязикові: 0.31   Різниця випадкова\n" +
//            " \t\t\t\t\tЗадньоязикові: 15.1   Різниця істотна\n" +
//            " \t\t\t\t\tНосові: 3.51   Різниця істотна\n" +
//            " \t\t\t\t\tСонорні: 0.38   Різниця випадкова\n" +
//            " \t\t\t\t\tЩілинні: 6.1   Різниця істотна\n" +
//            " \t\t\t\t\tЗімкнені: 4.08   Різниця істотна\n" +
//            "\n" +
//            "\n" +
//            " \t\t\t\t\tДАНІ ТЕКСТИ НАЛЕЖАТЬ ДО РІЗНИХ СТИЛІВ ";

    CriterionStudent(){

        jTextAreaStudent = new JTextArea(38, 100);

        JScrollPane scrollPane = new JScrollPane(jTextAreaStudent);


        JPanel panel = new JPanel();

        panel.add(scrollPane);

        JButton buttonStudent = new JButton("Розмежувати стилі за критерієм Стьюдента");

        panel.add(buttonStudent, BorderLayout.NORTH);

        buttonStudent.addActionListener((e) -> {
            method();
//            openFile(PATH_FILE);
        });

        jTextAreaStudent.setText("\n\n\n\n\n\n\n dfsdfgdggdf");
//        jTextAreaStudent.setText(student);


        this.add(panel);


    }

    void method() {
        DistributionOfGroups dog = new DistributionOfGroups();
        textSer(dog.seredne_znachenia_each_group_one_portion_left, x_text_ser);
        textSer(dog.seredne_znachenia_each_group_one_portion_right, y_text_ser);
        sKvadr(dog.seredne_znachenia_each_group_one_portion_left, x_text_ser, s_x_kvadr);
        sKvadr(dog.seredne_znachenia_each_group_one_portion_right, y_text_ser, s_y_kvadr);
        statisticCritiriy(x_text_ser, y_text_ser, s_x_kvadr, s_y_kvadr, statistic_criteriy);
        checkStatisticCriteriy(statistic_criteriy);
    }


    void checkStatisticCriteriy(double stat[]) {
        System.out.println("Statistic criteriy: ");

        for (int i=0; i < stat.length; i++) {
            System.out.println(stat[i]+"\t\t");
            if (stat[i] > CRITERIY_ST) {
                System.out.println("NEODNORIDNI");
            } else {
                System.out.println("ODNORIDNI");
            }
        }
    }

    void statisticCritiriy(double x_text_ser[], double y_text_ser[], double s_x_kvadr[], double s_y_kvadr[], double statistic_criteriy[]) {
        for (int i=0; i < 8; i++) {
            statistic_criteriy[i] = (abs(x_text_ser[i] - y_text_ser[i]))/(sqrt((s_x_kvadr[i] + s_y_kvadr[i])/(2*NUMBER_PORTIONS - 2)));
        }
    }

    void sKvadr(double ser_each_g_p[][], double text_ser[], double s_kvadr[]) {
        double sum;

        for (int i=0; i < 8; i++) {
            sum = 0;

            for (int j=0; j < 51; j++) {
                sum = sum + pow((ser_each_g_p[j][i] - text_ser[i]),2);
            }

            s_kvadr[i] = sum;
        }
    }

    void textSer(double ser_each_g_p[][], double text_ser[]) {
        double sum;

        for (int i=0; i < 8; i++) {
            sum = 0;

            for (int j=0; j < 51; j++) {
                sum = sum + ser_each_g_p[j][i];
            }

            text_ser[i] = sum/51.0;
        }

    }


    void openFile( String pathFile) {
        BufferedReader in = null;
        try {

            in = new BufferedReader(new FileReader(pathFile));
            for(;;) {
                String str = in.readLine();
                if ( str == null )
                    break;
                jTextAreaStudent.append(str + "\n");
                jTextAreaStudent.setEditable(false);
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
}
