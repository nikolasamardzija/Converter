package com.example.nidzo.spinaroundapp7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner unitTypeSpinner;
    private EditText amountTextView;
    TextView teaspoonTextView, tablespoonTextView, cupTextView, ounceTextView, pintTextView, quartTextView,
            gallonTextView, poundTextView, milliliterTextView, literTextView, milligramTextView, kilogramTextView;
    String itemSelectedInSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //github change
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addItemsToUnitTypeSpinner();
        addListenerToUnitTypeSpinner();
        initilizeTextViews();
        amountTextView = (EditText) findViewById(R.id.amount_text_view);
        amountTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    addListenerToUnitTypeSpinner();
                    checkIfConvertingFromTsp(itemSelectedInSpinner);
                    System.out.println("changed");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void initilizeTextViews() {
        teaspoonTextView = (TextView) findViewById(R.id.tsp_text_view);
        tablespoonTextView = (TextView) findViewById(R.id.tbs_text_view);
        cupTextView = (TextView) findViewById(R.id.cup_text_view);
        ounceTextView = (TextView) findViewById(R.id.oz_text_view);
        pintTextView = (TextView) findViewById(R.id.pint_text_view);
        quartTextView = (TextView) findViewById(R.id.quart_text_view);
        gallonTextView = (TextView) findViewById(R.id.gallon_text_view);
        poundTextView = (TextView) findViewById(R.id.pound_text_view);
        milliliterTextView = (TextView) findViewById(R.id.ml_text_view);
        literTextView = (TextView) findViewById(R.id.liter_text_view);
        milligramTextView = (TextView) findViewById(R.id.mg_text_view);
        kilogramTextView = (TextView) findViewById(R.id.kg_text_view);
    }

    public void addItemsToUnitTypeSpinner() {
        unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.conversion_types, android.R.layout.simple_spinner_item);
        unitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitTypeSpinner.setAdapter(unitTypeSpinnerAdapter);
    }

    public void addListenerToUnitTypeSpinner() {
        unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSelectedInSpinner = parent.getItemAtPosition(position).toString();
                checkIfConvertingFromTsp(itemSelectedInSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void checkIfConvertingFromTsp(String currentUnit) {
        switch (currentUnit){
            case "teaspoon": updateUnitTypesUsingTsp(Quantity.Unit.tsp);
                break;
            case "tablespoon" : updateUnitTypesUsingOther(Quantity.Unit.tbs);
                break;
            case "cup" : updateUnitTypesUsingOther(Quantity.Unit.cup);
                break;
            case "ounce" : updateUnitTypesUsingOther(Quantity.Unit.oz);
                break;
            case "pint" : updateUnitTypesUsingOther(Quantity.Unit.pint);
                break;
            case "quart" : updateUnitTypesUsingOther(Quantity.Unit.quart);
                break;
            case "gallon" : updateUnitTypesUsingOther(Quantity.Unit.gallon);
                break;
            case "pound" : updateUnitTypesUsingOther(Quantity.Unit.pound);
                break;
            case "milliliter" : updateUnitTypesUsingOther(Quantity.Unit.ml);
                break;
            case "liter" : updateUnitTypesUsingOther(Quantity.Unit.liter);
                break;
            case "milligram" : updateUnitTypesUsingOther(Quantity.Unit.mg);
                break;
            default: updateUnitTypesUsingOther(Quantity.Unit.kg);
                break;
        }

    }

    public void updateUnitTypesUsingTsp(Quantity.Unit curentUnit) {
        double doubleToConvert;

        try {
            if(!amountTextView.getText().toString().equals("")) {
                doubleToConvert = Double.parseDouble(amountTextView.getText().toString());
            }else {
                doubleToConvert = 0d;
            }
        }catch (NumberFormatException e){
            doubleToConvert = 0d;
        }


        String teaspoonValueAndUnit = doubleToConvert + " tsp";
        teaspoonTextView.setText(teaspoonValueAndUnit);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.tbs, tablespoonTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.cup, cupTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.oz, ounceTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.pint, pintTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.quart, quartTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.gallon, gallonTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.pound, poundTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.ml, milliliterTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.liter, literTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.mg, milligramTextView);
        updateUnitTextFieldTsp(doubleToConvert, Quantity.Unit.kg, kilogramTextView);
    }

    public void updateUnitTextFieldTsp(double doubleToConvert, Quantity.Unit unitConvertingTo, TextView theTextView) {
        Quantity unitQuantity = new Quantity(doubleToConvert, Quantity.Unit.tsp);

        String tempUnit = unitQuantity.to(unitConvertingTo).toString();
        theTextView.setText(tempUnit);
    }

    public void updateUnitTypesUsingOther(Quantity.Unit currentUnit) {
        double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);
        String valueInTeaspoons = currentQuantitySelected.to(Quantity.Unit.tsp).toString();
        teaspoonTextView.setText(valueInTeaspoons);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.tbs, tablespoonTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.cup, cupTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.oz, ounceTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.pint, pintTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.quart, quartTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.gallon, gallonTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.pound, poundTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.ml, milliliterTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.liter, literTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.mg, milligramTextView);
        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.kg, kilogramTextView);

        if (currentUnit.name().equals(currentQuantitySelected.unit.name())) {
            // Create the TextView text by taking the value in EditText and adding
            // on the currently selected unit in the spinner

            String currentUnitTextViewText = doubleToConvert + " " + currentQuantitySelected.unit.name();

            // Create the TextView name to change by getting the currently
            // selected quantities unit name and tacking on _text_view
            String currentTextViewName = currentQuantitySelected.unit.name() + "_text_view";

            // Get the resource id needed for the textView to use in findViewById
            int currentId = getResources().getIdentifier(currentTextViewName, "id", MainActivity.this.getPackageName());

            // Create an instance of the TextView we want to change
            TextView currentTextView = (TextView) findViewById(currentId);

            // Put the right data in the TextView
            currentTextView.setText(currentUnitTextViewText);
        }
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert, Quantity.Unit currentUnit, Quantity.Unit preferredUnit, TextView targetTextView) {
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);

        // Algorithm used quantityInTbs.to(Unit.tsp).to(Unit.ounce)
        String tempTextViewText = currentQuantitySelected.to(Quantity.Unit.tsp).to(preferredUnit).toString();
        targetTextView.setText(tempTextViewText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
