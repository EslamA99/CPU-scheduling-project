package com.cpuscheduling;

public class Process {
    String name;
    int burstTime;
    int arrivalTime;
    int priority;
    int quantumTime;
    int waitingTime;
    int turnaroundTime;
    int AgFactor;
    ChartTable chartTable;
    Process forStat;
    //int stoppedTime;
    //int idxAtAll;

    public Process(String name, int burstTime, int arrivalTime, int priority, int quantumTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.quantumTime = quantumTime;
        chartTable=new ChartTable(this.name);
        this.AgFactor=(burstTime+priority+arrivalTime);
        forStat=new Process(this.name,this.burstTime,this.arrivalTime,this.priority,this.quantumTime,0,0);
    }

    public Process(String name, int burstTime, int arrivalTime, int priority, int quantumTime, int waitingTime, int turnaroundTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.quantumTime = quantumTime;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
    }
}
