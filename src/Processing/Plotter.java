package Processing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import weka.core.Instances;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Log;
import org.jfree.util.LogContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by ressay on 11/10/18.
 */
public class Plotter
{
    Instances data;

    public Plotter(Instances data)
    {
        this.data = data;
    }

    public void plotBox()
    {
        JFrame frame = new JFrame("boxPlot");
        final BoxAndWhiskerCategoryDataset dataset = createSampleDataset();

        final CategoryAxis xAxis = new CategoryAxis("Attribute");
        final NumberAxis yAxis = new NumberAxis("Value");
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();

        renderer.setFillBox(true);
        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                "Box-plot",
                new Font("SansSerif", Font.BOLD, 14),
                plot,
                true
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        frame.setContentPane(chartPanel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);

    }

    public void plotBox(int attributeIndex)
    {
        JFrame frame = new JFrame("boxPlot");
        final BoxAndWhiskerCategoryDataset dataset = createDatasetBox(attributeIndex);

        final CategoryAxis xAxis = new CategoryAxis("Attribute");
        final NumberAxis yAxis = new NumberAxis("Value");
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();

        renderer.setFillBox(true);
        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                "Box-plot",
                new Font("SansSerif", Font.BOLD, 14),
                plot,
                true
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        frame.setContentPane(chartPanel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);

    }

    private BoxAndWhiskerCategoryDataset createDatasetBox(int attributeIndex) {

        final int entityCount = data.numInstances();

        final DefaultBoxAndWhiskerCategoryDataset dataset
                = new DefaultBoxAndWhiskerCategoryDataset();
            final ArrayList<Double> list = new ArrayList<>();
            if(data.attribute(attributeIndex).isNominal())
                return null;
            for (int k = 0; k < entityCount; k++) {
                final double value1 = data.instance(k).value(attributeIndex);
                list.add(value1);
            }

            dataset.add(list, "Series ", data.attribute(attributeIndex).name());

        return dataset;
    }

    private BoxAndWhiskerCategoryDataset createSampleDataset() {

        final int categoryCount = data.numAttributes();
        final int entityCount = data.numInstances();

        final DefaultBoxAndWhiskerCategoryDataset dataset
                = new DefaultBoxAndWhiskerCategoryDataset();
        for (int j = 0; j < categoryCount; j++) {
            final ArrayList<Double> list = new ArrayList<>();
            if(data.attribute(j).isNominal())
                continue;
            for (int k = 0; k < entityCount; k++) {
                final double value1 = data.instance(k).value(j);
                list.add(value1);
            }

            dataset.add(list, "Series ", data.attribute(j).name());
        }

        return dataset;
    }

    public void plotHisto(int attributeIndex)
    {
//        if(!data.attribute(attributeIndex).isNominal())
//            return;
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );
        if(data.attribute(attributeIndex).isNumeric())
        {

            TreeMap<Double,Integer> frequencies = new TreeMap<>();
            for (int i = 0; i < data.numInstances(); i++)
            {
                double value = data.instance(i).value(attributeIndex);
                if(!frequencies.containsKey(value))
                    frequencies.put(value,0);
                frequencies.put(value,
                        frequencies.get(value)+1);
            }
            for(Double key : frequencies.keySet())
                dataset.addValue(frequencies.get(key),key,"frequency");
        }
        if(data.attribute(attributeIndex).isNominal())
        {

            TreeMap<String,Integer> frequencies = new TreeMap<>();
            for (int i = 0; i < data.numInstances(); i++)
            {
                String value = data.attribute(attributeIndex).isNominal()?
                        data.instance(i).stringValue(attributeIndex):data.instance(i).value(attributeIndex)+"";
                if(!frequencies.containsKey(value))
                    frequencies.put(value,0);
                frequencies.put(value,
                        frequencies.get(value)+1);
            }
            for(String key : frequencies.keySet())
                dataset.addValue(frequencies.get(key),key,"frequency");
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "histogram",
                "Category",
                "frequency",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame("histogram");
        frame.setContentPane(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

}
