package duongnguyen.vongquanhphuyen.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import duongnguyen.vongquanhphuyen.R;

public class NoteActivity extends AppCompatActivity {
    private TextView tvTemperature, tvDescription, tvWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tvTemperature = findViewById(R.id.tvTemperature);
        tvDescription = findViewById(R.id.tvDescription);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        getWeatherData();
    }

    private void getWeatherData() {
        String latitude = "13.08";
        String longitude = "109.302";
        String apiKey = "914a3293293e01cd95d0fb1e2cb8890c";

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric&lang=vi";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainObject = response.getJSONObject("main");
                    double temp = mainObject.getDouble("temp");
                    int roundedTemp = (int) Math.round(temp);
                    tvTemperature.setText(roundedTemp + "°C");

                    JSONObject weatherObject = response.getJSONArray("weather").getJSONObject(0);
                    String description = weatherObject.getString("description");
                    description = description.substring(0, 1).toUpperCase() + description.substring(1);
                    tvDescription.setText(description);

                    JSONObject windObject = response.getJSONObject("wind");
                    double windSpeed = windObject.getDouble("speed");
                    double windSpeedKmh = windSpeed*3.6;
                    double roundedWindSpeed = Math.round(windSpeedKmh * 10.0) / 10.0;
                    tvWindSpeed.setText("Gió: " + roundedWindSpeed + " km/h");
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvDescription.setText("Lỗi xử lý dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvDescription.setText("Lỗi tải thời tiết");
            }
        });
        queue.add(jsonObjectRequest);
    }
}
