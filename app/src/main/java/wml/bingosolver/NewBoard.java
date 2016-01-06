package wml.bingosolver;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class NewBoard extends AppCompatActivity {


    EditText[] numbers;
    Button addNewBoardButton;
    Button autopopulateButton; // for testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_board);

        addNewBoardButton = (Button) findViewById(R.id.addNewBoardButton);
        autopopulateButton = (Button) findViewById(R.id.autopopulateButton);
        numbers = new EditText[25];
        initialize();


        addNewBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[][] bingo_values = extractValues();
                if (bingo_values == null) {
                    Toast.makeText(getApplicationContext(), "Too long, incomplete or repeated numbers in board. " +
                            "Please enter in values for all boxes.", Toast.LENGTH_LONG).show();
                } else {
                    addBoard(bingo_values);
                    exportToFile(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Successfully added new board.", Toast.LENGTH_SHORT).show();
                    TabFragment1.refreshListView();
                    finish();

                }
            }
        });

        // for testing purposes
        autopopulateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[][] bingo_values = new int[5][5];
                for(int i = 0; i < 5; i++)
                {
                    for(int j = 0; j < 5; j++)
                    {
                        bingo_values[i][j] = 5 * i + j;
                    }
                }

//                Toast.makeText(getApplicationContext(), "Autopopulate complete", Toast.LENGTH_SHORT).show();
                Log.d("Autopopulate complete", "a");
                addBoard(bingo_values);
                Log.d(MainActivity.boards.size() + "", "autocomplete");
                exportToFile(getApplicationContext());
                TabFragment1.refreshListView();
                finish();
            }
        });
    }

    public static void addBoard(int[][] bingo_values)
    {
        MainActivity.board_count++;
        BingoBoard temp = new BingoBoard(bingo_values);


        //check previously selected numbers called on new board

        for (int called_value : MainActivity.called_values)
        {
            temp.call(called_value);
        }

        MainActivity.boards.add(temp);

    }

    public static void exportToFile(Context ctx)
    {
        String FILENAME = "data";
        String output = MainActivity.export();


        try
        {

            FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            fos.write(output.getBytes());
//            MainActivity.importBoard(getApplicationContext());

            fos.close();

            MainActivity.importBoard(ctx); // test method
            Log.e("" + MainActivity.boards.size(), "export size");
        }
        catch (IOException ex)
        {
            // do nothing
        }
    }

    public int[][] extractValues()
    {
        int[][] bingo_values = new int[5][5];
        TreeSet<Integer> unique_values = new TreeSet<Integer>();
        for(int count = 0; count < 25; count++)
        {
            int row = count/5; // floor division by integer division
            int col = count%5;
            if( row == 2 && col == 2)
            {
                bingo_values[row][col] = 0;
            }
            else
            {
                String target = numbers[count].getText().toString();
                if (target.length() > 6)
                {
                    return null; // maybe too large to parse int
                }
                int target_value = (target.equals("")) ? -1 : Integer.parseInt(target);
                unique_values.add(target_value);
                if(target_value == -1)
                {
                    return null; // cannot proceed with incomplete values
                }
                bingo_values[row][col] = target_value;
            }
        }
        if(unique_values.size() == 24) //proper number of unique values
            return bingo_values;
        else
            return null;
    }
    public void initialize()
    {
        numbers[0] = (EditText) findViewById(R.id.val1);
        numbers[1] = (EditText) findViewById(R.id.val2);
        numbers[2] = (EditText) findViewById(R.id.val3);
        numbers[3] = (EditText) findViewById(R.id.val4);
        numbers[4] = (EditText) findViewById(R.id.val5);
        numbers[5] = (EditText) findViewById(R.id.val6);
        numbers[6] = (EditText) findViewById(R.id.val7);
        numbers[7] = (EditText) findViewById(R.id.val8);
        numbers[8] = (EditText) findViewById(R.id.val9);
        numbers[9] = (EditText) findViewById(R.id.val10);
        numbers[10] = (EditText) findViewById(R.id.val11);
        numbers[11] = (EditText) findViewById(R.id.val12);
        numbers[12] = null;
        numbers[13] = (EditText) findViewById(R.id.val14);
        numbers[14] = (EditText) findViewById(R.id.val15);
        numbers[15] = (EditText) findViewById(R.id.val16);
        numbers[16] = (EditText) findViewById(R.id.val17);
        numbers[17] = (EditText) findViewById(R.id.val18);
        numbers[18] = (EditText) findViewById(R.id.val19);
        numbers[19] = (EditText) findViewById(R.id.val20);
        numbers[20] = (EditText) findViewById(R.id.val21);
        numbers[21] = (EditText) findViewById(R.id.val22);
        numbers[22] = (EditText) findViewById(R.id.val23);
        numbers[23] = (EditText) findViewById(R.id.val24);
        numbers[24] = (EditText) findViewById(R.id.val25);

    }
}
