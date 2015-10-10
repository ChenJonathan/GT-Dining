package tech.com.gtdining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class InfoActivity extends Activity implements View.OnClickListener {

    private ArrayList<String> menu;

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

    public ArrayList<String> getMenu(int diningHall, int day, int meal) {
        try {
            String[] days = {" SUNDAY ", " MONDAY "," TUESDAY ", " WEDNESDAY ", " THURSDAY ", " FRIDAY ", " SATURDAY "};
            ArrayList<String> breakfast = new ArrayList<String>();
            ArrayList<String> lunch = new ArrayList<String>();
            ArrayList<String> dinner = new ArrayList<String>();
            ArrayList<String> lateNight = new ArrayList<String>();
            boolean reading  = false;
            int substart, subend, counter = 0;
            URL myUrl = null;
            if(diningHall == 1)
                myUrl = new URL("https://m.gatechdining.com/images/Brittain%2010.5_tcm252-82006.htm");
            else if(diningHall == 2)
                myUrl = new URL("https://m.gatechdining.com/images/North%20Ave%2010.5_tcm252-11299.htm");
            else if(diningHall == 3)
                myUrl = new URL("https://m.gatechdining.com/images/Woody's10.5_tcm252-82063.htm");
            URLConnection myCon = myUrl.openConnection();
            InputStream myIS = myCon.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(myIS));
            String line = null;
            while((line = br.readLine()) != null) {
                if(line.indexOf(days[day-1]) > -1)
                    reading = true;
                if(day != 7) {
                    if(line.indexOf(days[day]) > -1)
                        reading = false;
                }
                else if(day == 7)
                    if(line.indexOf(days[1]) > -1)
                        reading = false;
                if(reading){
                    if(line.indexOf("mealname") > -1) {
                        if(line.indexOf("BREAKFAST") != -1)
                            counter = 1;
                        else if(line.indexOf("LUNCH") != -1)
                            counter = 2;
                        else if(line.indexOf("DINNER") != -1)
                            counter = 3;
                        else if(line.indexOf("LATE NIGHT") != -1)
                            counter = 4;
                    }
                    substart = line.indexOf("onmouseout=\"pcls(this);\">") + 25;
                    subend = line.indexOf("span><img class=\"icon\"") -2;
                    if(substart > 0 && subend > 0)
                        if (counter == 1)
                            breakfast.add(line.substring(substart, subend));
                        else if (counter == 2)
                            lunch.add(line.substring(substart,  subend));
                        else if (counter == 3)
                            dinner.add(line.substring(substart, subend));
                        else if (counter == 4)
                            lateNight.add(line.substring(substart, subend));
                }
            }
            if(meal == 1) {
                return breakfast;
            }
            else if(meal == 2) {
                return lunch;
            }
            else if(meal == 3) {
                return dinner;
            }
            else if(meal == 4) {
                return lateNight;
            }
        }
        catch (Exception e) {

        }
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
