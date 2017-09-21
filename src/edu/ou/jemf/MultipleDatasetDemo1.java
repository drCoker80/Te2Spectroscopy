package edu.ou.jemf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demo showing the addition and removal of multiple datasets / renderers.
 */
public class MultipleDatasetDemo1 extends ApplicationFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private XYPlot plot;
	private int datasetIndex = 0;

	/**
	 * Constructs a new demonstration application.
	 *
	 * @param title
	 *            the frame title.
	 */
	public MultipleDatasetDemo1(final String title) {

		super(title);
		final XYSeriesCollection dataset1 = createRandomDataset("Series 1");
		final JFreeChart chart = ChartFactory.createXYLineChart("Multiple Dataset Demo 1", "Time", "Value", dataset1);
		chart.setBackgroundPaint(Color.white);

		this.plot = chart.getXYPlot();
		this.plot.setBackgroundPaint(Color.lightGray);
		this.plot.setDomainGridlinePaint(Color.white);
		this.plot.setRangeGridlinePaint(Color.white);
		final ValueAxis axis = this.plot.getDomainAxis();
		axis.setAutoRange(true);

		final NumberAxis rangeAxis2 = new NumberAxis("Range Axis 2");
		rangeAxis2.setAutoRangeIncludesZero(false);

		final JPanel content = new JPanel(new BorderLayout());

		final ChartPanel chartPanel = new ChartPanel(chart);
		content.add(chartPanel);

		final JButton button1 = new JButton("Add Dataset");
		button1.setActionCommand("ADD_DATASET");
		button1.addActionListener(this);

		final JButton button2 = new JButton("Remove Dataset");
		button2.setActionCommand("REMOVE_DATASET");
		button2.addActionListener(this);

		final JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(button1);
		buttonPanel.add(button2);

		content.add(buttonPanel, BorderLayout.SOUTH);
		chartPanel.setPreferredSize(new java.awt.Dimension(960, 544));
		setContentPane(content);

	}

	private XYSeriesCollection createRandomDataset(final String name) {
		final XYSeries series = new XYSeries(name);
		double value;
		double[] window = Config.window;
		for (int i = 0; i < 100; i++) {
			if (i >= window[0] && i <= window[1]) {
				value = Config.JEMFFunction(i);
				series.add(i, value);
			}
		}
		return new XYSeriesCollection(series);
	}

	/**
	 * Handles a click on the button by adding new (random) data.
	 *
	 * @param e
	 *            the action event.
	 */
	public void actionPerformed(final ActionEvent e) {

		if (e.getActionCommand().equals("ADD_DATASET")) {
			if (this.datasetIndex < 20) {
				this.datasetIndex++;
				this.plot.setDataset(this.datasetIndex, createRandomDataset("S" + this.datasetIndex));
				this.plot.setRenderer(this.datasetIndex, new StandardXYItemRenderer());
			}
		} else if (e.getActionCommand().equals("REMOVE_DATASET")) {
			if (this.datasetIndex >= 1) {
				this.plot.setDataset(this.datasetIndex, null);
				this.plot.setRenderer(this.datasetIndex, null);
				this.datasetIndex--;
			}
		}

	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 */
	public static void main(final String[] args) {

		final MultipleDatasetDemo1 demo = new MultipleDatasetDemo1("Multiple Dataset Demo 1");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
