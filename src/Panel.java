import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Class for displaying the Table for the data
 *
 */
public class Panel extends JPanel implements Observer {
    
	protected JTable table;

	/**
	 * Constructor for Panel class which also adds a scroll pane
	 *
	 */
    public Panel() {
    	
    	setLayout(new BorderLayout());
    	
    	Dimension preferred = new Dimension();
		preferred.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1.5, Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.5);
		setPreferredSize(preferred);
		
		Dimension screen = new Dimension();
		screen.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    	setSize(screen);
		
		
    	this.table = new JTable();
    	JScrollPane scrollPane = new JScrollPane(table);
    	scrollPane.setSize(screen);
    	add(scrollPane);
    	
    }
    
    
    /**
	 * Method for setting the value of drawable object and repainting
	 * the Panel
	 *
     * @param o Observable object that notified this plotPanel observer
     * @param arg Object passed by notifyObservers() 
     */
    @Override
    public void update(Observable o, Object arg) {
    	String[][] data = ((Repository)o).getTableData();
    	String[] headers = ((Repository)o).getHeaders();
    	this.table.setModel(new DefaultTableModel(data, headers));
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
}