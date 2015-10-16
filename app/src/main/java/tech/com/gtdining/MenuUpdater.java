package tech.com.gtdining;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Kevin on 10/15/2015.
 */
public class MenuUpdater extends AsyncTask<Integer, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(Integer... nums) {
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
                myUrl = new URL("https://m.gatechdining.com/dining-choices/resident/Brittain.html");
            else if(diningHall == 2)
                myUrl = new URL("https://m.gatechdining.com/dining-choices/resident/index.html");
            else if(diningHall == 3)
                myUrl = new URL("https://m.gatechdining.com/dining-choices/resident/woodruff.html");
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
                        if (counter == 1)
                            breakfast.add(line.substring(substart, subend));
                        else if (counter == 2)
                            lunch.add(line.substring(substart,  subend));
                        else if (counter == 3)
                            dinner.add(line.substring(substart, subend));
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute (ArrayList<String> result) {

    }
}
