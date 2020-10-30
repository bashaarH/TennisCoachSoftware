
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.awt.Dimension;

import javax.swing.*;

public class LineGraph extends ApplicationFrame {

	public LineGraph(String title, String axis, DefaultCategoryDataset dataset) {
		super(title);
		// Generate the chart 
		JFreeChart chart = ChartFactory.createLineChart(title, "", axis, dataset, PlotOrientation.VERTICAL, true, true,
				false);
		// Add the chart to a panel 
		ChartPanel panel = new ChartPanel(chart);
		Dimension d = new Dimension(400, 500);
		this.setPreferredSize(d);
		// Add the panel to this ApplicationFrame 
		this.setContentPane(panel);
		this.setVisible(true);
		this.pack();
	}

	public static void createPctGraph(Object[] values) {
		WinPct stat;
		String name = ((Statistic) values[values.length - 1]).getName();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < values.length; i++) {
			stat = (WinPct) values[i];
			dataset.addValue((stat.getFeeding() * 100), "Feed and Play", "" + (i + 1));
		}

		for (int i = 0; i < values.length; i++) {
			stat = (WinPct) values[i];
			
				dataset.addValue((stat.getServing() * 100), "Serving", "" + (i + 1));
		}

		LineGraph graph = new LineGraph(name + ": Win Percentages", "Win Percentage", dataset);
	}

	public static void createCrtGraph(Object[]values)
	{
		String name =( (Statistic) values[values.length - 1]).getName(); 
		CourtMovement stat;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0 ; i < values.length; i++)
		{
			stat = (CourtMovement) values[i];
			
			dataset.addValue(stat.getStart() + 1,  "Starting Court", "" + (i+1));
		}
		
		for (int i = 0 ; i < values.length; i++)
		{
			stat = (CourtMovement) values[i];
			// Make sure the ending court value for the stat is >= 0
			stat.adjust();
			dataset.addValue(stat.getEnd() + 1, "Ending Court", "" + (i+1));
		}
		LineGraph graph = new LineGraph(name +": Courts", "Court" , dataset);
	}
}
