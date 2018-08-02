/**
 * Created by Roman on 09.06.2017.
 */


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Created by Roman on 12.05.2017.
 */
public class DistributionOfPortions extends JPanel {

    private final JTextArea jTextAreaRightPortions;

    private final JTextArea jTextAreaLeftPortions;


    private PanelFile panelFile = new PanelFile();
    private String pathNewFile;

    private java.util.List<Character> chars = new ArrayList<>();
    public static File directory;

    private boolean rewriteFile = true;

    private static final String errorOpenFile = "Файл не було знайдено.";
//    int number_2symbol = 0;
    int next_portion = 0;

    /*                              {0    1    2    3    4    5    6    7    8    9    10   11   12   13  14    15    16    17   18   19   20   21   22   23   24 }
                                   {'p', 'b', 'm', 'f', 'v', 'w', 'r', 'θ', 'ð', 'd', 'n', 's', 'z', 't', 'l', 'tʃ', 'dʒ', 'ʃ', 'j', 'k', 'g', 'ŋ', 'z', 'h', 'ʒ'}; */
    public static char[] PHONEMS = {112, 98, 109, 102, 118, 119, 114, 952, 240, 100, 110, 115, 122, 116, 108,  116,  100, 643, 106, 107, 103, 331, 122, 104, 658};

    public static int portions_left[][] = new int[51][25];
    public static int portions_right[][] = new int [51][25];


// Інтерфейс
    DistributionOfPortions(){
        jTextAreaLeftPortions = new JTextArea(38, 56);
        jTextAreaRightPortions = new JTextArea(38, 60);

        JScrollPane scrollPaneFilesLeft = new JScrollPane(jTextAreaLeftPortions);
        JScrollPane scrollPaneFilesRight = new JScrollPane(jTextAreaRightPortions);

        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();

        panelLeft.add(scrollPaneFilesLeft);
        panelRight.add(scrollPaneFilesRight);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPane.setOneTouchExpandable(false);
        splitPane.setEnabled(false);


        JButton buttonPortionsLeft = new JButton("Розділити на порції лівий текст");
        JButton buttonPortionsRight = new JButton("Розділити на порції правий текст");

        buttonPortionsLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPortionsRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.add(buttonPortionsLeft);
        this.add(buttonPortionsRight);

        buttonPortionsRight.addActionListener((e) -> {
            chars.clear();
            System.out.println("fpr" + PanelTranscription.pathFileRight);
            readTranscription(PanelTranscription.pathFileRight, portions_right);
            writerFile(PanelFile.fileNameRight, portions_right);
            try {
                openFile(jTextAreaRightPortions, buttonPortionsRight, PanelFile.fileNameRight, pathNewFile );
            } catch (IOException e1) {
                errorDialog(errorOpenFile);
                e1.printStackTrace();

            }
        });

        buttonPortionsLeft.addActionListener((e) -> {
            chars.clear();
            System.out.println("fpl" + PanelTranscription.pathFileLeft);
            readTranscription(PanelTranscription.pathFileLeft, portions_left);
            writerFile(PanelFile.fileNameLeft, portions_left);
            try {
                openFile(jTextAreaLeftPortions, buttonPortionsLeft, PanelFile.fileNameLeft, pathNewFile);
            } catch (IOException e1) {
                errorDialog(errorOpenFile);
                e1.printStackTrace();
            }

        });

        JPanel jPanel = new JPanel();

        jPanel.add(buttonPortionsLeft, BorderLayout.NORTH);
        jPanel.add(buttonPortionsRight, BorderLayout.NORTH);

        jPanel.add(splitPane, BorderLayout.SOUTH);

        this.add(jPanel);
        this.add(splitPane);

    }


    void readTranscription(String pathFile, int portions[][]) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(pathFile)));

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
                errorDialog(chars.size());
                return;
            }

//  формування порцій
            creationPortions(portions);

            int suuum = 0;
            for (int i = 0; i < portions.length; i++) {
                System.out.println("Порція " + (i+1));
                int t = 0;
                for (int a=0; a < portions[i].length; a++){
                    if (a == 16) {
                        System.out.print("'" + "dʒ" + "'" + "- " + portions[i][a] + "\t\t");
                    } else if (a == 15) {
                        System.out.print("'" + "tʃ" + "'" + "- " + portions[i][a] + "\t\t");
                    } else {
                        System.out.print("'" + PHONEMS[a] + "'" + "- " + portions[i][a] + "\t\t");
//                        t = t + portions[i][a];
                    }
                    t = t + portions[i][a];
                }

                suuum = suuum + t;
                System.out.println(t);
                System.out.println(suuum);
//                System.out.println(number_2symbol);
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

    void errorDialog(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);

    }

    void errorDialog(int size) {
        String message = "У тексті приголосних фонем " + size + "." +
                "\nДля побудови вибірки необхідно не менше 51 000 приголосних фонем. " +
                "\nБудь ласка додайте більше фонем тексту.";
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    void creationPortions(int portions[][]) {
        int portion = 0;
        int number_2symbol = 0;
//        int next_portion = 0;

        for (int d = 0; d < chars.size(); d++ ) {

//      формування самих порцій

            for (int p=0; p < PHONEMS.length; p++) {

                if ((chars.get(d) == 100) && (chars.get(d + 1) == 658)) {
                    portions[portion][16]++;
                    number_2symbol++;
                    d++;
                } else
                if ((chars.get(d) == 116) && (chars.get(d + 1) == 643)) {
                    portions[portion][15]++;
                    number_2symbol++;
                    d++;
                } else
                if (chars.get(d) == PHONEMS[p]) {
                    portions[portion][p]++;
                }
            }

            next_portion = (portion + 1) * 1000 + number_2symbol;

            if ((d == next_portion) || (d==next_portion+1)) {

                System.out.println(portion);
                System.out.println("d " + d);
                System.out.println("number_2symbol " + number_2symbol);
                portions[portion][16] = portions[portion][16] - portions[portion][9];
                portions[portion][15] = portions[portion][15] - portions[portion][13];
                portions[portion][24] = portions[portion][24] - portions[portion][16];
                portions[portion][17] = portions[portion][17] - portions[portion][15];


                if (portion == 50) {
                    System.out.println("end");
                    return;
                } else {

                    portion++;
                }
            }

        }

    }


    boolean creatFile(String name) {
        for (File item: directory.listFiles()) {
            if (name == String.valueOf(item)) {
                item.delete();
                return false;
            } else  {
                return true;
            }
        }
        return false;
    }

    void creatDirectoryProject() {
        if (panelFile.fileNameLeft == panelFile.fileNameRight) {
            directory = new File("D://Analysis of texts/" + panelFile.fileNameLeft + "/");
            directory.mkdirs();
        } else if ((panelFile.fileNameLeft != null) && (panelFile.fileNameRight == null)) {
            directory = new File("D://Analysis of texts/" + panelFile.fileNameLeft + "/");
            directory.mkdirs();
        } else if ((panelFile.fileNameRight != null) && (panelFile.fileNameLeft == null)) {
            directory = new File("D://Analysis of texts/" + panelFile.fileNameRight + "/");
            directory.mkdirs();
        } else {
            directory = new File("D://Analysis of texts/" + panelFile.fileNameLeft + "_&_" + panelFile.fileNameRight + "/");
            directory.mkdirs();
        }
    }

    void writerFile(String nameFile, int portions[][]) {
        creatDirectoryProject();

        String name = "Portions_" + nameFile;
        File file = new File(directory, name);

//        rewriteFile = creatFile(name);


        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));

            writer.println("Кількість приголосних фонем у тексті - " + chars.size());

            writer.println();
            writer.println();
            writer.println();

            for (int i = 0; i< portions.length; i++) {
                writer.println(("Порція " + (i+1)).toUpperCase());
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

            pathNewFile = file.getPath();

            writer.flush();
            writer.close();

        } catch (IOException ex) {
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

