package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    private CountDownTimer countDownTimer;
    private TextView resultTextView;
    private static final String TAG = "Whack-A-Mole!";
    private Integer randomLocation;
    private Integer score;

    private final List<Button> holeButtonList = new ArrayList<>();

    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
            R.id.holeButton1, R.id.holeButton2, R.id.holeButton3, R.id.holeButton4, R.id.holeButton5,
            R.id.holeButton6, R.id.holeButton7, R.id.holeButton8, R.id.holeButton9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Get Intent
        Intent receivingEnd = getIntent();
        score = receivingEnd.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + score.toString());

        //Set current score value
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        resultTextView.setText(score.toString());

        //Initialise listener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonPressed = (Button) v;
                Log.v(TAG, "Reached");
                doCheck(buttonPressed);
                resetMole();
                setNewMole();
                placeMoleTimer(); //Reset timer.
            }
        };

        //Initialise buttons
        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button holeButton = (Button) findViewById(id);
            holeButton.setOnClickListener(listener);
            holeButtonList.add(holeButton);
        }

        //Once all initialized, start timer
        readyTimer();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
    }

    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        switch (checkButton.getText().toString()) {
            case "0":
                Log.v(TAG, "Missed, score deducted!");
                score -= 1;
                resultTextView.setText(score.toString());
                break;
            case "*":
                Log.v(TAG, "Hit, score added!");
                score += 1;
                resultTextView.setText(score.toString());
                break;
            default:
                Log.v(TAG, "Unknown button pressed, no case for it's text set.");
        }
    }

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        countDownTimer = new CountDownTimer(10*1000, 1000) {
            @Override
            public void onTick(long ms) {
                //Calculate time remaining
                Long timeRemaining = ms/1000;
                Log.v(TAG, "Ready CountDown!" + ms/ 1000);
                String msg = "Get ready in " + timeRemaining.toString() + " seconds!";

                //Toast is a small pop up which length is the same as the text length.
                Toast countDownMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                countDownMsg.show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                countDownTimer.cancel();
            }
        };

        countDownTimer.start();
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long ms) {
                //Calculate time remaining
                Long timeRemaining = ms/1000;
                Log.v(TAG, "New Mole Location!");
                resetMole();
                setNewMole();
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };

        countDownTimer.start();
    }

    public void resetMole()
    {
        holeButtonList.get(randomLocation).setText("0");
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        randomLocation = ran.nextInt(9);
        holeButtonList.get(randomLocation).setText("*");
    }
}

