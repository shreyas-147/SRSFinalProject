import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class BarPlot extends JPanel {
    private double[] values;
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private String[] dates;
    private int padding = 25;
    private int labelPadding = 25;
    private String title;

    public BarPlot(double[] v, String[] n, String t) {
        dates = n;
        values = v;
        title = t;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.length == 0)
            return;
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < values.length; i++) {
            if (minValue > values[i])
                minValue = values[i];
            if (maxValue < values[i])
                maxValue = values[i];
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height - 35;
        System.out.println(clientHeight);
        int barWidth = (clientWidth / values.length) - padding - labelPadding;

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int y = titleFontMetrics.getAscent();
        int x = (clientWidth - titleWidth) / 2;
        g.setFont(titleFont);
        g.drawString(title, x, y);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue)
            return;
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight()
                    - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (values.length > 0) {
                g.setColor(Color.black);
                g.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g.setColor(Color.BLACK);
                String yLabel = ((int) ((minValue
                        + (maxValue - minValue) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
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
                valueY += (int) ((maxValue - values[i]) * scale);
            else {
                valueY += (int) (maxValue * scale);
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
     * Displays the scatter plot with all the data
     */
    public static void barPlotGUI() {

        JFrame f = new JFrame();
        f.setSize(500, 500);

        List<LocalDate> dates = new ArrayList<LocalDate>();

        // Read dates from header list
        for (int i = 4; i < Repository.headers.size(); i++) {
            dates.add(LocalDate.parse(Repository.headers.get(i)));
        }

        List<Double> classData = new ArrayList<Double>();
        for (LocalDate date : dates) {
            List<Double> xAxis = Main.repo.getDataSet(date);
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
            date[i] = Main.repo.parseDate(String.valueOf(dates.get(i)));
            record[i] = classData.get(i);
        }
        System.out.println("dates array for plot");
        System.out.println(Arrays.toString(date));
        f.getContentPane().add(new BarPlot(record, date, "BarPlot"));
        f.setVisible(true);
    }

}