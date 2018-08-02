import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
 * Created by Roman on 09.06.2017.
 */
public class DistributionOfGroups extends JPanel{


//    private static final String PATH_LEFT_FILE = "C:\\Groups_Herbert_Schildt_-_Java_The_Complete_Reference.txt";
//    private static final String PATH_RIGHT_FILE = "C:\\Groups_Bruce_Eckel_-_Thinking_In_Java.txt";

    private final JTextArea jTextAreaRightGroups;
    private final JTextArea jTextAreaLeftGroups;

    private String pathNewFile;

    private String name_groups[] = {"Губні", "Передньоязикові", "Середньоязикові", "Задньоязикові", "Носові", "Сонорні",
                            "Щілинні", "Зімкненні"};
    int groups[][] = {
            {0, 1, 2, 5, 3, 4},
            {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17},
            {18},
            {19, 20, 21},
            {2, 10, 21},
            {5, 6, 14, 18},
            {7, 8, 11, 12, 17, 3, 4, 23, 24},
            {15, 16, 19, 20, 0, 1, 9, 13, 2, 10, 21},
    };

    double seredne_znachenia_each_fonem_each_group_left[][][] = new double[51][8][12];
    public static double seredne_znachenia_each_group_one_portion_left[][] = new double[51][8];
    public static double sum_left[][] = new double[51][8];
//    double sum_group_left[][] = new double[51][8];

    double seredne_znachenia_each_fonem_each_group_right[][][] = new double[51][8][12];
    public static double seredne_znachenia_each_group_one_portion_right[][] = new double[51][8];
    public static double sum_right[][] = new double[51][8];
//    double sum_group_right[][] = new double[51][8];

    int sum_sered;
    double sum_group_one;

    DistributionOfGroups(){
        jTextAreaLeftGroups = new JTextArea(38, 56);
        jTextAreaRightGroups = new JTextArea(38, 60);

        JScrollPane scrollPaneFilesLeft = new JScrollPane(jTextAreaLeftGroups);
        JScrollPane scrollPaneFilesRight = new JScrollPane(jTextAreaRightGroups);

        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();

        panelLeft.add(scrollPaneFilesLeft);
        panelRight.add(scrollPaneFilesRight);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPane.setOneTouchExpandable(false);
        splitPane.setEnabled(false);


        JButton buttonGroupsLeft = new JButton("Розділити на групи лівий текст");
        JButton buttonGroupsRight = new JButton("Розділити на групи правий текст");

        buttonGroupsLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonGroupsRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.add(buttonGroupsLeft);
        this.add(buttonGroupsRight);

        buttonGroupsRight.addActionListener((e) -> {
            createGroups(DistributionOfPortions.portions_right, seredne_znachenia_each_fonem_each_group_right, sum_right, seredne_znachenia_each_group_one_portion_right);
            writerFile(PanelFile.fileNameRight, DistributionOfPortions.portions_right, seredne_znachenia_each_fonem_each_group_right, seredne_znachenia_each_group_one_portion_right);
            try {
                openFile(jTextAreaRightGroups, buttonGroupsRight, PanelFile.fileNameRight, pathNewFile );
            } catch (IOException e1) {
                e1.printStackTrace();

            }        });

        buttonGroupsLeft.addActionListener((e) -> {
            createGroups(DistributionOfPortions.portions_left, seredne_znachenia_each_fonem_each_group_left, sum_left, seredne_znachenia_each_group_one_portion_left);
            writerFile(PanelFile.fileNameLeft, DistributionOfPortions.portions_left, seredne_znachenia_each_fonem_each_group_left, seredne_znachenia_each_group_one_portion_left);
            try {
                openFile(jTextAreaLeftGroups, buttonGroupsLeft, PanelFile.fileNameLeft, pathNewFile );
            } catch (IOException e1) {
                e1.printStackTrace();

            }
        });

        JPanel jPanel = new JPanel();

        jPanel.add(buttonGroupsLeft, BorderLayout.NORTH);
        jPanel.add(buttonGroupsRight, BorderLayout.NORTH);

        jPanel.add(splitPane, BorderLayout.SOUTH);

        this.add(jPanel);
        this.add(splitPane);

    }


//            0    1   2   5   3   4
//  Губні — [‘р’, ‘b’, ‘m’, ‘w’, ‘f’, ‘v’];
//                      6   7    8   9   10  11  12  13  14  15    16   17
//  Передньоязикові — [‘r’, ‘ɵ’, ‘ð’, ‘d’, ‘n’, ‘s’, ‘z’, ‘t’, ‘l’, ‘tƒ’, ‘dʒ’, ‘ƒ’];
//                      18
//  Середньоязикові — [‘j‘];
//                    19  20  21
//  Задньоязикові — [‘к’, ‘g’, ‘ŋ’];
//             2   10   21
//  Носові — [‘m’, ‘n’, ‘ ŋ’];
//              5   6   14  18
//  Сонорні — [‘w’, ‘r’, ‘l’, ‘j‘];
//              7   8   11  12  17   3    4   23  24
//  Щілинні — [‘ɵ’, ‘ð’, ‘s’, ‘z’, ‘ƒ’, ‘f’,  ‘v’, ‘h’, ‘ʒ’];
//                15   16   19  20   0   1   9   13  2   10   21
//  Зімкненні — [‘tƒ’, ‘dʒ’, ‘k’, ‘g’, ‘р’, ‘b’, ‘d’, ‘t’, ‘m’, ‘n’, ‘ ŋ’].


    void createGroups(int portions[][], double seredne_znachenia_each_fonem_each_group[][][], double sum[][], double seredne_znachenia_each_group_one_portion[][]) {

// Проходимо по порціях
        for (int portion=0; portion < portions.length; portion++) {
            System.out.println("Порція " + (portion+1));

//Кожної групи

//            sum_group_one = 0;
            for (int group=0; group < groups.length; group++ ) {
                System.out.println(name_groups[group]);

//Кожної фонеми групи
                for (int fonem=0; fonem < groups[group].length; fonem++) {
                    System.out.print("'" + DistributionOfPortions.PHONEMS[groups[group][fonem]]);
                    System.out.print("' " + "- ");
                    System.out.print(portions[portion][groups[group][fonem]]);
                    System.out.print("\t\t");
                }

//Рахуємо середнє значення кожної фонеми в кожній групі
                System.out.println("\nСереднє значення");

                sum_sered = 0;
                for (int fonem=0; fonem < groups[group].length; fonem++) {
                    sum_sered = sum_sered + portions[portion][groups[group][fonem]];
                    sum[portion][group] = sum_sered*1000/sfp(portions, portion);

                }
//                System.out.println("sum " +  sum_sered);

//                System.out.println("************************************************************************");
//                for (int p=0; p < portions.length; p++) {
//                    System.out.println("Порція " + (p + 1));
//                    for (int g = 0; g < groups.length; g++) {
//                        System.out.println(name_groups[g]);
//
//                        for (int fonem = 0; fonem < groups[g].length; fonem++) {
//                            System.out.print("'" + DistributionOfPortions.PHONEMS[groups[g][fonem]]);
//                            System.out.print("' " + "- ");
//                            System.out.print(portions[p][groups[g][fonem]]);
//                            System.out.print("\t\t");
//                        }
//                    }
//                }
//                System.out.println("************************************************************************");


                for (int fonem=0; fonem < groups[group].length; fonem++) {

                    seredne_znachenia_each_fonem_each_group[portion][group][fonem] = (double) Math.round(100 * portions[portion][groups[group][fonem]]/sum_sered) ;

                    System.out.print("'" + DistributionOfPortions.PHONEMS[groups[group][fonem]]);
                    System.out.print("' " + "- ");
                    System.out.print(seredne_znachenia_each_fonem_each_group[portion][group][fonem]);
                    System.out.print("\t\t");
                }

//Рахуємо середнє значення групи в кожній порції
                System.out.println("\nСереднє значення по групі");

                seredne_znachenia_each_group_one_portion[portion][group] = (double) Math.round(100 * sum[portion][group]/sumFonemsAllGroupsOnePortion(portions, portion));
                System.out.println(seredne_znachenia_each_group_one_portion[portion][group]);

                System.out.println("\n");

            }


            System.out.println("\n");
        }


        System.out.println("***************************************************");
        for(int p=0; p < portions.length; p++){
            System.out.println("-----------------------Порція " + (p+1));
            for (int g = 0; g < groups.length; g++) {
//                System.out.print(name_groups[g] + "- ");
                System.out.print(sum[p][g]/**1000/sfp(portions, p)*/ + "\t");
            }
//            System.out.println();
//            System.out.print("sumGroups- ");
            System.out.print(sumFonemsAllGroupsOnePortion(portions, p) + "\t");
//            System.out.print("sumPortion- ");
            System.out.print(sfp(portions, p));
            System.out.println();
        }
        System.out.println("***************************************************");

    }

    int sfp(int por[][], int p) {
        int s =0;
            for (int y=0; y<por[p].length; y++){
                s = s+por[p][y];
            }
        return s;
    }

// шукаємо кількість фонем у всіх групах однієї порції
    int sumFonemsAllGroupsOnePortion(int portions[][], int portion){

        int[][] sum = new int[51][8];
        int sum_in_one_group = 0;

        for (int group=0; group<groups.length;group++) {
            sum_sered = 0;

            for (int fonem = 0; fonem < groups[group].length; fonem++) {
                sum_sered = sum_sered + portions[portion][groups[group][fonem]];
                sum[portion][group] = sum_sered;
            }
            sum_in_one_group = sum_in_one_group + sum_sered;
        }
//        System.out.println("sum_in_one_group " + sum_in_one_group);
        return sum_in_one_group;
    }


    void writerFile(String nameFile, int portions[][], double seredne_znachenia_each_fonem_each_group[][][], double seredne_znachenia_each_group_one_portion[][]) {

        String name = "Groups_" + nameFile;
        File file = new File(DistributionOfPortions.directory, name);

//        rewriteFile = creatFile(name);


        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));

            for (int portion = 0; portion < portions.length; portion++) {
//                StringBuffer str = "Порція " + (portion + 1);
                writer.println(("Порція " + (portion + 1)).toUpperCase());

//Кожної групи

//                sum_group_one = 0;
                for (int group = 0; group < groups.length; group++) {
                    writer.println(name_groups[group]);

//Кожної фонеми групи
                    for (int fonem = 0; fonem < groups[group].length; fonem++) {
                        if (groups[group][fonem] == 16) {
                            writer.print("'" + "dʒ" + "'" + "- " + portions[portion][groups[group][fonem]] + "\t\t");
                        } else if (groups[group][fonem] == 15) {
                            writer.print("'" + "tʃ" + "'" + "- " + portions[portion][groups[group][fonem]] + "\t\t");
                        } else {
                            writer.print("'" + DistributionOfPortions.PHONEMS[groups[group][fonem]] + "'" + "- " + portions[portion][groups[group][fonem]] + "\t\t");
                        }
                    }

                    writer.println();
                    writer.println("Середнє значення кожної фонеми");
//                    writer.println();

//Рахуємо середнє значення кожної фонеми кожної групи

                    for (int f = 0; f < groups[group].length; f++) {

//                        seredne_znachenia_each_fonem_each_group[portion][group][f] = (double) Math.round(100 * portions[portion][groups[group][f]] / sum_sered);

                        if (groups[group][f] == 16) {
                            writer.print("'" + "dʒ" + "'" + "- " + seredne_znachenia_each_fonem_each_group[portion][group][f] + "\t\t");
                        } else if (groups[group][f] == 15) {
                            writer.print("'" + "tʃ" + "'" + "- " + seredne_znachenia_each_fonem_each_group[portion][group][f] + "\t\t");
                        } else {
                            writer.print("'" + DistributionOfPortions.PHONEMS[groups[group][f]] + "' " + "- " + seredne_znachenia_each_fonem_each_group[portion][group][f] + "\t\t");
                        }
                    }

                    writer.println();
                    writer.println("Середнє значення групи");
//                    seredne_znachenia_each_group_one_portion[portion][group] = (double) Math.round(100 * sum[portion][group]/sumFonemsAllGroupsOnePortion(portions, portion));
                    writer.println(seredne_znachenia_each_group_one_portion[portion][group]);

                    writer.println();

                }


                writer.println("");
                writer.println("");
                }


                pathNewFile = file.getPath();

                writer.flush();
                writer.close();

            }catch(IOException ex){
                ex.printStackTrace();
            }

    }




    void openFile(JTextArea textArea, JButton button, String nameFile, String pathFile) throws IOException {
        BufferedReader in = null;

        button.setText(nameFile);

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

}