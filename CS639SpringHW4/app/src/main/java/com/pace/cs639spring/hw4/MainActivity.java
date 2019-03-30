package com.pace.cs639spring.hw4;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CircleAnimationView mExampleView; //view to be used for our example

    //list and adapter
    ListView mListView;
    CircleAnimationAdapter mAdapter;

    EditText mRadius;
    EditText mSpeed;
    View mSelectedColorView;
    int mSelectedColor = -1;

    View mCreateCircleContainer;
    View mUpdateSpeedContainer;

    TextView mSpeedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExampleView = findViewById(R.id.example_circle_animation_view);
        mExampleView.start(); //start the example view animation
        mListView = findViewById(android.R.id.list);

        //attach a recyclerlistener to our ListView so that every time a row is about to get recycled
        //because the user is scrolling it off screen, we stop the CircleAnimationView from animating
        //This will prevent the CircleAnimationView from further updating the position of the circle.
        mListView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                CircleAnimationAdapter.ViewHolder viewHolder = (CircleAnimationAdapter.ViewHolder) view.getTag();
                viewHolder.mCircleAnimationView.stop();
            }
        });
        mAdapter = new CircleAnimationAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //update the selected position of the adapter. if this position is already selected, it will
                //deselect it, else it will mark this position as selected.
                mAdapter.setSelectedPosition(position);
                //toggle between the Circle Creation Interface & the Speed Control Interface based on whether or
                //not a list row is selected
                mCreateCircleContainer.setVisibility(mAdapter.isPositionSelected() ? View.GONE : View.VISIBLE);
                mUpdateSpeedContainer.setVisibility(mAdapter.isPositionSelected() ? View.VISIBLE : View.GONE);
                //if a position is selected, then it means we should show the circle speed interface
                //update the mSpeedValue TextView so that it's showing the circle's current speed.
                if (mAdapter.isPositionSelected()) {
                    Circle circle = (Circle) mAdapter.getItem(position);
                    mSpeedValue.setText(String.valueOf(circle.getSpeed()));
                }
            }
        });

        mRadius = findViewById(R.id.radius);
        mSpeed = findViewById(R.id.speed);
        mCreateCircleContainer = findViewById(R.id.create_content_container);
        mUpdateSpeedContainer = findViewById(R.id.update_speed_container);
        mSpeedValue = findViewById(R.id.speed_value);
    }

    public void onAddDataClicked(View view) {
        //radius + speed cannot be empty. color must be selected as well. Perform validation checks.
        String radius = mRadius.getText().toString();
        String speed = mSpeed.getText().toString();
        if (radius.isEmpty()) {
            Toast.makeText(this, R.string.radius_cannot_be_empty, Toast.LENGTH_LONG).show();
        } else if (speed.isEmpty()) {
            Toast.makeText(this, R.string.speed_cannot_be_empty, Toast.LENGTH_LONG).show();
        } else if (mSelectedColor == -1) {
            Toast.makeText(this, R.string.please_select_a_color, Toast.LENGTH_LONG).show();
        } else if (!radius.matches("\\d+") || Integer.parseInt(radius) <= 0) {
            Toast.makeText(this, R.string.please_enter_valid_radius, Toast.LENGTH_LONG).show();
        } else if (!speed.matches("\\d+") || Integer.parseInt(speed) <= 0) {
            Toast.makeText(this, R.string.please_enter_valid_speed, Toast.LENGTH_LONG).show();
        } else {
            //once everything is validated. create circle and add it to the adapter
            Circle circle = new Circle(Integer.parseInt(radius), mSelectedColor, Integer.parseInt(speed));
            mAdapter.addCircle(circle);
            //reset edittexts & colors
            mSpeed.setText("");
            mRadius.setText("");
            mSelectedColor = -1;
            mSelectedColorView.setSelected(false);
            mSelectedColorView = null;
        }


    }

    public void onColorClicked(View view) {
        int color = ((ColorDrawable)((ImageView)view).getDrawable()).getColor();
        //if the color we've selected is already selected, de-select it
        if (view == mSelectedColorView) {
            mSelectedColorView.setSelected(false);
            mSelectedColor = -1;
            mSelectedColorView = null;
        } else {
            //if a color other than the one we've selected was selected, the unselect it and select a new color
            if (mSelectedColorView != null) mSelectedColorView.setSelected(false);
            mSelectedColor = color;
            mSelectedColorView = view;
            mSelectedColorView.setSelected(true);
        }
    }

    public void onMinusClicked(View view) {
        //get current speed from mSpeedValue textView
        int currentSpeed = Integer.parseInt(mSpeedValue.getText().toString());
        //speed shouldn't be less than 1.
        if (currentSpeed == 1) {
            Toast.makeText(this, R.string.speed_cannot_be_less_than_one, Toast.LENGTH_LONG).show();
            return;
        }
        currentSpeed--;


        //update the speed of the circle at the selected position
        ((Circle) mAdapter.getItem(mAdapter.getSelectedPosition())).setSpeed(currentSpeed);
        //update the textview so that it reflects the correct value
        mSpeedValue.setText(String.valueOf(currentSpeed));
    }

    public void onPlusClicked(View view) {
        //get current speed from mSpeedValue textView
        int currentSpeed = Integer.parseInt(mSpeedValue.getText().toString());
        currentSpeed++;

        //update the speed of the circle at the selected position
        ((Circle) mAdapter.getItem(mAdapter.getSelectedPosition())).setSpeed(currentSpeed);
        //update the textview so that it reflects the correct value
        mSpeedValue.setText(String.valueOf(currentSpeed));
    }
}
