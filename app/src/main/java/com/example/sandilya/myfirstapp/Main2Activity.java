package com.example.sandilya.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


public class Main2Activity extends AppCompatActivity {
    Integer duration = 300;
    Button btnNext;
    private TextSwitcher mSwitcher;
    // Array of String to Show In TextSwitcher
    String textToShow[]={"Main HeadLine","Your Message","New In Technology","New Articles","Business News","What IS New"};
    int messageCount=textToShow.length;
    // to keep current Index of text
    int currentIndex=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the view from activity_main.xml
        //setContentView(R.layout.activity_main2);

        Slide enterTrans = new Slide();
        enterTrans.setDuration(duration);
        enterTrans.setSlideEdge(Gravity.BOTTOM);
        getWindow().setEnterTransition(enterTrans);

        Slide returnTrans = new Slide();
        returnTrans.setSlideEdge(Gravity.BOTTOM);
        returnTrans.setDuration(duration);
        getWindow().setReturnTransition(returnTrans);

        final TextSwitcher textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        btnNext=(Button)findViewById(R.id.d_button);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(Main2Activity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(36);
                //myText.setTextColor(Color.BLUE);
                return myText;
            }
        });

        // specify the in/out animations you wish to use
        textSwitcher.setInAnimation(this, R.anim.slide_bottom);
        textSwitcher.setOutAnimation(this, R.anim.slide_top);

        // provide two TextViews for the TextSwitcher to use
        // you can apply styles to these Views before adding
        //textSwitcher.addView(new TextView(MainActivity.this));
       // textSwitcher.addView(new TextView(Main2Activity.this));
        // you are now ready to use the TextSwitcher
        // it will animate between calls to setText
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                currentIndex++;
                // If index reaches maximum reset it
                if(currentIndex==messageCount)
                    currentIndex=0;
                textSwitcher.setText(textToShow[currentIndex]);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
