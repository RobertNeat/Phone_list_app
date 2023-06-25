package com.example.phone_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneEditorActivity extends AppCompatActivity {


    Button CancelBTN;
    Button SaveBTN;
    Button WebsiteBTN;

    //elementy formularza
    EditText ManufacturerET;
    EditText ModelET;
    EditText AndroidVersionET;
    EditText WebSiteET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_editor);

        //przypisanie wartości z widoku do zmiennych (formularz)
        ManufacturerET = findViewById(R.id.ManufacturerET);
        ModelET = findViewById(R.id.ModelET);
        AndroidVersionET = findViewById(R.id.AndroidVersionET);
        WebSiteET = findViewById(R.id.WebSiteET);

        //załadowanie wartości z intencji jeżeli to jest edycja
        Bundle bundle = getIntent().getExtras();
        if( bundle!=null && !bundle.isEmpty()) {//jeżeli nie jest puste to pola ustawić na wartości z extras
            ManufacturerET.setText(bundle.getString("manufacturer"));
            ModelET.setText(bundle.getString("model"));
            AndroidVersionET.setText(bundle.getString("android_version"));
            WebSiteET.setText(bundle.getString("website"));
        }

        //obsługa anulowania aktualnego widoku na podstawie "parentActivityName" ustawionego w manifeście dla aktualnego widoku
        CancelBTN = findViewById(R.id.CancelBTN);
        CancelBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //obsługa przejścia do witryny niejawną intencją (domyślna przeglądarka ją obsłuży)
        WebsiteBTN = findViewById(R.id.WebsiteBTN);
        WebsiteBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(WebSiteET.getText().toString().startsWith("http://") || WebSiteET.getText().toString().startsWith("https://")) {//sprawdzenie czy adres zaczyna się od "http://", w przeciwnym razie Toast z komunikatem
                    Intent open_webpage = new Intent("android.intent.action.VIEW", Uri.parse(WebSiteET.getText().toString()));
                    startActivity(open_webpage);
                }else{
                    Toast.makeText(PhoneEditorActivity.this,"Not valid website address", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //obsługa zapisania aktualnego formularza jako rekordu do bazy danych
        SaveBTN = findViewById(R.id.SaveBTN);
        SaveBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //zczytanie wartości z formularza
                String manufacturer = ManufacturerET.getText().toString();
                String model = ModelET.getText().toString();
                String str_android_version = AndroidVersionET.getText().toString();
                String website = WebSiteET.getText().toString();



                //room database - jest abstrakcyjnym singletonem (więc raczej sobie tego nie poużywam)
                //repozytorium - jest źródłem danych dla aplikacji

                try {

                    int android_version = Integer.parseInt(str_android_version);
                    ElementRepository elementRepository = new ElementRepository(getApplication());//albo spróbować "new application"

                    Element element = new Element(manufacturer, model, android_version, website);


                    if(bundle!=null && !bundle.isEmpty() ) {//obsługa aktualizacji danych jeżeli intencja nie jest pusta
                        long parsed_id = Long.parseLong(bundle.getString("id"));
                        Element element_update = new Element(parsed_id,manufacturer, model, android_version, website);//customowy inicjalizator, żeby można było aktualizować element na podstawie id
                        elementRepository.update(element_update);

                    }else {//dane były wpisane przez użytkownika więc dodajemy nową pozycję
                        elementRepository.insert(element);
                    }
                    finish(); //powrót do pierwszej aktywności
                }catch(NumberFormatException e){
                    Toast.makeText(PhoneEditorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        //walidacja producenta
        ManufacturerET.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && ManufacturerET.getText().toString().trim().length() == 0) {
                    ManufacturerET.setError("Manufacturer can't be empty");
                    Toast.makeText(PhoneEditorActivity.this,"Manufacturer can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //walidacja modelu
        ModelET.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && ModelET.getText().toString().trim().length() == 0) {
                    ModelET.setError("Model can't be empty");
                    Toast.makeText(PhoneEditorActivity.this,"Model can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //walidacja wersji androida
        AndroidVersionET.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(AndroidVersionET.getText().toString().length()==0){
                        AndroidVersionET.setError("Android version can't be empty");
                        Toast.makeText(PhoneEditorActivity.this, "Android version can't be empty", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            String version_text = AndroidVersionET.getText().toString();
                            int android_version = Integer.parseInt(version_text);
                        }
                        catch(NumberFormatException e){
                            Toast.makeText(PhoneEditorActivity.this, "The version should be numertic, Error:"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


        //walidacja strony internetowej
        WebSiteET.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && WebSiteET.getText().toString().trim().length() == 0) {
                    WebSiteET.setError("Website can't be empty");
                    Toast.makeText(PhoneEditorActivity.this,"Website can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}