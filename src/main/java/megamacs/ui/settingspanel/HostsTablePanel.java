package megamacs.ui.settingspanel;

import megamacs.conf.Host;
import megamacs.Injector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class HostsTablePanel extends JPanel {

    Injector i;

    public HostsTablePanel(Injector i) {
        super(new GridLayout(1,0));

        this.i = i;

        MyTableModel myTableModel = new MyTableModel();

        JTable table = new JTable(myTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        initColumnSizes(table);

        //Fiddle with the Sport column's cell editors/renderers.
        //setUpSportColumn(table, table.getColumnModel().getColumn(2));

        //Add the scroll pane to this panel.
        add(scrollPane);

        // init values

        for (int rowIndex=0; rowIndex<i.confService.getHosts().size();rowIndex++){
            myTableModel.setValueAt(i.confService.getHosts().get(rowIndex).getIpAddress(),rowIndex,0);
            myTableModel.setValueAt(i.confService.getHosts().get(rowIndex).getTrigram(),rowIndex,1);
            myTableModel.setValueAt(i.confService.getHosts().get(rowIndex).getIsLocal(),rowIndex,2);
        }
    }

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table) {
        MyTableModel model = (MyTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
                table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                    null, column.getHeaderValue(),
                    false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                    getTableCellRendererComponent(
                            table, longValues[i],
                            false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"IP address",
                "Trigram",
                "isLocal"};

        public final Object[] longValues = {"192.120.0.1","ABO",Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return i.confService.getHosts().size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            Host host = i.confService.getHosts().get(row);


            switch (col) {
                case 0:
                    return host.getIpAddress();
                case 1:
                    return host.getTrigram();
                case 2:
                    return host.getIsLocal();
            }
            return null;
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return true;
            /*
            if (col < 2) {
                return false;
            } else {
                return true;
            }
            */
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            //if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            //}

            Host host = i.confService.getHosts().get(row);

            switch (col) {
                case 0:
                    host.setIpAddress((String)value);
                    break;
                case 1:
                    host.setTrigram((String)value);
                    break;
                case 2:
                    host.setIsLocal((Boolean)value);
                    break;
            }

            fireTableCellUpdated(row, col);
        }
    }
}
