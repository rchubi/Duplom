import javax.swing.*;

/**
 * Created by Roman on 09.05.2017.
 */
public class Window extends JFrame{
    Window() {
        super("Диференціація текстів на фонологічному рівні");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        JPanel panelFile = new PanelFile();
        JPanel panelTranscription = new PanelTranscription();
        JPanel DistributionOfPortions = new DistributionOfPortions();
        JPanel DistributionOfGroups = new DistributionOfGroups();
        JPanel CriterionPearson = new CriterionPearson(5.8);
        JPanel CriterionStudent = new CriterionStudent();

        tabbedPane.add("Файли", panelFile);
        tabbedPane.add("Транскрипція", panelTranscription);
        tabbedPane.add("Розподіл по порціях", DistributionOfPortions);
        tabbedPane.add("Розподіл по групах", DistributionOfGroups);
        tabbedPane.add("Критерій Пірсона", CriterionPearson);
        tabbedPane.add("Критерій Стьюдента", CriterionStudent);
        getContentPane().add(tabbedPane);
        setSize(1400, 750);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
