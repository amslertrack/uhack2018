package onepunch.uhack.amslertrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class AmslerTrackAct extends AppCompatActivity {
    private ProgressBar mSpinner;

    public ProgressBar getSpinner() {
        return mSpinner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amsler_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context thisContext = (Context)this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(thisContext, AmslerTrackSlidePagerActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //startActivityForResult()
                }
        });


        mSpinner = findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.GONE); //GONE

        AmslerTrackView amslerTrackView = findViewById(R.id.amslertrackview);
        amslerTrackView.requestFocus();

        //Show the instructions
        Intent intent = new Intent(this, AmslerTrackSlidePagerActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent); //startActivityForResult()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amsler_track, menu);
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
