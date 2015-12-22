package wml.bingosolver;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TabFragment2 extends Fragment {
    EditText numberEnteringEditText;
    Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        numberEnteringEditText  = (EditText)view.findViewById(R.id.numberEnteringEditText);

        numberEnteringEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String input = (numberEnteringEditText.getText().toString());
                    if(input.length() > 0)
                    {
                        int value = Integer.parseInt(input);
                        processNumber(value);
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

    public void processNumber(int val)
    {
        BingoBoard winner = null;
        for (BingoBoard b: MainActivity.boards)
        {
            b.call(val);
            int[] result = b.bingoChecking();
            String[] bingoMessage = null;
            switch (result[0])
            {
            // case 0: no bingo, do nothing
                case 1: bingoMessage = new String[]{"horizontal", "row", "" + result[1]};
                case 2: bingoMessage = new String[]{"vertical", "column", "" + result[1]};
                case 3: bingoMessage = new String[]{"diagonal", "diagonal", "" + result[1]};
            }
            winner = b; // winner will be last board that wins
        }
        if( winner!= null)
        {
            Log.e("winner !!!!!!", "win");
            //TODO alert user that there is a bingo.
        }

    }
}