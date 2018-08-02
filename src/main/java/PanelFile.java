//import ConnectInternet.ExtFileFilter;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by Roman on 09.05.2017.
 */
public class PanelFile extends JPanel {

    private  JTextArea jTextAreaRight;
    private  JTextArea jTextAreaLeft;

    private JButton buttonLeft;
    private JButton buttonRight;

    public static String fileNameLeft;
    public static String fileNameRight;

    private String nameFile;

    private JFileChooser fileChooser;

    PanelFile() {

//  текстові поля
        jTextAreaLeft = new JTextArea(38, 56);
        jTextAreaRight = new JTextArea(38, 60);

//  текстові поля
        JScrollPane scrollPaneFilesLeft = new JScrollPane(jTextAreaLeft);
        JScrollPane scrollPaneFilesRight = new JScrollPane(jTextAreaRight);

        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();

        panelLeft.add(scrollPaneFilesLeft, BorderLayout.SOUTH);
        panelRight.add(scrollPaneFilesRight);

//  розділ вікна на 2 підвікна
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPane.setOneTouchExpandable(false);

        buttonLeft = new JButton("Відкрити файл з лівого боку");
        buttonRight = new JButton("Відкрити файл з правого боку");


        buttonLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

//        panelLeft.add(nameFileLeft, BorderLayout.NORTH);
        panelLeft.add(buttonLeft, BorderLayout.NORTH);
        panelRight.add(buttonRight, BorderLayout.NORTH);
//        panelRight.add(nameFileRight, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();

        buttonLeft.addActionListener((e) -> {
            openFileFromFileChooser(jTextAreaLeft, buttonLeft, 1);
        });

        buttonRight.addActionListener((e) -> {
            openFileFromFileChooser(jTextAreaRight, buttonRight, 2);
        });

//        panel.add(nameFileLeft, BorderLayout.NORTH);
        jPanel.add(buttonLeft, BorderLayout.NORTH);
        jPanel.add(buttonRight, BorderLayout.NORTH);
//        panel.add(nameFileRight, BorderLayout.NORTH);


        this.add(jPanel);
        this.add(splitPane);
    }

    void filePath(JButton button, String name) {
        if (button == buttonLeft) {
            fileNameLeft = name;
            System.out.println("fnl " + "   " + fileNameLeft);
        } else {
            fileNameRight = name;
            System.out.println("fnr " + "   " + fileNameRight);
        }
    }

    public void openFileFromFileChooser(JTextArea textArea, JButton button, int numberSide) {
        fileChooser = new JFileChooser();
        ExtFileFilter fileFilter = new ExtFileFilter("txt", "D:\\");
        fileChooser.addChoosableFileFilter(fileFilter);


        if( fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            nameFile = file.getName();
            System.out.println(nameFile);
            filePath(button, nameFile);

            System.out.println(file);
            //--- проверить, что это файл и он доступен для чтени
            if ( !file.isFile() || !file.canRead() ) {
                errorDialog();
//                System.out.println("Fail "+file.getName());
                return;
            }
            textArea.setText("");

            BufferedReader in = null;

            button.setText(file.getName());
            try {
                in = new BufferedReader(new FileReader(file));
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
                if ( in != null )
                    try {
                        in.close();
                    } catch ( IOException e ) {
                    }
            }
        }
    }

    void errorDialog() {
        String message = "Вибрано не правильний файл. Файл повинен мати розширення .txt.";
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
