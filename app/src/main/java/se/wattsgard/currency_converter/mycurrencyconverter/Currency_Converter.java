package se.wattsgard.currency_converter.mycurrencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        fromAmount = (EditText) findViewById(R.id.editTextFromAmount);
        resultText = (TextView) findViewById(R.id.resultsTextView);

    }

/*    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_currency_converter , menu);
        Log.v(myLogTag, "Now the options menu has been created!");
        return true;
    } */

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


    private class CalculateResultListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            //Adding logs
            Log.v(myLogTag, "Now we got a CalculateResultListener");

            int selectedIndex = spinnerFrom.getSelectedItemPosition();

            Log.v(myLogTag, "Index was something");

            //Whenever a spinner is selected, we shall try to calculate the outcome
            double toAmount =  calculateOutcome();
            //resultText.setText(toAmount);
            resultText.setText(Double.toString(toAmount));

            Log.v(myLogTag, "Amount was something");
            Log.v(myLogTag, Double.toString(toAmount));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private double calculateOutcome() {
        //Wonder where to do the check if all required fields are selected or not
        //If to do it here, or to override each and every fields onSelect, and then
        //use this method once all is properly filled in.


        //Get all the data needed to do the conversion.

        String [] rates = getResources().getStringArray(R.array.currency_rate_array);
        int indexFrom = spinnerFrom.getSelectedItemPosition();
        int indexTo = spinnerTo.getSelectedItemPosition();


        if (sanityCheckonValues()) {
            //Only run this part of the activity in case the input data is ok
            double fromMoney = Double.parseDouble( resultText.toString() );
            double rateFrom = Double.parseDouble(rates[indexFrom]);
            double rateTo = Double.parseDouble(rates[indexTo]);
            double finalSum = fromMoney * (rateTo / rateFrom);
            return finalSum;
        }

        // Need to reconsider how to handle to NOT update
        // the output text field
        return 0.0;

    }

    private boolean sanityCheckonValues() {
        boolean valuesOk = false;

        //Check the first spinner
        int indexFrom = spinnerFrom.getSelectedItemPosition();
        if (indexFrom > 0 ) {
            //This means that the spinnerFrom has a valid value...
            valuesOk = true;
        } else {
            return false;
        }

        //Check the second spinner
        int indexTo = spinnerTo.getSelectedItemPosition();
        if (valuesOk & (indexTo > 0) ) {
            //This means that spinnerTo has a valid value
            valuesOk = true;
        } else {
            //Otherwise we return false
            return false;
        }

        //Check the fromAmount so that it is actually is something.
        //The field itself is already secured since it is set to
        //digits only
        double tempDouble = Double.parseDouble( fromAmount.getText().toString());

        resultText.setText(Double.toString(tempDouble));
                /*
        if (valuesOk & !(String.() fromAmount.toString(""))) {
            //If inside here, it means both spinners are ok,
            //and the value of the field fromAmount is NOT ""
            //That means all values should be ok and we can go ahead and return a TRUE
            valuesOk = true;
        }
        */

        return valuesOk;

    }


}


