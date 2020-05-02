
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
        //����filename
        String filename = kb.nextLine( );
        //�����ļ�������
        //filename = "G:\\java����\\day03\\" + filename;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        //��ȡ�ļ�����
        String eachLine = null;
        int lineNum = 0;
        String data[] = new String[100];
        while((eachLine=reader.readLine())!=null && !"".equals(eachLine.trim())){
            if(lineNum+1>100){ break;}
            data[lineNum++] = eachLine;
        }
        //�ļ��й��ж��ٸ�Module��
        int moduleCount = 0;

        for(int i=0;i<lineNum;){
            String moduleid = data[i];
            boolean onTask = Boolean.parseBoolean(data[i+1]);
            int maxNauts = Integer.parseInt(data[i+2]);
            int currentNaut = Integer.parseInt(data[i+3]);
            //�����һ��module
            Module newModule = new Module(moduleid,maxNauts);
            newModule.setTask(onTask);
            newModule.setCurrentNaut(currentNaut);
            base[moduleCount++] = newModule;
            //��ǰModule���м���Marsaunt������currentNaut�ĸ���
            i=i+4;
            if(currentNaut>0){
                //����Marsaunt����
                for(int j=0;j<currentNaut;j++){
                    String name = data[i];
                    String marsauntid = data[i+1];
                    int hours = Integer.parseInt(data[i+2]);
                    //����Marsaunt����
                    Marsnaut marsnaut = new Marsnaut(name,marsauntid,hours);
                    newModule.setMarsnaut(j,marsnaut);
                    //������i������+3����Ϊһ��Marsaunt����Ϣռ����
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
        //�жϻ��ǻ���ģ�������Ƿ�����
        //�Ƿ��п�ģ�飬����е�һ����ģ����±��Ǽ�
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
        //������������������µ�ģ��
        System.out.print( "\tEnter module id >> " );
        //����module id
        String moduleid = kb.nextLine( );
        //�ж������module id�Ƿ��Ѵ���
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
        //���뱾ģ��Marsnaunt���������
        System.out.print( "\tEnter max number of marsnauts >> " );
        int maxNauts = kb.nextInt();
        //����һ��ģ��
        Module module = new Module(moduleid,maxNauts);
        //��������ģ�����base������
        base[emptyModuleIndex] = module;
    }

    public void addMarsnaut( )
    {
        //�ж�base�Ƿ�Ϊ��
        if(emptyModules()){
            return;
        }
        System.out.print( "\tEnter module id >> " );
        //����module id
        String moduleid = kb.nextLine();
        Module curModule = extistModule(moduleid);
        if(curModule==null){
            return;
        }

        //�ж�moduleid���ڵ�Module��Marsnaut�������Ƿ��ѵ�������
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
        //����module id
        String marsnautId = kb.nextLine( );
        //������ģ���marsaaunt���бȽ�,����������ͬ��marsnautId
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
        //����marsaunt name
        String marsnautName = kb.nextLine( );
        //����Marsaunt���󣬲����ö������ӵ���Ӧ��module��
        curModule.addMarsnaut(marsnautId,marsnautName);
    }

    public void addTask( )
    {
        //�ж�base�Ƿ�Ϊ��
        if(emptyModules()){
            return;
        }
        System.out.print( "\tEnter module id >> " );
        //����module id
        String moduleid = kb.nextLine();
        Module curModule = extistModule(moduleid);
        if(curModule==null){
            return;
        }
        //�жϸ�ģ���Ƿ��ڹ���״̬
        if(curModule.isTask()){
            System.out.print( "\n This Module is already on a task, cannot add another task \n" +
                    "till current task is completed \n" );
            return;
        }
        //�жϸ�ģ���Ƿ�����Marsanut����
        if(curModule.getCurrentNaut()==0){
            System.out.print( "\n This Module does not have any Marsnauts, so cannot be assigned a task \n" );
            return;
        }

        System.out.print( "\t Enter task level >> " );
        String taskLevel = kb.nextLine();
        //�ж��Ƿ������������
        //��ǰModule�߱��ĵȼ�������ȼ��Ĳ�
        int level = curModule.totalCapability(taskLevel);
        if(level<0){
            System.out.print( "\n The Marsnauts in this Module do not have the required capability for this task \n" );
            return;
        }
        //���빤��ʱ��
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
        //�ж�base�Ƿ�Ϊ��
        if(emptyModules()){
            return;
        }
        System.out.print( "\tEnter module id >> " );
        //����module id
        String moduleid = kb.nextLine();
        Module curModule = extistModule(moduleid);
        if(curModule==null){
            return;
        }
        //�ж�Module�Ƿ���ִ������״̬
        if(!curModule.isTask()){
            System.out.println("\n That Module is not on a Task  " +
                    " so cannot end a Task");
            return ;
        }
        //��������
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
        //�����ļ�����
        String outFileName = kb.nextLine();
        //outFileName = "G:\\java����\\day03\\"  + outFileName;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
        //��Ҫд�����ݷŵ�һ��������
        String[] data = new String[200];//���������д200������
        int lineNum = 0;
        for(int i=0;i<base.length && base[i]!=null;i++){
            Module module = base[i];
            //��¼��ǰModule����Ϣ
            data[lineNum++] = module.getModuleId();
            data[lineNum++] = ""+module.isTask();
            data[lineNum++] = ""+module.getMaxNauts();
            data[lineNum++] = ""+module.getCurrentNaut();
            //��¼��ǰModule��Marsaunt����Ϣ
            for(int j=0;j<module.getCurrentNaut();j++){
                Marsnaut marsnaut = module.getMarsnaut(j);
                data[lineNum++] = ""+ marsnaut.getName();
                data[lineNum++] = ""+ marsnaut.getMarsnautId();
                data[lineNum++] = ""+ marsnaut.getHours();
            }
        }
        //��data����������ļ���
        for(int k=0;k<data.length && data[k]!=null;k++){
            writer.write(data[k] + "\n");
        }
        //�ر��ļ�������
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

    //�Զ��庯���ж�base��module�Ƿ�Ϊ��
    private boolean emptyModules(){
        //�ж�base�Ƿ�Ϊ��
        if(base[0]==null){
            System.out.println("\n No Modules present, cannot add Marsnaut");
            System.out.println(" until at least one Module has been constructed.\n");
            return true;
        }
        return false;
    }

    //�ж������ModuleId�Ƿ��Ѵ���
    private Module extistModule(String moduleid){
        //�ж�moduleid�Ƿ����
        Module curModule = null;
        for(int i=0;i<base.length;i++){
            if(base[i]!=null && moduleid.equalsIgnoreCase(base[i].getModuleId())){
                curModule = base[i];
            }
        }
        //���moduleid������
        if(curModule==null){
            System.out.println("\n No module with that id was found.\n");
        }
        return curModule;
    }
}

