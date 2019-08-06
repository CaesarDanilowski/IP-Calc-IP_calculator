import com.cezarydanilowski.*;

import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CalcFrame frame = new CalcFrame();
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            frame.setSize(new Dimension(800, 500));
            frame.setTitle("IPCalc v1.0");
            frame.setResizable(false);
            frame.setLocation(screenSize.width / 4, screenSize.height / 4);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
