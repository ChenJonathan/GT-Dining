package tech.com.gtdining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

public class InfoActivity extends Activity implements View.OnClickListener {

    private String menu;

    private int diningHall;
    private int day;
    private int hour;
    private int meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        diningHall = intent.getIntExtra("Dining Hall", 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_WEEK);
        hour = cal.get(Calendar.HOUR_OF_DAY);

        if(hour <= 11) {
            meal = 1;
        } else if(diningHall == 1 && hour > 11 && hour <= 16) {
            meal = 2;
        } else {
            meal = 3;
        }

        menu = getMenu(diningHall, day, meal);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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

    public String getMenu(int diningHall, int day, int meal) {
        return "";
    }

    @Override
    public void onClick(View v) {

    }
}
