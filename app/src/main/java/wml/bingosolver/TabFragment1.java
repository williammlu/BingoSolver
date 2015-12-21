package wml.bingosolver;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TabFragment1 extends Fragment
{

    Button clearButton;
    Button addButton;
    Button boardcountbutton;
    Button fileprintbutton;
    Activity activity;
    EditText printEditText;
//    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        clearButton = (Button)view.findViewById(R.id.clearButton);
        addButton = (Button)view.findViewById(R.id.addButton);
        boardcountbutton = (Button) view.findViewById((R.id.boardcountbutton));
        fileprintbutton = (Button) view.findViewById(R.id.fileprintbutton);
        printEditText = (EditText)view.findViewById(R.id.printEditText);
//        Toast.makeText(activity,"Text!",Toast.LENGTH_SHORT).show();
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // TODO make warning before data is set

                clearAllBoardData(getContext());
                Log.e("" + MainActivity.board_count, "clear");
//                Toast.makeText(getActivity(),"Cleared all!",Toast.LENGTH_SHORT).show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start new activity to add new board to mainactivity.java's boards variable
                Intent intent = new Intent(getActivity(), NewBoard.class);
                startActivity(intent);
            }
        });
        boardcountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO make warning and then if needed, set data.txt to '"0"
                Toast.makeText(getActivity(), "" + MainActivity.board_count, Toast.LENGTH_SHORT).show();
                if(MainActivity.board_count > 0 )
                {
                    Log.e(MainActivity.boards.get(0).toString(),"count");
                }
            }
        });
        fileprintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    String FILENAME = "data";

                    FileInputStream fin = getContext().openFileInput(FILENAME);
                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    printEditText.setText( temp);
                } catch (IOException ex) {

                }
            }
        });

        return view;


    }
    /**
     * Creates or replaces data.txt with "0" and clears boards stored
     */
    public void clearAllBoardData(Context ctx)
    {
        String FILENAME = "data";
        try
        {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            fos.write("0".getBytes());
            fos.close();
            MainActivity.board_count = 0;
            MainActivity.boards = new ArrayList<BingoBoard>();
            MainActivity.importBoard(getContext());
        }
        catch (IOException ex)
        {
            // do nothing
        }
    }


}