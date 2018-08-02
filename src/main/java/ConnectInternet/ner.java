package ConnectInternet;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roman on 16.08.2017.
 */



public class ner {
    public static void main(String[] args) {
        LabelFrame frame = new LabelFrame();
        frame.setSize(505, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

class LabelFrame extends JFrame{
    //створюємо панель
    JPanel panel=new JPanel();

    LabelFrame(){
        super("Додавання нових слів-фонем");

        Label label6 = new Label(" ");
        Label label7 = new Label("");
        Label label1 = new Label("aj ərɪdʒənəli əprotʃt dʒavə æz dʒəst ənəðər progræmɪŋ læŋgwədʒ wɪtʃ ən mɛni");
        Label label2 = new Label("sɛnsəz ɪt ɪz bət tajm pæst ænd stədid mɔr dip bɪgæn tu si ðæt ðə fəndəmɛntəl");
        Label label3 = new Label("ɪntɛnt əv ðɪs læŋgwədʒ wəz dɪfərənt frəm əðər læŋgwədʒəz hæd sin əp pɔjnt");
        Label label4 = new Label("progræmɪŋ əbawt mænədʒɪŋ kəmplɛksəti prabləm ju want salv led əpan salvd");
        Label label5 = new Label("bɪkɔz most awər pradʒɛkts fel jɛt ɔl æm əwɛr ɔlmost nən hæv gɔn awt dəsajdəd");

        Label label8 = new Label(" ");
        Label label9 = new Label("");
        String string1 = "Слова-фонеми, які будуть додані до файлу 'Transcription'!!!";
        Font f = new Font("Arial", Font.BOLD, 16);
        Label label = new Label();
//        label11.setText(string2);
        label.setText(string1);

        label.setFont(f);
        panel.add(label);
//panel.add(label6);
//        panel.add(label7);

//        panel.add(label11);
//        label1.setText("I originally approached Java as just another programming language which");
//        System.out.println();
//        jFrame.add(new Label("in many senses it is But time passed and studied more deep began to see"));
//        System.out.println();
//        jFrame.add(new Label("that the fundamental intent of this language was different from other"));
//        System.out.println();
//        jFrame.add(new Label("languages had seen up point Programming about managing complexity problem"));
//        System.out.println();
//        jFrame.add(new Label( "you want solve laid upon solved Because most our projects fail yet all"));
//        System.out.println();
//        jFrame.add(new Label(  "am aware almost none have gone out decided their main design goal would"));
//        System.out.println();
//        jFrame.add(new Label(   "be conquer developing and maintaining programs course many decisions made"));
//        System.out.println();
//        jFrame.add(new Label(      "mind at some point always issues considered essential added into mix"));
//        System.out.println();
//        jFrame.add(new Label(    "Inevitably those cause eventually hit wall For example had backwards"));
//        System.out.println();
//        jFrame.add(new Label(    "compatible easy migration both very useful account much success they"));
//        System.out.println();
//        jFrame.add(new Label(    "expose extra prevents certainly you can blame management if a catching"));
//        System.out.println();
//        jFrame.add(new Label(     "mistakes why another example tied really designed extensions piled upon"));
//        System.out.println();
//        jFrame.add(new Label(     "produced truly syntax Unix tools"));
//        jFrame.add(label1);
//        jFrame.add(label2);
//        jFrame.add(label3);
//        jFrame.add(label4);
//        jFrame.add(label5);
        panel.add(label1, BorderLayout.CENTER);
        panel.add(label2, BorderLayout.CENTER);
        panel.add(label3, BorderLayout.CENTER);
        panel.add(label4, BorderLayout.CENTER);
        panel.add(label5, BorderLayout.CENTER);
        panel.add(label8, BorderLayout.CENTER);
        panel.add(label9, BorderLayout.CENTER);

//       errorDialog();

        panel.add(new Button("Ok"), BorderLayout.LINE_END);
        // вирівнювання за замовчуванням (CENTER)

        this.add(panel);
    }

}