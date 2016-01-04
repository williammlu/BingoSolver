package wml.bingosolver;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import wml.bingosolver.VenmoLibrary;

public class TabFragment3 extends Fragment {

    ImageButton venmoButton;
    Activity activity;
    final int REQUEST_CODE_VENMO_APP_SWITCH = 2;
    String venmoSecretCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();

        AssetManager assetManager = activity.getAssets();
        InputStream input;
        try {
            input = assetManager.open("venmoKey.txt"); // file is not found in repo
            int size = input.available();

            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            // byte buffer into a string
            venmoSecretCode = new String(buffer);


        }
        catch(IOException exception)
        {

            Log.e("venmokey not found", " frag3");
        }

        View view = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);
        venmoButton = (ImageButton)view.findViewById(R.id.venmoButton);
        venmoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VenmoLibrary.isVenmoInstalled(activity.getApplicationContext())) {

//                String selfVenmoID = ""; //not necessary
                    String uploaderVenmoID = "wmlwml";
                    String paymentAmount = "3.50"; //"0.10";
                    String paymentNote = "For making a very specific app about Bingo!";
                    String txn = "pay";
                    doTransaction(uploaderVenmoID, paymentAmount, paymentNote, txn);

                } else {
                    Toast.makeText(activity.getApplicationContext(), "Please install Venmo", Toast.LENGTH_LONG).show();

                }

            }
        });



        return view;
    }
    public void doTransaction(String recipient, String amount, String note, String txn) {
        try {
            Intent venmoIntent = VenmoLibrary.openVenmoPayment("3252", "NotehubTest", recipient, amount, note, txn);
            startActivityForResult(venmoIntent, REQUEST_CODE_VENMO_APP_SWITCH); //REQUEST_CODE_VENMO_APP_SWITCH is the requestCode we are using for Venmo. Feel free to change this to another number.
        } catch (android.content.ActivityNotFoundException e) //Venmo native app not install on device, so let's instead open a mobile web version of Venmo in a WebView
        {
            Toast.makeText(activity.getApplicationContext(), "Please install Venmo to download paid notes", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.e("resultcancel", "" + activity.RESULT_CANCELED);
        Log.e("result_ok", ""+ activity.RESULT_OK);
        Log.e("result_firstuser", ""+ activity.RESULT_FIRST_USER);
        Log.e(requestCode + "", "" + resultCode);

        Intent returnIntent = new Intent();
        int resultCode2 = activity.RESULT_CANCELED;
        switch(requestCode) {
            case REQUEST_CODE_VENMO_APP_SWITCH: {
                if(resultCode == activity.RESULT_OK) {
                    String signedrequest = data.getStringExtra("signedrequest");
                    if(signedrequest != null) {
                        VenmoLibrary.VenmoResponse response = (new VenmoLibrary()).validateVenmoPaymentResponse(signedrequest,
                                venmoSecretCode);
                        Log.e("success", response.getSuccess());
                        if(response.getSuccess().equals("1")) {
                            //Payment successful.  Use data from response object to display a success message
                            String note = response.getNote();
                            String amount = response.getAmount();
                            Log.e("receipient", response.getPaymentId());
                            Log.e("money", amount + "");

                            //success!!
                            Toast.makeText(activity, "Thank you for your donation!", Toast.LENGTH_LONG).show();
                        }
                        else if(response.getSuccess().equals("2"))
                        {
                            // results from requesting money instead
                            Toast.makeText(activity, "Why you gotta be like that?", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        String error_message = data.getStringExtra("error_message");
                        Log.e("error", error_message);
                        //An error ocurred.  Make sure to display the error_message to the user
                    }
                }
                else if(resultCode == activity.RESULT_CANCELED) {
                    //The user cancelled the payment
                    Toast.makeText(activity, "Venmo Cancelled", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}