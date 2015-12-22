package wml.bingosolver;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    public static ArrayList<BingoBoard> boards;
    public static int board_count;
    EditText printEditText;
    static ArrayList<Integer> called_values = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        printEditText = (EditText)findViewById(R.id.printEditText);
        //TODO: load in board from text file
        boards = new ArrayList<BingoBoard>();
        board_count = 0;
        try
        {
            boards = importBoard(getApplicationContext());
            System.out.println("Currently there are " + board_count + " a " + boards.size() + " boards");

        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), "Failed to find previous data." +
                    "Add a new board!", Toast.LENGTH_SHORT).show();

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Manage Boards"));
        tabLayout.addTab(tabLayout.newTab().setText("Entering Numbers"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Exports string equivalent of data.txt
     */
    public static String export()
    {
        int numElem = board_count;
        String exp = numElem + "\n";
        Log.e(exp, "error befre!");
        for(BingoBoard b: boards)
        {
            exp +=  b.toString();
        }
//        System.out.println("export" + exp);
        return exp;
    }




    /**
     * Imports boards from a text file with such structure:
     * 1 <---- number of boards
     * 1 2 3 4 5
     * 2 3 4 5 6
     * 7 8 9 10 11
     * 1 2 3 4 5
     * 3 4 4 3 2
     */
    public static ArrayList<BingoBoard> importBoard(Context ctx) throws IOException
    {
        String FILENAME = "data";

        FileInputStream fin = ctx.openFileInput(FILENAME);
        int c;
        String temp="";
        while( (c = fin.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
//        System.out.println("importing:" + temp);
        if(temp.length() > 0) {
//            Log.e(temp, "something");

            Scanner reader = new Scanner(temp);
            String nextline = reader.nextLine();
//            System.out.println(nextline);
            int numBoards = Integer.parseInt(nextline);
            board_count = numBoards;
            ArrayList<BingoBoard> board_list = new ArrayList<BingoBoard>();
            for (int board_c = 0; board_c < numBoards; board_c++) {
                int[][] temp_board = new int[5][5];

                for (int lCount = 0; lCount < 5; lCount++) {
                    String line = reader.nextLine();

                    String[] values = line.split(" ");
                    int[] line_vals = new int[values.length];
                    for (int elem = 0; elem < 5; elem++) {
                        line_vals[elem] = Integer.parseInt(values[elem]);
                    }

                    temp_board[lCount] = line_vals;
                }
                board_list.add(new BingoBoard(temp_board));
            }
            return board_list;
//            return new BingoBoard[0];
        }
        else
        {
            throw new IOException();
        }


    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}