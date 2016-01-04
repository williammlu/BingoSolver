package wml.bingosolver;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class TabFragment3 extends Fragment {

    ImageButton venmoButton;
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);
        venmoButton = (ImageButton)view.findViewById(R.id.venmoButton);


        return view;
    }
}