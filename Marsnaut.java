

/**
 * Created by Administrator on 2018/5/6 0006.
 */
public class Marsnaut {
    private String name;
    private String marsnautId;
    private int hours;
    private String capability = "Base Only";

    public Marsnaut(String name, String marsnautId, int hours) {
        this.name = name;
        this.marsnautId = marsnautId;
        this.hours = hours;
        this.capability = getCapability();
    }

    public Marsnaut(String name, String marsnautId) {
        this.name = name;
        this.marsnautId = marsnautId;
        this.hours = 0;
        this.capability = getCapability();
    }

    private void setCapability(){
        if(hours>=0 && hours<=5){
            capability = "Base only";
        }else if(hours>=6 && hours<=12){
            capability = "Farms";
        }else if(hours>=13 && hours<=19){
            capability = "  Life Support";
        }else{
            capability = "Mars rover";
        }
    }

    public String getCapability(){
        setCapability();
        return capability;
    }

    public void incrementHours(int hours){
        this.hours = this.hours + (hours + 1);
        setCapability();
    }

    @Override
    public String toString() {
        return "\tmarsnaut[ name:" + name + "\n" +
                "             id:" + marsnautId + " \t hours=" + hours + "\n" +
                "             cap:" + capability + "\n" +
                "           ]";
    }

    public boolean supportCapability(String capability){
        //Base only  Farms  Life Support  Mars rover
        int curLevel = capabilityLevel(this.capability);
        int inputLevel = capabilityLevel(capability);
        return curLevel>=inputLevel;
    }

    public int compareCapability(String capability){
        //Base only  Farms  Life Support  Mars rover
        int curLevel = capabilityLevel(this.capability);
        int inputLevel = capabilityLevel(capability);
        return curLevel-inputLevel;
    }

    public static int capabilityLevel(String capability){
        int level = 0 ;
        if("Base only".equalsIgnoreCase(capability)){
            level = 1;
        }else if("Farms".equalsIgnoreCase(capability)){
            level = 2;
        }else if("Life Support".equalsIgnoreCase(capability)){
            level = 3;
        }else if("Mars rover".equalsIgnoreCase(capability)){
            level = 4;
        }
        return level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarsnautId() {
        return marsnautId;
    }

    public void setMarsnautId(String marsnautId) {
        this.marsnautId = marsnautId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
