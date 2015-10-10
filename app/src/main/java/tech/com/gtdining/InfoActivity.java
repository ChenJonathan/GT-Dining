package tech.com.gtdining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class InfoActivity extends Activity implements View.OnClickListener {

    private ListView listView;

    private ArrayList<String> menu;

    private int diningHall;
    private int day;
    private int meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getActionBar().setTitle("Menu Information");

        Intent intent = getIntent();
        diningHall = intent.getIntExtra("Dining Hall", 0);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_WEEK);

        updateMenu();

        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                menu );

        listView.setAdapter(arrayAdapter);
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

    public ArrayList<String> getMenu(int diningHall, int day, int meal) {
        return new ArrayList<String>();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.sundayButton:
                day = 1;
                break;
            case R.id.mondayButton:
                day = 2;
                break;
            case R.id.tuesdayButton:
                day = 3;
                break;
            case R.id.wednesdayButton:
                day = 4;
                break;
            case R.id.thursdayButton:
                day = 5;
                break;
            case R.id.fridayButton:
                day = 6;
                break;
            case R.id.saturdayButton:
                day = 7;
                break;
            case R.id.breakfastButton:
                meal = 1;
                break;
            case R.id.lunchButton:
                meal = 2;
                break;
            case R.id.dinnerButton:
                meal = 3;
                break;
        }
        updateMenu();
    }

    public void updateMenu() {
        menu = getMenu(diningHall, day, meal);
    }
}
