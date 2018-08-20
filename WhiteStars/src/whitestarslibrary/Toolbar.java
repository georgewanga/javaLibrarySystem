package whitestarslibrary;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar {
    
    public JButton[] button;
    //for creating the name of the image file 24*24
    public String[] imageName24 = {"images/Newbook.gif", "images/listb.gif",
        "images/Add24.gif",
        "images/List24.gif",
        "images/Delete24.gif",
        "images/Find24.gif", "images/Export24.gif",
        "images/Import24.gif",
        "images/Exit24.gif"};
    //for creating the tipText for the toolbar
    public String[] tipText = {"Add Books", "List All Books",
        "Add Members", "List Members", "Delete Member",
        "Search", "Borrow Books", "Return Books", "Exit"};

    public Toolbar() {
        button = new JButton[20];
        for (int i = 0; i < imageName24.length; i++) {
                addSeparator(new Dimension(10,10));
            if (i == 2 || i == 5 || i == 6 || i == 8) //for adding separator to the toolBar
            {
                addSeparator(new Dimension(50,10));
            }
            //for adding the buttons to toolBar
            add(button[i] = new JButton(new ImageIcon(ClassLoader.getSystemResource(imageName24[i]))));
            //for setting the ToolTipText to the button
            button[i].setToolTipText(tipText[i]);
        }
    }
}
