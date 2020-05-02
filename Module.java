

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Module
{
    private boolean task = false;
    private String moduleId;
    private Marsnaut[] marsnaut;
    private int maxNauts;
    private int currentNaut = 0;

    public Module (String moduleId, int maxNauts) {
        this.moduleId = moduleId;
        marsnaut = new Marsnaut[maxNauts];
        this.maxNauts = maxNauts;
    }

    public Module (File textFile) {

    }

    public void addMarsnaut(String marsnautId, String marsnautName) {
        if (currentNaut<20) {
            marsnaut[currentNaut++] = new Marsnaut(marsnautName, marsnautId);
            return;
        }
        System.out.println("This module already has its maximum number of Marsnauts");
    }

    public void addMarsnaut(String marsnautId, String marsnautName, int hours) {
        if (currentNaut<20) {
            marsnaut[currentNaut++] = new Marsnaut(marsnautName, marsnautId, hours);
            return;
        }
        System.out.println("This module already has its maximum number of Marsnauts");
    }

    //计算当前Module中所有Marsnaunt的能力与用户输入能力的等级差别
    public int totalCapability(String capability){
        int total = 0;
        for(int i=0;i<currentNaut;i++){
            total+=marsnaut[i].compareCapability(capability);
        }
        return total;
    }

    public void startTask(int hours) {
        this.task = true;
        for(int i=0;i<currentNaut;i++){
            marsnaut[i].incrementHours(hours);
        }
    }
    //相应的setter和getter方法
    public boolean isTask() {
        return task;
    }

    public void setTask(boolean task) {
        this.task = task;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public int getMaxNauts() {
        return maxNauts;
    }

    public int getCurrentNaut() {
        return currentNaut;
    }

    public void setCurrentNaut(int currentNaut) {
        this.currentNaut = currentNaut;
    }
    //返回marsaunt数组中index位置的Marsaunt对象
    public Marsnaut getMarsnaut(int index){
        return marsnaut[index];
    }
    public void setMarsnaut(int index,Marsnaut marsnaut){
        this.marsnaut[index] = marsnaut;
    }
    @Override
    public String toString() {
        StringBuffer strMarssnaut = new StringBuffer("");
        strMarssnaut.append("\t Max Marsnauts: "+ maxNauts + " Current Marsnauts: " + currentNaut + "\n");
        for(int i=0;i<currentNaut;i++){
            strMarssnaut.append(marsnaut[i].toString() + "\n");}
        if(currentNaut==0){
            strMarssnaut.append("\t No Marsnauts assigned to this Module \n");
        }

        return "Module[ id:" + moduleId + "\n" +
                (this.task?"\t Currently is on a Task":"\t Currently is not on a Task") +  "\n" +
                strMarssnaut.toString() +  " ]";

    }

    public void displayModule(){
        StringBuffer strMarssnaut = new StringBuffer("");
        strMarssnaut.append("Max Marsnauts: "+ maxNauts + " Current Marsnauts: " + currentNaut + "\n");
        String result =  "Module[ id:" + moduleId + "\n" +
                         (this.task?"Currently is on a Task":"Currently is not on a Task") +  "\n" +
                          strMarssnaut.toString() +  " \n ]";
        System.out.println(result);
    }
}
