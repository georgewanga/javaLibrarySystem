package whitestarslibrary;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

public class StatusBar extends JPanel {

    private final Members member;
    private final JLabel statusBar = new JLabel("White Stars Preparatory School   -  We make learning Fun !!");

    //constructor of StatusBar
    public StatusBar() {
        Date date1 = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String Dat = dateFormat.format(date1);

        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String tim = sdf.format(date1.getTime());

        member = new Members();
        member.connection("SELECT * FROM wspslibrary.users WHERE login = 'active'");
        String nom = member.getFirstName() + " " + member.getSurame() + " " + member.getMiddleName();

        statusBar.addAncestorListener(null);
        statusBar.setOpaque(true);
        statusBar.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        statusBar.setBackground(Color.GREEN);
        statusBar.setForeground(Color.red);
        statusBar.setText("Logged in as : " + nom + "    At: " + tim + "        White Stars Preparatory School - We make learning Fun !!            Date Today: " + Dat);
        this.add(statusBar);
        this.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    }
}
