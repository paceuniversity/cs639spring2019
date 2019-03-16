package com.pace.cs639spring.hw3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SophisticatedLineGraphView mGraphView;
    EditText mDateInput;
    EditText mStudentCountInput;

    SeekBar mSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGraphView = findViewById(R.id.line_graph);
        mDateInput = findViewById(R.id.date);
        mStudentCountInput = findViewById(R.id.count);
        mSeekBar = findViewById(R.id.seek_bar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float radius = 5 + ((((float)progress)/100f) * 5);
                mGraphView.setCircleRadius(radius);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onAddDataClicked(View view) {
        if (!areInputsFilled()) return;


        Date date;
        //we're only going to accept dates in the format of month/day
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        try {
            date = format.parse(mDateInput.getText().toString()); //try to parse date from string
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.please_enter_in_valid_date,
                    Toast.LENGTH_LONG).show();
            return;
        }


        int studentCount;
        try {
            //try to parse student count from the text field
            studentCount = Integer.parseInt(mStudentCountInput.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.please_enter_in_a_valid_number_of_students,
                    Toast.LENGTH_LONG).show();
            return;
        }

        //add date and student count to graph view
        mGraphView.addData(date.getTime(), studentCount);

        //clear input fields so user can enter in new values if they'd like
        mStudentCountInput.setText("");
        mDateInput.setText("");
    }

    public void onClearDataClicked(View v) {
        mGraphView.clearData();
    }

    private boolean areInputsFilled() {
        if (mDateInput.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.date_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (mStudentCountInput.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.please_enter_the_total_amount_of_students_present,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void onHighlightIntegralClicked(View view) {
        mGraphView.highlightIntegral(((CheckBox)view).isChecked());
    }

    public void onShowLinesClicked(View view) {
        mGraphView.showLines(((CheckBox)view).isChecked());
    }
}