package com.pace.cs639spring.hw6;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    protected GeoDataClient mGeoDataClient;

    EditText mDate;
    TextView mPlaceName;
    ImageView mPlaceImage;
    TextView mWeather;
    ImageView mWeatherIcon;

    Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDate = findViewById(R.id.date);
        mPlaceName = findViewById(R.id.place_name);
        mPlaceImage = findViewById(R.id.place_image);
        mWeather = findViewById(R.id.weather);
        mWeatherIcon = findViewById(R.id.weather_icon);
        mGeoDataClient = Places.getGeoDataClient(this);
        configurePlaceAutocompleteFragment();
    }


    public void configurePlaceAutocompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                populatePlace(place);
            }

            @Override
            public void onError(Status status) {
                Log.i("PLACE_PICKER", "An error occurred: " + status);
            }
        });
    }

    private void populatePlace(Place place) {
        mPlace = place;
        mPlaceName.setText(place.getName());
        getPhotoForPlace(place.getId());
    }

    // Request photos and metadata for the specified place.
    private void getPhotoForPlace(String placeId) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();
                        mPlaceImage.setVisibility(bitmap == null ? View.GONE : View.VISIBLE);
                        mPlaceImage.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void onSearchClicked(View v) {
        Date suppliedDate = getParsedDate();
        if (suppliedDate == null) {
            Toast.makeText(this, R.string.please_enter_a_valid_date, Toast.LENGTH_LONG).show();
        } else if (!isDateTodayOrAtMostSevenDaysInFuture(suppliedDate))  {
            Toast.makeText(this, R.string.please_enter_a_date_seven_days_in_future, Toast.LENGTH_LONG).show();
        } else if (mPlace == null) {
            Toast.makeText(this, R.string.please_select_place_name, Toast.LENGTH_LONG).show();
        } else {
            fetchWeatherForLocation(mPlace.getLatLng(), suppliedDate);
        }
    }


    private Date getParsedDate() {
        Date date = null;
        //we're only going to accept dates in the format of month/day/year
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(mDate.getText().toString()); //try to parse date from string
        } catch (ParseException e) {
        }
        return date;
    }

    private boolean isDateTodayOrAtMostSevenDaysInFuture(Date date) {
        long msDiff = date.getTime() - System.currentTimeMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
        return 0 <= daysDiff && daysDiff < 7;
    }



    private void fetchWeatherForLocation(LatLng latLng, Date date) {
        //YYYY-MM-dd is the date format accepted by the World Weather Online API
        String dateString = new SimpleDateFormat("YYYY-MM-dd").format(date);
        WeatherRetrofitClient.getInstance().fetchWeather(latLng.latitude, latLng.longitude, dateString, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //convert response body into a string
                    JSONObject responseJSON = new JSONObject(response.body().string());

                    //parsing the weather from the JSON. This could've all been done in one line using method chaining,
                    //but I'm breaking it down into separate lines for clarity.
                    JSONArray weatherJsonArray = responseJSON.optJSONObject("data").optJSONArray("weather");
                    JSONObject weatherJsonForSuppliedDate = weatherJsonArray.optJSONObject(0);
                    JSONArray hourlyWeatherForDateJsonArray = weatherJsonForSuppliedDate.optJSONArray("hourly");

                    //the hourly values come in intervals of 3. The first JSONObject in the array is the weather at midnight,
                    //the second JSONObject in the array is the weather at 3AM, third JSONObject is the weather at 6AM, etc.
                    //so to get the weather for noon, I'd have to get the fifth JSONObject in the array which I can do be
                    //supplying an index of 4, since JSONArrays are zero-indexed.
                    JSONObject weatherForDateAtNoon = hourlyWeatherForDateJsonArray.getJSONObject(4);
                    //get the degrees value from the JSONObject
                    String degrees = weatherForDateAtNoon.getString("tempF");
                    //get the weather description from the JSONObject
                    String weather = weatherForDateAtNoon.getJSONArray("weatherDesc").optJSONObject(0).optString("value");
                    mWeather.setText(getString(R.string.degree_comma_description, degrees, weather));
                    //get the weather icon url
                    String url = weatherForDateAtNoon.getJSONArray("weatherIconUrl").optJSONObject(0).optString("value");
                    mWeatherIcon.setVisibility(url == null ? View.GONE : View.VISIBLE);
                    Picasso.with(MainActivity.this).load(url).fit().centerCrop().into(mWeatherIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("PLACE_PICKER", "An error occurred: " + t);
            }
        });
    }
}
