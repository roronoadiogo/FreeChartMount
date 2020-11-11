package com.roronoadiogo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.text.AttributedString;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Renderer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLabelLocation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYAnnotationEntity;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.HighLowRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.RangeType;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLC;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class JFreeChartFrame extends JFrame {

	private JFrame framePrimary;

	public JFreeChartFrame() {
		this.framePrimary = new JFrame();
		initalize(framePrimary);
	}

	private void initalize(JFrame frame) {

		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Look and feel falhou");
		}

		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.setSize(400, 300);
		frame.setTitle("FreeChart");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		ChartPanel panelChart = createChartSale();
		panelChart.setSize(400, 300);

		JPanel panel = new JPanel();
		frame.add(panel);
		frame.add(panelChart);

		frame.setVisible(true);

	}

	private ChartPanel createChart() {

		// iniciar dataset
		XYDataset dataset = new XYSeriesCollection();

		// criar grafico para swing compnente
		JFreeChart chart = ChartFactory.createXYLineChart("", "R$", "Semana", dataset, PlotOrientation.VERTICAL, true,
				true, false);

		ChartPanel panel = new ChartPanel(chart);

		// Definir redenrização
		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer shape = new XYLineAndShapeRenderer();

		shape.setSeriesPaint(0, Color.getColor("#6394ef"));
		shape.setSeriesStroke(0, new BasicStroke(4.0f));
		shape.setSeriesPaint(1, Color.getColor("#ce6394"));
		shape.setSeriesStroke(1, new BasicStroke(4.0f));

		NumberAxis rangeAxis1 = new NumberAxis("R$");
		rangeAxis1.setAutoRangeIncludesZero(false);
		rangeAxis1.setAutoRangeMinimumSize(100);
		rangeAxis1.setAutoRangeStickyZero(false);
		rangeAxis1.setRangeType(RangeType.POSITIVE);
		rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis1.setLabelLocation(AxisLabelLocation.MIDDLE);
		
		DateAxis dateAxis = new DateAxis();
		dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy")); 
		
		dateAxis.setLabel("Semana");
		
		plot.setDomainAxis(dateAxis);

		plot.setRangeAxis(rangeAxis1);
		chart.setBackgroundPaint(Color.getColor("#EBEBEB"));		
		plot.setRenderer(shape);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setDomainGridlinesVisible(true);
		
		shape.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());
		
		plot.setDomainGridlineStroke(new BasicStroke(1.0f));

		plot.setRangeZeroBaselineVisible(true);
		
		TimeSeries series = new TimeSeries("Anidro");
		series.add(new Day(new Date()), 1650);
		series.add(new Day(new Date()).next().next(), 1800);
		
		TimeSeries series1 = new TimeSeries("Hidratado");
		series1.add(new Day(new Date()), 1900);
		series1.add(new Day(new Date()).next().next(), 1850);
	
		TimeSeriesCollection colletionTIme = new TimeSeriesCollection();
		colletionTIme.addSeries(series1);
		colletionTIme.addSeries(series);
		
		plot.setDataset(colletionTIme);

		return panel;
	}

	private ChartPanel createChartSale() {
		
		XYDataset data = new TimeSeriesCollection();		
		JFreeChart chart = ChartFactory.createXYLineChart("", "", "", data, PlotOrientation.VERTICAL, true, true, false);
				
		XYPlot plot = chart.getXYPlot();
		
		NumberAxis axis = new NumberAxis("Preço (R$/m³)");
		
		String pattern  ="dd/MM";
		DateFormat format = new SimpleDateFormat(pattern);
		
		DateAxis date = new DateAxis("Dia");
		date.setDateFormatOverride(format);
		
		plot.setDomainAxis(date);
		plot.setRangeAxis(axis);
		
		TimeSeries serie = new TimeSeries("Média");
		serie.add(new Day(), 1800);
		serie.add(new Day(new Date()).next().next(), 1850);
		serie.add(new Day(new Date()).next().next().next(), 1250);
		
		TimeSeries series1 = new TimeSeries("Hidratado");
		series1.add(new Day(new Date()), 1900);
		series1.add(new Day(new Date()).next().next(), 1850);
		
		TimeSeriesCollection colletionTIme = new TimeSeriesCollection();
		colletionTIme.addSeries(series1);
		colletionTIme.addSeries(serie);
		
		plot.setDataset(colletionTIme);
		
		return new ChartPanel(chart);
		
	}

}
