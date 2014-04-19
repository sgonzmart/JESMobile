package GUI;

import java.awt.*;
import javax.swing.*;

public class Gap extends JComponent {

    public Gap() {
        /* Set default min and max size of the GAP */
        Dimension min = new Dimension(0, 0);
        Dimension max = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        setMinimumSize(min);
        setMaximumSize(max);
        setPreferredSize(min);
    }

    public Gap(int size) {
        /* Set min and max size of the GAP */
        Dimension dim = new Dimension(size, size);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
    }
}
