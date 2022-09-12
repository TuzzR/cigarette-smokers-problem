import java.util.ArrayList;
import java.util.Random;

class Yo {

    public static void main(String[] args) {

        table Table = new table();

        agent Monitor = new agent(Table);

        Monitor.start();

        // creates 3 smoker thread from the monitor, so the threads can wake up the monitor
        for (int i = 0; i < 3; i++)
        {

            SMOKERS SMOKERSThread = new SMOKERS(Table, i, "Smoker " + Integer.toString(i+1), Monitor);

            SMOKERSThread.start();
        }
    }
}

class SMOKERS extends Thread {

    //intilizes all of the variables
    private table Table = new table();
    private String ITEMS;
    private int ITEMNUMBER;
    private agent Monitor;

    public SMOKERS(table newTable, int newITEM, String NAME, agent newMonitor)
    {
//initilizing all of the variables
        ITEMNUMBER = newITEM;
        this.Table = newTable;
        setName(NAME);
        Monitor = newMonitor;
    }

    @Override
    public void run()
    {

        while(true)
        {
            //gets the item from the table and puts it into items
            ITEMS = Table.getSmokerItems(ITEMNUMBER);
            // if the table doesnt has an ingredient and the table is not empty
            if (!Table.hasIngredient(ITEMS) && !Table.isEmpty())
            {
                //print out the thread witht he missing item
                System.out.println("***********************************");
                System.out.println("Hey I am " + getName() + " and i have the " + ITEMS + " your missing.\n");
                System.out.println("***********************************");
                try {
                    //tell the thread to smoke
                    HighOnPotenuse();
                    // prints that the thread is going to let another read do the same operation
                    System.out.println(getName() + " is going to let someone else SMOKE");

                    // the thread tells the monitor to continue
                    Monitor.wake();
                } catch (Exception e) {}
            }
        }
    }

    public synchronized void HighOnPotenuse() throws Exception
    {
//this prints what each thread is doing when it matches up witht hte monitor
        System.out.println(getName() + " gets the items");
        Thread.sleep(100);
        System.out.println(getName() + " starts smoking");
        Thread.sleep(100);
        System.out.println(getName() + " is satisfied");

    }

}


class agent extends Thread {
//initiates the varibles

    private table Table;

    public agent(table newTable)
    {
        Table = newTable;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {}
            Table.getRandom();
            // this tells the smoker threads to look at the table
            //this prints the greeting message and what items the monitor has
            System.out.println("\nHey, Do You Wanna Smoke...?\nWell The Problem is there are only " + Table.getMonitorItems());
            // pause the agent while one SMOKERS thread is running
            pause();
        }
    }

    public synchronized void wake()
    {
        try
        {
            notify();
        } catch(Exception e){}
    }


    public synchronized void pause()
    {
        try
        {
            this.wait();
        } catch (Exception e) {}
    }


}

@SuppressWarnings("unchecked")
// i put the supress warning in because of the converting between glases in the array
class table {

    //Variables for storing ITEMSs(Weed,Blunt,Lighter)
    private ArrayList allItems  = new ArrayList();
    private ArrayList MonitorItems = new ArrayList();

    public table()
    {
        //adds items
        allItems .add("Tobacco");
        allItems .add("Paper");
        allItems .add("Lighter");
    }

    //gets two random item from the list
    public void getRandom()
    {
        Random random = new Random();
        // clears monitor array
        MonitorItems.clear();
        //copies what in the arry list to another arra

        ArrayList copyAllElements = (ArrayList) allItems .clone();
        //picks an item and adds it to the monitors items
        int ITEMS1 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(ITEMS1));

        copyAllElements.remove(ITEMS1);
        //picks an item and adds it to the monitors items
        int ITEMS2 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(ITEMS2));
    }
    //to check if the table is empty so you dont get any erros with arrays
    public boolean isEmpty()
    {
        return (MonitorItems.size() == 0);
    }
    //gets the items and notifies the other threads
    public synchronized String getMonitorItems()
    {
        notifyAll();
        return MonitorItems.toString();
    }
    //this gets the item and pairs it with the correct thread
    public synchronized String getSmokerItems(int x)
    {
        try {
            this.wait();
        } catch (Exception e) {}
        return (String) allItems .get(x);
    }
    //checking if the smoker has the same items as the monitor
    public boolean hasIngredient(String ITEMSName)
    {
        return (MonitorItems.contains(ITEMSName));
    }

    public synchronized void pause()
    {
        try {
            this.wait();
        } catch (Exception e) {}
    }
}