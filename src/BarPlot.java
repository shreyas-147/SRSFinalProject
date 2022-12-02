import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

/**
 * Class for Plotting the Bar Graph for the selected dates
 *
 */
public class BarPlot extends JPanel {
    private double[] values;
    private int pointWidth = 4;
    private int yDivisions = 10;
    private String[] dates;
    private int padding = 25;
    private int labelPadding = 25;
    private String title;

    /**
     * Constructor for initializing the class variables
     *
     * @param v
     * @param n
     * @param t
     */
    public BarPlot(double[] v, String[] n, String t) {
        dates = n;
        values = v;
        title = t;
    }

    /**
     * Method for painting the Bar Graph
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.length == 0)
            return;
        double minV = 0;
        double maxV = 0;
        for (int i = 0; i < values.length; i++) {
            if (minV > values[i])
                minV = values[i];
            if (maxV < values[i])
                maxV = values[i];
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height - 35;
        int barWidth = (clientWidth / values.length) - padding - labelPadding;

        Font font = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(font);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int y = titleFontMetrics.getAscent();
        int x = (clientWidth - titleWidth) / 2;
        g.setFont(font);
        g.drawString(title, x, y);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxV == minV)
            return;
        double scale = (clientHeight - top - bottom) / (maxV - minV);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < yDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight()
                    - ((i * (getHeight() - padding * 2 - labelPadding)) / yDivisions + padding + labelPadding);
            int y1 = y0;
            if (values.length > 0) {
                g.setColor(Color.black);
                g.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g.setColor(Color.BLACK);
                String yLabel = ((int) ((minV
                        + (maxV - minV) * ((i * 1.0) / yDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g.drawLine(x0, y0, x1, y1);
        }
        for (int i = 0; i < values.length; i++) {
            int valueX = i * barWidth + labelPadding + padding;
            int valueY = top;
            int height = (int) (values[i] * scale);
            if (values[i] >= 0)
                valueY += (int) ((maxV - values[i]) * scale);
            else {
                valueY += (int) (maxV * scale);
                height = -height;
            }

            g.setColor(Color.blue);
            g.fillRect(valueX, valueY, barWidth - 50, height);
            g.setColor(Color.black);
            g.drawRect(valueX, valueY, barWidth - 50, height);
            int labelWidth = labelFontMetrics.stringWidth(dates[i]);
            x = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(dates[i], x, y);
        }
    }

    /**
     * Method for initiating the Bar plot GUI
     *
     */
    public static void barPlotGUI() {

        JFrame f = new JFrame();
        f.setSize(500, 500);

        List<LocalDate> dates = new ArrayList<LocalDate>();

        // Read dates from header list
        for (int i = 4; i < AttendanceDatabase.fileHeaders.size(); i++) {
            dates.add(LocalDate.parse(AttendanceDatabase.fileHeaders.get(i)));
        }

        List<Double> classData = new ArrayList<Double>();
        for (LocalDate date : dates) {
            List<Double> xAxis = Main.attendanceRecord.getDataSet(date);
            double count = 0;
            for (int i = 0; i < xAxis.size(); i++) {
                if (xAxis.get(i) > 0)
                    count++;
            }
            classData.add(count);
        }
        String[] date = new String[dates.size()];
        double[] record = new double[classData.size()];
        for (int i = 0; i < dates.size(); i++) {
            date[i] = Main.attendanceRecord.parseDate(String.valueOf(dates.get(i)));
            record[i] = classData.get(i);
        }
        System.out.println("\n Dates array for plot :");
        System.out.println(Arrays.toString(date)+"\n");
        f.getContentPane().add(new BarPlot(record, date, "BarPlot"));
        Dimension d= new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        f.setSize(d.width,d.height);
        f.setVisible(true);
    }

}
