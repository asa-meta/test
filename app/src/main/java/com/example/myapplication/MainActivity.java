package com.example.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.font.PDFont;
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font;
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDAcroForm;
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDTextField;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(),"PMingLiU.ttf");
        EditText editText = findViewById(R.id.et1);
        editText.setTypeface(typeface);


        PDFBoxResourceLoader.init(this);

    }

    private String TAG = "MainActiiii";

    private void load() {
        try {
            PDDocument pdDocument = PDDocument.load(getAssets().open("2_wrapper.pdf"));
            PDAcroForm pdAcroForm = pdDocument.getDocumentCatalog().getAcroForm();
            if (pdAcroForm == null) {
                Log.d("-----------------", "mei you fieid");
            } else {
                PDFont pdFont = PDType0Font.load(pdDocument,getAssets().open("PMingLiU.ttf"));
                String fontName = pdAcroForm.getDefaultResources().add(pdFont).getName();
                String defaultAppearanceString = "/" + fontName + " 10 Tf 0 g";

                Log.d(TAG, "load: "+defaultAppearanceString);
                PDTextField id3 = (PDTextField) pdAcroForm.getField("test");
                id3.setDefaultAppearance(defaultAppearanceString);
                EditText et1 = findViewById(R.id.et1);
                id3.setValue(et1.getText().toString());
                id3.setReadOnly(true);
            }
            File pdfFile = new File(Environment.getExternalStorageDirectory(), "3.pdf");
            pdDocument.save(pdfFile);

            PDFView pdfView = findViewById(R.id.pdfView);
            pdfView.fromFile(pdfFile)
                    .enableAnnotationRendering(true)
                    .load();




        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();

        }

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