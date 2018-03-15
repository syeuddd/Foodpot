package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    ArrayList<Step> currentStepList;
    int counter = 0;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        description = findViewById(R.id.stepDescription);
        final Button nextButton = findViewById(R.id.nextStepButton);
        final Button previousButton = findViewById(R.id.previousStepButton);


        Intent stepDetailsIntent = getIntent();

        currentStepList = stepDetailsIntent.getParcelableArrayListExtra("stepDetails");
        counter = stepDetailsIntent.getIntExtra("currentPosition",0);

        if (currentStepList != null){
            Step currentStep = currentStepList.get(counter);
            description.setText(currentStep.getDescription());
        }

        if (counter==0){
            previousButton.setVisibility(View.INVISIBLE);
        }

        if ((counter+1)==currentStepList.size()){

            nextButton.setVisibility(View.INVISIBLE);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter >= 0 && counter <= currentStepList.size()) {
                    counter++;
                    if (counter < currentStepList.size()) {
                        if (previousButton.getVisibility()==View.INVISIBLE){
                            previousButton.setVisibility(View.VISIBLE);
                        }
                        StepDetailActivity.this.description.setText(currentStepList.get(counter).getDescription());
                    } else {
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter > 0 && counter <= currentStepList.size()) {
                    counter--;

                    if (counter==0){
                        previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (counter < currentStepList.size()) {
                        StepDetailActivity.this.description.setText(currentStepList.get(counter).getDescription());
                        if (nextButton.getVisibility()==View.INVISIBLE){
                            nextButton.setVisibility(View.VISIBLE);
                        }
                    }else {
                        previousButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }
}
