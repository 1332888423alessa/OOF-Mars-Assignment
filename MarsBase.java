
import java.util.Scanner;
import java.io.*;

public class MarsBase
{
    private Module [ ] base;
    
    private Scanner kb;

    private final int ADD_MODULE = 1;
    private final int ADD_PERSONNEL = 2;
    private final int ADD_TASK = 3;
    private final int END_TASK = 4;
    private final int DISPLAY = 5;
    private final int SAVE_TO_FILE = 6;
    private final int EXIT = 7;

    private int moduleCounter = 0;
    private final int SIZE = 20;

    private final int DISPLAY_ALL = 1;
    private final int DISPLAY_MODULES = 2;
    private final int DISPLAY_MARSNAUTS = 3;
    private final int DISPLAY_SINGLE_MODULE = 4;
    private final int DISPLAY_SINGLE_MARSNAUT = 5;
    private final int DISPLAY_EXIT = 6;

    public static void main( String [ ] args ) throws IOException
    {
         MarsBase mb = new MarsBase( );
         mb.run( );
    }

    public MarsBase( ) throws IOException
    {
         kb = new Scanner( System.in );
         base =  new Module[ SIZE ];
         load( );
    }

    /*
     * The load method is called before the menu is shown to the user
     *
     * The user is asked to enter a file name. The file will always
     * exist, it may be empty but it will always exist, so there
     * is no need for FileNotFoundException, try/catch code.
     *
     * After the contents of the file have been read into the array
     * then the file is closed and all interactions with the program 
     * are through the array, unless the save to file option is selected.
     *
     */
    public void load( ) throws IOException
    {
        System.out.print( "\t Enter file name >> " );
        //输入filename
        String filename = kb.nextLine( );
        //创建文件流独享
        //filename = "G:\\java辅导\\day03\\" + filename;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        //读取文件内容
        String eachLine = null;
        int lineNum = 0;
        String data[] = new String[100];
        while((eachLine=reader.readLine())!=null && !"".equals(eachLine.trim())){
            if(lineNum+1>100){ break;}
            data[lineNum++] = eachLine;
        }
        //文件中共有多少个Module？
        int moduleCount = 0;

        for(int i=0;i<lineNum;){
            String moduleid = data[i];
            boolean onTask = Boolean.parseBoolean(data[i+1]);
            int maxNauts = Integer.parseInt(data[i+2]);
            int currentNaut = Integer.parseInt(data[i+3]);
            //构造出一个module
            Module newModule = new Module(moduleid,maxNauts);
            newModule.setTask(onTask);
            newModule.setCurrentNaut(currentNaut);
            base[moduleCount++] = newModule;
            //当前Module中有几个Marsaunt，就是currentNaut的个数
            i=i+4;
            if(currentNaut>0){
                //创建Marsaunt对象
                for(int j=0;j<currentNaut;j++){
                    String name = data[i];
                    String marsauntid = data[i+1];
                    int hours = Integer.parseInt(data[i+2]);
                    //构造Marsaunt对象
                    Marsnaut marsnaut = new Marsnaut(name,marsauntid,hours);
                    newModule.setMarsnaut(j,marsnaut);
                    //外面行i的数量+3，因为一个Marsaunt的信息占三行
                    i = i + 3;
                }
            }
        }

        }

    public void run( ) throws IOException
    {
         int choice = -1;
         while( choice != EXIT )
         {
              displayMainMenu( );
              System.out.print( "\tEnter choice >> " );
              choice = kb.nextInt( );
              kb.nextLine( );
              processMain( choice );
         }
    }

    public void displayMainMenu( )
    {
         System.out.println( "\n\tMars Base Main Menu" );
         System.out.println( ADD_MODULE + ". Add Module" );
         System.out.println( ADD_PERSONNEL + ". Add Marsnaut to Module" );
         System.out.println( ADD_TASK + ". Add Task" );
         System.out.println( END_TASK + ". End Task" );
         System.out.println( DISPLAY + ". Display Modules" );
         System.out.println( SAVE_TO_FILE + ". Save to file" );
         System.out.println( EXIT + ". Exit the program" );
    }

    public void processMain( int choice ) throws IOException
    {
         switch( choice )
         {
              case ADD_MODULE:
                   addModule( );
                break;
         
              case DISPLAY:
                   runDisplay( );
                break;

              case ADD_PERSONNEL:
                   addMarsnaut( );
                break;

              case ADD_TASK:
                   addTask( );
                break;

              case END_TASK:
                   endTask( );
                break;

              case SAVE_TO_FILE:
                   save( );
                break;

              case EXIT:
              // just trap this choice basically so that it doesn't
              // show as an error.
                   System.out.println( "\nGood bye from Mars Base" );
                break;

              default:
                   System.out.println("\nThat is not a valid choice" );
                break;
         }
    }

    public void displayDisplayMenu( )
    {
         System.out.println( "\n\tMars Base Display Menu" );
         System.out.println( DISPLAY_ALL + ". Display All" );
         System.out.println( DISPLAY_MODULES + 
                            ". Display Modules only" );
         System.out.println( DISPLAY_MARSNAUTS + 
                            ". Display Marsnauts only" );
         System.out.println( DISPLAY_SINGLE_MODULE + 
                            ". Display Single Module" );
         System.out.println( DISPLAY_SINGLE_MARSNAUT + 
                            ". Display Single Marsnaut" );
         System.out.println( DISPLAY_EXIT + ". Return to main menu" );
    }

    public void processDisplay( int choice )
    {
         switch( choice )
         {
              case DISPLAY_ALL:
                      displayAll( );
                break;
         
              case DISPLAY_MODULES:
                       displayModules( );
                break;

              case DISPLAY_MARSNAUTS:
                       displayMarsnauts( );
                break;

              case DISPLAY_SINGLE_MODULE:
                       displaySingleModule( );
                break;

              case DISPLAY_SINGLE_MARSNAUT:
                        displaySingleMarsnaut( );
                break;


              case DISPLAY_EXIT:
              // just trap this choice basically so that it doesn't
              // show as an error.
                   System.out.println( "\nReturning from display menu" );
                break;

              default:
                   System.out.println("\nThat is not a valid choice" );
                break;
         }
    }

    public void addModule( )
    {
        //判断火星基地模块数量是否已满
        //是否有空模块，如果有第一个空模块的下标是几
        int emptyModuleIndex = -1;
        boolean full = true;
        for(int i=0;i<base.length;i++){
            if(base[i]==null){
                emptyModuleIndex = i;
                full = false;
                break;
            }
        }
        if(full){
            System.out.println("\n The base is full, no free Module spaces.");
            return;
        }
        //如果不满，则可以添加新的模块
        System.out.print( "\tEnter module id >> " );
        //输入module id
        String moduleid = kb.nextLine( );
        //判断输入的module id是否已存在
        boolean unique = true;
        for(int i=0;i<emptyModuleIndex;i++){
            if(base[i]!=null && moduleid.equalsIgnoreCase(base[i].getModuleId())){
                unique = false;
                break;
            }
        }
        if(!unique){
            System.out.println("\n Module id's must be unique \n" +
                    "That module id is already in use");
            return;
        }
        //输入本模块Marsnaunt的最大数量
        System.out.print( "\tEnter max number of marsnauts >> " );
        int maxNauts = kb.nextInt();
        //新增一个模块
        Module module = new Module(moduleid,maxNauts);
        //将新增的模块放入base数组中
        base[emptyModuleIndex] = module;
    }

    public void addMarsnaut( )
    {
        //判断base是否为空
        if(emptyModules()){
            return;
        }
        System.out.print( "\tEnter module id >> " );
        //输入module id
        String moduleid = kb.nextLine();
        Module curModule = extistModule(moduleid);
        if(curModule==null){
            return;
        }

        //判断moduleid所在的Module中Marsnaut的数量是否已到达上限
        int emptyMarsnauntIndex = -1;
        for(int i=0;i<curModule.getMaxNauts();i++){
            if(curModule.getMarsnaut(i)==null){
                emptyMarsnauntIndex = i;
                break;
            }
        }
        if(emptyMarsnauntIndex==-1){
            System.out.println("\n This module already has its maximum number of Marsnauts.\n");
            return ;
        }

        System.out.print( "\tEnter the id of the prospective Marsnaut >> " );
        //输入module id
        String marsnautId = kb.nextLine( );
        //对所有模块的marsaaunt进行比较,看否有有相同的marsnautId
        boolean unique = true;
        for(int i=0;i<base.length && base[i]!=null;i++){
            for(int j=0;j<base[i].getMaxNauts() && base[i].getMarsnaut(j)!=null;j++){
                Marsnaut temp = base[i].getMarsnaut(j);
                if(marsnautId.equals(temp.getMarsnautId())){
                    unique = false;
                    break;
                }
            }
        }
        if(!unique){
            System.out.println("\n Marsnaut id's must be unique.");
            System.out.println(" That Marsnaut id is already in use.\n");
            return ;
        }

        System.out.print( "\tEnter Marsnaut name >> " );
        //输入marsaunt name
        String marsnautName = kb.nextLine( );
        //构造Marsaunt对象，并将该对象增加到对应的module中
        curModule.addMarsnaut(marsnautId,marsnautName);
    }

    public void addTask( )
    {
        //判断base是否为空
        if(emptyModules()){
            return;
        }
        System.out.print( "\tEnter module id >> " );
        //输入module id
        String moduleid = kb.nextLine();
        Module curModule = extistModule(moduleid);
        if(curModule==null){
            return;
        }
        //判断该模块是否处于工作状态
        if(curModule.isTask()){
            System.out.print( "\n This Module is already on a task, cannot add another task \n" +
                    "till current task is completed \n" );
            return;
        }
        //判断该模块是否已有Marsanut对象
        if(curModule.getCurrentNaut()==0){
            System.out.print( "\n This Module does not have any Marsnauts, so cannot be assigned a task \n" );
            return;
        }

        System.out.print( "\t Enter task level >> " );
        String taskLevel = kb.nextLine();
        //判断是否具有这种能力
        //当前Module具备的等级与输入等级的差
        int level = curModule.totalCapability(taskLevel);
        if(level<0){
            System.out.print( "\n The Marsnauts in this Module do not have the required capability for this task \n" );
            return;
        }
        //输入工作时间
        System.out.print( "	Enter extra hours default is 0, just press the enter key for 0,\n" +
                "or enter extra hours >> (just pressed the enter key)");
        String hours = kb.nextLine();
        if(hours==null || hours.trim().length()==0){
            hours = "0";
        }
        int workHours = 0;
        try{
            workHours = Integer.parseInt(hours);
        }catch (Exception ex){
            workHours = 0;
        }
        curModule.startTask(workHours);
    }

    public void endTask( )
    {
        //判断base是否为空
        if(emptyModules()){
            return;
        }
        System.out.print( "\tEnter module id >> " );
        //输入module id
        String moduleid = kb.nextLine();
        Module curModule = extistModule(moduleid);
        if(curModule==null){
            return;
        }
        //判断Module是否在执行任务状态
        if(!curModule.isTask()){
            System.out.println("\n That Module is not on a Task  " +
                    " so cannot end a Task");
            return ;
        }
        //结束任务
        curModule.setTask(false);
    }
    
    /*
     * This method saves the contents of the array back
     * to a test file. It does not have to be the same 
     * text file as the input file.
     *
     * The contents of the array need to be written back to the
     * text file in the same format as the input file.
     * We must be able to use the output file as an input file
     * the next time that the program is run.
     *
     */
    public void save( ) throws IOException
    {
        System.out.print( "\tEnter file name >> " );
        //构造文件对象
        String outFileName = kb.nextLine();
        //outFileName = "G:\\java辅导\\day03\\"  + outFileName;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
        //把要写的内容放到一个数组中
        String[] data = new String[200];//假设最多能写200行数据
        int lineNum = 0;
        for(int i=0;i<base.length && base[i]!=null;i++){
            Module module = base[i];
            //记录当前Module的信息
            data[lineNum++] = module.getModuleId();
            data[lineNum++] = ""+module.isTask();
            data[lineNum++] = ""+module.getMaxNauts();
            data[lineNum++] = ""+module.getCurrentNaut();
            //记录当前Module中Marsaunt的信息
            for(int j=0;j<module.getCurrentNaut();j++){
                Marsnaut marsnaut = module.getMarsnaut(j);
                data[lineNum++] = ""+ marsnaut.getName();
                data[lineNum++] = ""+ marsnaut.getMarsnautId();
                data[lineNum++] = ""+ marsnaut.getHours();
            }
        }
        //将data数据输出到文件中
        for(int k=0;k<data.length && data[k]!=null;k++){
            writer.write(data[k] + "\n");
        }
        //关闭文件流对象
        writer.close();
    }

    /*
     * It is suggested that you write this method to make
     * it easier to find a particular Module in the array,
     * or to check that a particular Module id is not in use.
     *
     * Have this method take the id a Module as a paramter and
     * return the index of that Module, or -1 if there is no
     * Module with that id in the array
     *
     */
    private int searchModule( String id )
    {
         int index = -1;


         return index;
    }
    
    /*
     * This method displays all of the Modules in the array,
     * with all their associated Marsnauts
     *
     */
    public void displayAll( )
    {
         System.out.println("Here is the complete information for Mars Base \n");
         for(Module module:base){
             if(module!=null){
                 System.out.println(module.toString() + "\n");
             }
         }
    }

    /*
     * This method displays just the Modules, with NO information
     * about their Marsnauts
     *
     */
    public void displayModules( )
    {
        System.out.println("Here is the complete information for Modules only \n");
        for(int i=0;i<base.length && base[i]!=null;i++){
            base[i].displayModule();
        }
    }
    
    /*
     * This method displays just the Marsnauts, without any
     * associated Module information
     *
     */
    public void displayMarsnauts( )
    {
        System.out.println("Here is the complete information for Marsnauts only \n");
        for(int i=0;i<base.length && base[i]!=null;i++){
            for(int j=0;j<base[i].getCurrentNaut();j++){
                String result = base[i].getMarsnaut(j).toString();
                System.out.println(result);
            }
        }
    }

    /*
     * This method firstly asks the user to enter a Module id.
     * If the Module with that id is found in the array, then all
     * of the information for that Module, and just that Module,
     * including the associated Marsnauts (if any) are displayed
     * to the screen
     *
     */
    public void displaySingleModule( )
    {
         if(emptyModules()) {
            System.out.println("No modules present, can't display modules");
            return;
        }
        System.out.print("\tEnter Module id >> ");
        String moduleId = kb.nextLine();
        boolean isFind = false;
        for (int i=0; i<base.length && base[i] != null; i++) {
            if (base[i].getModuleId().equalsIgnoreCase(moduleId)) {
                isFind = true;
                System.out.println("\tHere is the information for a Single Module");
                System.out.println(base[i].toString() + "\n");
            }
        }
        if(!isFind) {
            System.out.println("\tNo Module with that id was found");
        }
    }

    /*
     * This method asks the user to enter the id of Marsnaut
     * The method needs to search through all of the Marsnauts
     * in all of the Modules in the array until it finds the
     * Marsnaut with the id as entered by the user.
     *
     * If a Marsnaut is found with the id as entered by the user
     * is found, then the information for that Marsnaut is
     * displayed to the screen.
     *
     */
    public void displaySingleMarsnaut( )
    {
        boolean isExist = false;
        for (int i=0;i<base.length && base[i] !=null; i++) {
            if ( base[i].getCurrentNaut()!=0) {
                isExist = true;
                break;
            } 
         }       
         if (!isExist) {
             System.out.println("No marsnaut added, can't display marsnauts");
            return;
         } 
         boolean isFind = false;
         System.out.print("\nPlease enter Marsnaut id >> ");
         String marsnautId = kb.nextLine();     
         for(int i=0;i<base.length && base[i]!=null;i++){
            for(int j=0;j<base[i].getCurrentNaut();j++){
                if (base[i].getMarsnaut(j).getMarsnautId().equalsIgnoreCase(marsnautId)) {
                    isFind = true;
                    String result = base[i].getMarsnaut(j).toString();
                    System.out.println(result);
                }
            }
        }
        if (!isFind) {
            System.out.println("\nNo marsnaut with that id was found.");
        }
    }

    public void runDisplay( ) throws IOException
    {
         int choice = -1;
         while( choice != DISPLAY_EXIT )
         {
              displayDisplayMenu( );
              System.out.print( "\tEnter choice >> " );
              choice = kb.nextInt( );
              kb.nextLine( );
              processDisplay( choice );
         }
    }

    //自定义函数判断base中module是否为空
    private boolean emptyModules(){
        //判断base是否为空
        if(base[0]==null){
            System.out.println("\n No Modules present, cannot add Marsnaut");
            System.out.println(" until at least one Module has been constructed.\n");
            return true;
        }
        return false;
    }

    //判断输入的ModuleId是否已存在
    private Module extistModule(String moduleid){
        //判断moduleid是否存在
        Module curModule = null;
        for(int i=0;i<base.length;i++){
            if(base[i]!=null && moduleid.equalsIgnoreCase(base[i].getModuleId())){
                curModule = base[i];
            }
        }
        //如果moduleid不存在
        if(curModule==null){
            System.out.println("\n No module with that id was found.\n");
        }
        return curModule;
    }
}

