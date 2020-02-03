package com.cpuscheduling;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CharAndStatistics extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel chartPanelGUI;
    private JTable processData;
    private JLabel AvgWaiting;
    private JTextArea AgHistory;
    private JLabel avgTurnRound;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private static ArrayList<ChartTable>chartTable;
    public CharAndStatistics(ArrayList<ChartTable>chartTables) {
        chartTable=chartTables;
        setTitle("Chart And Statistics");
        setSize(800, 600);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final IntervalCategoryDataset dataset = createDataset();
        //-------------------------------------------------------
        final JFreeChart chart = ChartFactory.createGanttChart(
                "CPU Scheduling",  // chart title
                "Process",              // domain axis label
                "Time",              // range axis label
                dataset,             // data
                true,                // include legend
                false,                // tooltips
                false                // urls
        );
        //-------------------------------------------------------
        setColors(chart);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(true);
        chartPanelGUI.setLayout(new BorderLayout());
        chartPanelGUI.add(chartPanel, BorderLayout.NORTH);
        setStatistics();
    }

    private void setStatistics() {
        AgHistory.setText(Scheduling.AGHistory);

        AvgWaiting.setText(String.valueOf(Scheduling.avg));
        avgTurnRound.setText(String.valueOf(Scheduling.avgTurnRound));
        processData.setModel(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("WaitingTime");
        tableModel.addColumn("TurnaroundTime");
        for(int i=0;i<Scheduling.statistics.size();i++){
            tableModel.insertRow(tableModel.getRowCount(), new Object[]{Scheduling.statistics.get(i).name,Scheduling.statistics.get(i).waitingTime,Scheduling.statistics.get(i).turnaroundTime});
        }
    }

    private void setColors(JFreeChart chart) {
        CategoryPlot categoryplot = (CategoryPlot)chart.getPlot();
        DateAxis axis=(DateAxis) categoryplot.getRangeAxis();//For X Axis Format
        axis.setDateFormatOverride(new SimpleDateFormat("SSS"));//For X Axis Format
        CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
        for(int i=0;i<chartTable.size();i++){
            Color color;
            try {
                Field field = Class.forName("java.awt.Color").getField(chartTable.get(i).color);
                color = (Color)field.get(null);
            } catch (Exception e) {
                color = null; // Not defined
            }
            categoryitemrenderer.setSeriesPaint(i, color);
        }

    }
    public static IntervalCategoryDataset createDataset() {
        final TaskSeriesCollection collection = new TaskSeriesCollection();
        for(int i=0;i<chartTable.size();i++){
            String name=chartTable.get(i).name;
            ArrayList<Rang>rangs=chartTable.get(i).Rang_Arr;
            TaskSeries series=new TaskSeries(name);
            Task t=new Task(name,new SimpleTimePeriod(rangs.get(0).low,rangs.get(rangs.size()-1).high));
            for(int j=0;j<rangs.size();j++){
                t.addSubtask(new Task(name,new SimpleTimePeriod(rangs.get(j).low,rangs.get(j).high)));
            }
            series.add(t);
            collection.add(series);
        }
        return collection;
    }



}
