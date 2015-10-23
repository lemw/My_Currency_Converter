package se.wattsgard.currency_converter.mycurrencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class Currency_Converter extends AppCompatActivity {

    private String myLogTag = "MyTag";

    private String [] array;
    private ArrayAdapter<String> adapter;
    private Spinner spinnerFrom, spinnerTo;
    //private String fromAmount;
    //private String toAmount;
    private EditText fromAmount;
//    private EditText resultText;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);
        Log.v(myLogTag, "Now the activity has been created");
        setUpTheSpinners();

        //Handle the output field with the conversion result...
        resultText = (TextView) findViewById(R.id.resultsTextView);

        //Handle the textWatcher
        TextWatcher textWatcher = new MyTextWatcher();
        fromAmount = (EditText) findViewById(R.id.editTextFromAmount);
        fromAmount.addTextChangedListener(textWatcher);

    }

    public void setUpTheSpinners() {
        // This is to set the spinners with the currency names
        array = getResources().getStringArray(R.array.currency_name_array);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdapterView.OnItemSelectedListener listener = new CalculateResultListener();

        spinnerFrom = (Spinner)findViewById(R.id.spinnerFrom);
        spinnerFrom.setAdapter(adapter);
        spinnerFrom.setOnItemSelectedListener(listener);

        spinnerTo = (Spinner)findViewById(R.id.spinnerTo);
        spinnerTo.setAdapter(adapter);
        spinnerTo.setOnItemSelectedListener(listener);


        Log.v(myLogTag, "Now the Spinners are setup");
    }

    private void calculateOutcome() {

        //Get all the data needed to do the conversion.
        String [] rates = getResources().getStringArray(R.array.currency_rate_array);
        int indexFrom = spinnerFrom.getSelectedItemPosition();
        int indexTo = spinnerTo.getSelectedItemPosition();
        Log.v(myLogTag, "starting sanity check of values  " );

        if (sanityCheckValues()) {
            //Only run this part of the activity in case the input data is ok

            //Amount of money to convert
            Log.v(myLogTag, "fromMoney string is  " + fromAmount.getText().toString() );
            double fromMoney = Double.parseDouble( fromAmount.getText().toString());
            Log.v(myLogTag, "fromMoney is  " + fromMoney);

            //From rate
            Log.v(myLogTag, "rateFrom string is  " + rates[indexFrom]);
            double rateFrom = Double.parseDouble(rates[indexFrom]);
            Log.v(myLogTag, "rateFrom is  " + rateFrom);

            //To rate!
            Log.v(myLogTag, "rateTo string is  " + rates[indexTo]);
            double rateTo = Double.parseDouble(rates[indexTo]);
            Log.v(myLogTag, "rateTo is  " + rateTo);

            //Calculating the final sum
            Log.v(myLogTag, "finalSum is  calculated now...");
            double finalSum = fromMoney * ( rateFrom / rateTo);
            Log.v(myLogTag, "finalSum is  " + finalSum);
            //return finalSum;

            String [] currencies = getResources().getStringArray(R.array.currency_name_array);
            String formattedString = String.format("%.2f %s", finalSum, currencies[spinnerTo.getSelectedItemPosition()]);
            resultText.setText(formattedString);

        }

        // Need to reconsider how to handle to NOT update
        // the output text field
        //return 0.0;

    }

    private boolean sanityCheckValues() {
        boolean valuesOk = false;

        Log.v(myLogTag, "sanityCheckValues starting");
        Log.v(myLogTag, "Checking if all values are ok...");

        //Check the first spinner
        int indexFrom = spinnerFrom.getSelectedItemPosition();
        if (indexFrom > 0 ) {
            //This means that the spinnerFrom has a valid value...
            valuesOk = true;
            Log.v(myLogTag, "spinnerFrom has index, proceeding... " + indexFrom );

        } else {
            Log.v(myLogTag, "spinnerFrom has index=0, stopping");

            return false;
        }

        //Check the second spinner
        int indexTo = spinnerTo.getSelectedItemPosition();
        if (valuesOk & (indexTo > 0) ) {
            //This means that spinnerTo has a valid value
            Log.v(myLogTag, "spinnerTo has index, proceeding... " + indexTo );
            valuesOk = true;
        } else {
            //Otherwise we return false
            Log.v(myLogTag, "spinnerTo has index=0, stopping");
            return false;
        }

        //Check the fromAmount so that it is actually is something.
        //The field itself is already secured since it is set to
        //digits only
        try {
            //To make sure to catch the exception in case the text in the
            //from amount is not a number...
            double tempDouble = Double.parseDouble(fromAmount.getText().toString());
            Log.v(myLogTag, "succeeded with converting the fromAmount " + tempDouble);
            valuesOk = true;
        }
        catch (Exception e) {
            //We just got an exception, so there must be something wrong.
            //Setting valuesOk to false!
            valuesOk = false;
            Log.v(myLogTag, "Exception caught...");
            Log.v(myLogTag, "Exception!" + e);
            return valuesOk;
        }

        Log.v(myLogTag, "sanityCheckValues done, result is " + valuesOk);
        return valuesOk;

    }
    //This is to monitor the spinners...
    private class CalculateResultListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.v(myLogTag, "Now we got a CalculateResultListener, let's try to calculate the results");

            //Whenever a spinner is selected, we shall try to calculate the outcome
            calculateOutcome();
            Log.v(myLogTag, "Tried, dunno if it worked, Index changed");

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
    //This is to monitor the editText field...
    private class MyTextWatcher implements TextWatcher {

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Seems I never manage to get in here .... :-(
            Log.v(myLogTag, "Now we got a TextWatcher event onTextChanged, let's try to calculate the results");
            calculateOutcome();
            Log.v(myLogTag, "Tried to calculate, dunno if it worked, TEXT changed");

        }
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    }


}


