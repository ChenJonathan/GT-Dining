package tech.com.gtdining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import java.util.Calendar;

public class MainActivity extends Activity implements View.OnClickListener {

    Calendar c = Calendar.getInstance();
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, InfoActivity.class);
    }

    @Override
    protected void onResume() {

        super.onResume();
        imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(this);

        if(isClosed("Brittain")) {
            imageButton1.setImageResource(R.drawable.brittainclosed);
        }

        imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);

        if(isClosed("North Ave")) {
            imageButton2.setImageResource(R.drawable.northaveclosed);
        }

        imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(this);

        if(isClosed("Woody's")) {
            imageButton3.setImageResource(R.drawable.woodysclosed);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean isClosed(String diningHall) {
        int day = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if(diningHall.equals("Brittain")) {
            if(day == 1 && hour >= 16 && hour <=20) {
                return false; //open
            } else if(day > 1 && day < 6 && hour >= 7 && hour <= 20) {
                return false;
            } else if(day == 6 && hour >= 7 && hour <= 15) {
                return false;
            } else {
                return true;
            }

        } else if(diningHall.equals("North Ave") || diningHall.equals("Woody's")) {
            if(day == 1 && ((hour >= 10 && hour <=24) || (hour >= 0 && hour <=2))) {
                return false; //open
            } else if(day > 1 && day < 6 && ((hour >= 7 && hour <=24) || (hour >= 0 && hour <=2))) {
                return false;
            } else if(day == 6 && hour >= 7 && hour <= 22) {
                return false;
            } else if(day == 7 && hour >= 10 && hour <= 22) {
                return false;
            } else {
                return true;
            }

        }
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

    @Override
    public void onClick(View v) {
        int diningHall = 0;
        switch(v.getId()) {
            case R.id.imageButton1:
                diningHall = 1;
                break;
            case R.id.imageButton2:
                diningHall = 2;
                break;
            case R.id.imageButton3:
                diningHall = 3;
                break;
        }
        intent.putExtra("Dining Hall", diningHall);
        startActivity(intent);
    }
}
