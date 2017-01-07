package com.ranjeet.weatherapp.helper;


import android.text.format.DateUtils;
import android.util.Log;

import com.ranjeet.weatherapp.R;
import com.ranjeet.weatherapp.adapter.ForecastOverviewAdapter;
import com.ranjeet.weatherapp.model.Day;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ranjeet Kushwaha on 07.01.2017
 */
public class WeatherHelper {

    private NotificationHelper nh;

    private Double kelvin = 272.15;
    private String city = "lol";
    private String description, formattedDater, tomorrow_desc;
    private Integer weatherid, sunrise, sunset, humidity, tomorrowweatherid;
    private Double temperature_max, speed, pressure, tomorrow_temp;
    private static Integer istat = R.drawable.ic_sunny;
    public ArrayList<Day> items;
    public ForecastOverviewAdapter adapter;



    public void setCity(String in){
        city = in;
    }

    public void setDescription(String in){
            description = in;
    }

    public void setWeatherId(Integer in){
        weatherid = in;
    }

    public void setTomorrowWeatherId(Integer in){
        tomorrowweatherid = in;
    }

    public void setSunrise(Integer in){
        sunrise = in;
    }

    public void setSunset(Integer in){
        sunset = in;
    }

    public void setTemperature_max(Double in){
        temperature_max = in;
    }

    public void setHumidity(Integer in){
        humidity = in;
    }

    public void setPressure(Double in){
        pressure = in;
    }

    public void setSpeed(Double in){
        speed = in;
    }

    public void setTomorrowTemp(Double in){
        tomorrow_temp = in;
    }

    public void setTomorrowDesc(String in){
        tomorrow_desc = in;
    }

    public String getCity(){
        return city;
    }
    public String getDescription(){
        return description;
    }

    public Integer getWeatherId(){
        return weatherid;
    }

    public Integer getTomorrowWeatherId(){
        return tomorrowweatherid;
    }

    public Integer getSunrise(){
        return sunrise;
    }

    public Integer getSunset(){
        return sunset;
    }

    public Double getTemperature_max(){
        return temperature_max;
    }

    public Integer getHumidity(){
        return humidity;
    }

    public Double getPressure(){
        return pressure;
    }

    public Double getSpeed(){
        return speed;
    }

    public Double getTomorrow_temp(){
        return tomorrow_temp;
    }

    public String getTomorrow_desc(){
        return tomorrow_desc;
    }

    /**
     *man
     * @param in json input
     */
    public void ParseData(String in){
        try {
            JSONObject reader = new JSONObject(in);
Log.d("JSONObject","JSONObject="+reader);
            JSONObject city = reader.optJSONObject("city");
            setCity(city.getString("name"));

            JSONArray list = reader.optJSONArray("list");
            JSONObject JSONList = list.optJSONObject(0);

            JSONArray weather = JSONList.optJSONArray("weather");
            JSONObject JSONWeather = weather.optJSONObject(0);

            setDescription(JSONWeather.optString("description"));
            setWeatherId(JSONWeather.optInt("id"));

            //JSONObject sys = reader.getJSONObject("sys");
            String country = city.optString("country");
            //setSunrise(sys.getInt("sunrise"));
            //setSunset(sys.getInt("sunset"));

            JSONObject temp = JSONList.optJSONObject("temp");
            //Integer temperature_min = main.getInt("temp_min");
            setTemperature_max(temp.optDouble("morn"));
            setHumidity(JSONList.optInt("humidity"));
            setPressure(JSONList.optDouble("pressure"));

            //JSONObject wind = reader.getJSONObject("wind");
            setSpeed(JSONList.optDouble("speed"));
            //temperature_min =  Math.round(temperature_min) - kelvin;
            setTemperature_max(temperature_max);


            JSONObject tomorrowJSONList = list.optJSONObject(1);
            JSONObject tomorrowtemp = tomorrowJSONList.optJSONObject("temp");
            setTomorrowTemp(tomorrowtemp.optDouble("day"));
            JSONArray tomorrowweather = tomorrowJSONList.optJSONArray("weather");
            JSONObject tomorrowJSONWeather = tomorrowweather.optJSONObject(0);
            setTomorrowWeatherId(tomorrowJSONWeather.optInt("id"));
            setTomorrowDesc(tomorrowJSONWeather.optString("description"));


            items = new ArrayList<>();
            for (int i = 0; i < list.length(); i++) {
                JSONObject forJSONList = list.optJSONObject(i);
                JSONArray forWeather = forJSONList.optJSONArray("weather");
                JSONObject forJSONWeather = forWeather.optJSONObject(i);
                JSONObject fortemp = forJSONList.optJSONObject("temp");
                Log.i("RecyclerView", "JSON Parsing Nr." + i);
                items.add(new Day(forJSONList.optInt("dt"), fortemp.optDouble("max"), fortemp.optDouble("min"), forJSONList.optDouble("pressure"), forJSONList.optInt("humidity"), forJSONWeather.optInt("id"), forJSONWeather.optString("description"), forJSONList.optDouble("speed")));
                Log.i("RecyclerView", "Added items Nr" + i);
                adapter.notifyItemInserted(i);
                Log.i("RecyclerView", "notifyItemInserted Nr." + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JSON Parsing", e.toString());
        }
    }

    public void ParseDataSingle(String in){
        try {
            JSONObject reader = new JSONObject(in);

            JSONObject city = reader.optJSONObject("city");
            setCity(city.optString("name"));

            JSONArray list = reader.optJSONArray("list");
            JSONObject JSONList = list.optJSONObject(0);

            JSONArray weather = JSONList.optJSONArray("weather");
            JSONObject JSONWeather = weather.optJSONObject(0);

            setDescription(JSONWeather.optString("description"));
            setWeatherId(JSONWeather.optInt("id"));

            //JSONObject sys = reader.getJSONObject("sys");
            String country = city.optString("country");
            //setSunrise(sys.getInt("sunrise"));
            //setSunset(sys.getInt("sunset"));

            JSONObject temp = JSONList.optJSONObject("temp");
            //Integer temperature_min = main.getInt("temp_min");
            setTemperature_max(temp.optDouble("morn"));
            setHumidity(JSONList.optInt("humidity"));
            setPressure(JSONList.optDouble("pressure"));

            //JSONObject wind = reader.getJSONObject("wind");
            setSpeed(JSONList.optDouble("speed"));
            //temperature_min =  Math.round(temperature_min) - kelvin;
            setTemperature_max(temperature_max);


            JSONObject tomorrowJSONList = list.optJSONObject(1);
            JSONObject tomorrowtemp = tomorrowJSONList.optJSONObject("temp");
            setTomorrowTemp(tomorrowtemp.optDouble("day"));
            JSONArray tomorrowweather = tomorrowJSONList.optJSONArray("weather");
            JSONObject tomorrowJSONWeather = tomorrowweather.optJSONObject(0);
            setTomorrowWeatherId(tomorrowJSONWeather.optInt("id"));
            setTomorrowDesc(tomorrowJSONWeather.optString("description"));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JSON Parsing", e.toString());
        }
    }

    public ArrayList<Day> getDays() {
        return items;
    }
    /**
     *
     * @param ID the weather id
     * @return Integer weather drawable
     */
    public Integer convertWeather(Integer ID){
        switch (ID){
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                istat = R.drawable.ic_rain;
                break;
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                istat = R.drawable.ic_rain;
                break;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
                istat = R.drawable.ic_rain;
                break;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                istat = R.drawable.ic_rain;
                //stat = R.drawable.ic_snow;
                break;
            case 700:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
            case 771:
            case 781:
                istat = R.drawable.ic_cloud;
                break;
            case 800:
                istat = R.drawable.ic_sunny;
                break;
            case 801:
            case 802:
                istat = R.drawable.ic_cloudy;
                break;
            case 803:
            case 804:
                istat = R.drawable.ic_cloud;
                break;
            case 900:
            case 901:
            case 902:
            case 903:
            case 904:
            case 905:
            case 906:
                istat = R.drawable.ic_cloud;
                break;
            default:
                istat = R.drawable.ic_sunny;
                break;
        }
        return istat;
    }
    public static Integer convertWeatherToColor(Integer ID){
        switch (ID){
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                istat = R.color.rainyWeather;
                break;
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                istat = R.color.badWeather;
                break;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
                istat = R.color.rainyWeather;
                break;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                istat = R.color.badWeather;
                break;
            case 700:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
            case 771:
            case 781:
                istat = R.color.warningWeather;
                break;
            case 800:
                istat = R.color.sunnyWeather;
                break;
            case 801:
                istat = R.color.sunnyWeather;
                break;
            case 802:
                istat = R.color.goodWeather;
                break;
            case 803:
            case 804:
                istat = R.color.goodWeather;
                break;
            case 900:
            case 901:
            case 902:
            case 903:
            case 904:
            case 905:
            case 906:
                istat = R.color.warningWeather;
                break;
            default:
                istat = R.color.sunnyWeather;
                break;
        }
        return istat;
    }

    /**
     *
     * @param Time unix timestamp
     * @return SimpleDateFormat
     */
    public String convertTime(Integer Time){
        formattedDater = DateUtils.getRelativeTimeSpanString(Time, System.currentTimeMillis(), 0).toString();
        return formattedDater;
    }

}