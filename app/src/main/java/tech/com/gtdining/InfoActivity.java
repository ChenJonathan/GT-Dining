package tech.com.gtdining;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

public class InfoActivity extends Activity implements View.OnClickListener {

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if(hour <= 11) {
            meal = 1;
        } else if(hour > 11 && hour <= 16) {
            meal = 2;
        } else {
            meal = 3;
        }

        updateMenu();

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
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
        MenuUpdater updater = new MenuUpdater();
        updater.execute(diningHall, day, meal);
        while(menu == null) {
        }
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
    }

    public class MenuUpdater extends AsyncTask<Integer, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... nums) {
            ArrayList<String> test = new ArrayList<>();
            try{
                int diningHall = nums[0];
                int day = nums[1];
                int meal = nums[2];
                String[] days = {" SUNDAY ", " MONDAY "," TUESDAY ", " WEDNESDAY ", " THURSDAY ", " FRIDAY ", " SATURDAY "};
                ArrayList<String> breakfast = new ArrayList<>();
                ArrayList<String> lunch = new ArrayList<>();
                ArrayList<String> dinner = new ArrayList<>();
                boolean reading  = false;
                int substart, subend, counter = 0;
                URL myUrl = null;
                if(diningHall == 1)
                    myUrl = getLink("https://m.gatechdining.com/dining-choices/resident/Brittain.html");
                else if(diningHall == 2)
                    myUrl = getLink("https://m.gatechdining.com/dining-choices/resident/index.html");
                else if(diningHall == 3)
                    myUrl = getLink("https://m.gatechdining.com/dining-choices/resident/woodruff.html");
                URLConnection myCon = myUrl.openConnection();
                InputStream myIS = myCon.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(myIS));
                String line;
                while((line = br.readLine()) != null) {
                    if(line.contains(days[day-1]))
                        reading = true;
                    if(day != 7) {
                        if(line.contains(days[day]))
                            reading = false;
                    }
                    else if(line.contains(days[1]))
                        reading = false;
                    if(reading){
                        if(line.contains("mealname")) {
                            if(line.contains("BREAKFAST"))
                                counter = 1;
                            else if(line.contains("LUNCH"))
                                counter = 2;
                            else if(line.contains("DINNER"))
                                counter = 3;
                        }
                        substart = line.indexOf("onmouseout=\"pcls(this);\">") + 25;
                        subend = line.indexOf("span><img class=\"icon\"") -2;
                        if(substart > 0 && subend > 0)
                            if (counter == 1) {
                                breakfast.add(line.substring(substart, subend));
                            }
                            else if (counter == 2) {
                                lunch.add(line.substring(substart, subend));
                            }
                            else if (counter == 3) {
                                dinner.add(line.substring(substart, subend));
                            }
                    }
                }
                if(meal == 1) {
                    test = breakfast;
                    //return breakfast;
                }
                else if(meal == 2) {
                    test = lunch;
                    //return lunch;
                }
                else if(meal == 3) {
                    test = dinner;
                    //return dinner;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(test);
            return test;
        }

        @Override
        protected void onPostExecute (ArrayList<String> result) {
            super.onPostExecute(result);
            System.out.println("anal fuck this shittt");
            System.out.println("12fuck this shittt");
            System.out.println("12fuck this shittt");
            System.out.println("12fuck this shittt");
            System.out.println("12fuck this shittt");
            setMenu(result);
        }

        public URL getLink(String diningUrl) {
            try {
                URL myUrl = new URL(diningUrl);
                URLConnection myCon = myUrl.openConnection();
                InputStream myIS = myCon.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(myIS));
                String line = null;
                String printthis = "";
                int substart = 0;
                int subend = 0;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("<a href=\"/images") > -1) {
                        substart = line.indexOf("href=") + 6;
                        subend = line.indexOf("target=") - 2;
                        if (substart > -1 && subend > -1) {
                            printthis = "https://m.gatechdining.com" + line.substring(substart, subend);
                            printthis = StringUtils.unescapeHtml3(printthis);
                            URL returnthis = new URL(printthis);
                            return returnthis;
                        }
                    }
                }
                return null;
            } catch (Exception e) {}
            return null;
        }
    }
}