package wml.bingosolver;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TabFragment2 extends Fragment {
    EditText numberEnteringEditText;
    Activity activity;
    static CheckBox ignoreHorizontalCheckBox;
    static CheckBox ignoreVerticalCheckBox;
    static CheckBox ignoreDiagonalCheckBox;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        numberEnteringEditText  = (EditText)view.findViewById(R.id.numberEnteringEditText);
        ignoreHorizontalCheckBox = (CheckBox) view.findViewById(R.id.ignoreHorizontalCheckBox);
        ignoreVerticalCheckBox = (CheckBox) view.findViewById(R.id.ignoreVerticalCheckBox);
        ignoreDiagonalCheckBox = (CheckBox) view.findViewById(R.id.ignoreDiagonalCheckBox);


        numberEnteringEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String input = (numberEnteringEditText.getText().toString());
                    if(input.length() > 0)
                    {
                        int value = Integer.parseInt(input);
                        processNumber(value,getContext());
                        Log.e(value + "", "value");
                        numberEnteringEditText.setText("");
                        numberEnteringEditText.requestFocus();
                    }
                    handled = true;

                }
                return handled;
            }
        });
        return view;
    }

    public static void processNumber(int val,Context ctx)
    {
        MainActivity.called_values.add(val);
        BingoBoard winner = null;
        String[] bingoMessage = null;
        int winningCount = -1;
        int count = 0;

        boolean ignore_hort = ignoreHorizontalCheckBox.isChecked();
        boolean ignore_vert = ignoreVerticalCheckBox.isChecked();
        boolean ignore_diag = ignoreDiagonalCheckBox.isChecked();

        for (BingoBoard b: MainActivity.boards)
        {
            b.call(val);
            int[] result = b.bingoChecking(ignore_hort, ignore_vert, ignore_diag);
            switch (result[0])
            {
            // case 0: no bingo, do nothing
                case 1: bingoMessage = new String[]{"Horizontal", "Row ", "" + result[1]};
                    break;
                case 2: bingoMessage = new String[]{"Vertical", "Column ", "" + result[1]};
                    break;
                case 3: bingoMessage = new String[]{"Diagonal", "Diagonal ", "" + result[1]};
                    break;
            }
            if (result[0] != 0) {
                winner = b; // winner will be last board that wins
                winningCount = count;
            }
            count++;
        }

        TabFragment1.refreshListView(); // update listview of boards with new parenthesized bingo pieces

        if( winner!= null)
        {
            Log.e("winner !!!!!!", "win");
            //TODO alert user that there is a bingo.
            String boardDisplayed = winner.toPrettyString(5, false);
            new AlertDialog.Builder(ctx)
                    .setTitle("Winning Board " + winningCount + " : " + bingoMessage[0])
                    .setMessage("Bingo at " + bingoMessage[1] + bingoMessage[2] + "\n" + boardDisplayed)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // continue
//                        }
//                    })
////                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int which) {
////                            // do nothing
////                        }
////                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();

        }

    }
}