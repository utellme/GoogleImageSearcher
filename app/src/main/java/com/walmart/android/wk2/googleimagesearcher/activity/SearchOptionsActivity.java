package com.walmart.android.wk2.googleimagesearcher.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.walamrt.android.wk2.googleimagesearcher.R;
import com.walmart.android.wk2.googleimagesearcher.models.SearchSettings;

public class SearchOptionsActivity extends AppCompatActivity {

    private SearchSettings searchSettings;
    private EditText etSiteFilter;
    private Spinner  spImageSize, spImageType, spColorFilter;
    ArrayAdapter<CharSequence> imageSizeAdapter, imageTypeAdapter, colorFilterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_options);

        //getSupportActionBar().show();
        //Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();

        searchSettings = (SearchSettings)getIntent().getSerializableExtra("settings");

        System.out.println("Search Options Activity");
        System.out.println("Search Options Activity: " + searchSettings.getsColorFilter() + "," + searchSettings.getsImageSize() + "," + searchSettings.getsImageType() + "," + searchSettings.getsSiteFilter());

        setViews();

        setSearchSettings();

    }

    private void setViews(){

        etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
        spImageSize = (Spinner)findViewById(R.id.spImageSize);
        spImageType = (Spinner)findViewById(R.id.spImageType);
        spColorFilter = (Spinner)findViewById(R.id.spColorFilter);


        //ImageSize
        // Create an ArrayAdapter using the string array and a default spinner layout
        imageSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_size_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        imageSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spImageSize.setAdapter(imageSizeAdapter);

        //ImageType
        // Create an ArrayAdapter using the string array and a default spinner layout
        imageTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_type_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        imageTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spImageType.setAdapter(imageTypeAdapter);

        // ColorFilter
        // Create an ArrayAdapter using the string array and a default spinner layout
        colorFilterAdapter = ArrayAdapter.createFromResource(this,
                R.array.color_filter_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        colorFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spColorFilter.setAdapter(colorFilterAdapter);



    }

    private void setSearchSettings() {


        etSiteFilter.setText(searchSettings.getsSiteFilter());

        //imagesize
        int position = imageSizeAdapter.getPosition(searchSettings.getsImageSize());
        spImageSize.setSelection(position);

        //imagesize
        position = imageTypeAdapter.getPosition(searchSettings.getsImageType());
        spImageType.setSelection(position);

        //color filter
        position = colorFilterAdapter.getPosition(searchSettings.getsColorFilter());
        spColorFilter.setSelection(position);

    }

    public void onSettingsSave(View view){

        Intent retData = new Intent();

        //SearchSettings settings = new SearchSettings();
       // searchSettings.setsImageType(etImageType.getText().toString());
       // searchSettings.setsImageSize(etImageSize.getText().toString());
        //searchSettings.setsColorFilter(etColorFilter.getText().toString());
        searchSettings.setsSiteFilter(etSiteFilter.getText().toString());

        searchSettings.setsImageSize(spImageSize.getSelectedItem().toString());
        searchSettings.setsImageType(spImageType.getSelectedItem().toString());
        searchSettings.setsColorFilter(spColorFilter.getSelectedItem().toString());

        System.out.println("On Save ");
        System.out.println("On Save: " + searchSettings.getsColorFilter() + "," + searchSettings.getsImageSize() + "," + searchSettings.getsImageType() + "," + searchSettings.getsSiteFilter());

        retData.putExtra("settings", searchSettings);


        // Activity finished ok, return the data
        setResult(RESULT_OK, retData); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    public void onSettingsCancel(View view){

        // Activity finished ok, return the data
        setResult(RESULT_CANCELED, null); // set result code and bundle data for response
        finish();
    }

    public void onSettingsReset(View view){

        //reset to default values i.e. no settings
        spImageSize.setSelection(0);
        spImageType.setSelection(0);
        spColorFilter.setSelection(0);
        etSiteFilter.setText("");
    }
}
