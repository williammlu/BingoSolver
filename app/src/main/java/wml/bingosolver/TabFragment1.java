package wml.bingosolver;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
    Button boardcountbutton; // testing
    Button fileprintbutton; // testing
    Button resetButton;
    Activity activity;
    EditText printEditText; // testing
    static ListView boardsListView;
    static BoardAdapter bAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        clearButton = (Button)view.findViewById(R.id.clearButton);
        addButton = (Button)view.findViewById(R.id.addButton);
        boardcountbutton = (Button) view.findViewById((R.id.boardcountbutton));
        fileprintbutton = (Button) view.findViewById(R.id.fileprintbutton);
        resetButton = (Button) view.findViewById(R.id.resetButton);


        printEditText = (EditText)view.findViewById(R.id.printEditText);
        boardsListView = (ListView) view.findViewById(R.id.boardsListView);
        bAdapter = new BoardAdapter(getContext(), R.layout.board_row_view, MainActivity.boards);
        boardsListView.setAdapter(bAdapter);

//        Toast.makeText(activity,"Text!",Toast.LENGTH_SHORT).show();
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // TODO make warning before data is set
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete all boards")
                        .setMessage("Are you sure you want to delete all boards?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                clearAllBoardData(getContext());
                                Log.e("" + MainActivity.board_count, "clear");
                                MainActivity.board_count = 0;
                                MainActivity.boards.clear();
                                refreshListView();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



//                Toast.makeText(getActivity(),"Cleared all!",Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // TODO make warning before data is set
                new AlertDialog.Builder(getContext())
                        .setTitle("Reset Values")
                        .setMessage("Are you sure you want to reset the values that have been called?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for(BingoBoard b: MainActivity.boards)
                                {
                                    b.resetPieces();
                                }
                                MainActivity.called_values.clear();
                                refreshListView();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



//                Toast.makeText(getActivity(),"Cleared all!",Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start new activity to add new board to mainactivity.java's boards variable
                Intent intent = new Intent(getActivity(), NewBoard.class);
                startActivity(intent);
//                TabFragment2.processNumber(-1, getContext());

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
                    System.out.println("There are " + MainActivity.boards.size() + "boards");
                    System.out.println("clickprint" + temp);
                } catch (IOException ex) {

                }
            }
        });

        return view;


    }

    /**
     * Updates the list of bingoboards
     */
    public static void refreshListView()
    {
        bAdapter.notifyDataSetChanged();
        boardsListView.refreshDrawableState();
    }


    /**
     * Creates or replaces data.txt with "0" and clears boards stored
     */
    public void clearAllBoardData(Context ctx)
    {
        String FILENAME = "data";
        MainActivity.called_values.clear();
        try
        {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            fos.write("0".getBytes());
            fos.close();

        }
        catch (IOException ex)
        {
            // do nothing
        }

    }


    public class BoardAdapter extends ArrayAdapter<BingoBoard> {

        private Context context;
        private int resource;
        private ArrayList<BingoBoard> saved_boards;

        public BoardAdapter(Context context, int resource, ArrayList<BingoBoard> b) {
            super(context, resource, b);
            this.context = context;
            this.resource = resource;
            this.saved_boards = b;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final BingoBoard b = saved_boards.get(position);
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            convertView.setClickable(true);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
            //}
            TextView boardContent = (TextView)convertView.findViewById(R.id.singleBoardTextView);
            TextView boardCount = (TextView)convertView.findViewById(R.id.boardInstanceCountTextView);
            Button bingoBoardDeleteButton = (Button)convertView.findViewById(R.id.bingoBoardDeleteButton);
            boardContent.setText(Html.fromHtml(b.toPrettyString(3, true)));
            boardCount.setText("Board " + position);
            bingoBoardDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO make warning before data is set
                    new AlertDialog.Builder(getContext())
                            .setTitle("Delete board")
                            .setMessage("Are you sure you want to delete this board?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    MainActivity.boards.remove(b);
                                    MainActivity.board_count--;
                                    NewBoard.exportToFile(getContext());
                                    refreshListView();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            return convertView;
        }
    }

}