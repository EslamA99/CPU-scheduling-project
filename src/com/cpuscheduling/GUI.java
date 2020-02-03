package com.cpuscheduling;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JPanel panel1;
    private JButton SJFbtn;
    private JButton prioritybtn;
    private JButton SRTFbtn;
    private JButton AGbtn;
    private JButton ADDbtn;
    private JTable processData;
    private JButton removeRowButton;
    DefaultTableModel tableModel = new DefaultTableModel();
    public static ArrayList<ChartTable> chartTable = new ArrayList<>();
    public static ArrayList<Process> processes = new ArrayList<>();

    public GUI() {
        setTitle("DataForm");
        setSize(800, 600);
        add(panel1);
        processData.setModel(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("ArrivalTime");
        tableModel.addColumn("BurstTime");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Quantum");
        tableModel.addColumn("Color");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnActions();



    }

    private void btnActions() {

        removeRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] rows = processData.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    tableModel.removeRow(rows[i] - i);
                }
            }
        });
        ADDbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tableModel.insertRow(tableModel.getRowCount(), new Object[]{"P", 0, 0, 0, 0, ""});
            }
        });
        SJFbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        processes.clear();
                        chartTable.clear();
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            Process p = new Process(tableModel.getValueAt(i, 0).toString(),
                                    Integer.parseInt(tableModel.getValueAt(i, 2).toString()),
                                    Integer.parseInt(tableModel.getValueAt(i, 1).toString()),
                                    Integer.parseInt(tableModel.getValueAt(i, 3).toString()),
                                    Integer.parseInt(tableModel.getValueAt(i, 4).toString())
                            );

                            processes.add(p);
                            p.chartTable.color = tableModel.getValueAt(i, 5).toString();
                            chartTable.add(p.chartTable);
                        }
                        Scheduling.SJF(processes);
                        CharAndStatistics charAndStatistics = new CharAndStatistics(chartTable);
                        charAndStatistics.setVisible(true);
                    }
                });

            }
        });
        prioritybtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                processes.clear();
                chartTable.clear();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Process p = new Process(tableModel.getValueAt(i, 0).toString(),
                            Integer.parseInt(tableModel.getValueAt(i, 2).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 1).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 3).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 4).toString())
                    );

                    processes.add(p);
                    p.chartTable.color = tableModel.getValueAt(i, 5).toString();
                    chartTable.add(p.chartTable);
                }
                Scheduling.Priority(processes);
                CharAndStatistics charAndStatistics = new CharAndStatistics(chartTable);
                charAndStatistics.setVisible(true);
            }
        });
        SRTFbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                processes.clear();
                chartTable.clear();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Process p = new Process(tableModel.getValueAt(i, 0).toString(),
                            Integer.parseInt(tableModel.getValueAt(i, 2).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 1).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 3).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 4).toString())
                    );

                    processes.add(p);
                    p.chartTable.color = tableModel.getValueAt(i, 5).toString();
                    chartTable.add(p.chartTable);
                }
                Scheduling.SRTF(processes);
                CharAndStatistics charAndStatistics = new CharAndStatistics(chartTable);
                charAndStatistics.setVisible(true);
            }
        });
        AGbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                processes.clear();
                chartTable.clear();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Process p = new Process(tableModel.getValueAt(i, 0).toString(),
                            Integer.parseInt(tableModel.getValueAt(i, 2).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 1).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 3).toString()),
                            Integer.parseInt(tableModel.getValueAt(i, 4).toString())
                    );

                    processes.add(p);
                    p.chartTable.color = tableModel.getValueAt(i, 5).toString();
                    chartTable.add(p.chartTable);
                }
                Scheduling.AG(processes);
                CharAndStatistics charAndStatistics = new CharAndStatistics(chartTable);
                charAndStatistics.setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });
    }
}
