 import static java.lang.Math.*;

/**
 * Created by Roman on 25.04.2018.
 */
public class CrSt {
    double[] x_text_ser = new double[8];
    double[] y_text_ser = new double[8];

    double[] s_x_kvadr = new double[8];
    double[] s_y_kvadr = new double[8];

    double[] statistic_criteriy = new double[8];

    static final double CRITERIY_ST = 1.96;
    static final int NUMBER_PORTIONS = 51;

    CrSt() {
        DistributionOfGroups dog = new DistributionOfGroups();
        textSer(dog.seredne_znachenia_each_group_one_portion_left, x_text_ser);
        textSer(dog.seredne_znachenia_each_group_one_portion_right, y_text_ser);
        sKvadr(dog.seredne_znachenia_each_group_one_portion_left, x_text_ser, s_x_kvadr);
        sKvadr(dog.seredne_znachenia_each_group_one_portion_right, y_text_ser, s_y_kvadr);
        statisticCritiriy(x_text_ser, y_text_ser, s_x_kvadr, s_y_kvadr, statistic_criteriy);
        checkStatisticCriteriy(statistic_criteriy);
    }

    void method() {
//        textSer();
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

}
