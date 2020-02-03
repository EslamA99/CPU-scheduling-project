package com.cpuscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scheduling {
    public static double avg = 0;
    public static  double avgTurnRound=0;
    public static ArrayList<Process> statistics = new ArrayList<>();
    public static String AGHistory="";
    public static void SJF(ArrayList<Process> arr) {
        AGHistory="";
        Collections.sort(arr, Comparator.comparing(process -> process.arrivalTime));
        avg = 0;
        avgTurnRound=0;
        statistics.clear();
        for (int i = 0; i < arr.size(); i++) {
            statistics.add(arr.get(i).forStat);
        }
        ArrayList<Process> readyQueue = new ArrayList<>();
        Process currProcess;
        readyQueue.add(arr.get(0));
        currProcess = readyQueue.get(0);
        arr.remove(0);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).arrivalTime == currProcess.arrivalTime) {
                readyQueue.add(arr.get(i));
                arr.remove(i--);
            }
        }
        Collections.sort(readyQueue, Comparator.comparing(process -> process.burstTime));
        currProcess = readyQueue.get(0);
        int totalTime = currProcess.arrivalTime;
        Rang rang = new Rang();
        while (readyQueue.size() > 0) {
            rang.low = totalTime;
            totalTime += currProcess.burstTime;
            rang.high = totalTime;
            currProcess.forStat.waitingTime = totalTime - currProcess.burstTime - currProcess.arrivalTime;
            currProcess.chartTable.Rang_Arr.add(rang);
            rang = new Rang();
            currProcess.burstTime = 0;
            readyQueue.remove(currProcess);
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).arrivalTime <= totalTime) {
                    readyQueue.add(arr.get(i));
                    arr.remove(i--);
                } else break;
            }
            Collections.sort(readyQueue, Comparator.comparing(process -> process.burstTime));
            if (readyQueue.size() != 0)
                currProcess = readyQueue.get(0);
            if(readyQueue.isEmpty()&&!arr.isEmpty()){/**/
                totalTime=arr.get(0).arrivalTime;
                currProcess=arr.get(0);
                readyQueue.add(currProcess);
                arr.remove(0);
            }
        }
        for (int i = 0; i < statistics.size(); i++) {
            statistics.get(i).turnaroundTime = statistics.get(i).waitingTime + statistics.get(i).burstTime;
            avg += statistics.get(i).waitingTime;
            avgTurnRound+=statistics.get(i).turnaroundTime;
        }
        avg = avg / (double) statistics.size();
        avgTurnRound=avgTurnRound/(double)statistics.size();
    }

    public static void Priority(ArrayList<Process> arr) {
        AGHistory="";
        Collections.sort(arr, Comparator.comparing(process -> process.arrivalTime));
        avg = 0;
        avgTurnRound=0;
        statistics.clear();
        for (int i = 0; i < arr.size(); i++) {
            statistics.add(arr.get(i).forStat);
        }
        ArrayList<Process> readyQueue = new ArrayList<>();
        Process currProcess;
        readyQueue.add(arr.get(0));
        currProcess = readyQueue.get(0);
        arr.remove(0);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).arrivalTime == currProcess.arrivalTime) {
                readyQueue.add(arr.get(i));
                arr.remove(i--);
            }
        }
        Collections.sort(readyQueue, Comparator.comparing((Process process) -> process.priority).thenComparing(process -> process.arrivalTime));
        currProcess = readyQueue.get(0);
        int totalTime = currProcess.arrivalTime;
        Rang rang = new Rang();
        while (readyQueue.size() > 0) {
            rang.low = totalTime;
            totalTime += currProcess.burstTime;
            rang.high = totalTime;
            currProcess.forStat.waitingTime = totalTime - currProcess.forStat.burstTime - currProcess.arrivalTime;
            currProcess.chartTable.Rang_Arr.add(rang);
            rang = new Rang();
            currProcess.burstTime = 0;
            readyQueue.remove(currProcess);
            for (int i = 0; i < readyQueue.size(); i++) {
                if (readyQueue.get(i).arrivalTime < totalTime)
                    if (readyQueue.get(i).priority != 0)
                        readyQueue.get(i).priority--;
            }
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).arrivalTime <= totalTime) {
                    readyQueue.add(arr.get(i));
                    arr.remove(i--);
                } else break;
            }
            Collections.sort(readyQueue, Comparator.comparing((Process process) -> process.priority).thenComparing(process -> process.arrivalTime));
            if (readyQueue.size() != 0)
                currProcess = readyQueue.get(0);
            if(readyQueue.isEmpty()&&!arr.isEmpty()){/**/
                totalTime=arr.get(0).arrivalTime;
                currProcess=arr.get(0);
                readyQueue.add(currProcess);
                arr.remove(0);
            }
        }
        for (int i = 0; i < statistics.size(); i++) {
            statistics.get(i).turnaroundTime = statistics.get(i).waitingTime + statistics.get(i).burstTime;
            avg += statistics.get(i).waitingTime;
            avgTurnRound+=statistics.get(i).turnaroundTime;
        }
        avg = avg / (double) statistics.size();
        avgTurnRound=avgTurnRound/(double)statistics.size();
    }

    public static void SRTF(ArrayList<Process> arr) {
        AGHistory="";
        Collections.sort(arr, Comparator.comparing(process -> process.arrivalTime));
        avg = 0;
        avgTurnRound+=0;
        statistics.clear();
        for (int i = 0; i < arr.size(); i++) {
            statistics.add(arr.get(i).forStat);
        }
        ArrayList<Process> readyQueue = new ArrayList<>();
        Process currProcess;
        readyQueue.add(arr.get(0));
        currProcess = readyQueue.get(0);
        arr.remove(0);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).arrivalTime == currProcess.arrivalTime) {
                readyQueue.add(arr.get(i));
                arr.remove(i--);
            }
        }
        Collections.sort(readyQueue, Comparator.comparing(process -> process.burstTime));
        currProcess = readyQueue.get(0);
        int totalTime = currProcess.arrivalTime;
        Rang rang = new Rang();
        rang.low = totalTime;
        Boolean madeSwitch;
        while (readyQueue.size() > 0) {
            madeSwitch = false;
            totalTime++;
            currProcess.burstTime--;
            if (currProcess.burstTime == 0) {
                madeSwitch = true;
                rang.high = totalTime;
                currProcess.chartTable.Rang_Arr.add(rang);
                currProcess.forStat.waitingTime = totalTime - currProcess.forStat.burstTime - currProcess.arrivalTime;
                readyQueue.remove(currProcess);

            }
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).arrivalTime <= totalTime) {
                    readyQueue.add(arr.get(i));
                    arr.remove(i--);
                } else break;
            }
            Collections.sort(readyQueue, Comparator.comparing(process -> process.burstTime));
            if (readyQueue.size() != 0) {
                if (currProcess != readyQueue.get(0)) {
                    madeSwitch = true;
                    rang.high = totalTime;

                    currProcess.chartTable.Rang_Arr.add(rang);

                    for (int i = 0; i < arr.size(); i++) {
                        if (arr.get(i).arrivalTime <= totalTime + 1) {
                            readyQueue.add(arr.get(i));
                            arr.remove(i--);
                        } else break;
                    }
                    Collections.sort(readyQueue, Comparator.comparing(process -> process.burstTime));
                    currProcess = readyQueue.get(0);

                }
            }
            if (madeSwitch) {
                totalTime++;
                rang = new Rang();
                rang.low = totalTime;
            }
            if(readyQueue.isEmpty()&&!arr.isEmpty()){/**/
                totalTime=arr.get(0).arrivalTime;
                currProcess=arr.get(0);
                readyQueue.add(currProcess);
                arr.remove(0);
            }
        }
        for (int i = 0; i < statistics.size(); i++) {
            statistics.get(i).turnaroundTime = statistics.get(i).waitingTime + statistics.get(i).burstTime;
            avg += statistics.get(i).waitingTime;
            avgTurnRound+=statistics.get(i).turnaroundTime;
        }
        avg = avg / (double) statistics.size();
        avgTurnRound=avgTurnRound/(double)statistics.size();
    }


    public static void AG(ArrayList<Process> arr) {
        AGHistory="";
        ArrayList<Process> arrived = new ArrayList();
        ArrayList<Process> result = new ArrayList();
        avg=0;
        avgTurnRound=0;
        Rang rang = new Rang();
        for (int i = 0; i < arr.size(); i++)
            result.add(new Process(arr.get(i).name, arr.get(i).burstTime, arr.get(i).arrivalTime, arr.get(i).priority, arr.get(i).quantumTime));
        boolean firstTime = true;
        int totalTime = 0;
        rang.low = totalTime;
        ArrayList<Process> queue = new ArrayList<>();
        ArrayList<Process> dieList = new ArrayList<>();
        while (arr.size() > 0) {
            arrived.clear();
            if (firstTime) {
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(0).arrivalTime == arr.get(i).arrivalTime)
                        arrived.add(arr.get(i));
                    else break;
                }
                firstTime = false;
            } else {
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i).arrivalTime <= totalTime)
                        arrived.add(arr.get(i));
                    else break;
                }
            }
            Collections.sort(arrived, Comparator.comparing(process -> process.AgFactor));
            Process currProcess = arrived.get(0);
            boolean nonPrimitative = true;
            int i = 0;
            while (i <= currProcess.quantumTime) {
                arrived = update(arr, totalTime);
                if (nonPrimitative) {
                    if (currProcess.burstTime - Math.ceil(currProcess.quantumTime / 2.0) <= 0) { // finish
                        /////////////////////////////////////
                        AGHistory+="Quantum (";
                        for (int j = 0; j < result.size(); j++) {
                            if (j == result.size() - 1)AGHistory+=result.get(j).quantumTime;
                            else AGHistory+=result.get(j).quantumTime + ",";
                        }
                        AGHistory+=") ->  ceil(50%) = (";
                        for (int j = 0; j < result.size(); j++) {
                            if (j == result.size() - 1)
                                AGHistory+=(int) Math.ceil(result.get(j).quantumTime / 2.0);
                            else AGHistory+=(int) Math.ceil(result.get(j).quantumTime / 2.0) + ",";
                        }
                        AGHistory+=") " + currProcess.name + " Running\n";
                        /////////////////////////////////////////////////////////1     5
                        totalTime += currProcess.burstTime;
                        //////////
                        rang.high = totalTime;
                        currProcess.chartTable.Rang_Arr.add(rang);
                        rang = new Rang();
                        rang.low = totalTime;
                        /////////////
                        currProcess.quantumTime = 0;
                        currProcess.burstTime = 0;
                        int indx = 0;
                        for (int j = 0; j < result.size(); j++) {
                            if ((currProcess.name).equals(result.get(j).name)) indx = j;
                        }
                        currProcess.waitingTime = totalTime - result.get(indx).burstTime - currProcess.arrivalTime;
                        currProcess.turnaroundTime = currProcess.waitingTime + result.get(indx).burstTime;
                        result.get(indx).quantumTime = 0;
                        dieList.add(currProcess);
                        arr.remove(currProcess);
                        arrived.remove(currProcess);
                        if (queue.isEmpty()){
                            Process temp=pickProcess(arr,totalTime);
                            if(temp==null)continue;
                            else currProcess=temp;
                        }else{
                            currProcess = queue.get(0);
                            queue.remove(0);
                        }
                        i = 0;
                        continue;
                    } else {
                        /////////////////////////////////////
                        /*System.out.print("Quantum (");*/
                        AGHistory+="Quantum (";
                        for (int j = 0; j < result.size(); j++) {
                            if (j == result.size() - 1)AGHistory+=result.get(j).quantumTime;
                            else AGHistory+=result.get(j).quantumTime + ",";
                        }
                        AGHistory+=") ->  ceil(50%) = (";
                        for (int j = 0; j < result.size(); j++) {
                            if (j == result.size() - 1)
                                AGHistory+=(int) Math.ceil(result.get(j).quantumTime / 2.0);
                            else AGHistory+=(int) Math.ceil(result.get(j).quantumTime / 2.0) + ",";
                        }
                        AGHistory+=") " + currProcess.name + " Running\n";
                        /////////////////////////////////////////////////////////
                        totalTime += Math.ceil(currProcess.quantumTime / 2.0);
                        i += Math.ceil(currProcess.quantumTime / 2.0);
                        currProcess.burstTime -= Math.ceil(currProcess.quantumTime / 2.0);
                        nonPrimitative = false;
                        continue;
                    }
                } else if (!nonPrimitative) {
                    if (currProcess.burstTime == 0) {//finish its all job
                        /////////////////
                        rang.high = totalTime;
                        currProcess.chartTable.Rang_Arr.add(rang);
                        rang = new Rang();
                        rang.low = totalTime;
                        //////////////////////////
                        arr.remove(currProcess);
                        arrived.remove(currProcess);
                        currProcess.quantumTime = 0;
                        currProcess.burstTime = 0;
                        int indx = 0;
                        for (int j = 0; j < result.size(); j++) {
                            if ((currProcess.name).equals(result.get(j).name)) indx = j;
                        }
                        currProcess.waitingTime = totalTime - result.get(indx).burstTime - currProcess.arrivalTime;
                        dieList.add(currProcess);
                        currProcess.turnaroundTime = currProcess.waitingTime + result.get(indx).burstTime;
                        result.get(indx).quantumTime = 0;

                        if (queue.isEmpty()) {
                            //////////
                            rang.high = totalTime;
                            currProcess.chartTable.Rang_Arr.add(rang);
                            rang = new Rang();
                            rang.low = totalTime;
                            //////////
                            Process temp=pickProcess(arr,totalTime);
                            if(temp==null)continue;
                            else currProcess=temp;
                        } else {
                            currProcess = queue.get(0);
                        }
                        nonPrimitative = true;
                        i = 0;
                        continue;
                    }
                    if (i == currProcess.quantumTime) {//finish quantum
                        //////////
                        rang.high = totalTime;
                        currProcess.chartTable.Rang_Arr.add(rang);
                        rang = new Rang();
                        rang.low = totalTime;
                        /////////
                        currProcess.quantumTime += (int) Math.ceil(.1 * (getMean(arr, totalTime)));
                        int indx = 0;
                        for (int j = 0; j < result.size(); j++) {
                            if ((currProcess.name).equals(result.get(j).name)) indx = j;
                        }
                        result.get(indx).quantumTime = currProcess.quantumTime;
                        queue.add(currProcess);
                        currProcess = queue.get(0);
                        queue.remove(0);
                        nonPrimitative = true;
                        i = 0;
                        continue;
                    }
                    Process tempProcess = pickProcess(arr, totalTime);
                    if (tempProcess != null) {
                        if (currProcess != tempProcess) {
                            /////////////////
                            rang.high = totalTime;
                            currProcess.chartTable.Rang_Arr.add(rang);
                            rang = new Rang();
                            rang.low = totalTime;
                            //////////////////
                            currProcess.quantumTime += (currProcess.quantumTime - i);
                            /////////////////////////////////////////////////////
                            int indx = 0;
                            for (int j = 0; j < result.size(); j++) {
                                if ((currProcess.name).equals(result.get(j).name)) indx = j;
                            }
                            result.get(indx).quantumTime = currProcess.quantumTime;
                            /////////////////////////////////////////////////
                            queue.add(currProcess);
                            currProcess = tempProcess;
                            queue.remove(tempProcess);
                            nonPrimitative = true;
                            i = 0;
                        } else {
                            currProcess.burstTime -= 1;
                            totalTime++;
                            i++;
                        }
                    }
                }
            }
        }
        ////////////////////////////
        AGHistory+="Quantum (";
        for (int j = 0; j < result.size(); j++) {
            if (j == result.size() - 1) AGHistory+=result.get(j).quantumTime + ")\n";
            else AGHistory+=result.get(j).quantumTime + ",";
        }
        statistics=dieList;
        for(int i=0;i<statistics.size();i++){
            avg+=statistics.get(i).waitingTime;
            avgTurnRound+=statistics.get(i).turnaroundTime;
        }
        avg=avg/(double)statistics.size();
        avgTurnRound+=avgTurnRound/(double)statistics.size();
    }


    private static ArrayList<Process> update(ArrayList<Process> arr, int totalTime) {
        ArrayList<Process> arrived = new ArrayList<>();
        for (int j = 0; j < arr.size(); j++) {
            if (arr.get(j).arrivalTime <= totalTime) {
                arrived.add(arr.get(j));
            }
        }
        return arrived;
    }

    private static double getMean(ArrayList<Process> arr, int totalTime) {
        ArrayList<Process> arrived = new ArrayList<>();
        for (int j = 0; j < arr.size(); j++) {
            if (arr.get(j).arrivalTime <= totalTime) {
                arrived.add(arr.get(j));
            }
        }
        double sum = 0, temp = 0;
        for (int j = 0; j < arrived.size(); j++) {
            sum += arrived.get(j).quantumTime;
        }
        temp = (sum / arrived.size());
        return temp;
    }

    private static Process pickProcess(ArrayList<Process> arr, int totalTime) {
        ArrayList<Process> arrived = new ArrayList<>();
        for (int j = 0; j < arr.size(); j++) {
            if (arr.get(j).arrivalTime <= totalTime) {
                arrived.add(arr.get(j));
            }
        }
        Collections.sort(arrived, Comparator.comparing(process -> process.AgFactor));
        if (arrived.size() != 0)
            return arrived.get(0);

        return null;
    }

}
