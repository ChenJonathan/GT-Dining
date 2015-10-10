import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test {
	public static URL getLink(String diningUrl) throws IOException{
		URL myUrl = new URL(diningUrl);
		URLConnection myCon = myUrl.openConnection();
		InputStream myIS = myCon.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(myIS));
		String line = null;
		String printthis = "";
		int substart = 0;
		int subend = 0;
		while((line = br.readLine()) != null) {
			if(line.indexOf("<a href=\"/images") > -1){
				substart = line.indexOf("href=")+ 6;
    			subend = line.indexOf("target=") - 2;
    		if(substart > -1 && subend > -1){
    			printthis = "https://m.gatechdining.com" + line.substring(substart, subend);
    		printthis =  StringUtils.unescapeHtml3(printthis);
    		URL returnthis = new URL(printthis);
    		return returnthis;
    		}
			}
		}
		return null;
	}
}
