package GUI;

import java.awt.*;

public class AyudaGB extends GridBagConstraints {

    /* Creates helper at top left, component always fills cells. */
    public AyudaGB() {
        gridx = 0;
        gridy = 0;
        fill = GridBagConstraints.BOTH; // Component fills area
    }

    /* Moves the helper's cursor to the right one column. */
    public AyudaGB nextCol() {
        gridx++;
        return this;
    }

    /* Moves the helper's cursor to first col in next row. */
    public AyudaGB nextRow() {
        gridx = 0;
        gridy++;
        return this;
    }


    /* Expandable Width. Returns new helper allowing horizontal expansion. A new
     * helper is created so the expansion values don't pollute the origin
     * helper. */
    public AyudaGB expandW() {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.weightx = 1.0;
        return duplicate;
    }

    /* Expandable Height. Returns new helper allowing vertical expansion. */
    public AyudaGB expandH() {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.weighty = 1.0;
        return duplicate;
    }

    /* Sets the width of the area in terms of number of columns. */
    public AyudaGB width(int colsWide) {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.gridwidth = colsWide;
        return duplicate;
    }

    /* Width is set to all remaining columns of the grid. */
    public AyudaGB width() {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.gridwidth = REMAINDER;
        return duplicate;
    }

    /* Sets the height of the area in terms of rows. */
    public AyudaGB height(int rowsHigh) {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.gridheight = rowsHigh;
        return duplicate;
    }

    /* Height is set to all remaining rows. */
    public AyudaGB height() {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.gridheight = REMAINDER;
        return duplicate;
    }

    /* Alignment is set by parameter. */
    public AyudaGB align(int alignment) {
        AyudaGB duplicate = (AyudaGB) this.clone();
        duplicate.fill = NONE;
        duplicate.anchor = alignment;
        return duplicate;
    }
}
