package tempcontroller.challenge.com.challenge75f;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tempcontroller.challenge.com.challenge75f.listeners.OnSwipeTouchListener;
import tempcontroller.challenge.com.challenge75f.views.CustomArcProgressBar;

public class MainActivity extends AppCompatActivity implements OnSwipeTouchListener.SwipeTouchListener {


    private MenuItem mMenuItems;
    private TextView adjustableTemp;
    private CustomArcProgressBar customArcProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adjustableTemp = (TextView)findViewById(R.id.tv_device_temp);

        customArcProgressBar = (CustomArcProgressBar)findViewById(R.id.capb_custom_circle);
        customArcProgressBar.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        customArcProgressBar.setOnTouchListener(new OnSwipeTouchListener(this));
        getSupportActionBar().setTitle("Austin Home");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mMenuItems = menu.findItem(R.id.action_settings);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Menu Icon Pressed", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwipeLeft() {
        int tempValue = Integer.parseInt(adjustableTemp.getText().toString());
        if(tempValue > 50) {
            degrees -=1;
            adjustableTemp.setText(String.valueOf(--tempValue));
            startRotating();
            if(tempValue == 50) {
                Toast.makeText(MainActivity.this, "Minimum Value of Temp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSwipeRight() {
        int tempValue = Integer.parseInt(adjustableTemp.getText().toString());
        if(tempValue < 100) {
            degrees +=1;
            startRotating();
            adjustableTemp.setText(String.valueOf(++tempValue));
            if (tempValue == 100) {
                Toast.makeText(MainActivity.this, "Maximum Value of Temp", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Handler to call the canvas to rotate.
    public void startRotating()
    {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customArcProgressBar.doRotation(degrees);
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    private int degrees = 0;
}
