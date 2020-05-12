package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    private static final String TAG = "Whack-A-Mole"; //final means locked at run time

    private TextView resultTextView;
    private Button holeButton1;
    private Button holeButton2;
    private Button holeButton3;
    private List<Button> holeButtonList = new ArrayList<>();
    private Integer randomLocation;
    private Integer score;

    //Pre-Instantiation object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        holeButton1 = (Button)findViewById(R.id.holeButton1);
        holeButton2 = (Button)findViewById(R.id.holeButton2);
        holeButton3 = (Button)findViewById(R.id.holeButton3);
        holeButtonList.add(holeButton1);
        holeButtonList.add(holeButton2);
        holeButtonList.add(holeButton3);
        Log.v(TAG, "Finished Pre-initialisation."); //verbose filter for easy reading
    }

    //Loaded and usable
    @Override
    protected void onStart() {
        super.onStart();

        setNewMole();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonPressed = (Button) v;
                Log.v(TAG, "Reached");
                switch (holeButtonList.indexOf(buttonPressed)) {
                    case 0:
                        Log.v(TAG, "Button Left Clicked!");
                        break;
                    case 1:
                        Log.v(TAG, "Button Middle Clicked!");
                        break;
                    case 2:
                        Log.v(TAG, "Button Right Clicked!");
                        break;
                    default:
                        Log.v(TAG, "Unknown Button pressed. Found within ButtonList");
                }

                score = Integer.parseInt(resultTextView.getText().toString());
                switch (buttonPressed.getText().toString()) {
                    case "0":
                        Log.v(TAG, "Missed, score deducted!");
                        score -= 1;
                        resultTextView.setText(score.toString());
                        break;
                    case "*":
                        Log.v(TAG, "Hit, score added!");
                        score += 1;
                        resultTextView.setText(score.toString());
                        doCheck(buttonPressed);
                        break;
                    default:
                        Log.v(TAG, "Unknown button pressed, no case for it's text set.");
                }
                resetMole();
                setNewMole();
            }
        };
        holeButton1.setOnClickListener(listener);
        holeButton2.setOnClickListener(listener);
        holeButton3.setOnClickListener(listener);

        Log.v(TAG, "Starting Gui.");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (score > 0 && score % 10 == 0) {
            nextLevelQuery();
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        randomLocation = ran.nextInt(3);
        holeButtonList.get(randomLocation).setText("*");
    }

    public void resetMole()
    {
        holeButtonList.get(randomLocation).setText("0");
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false); //closable without an option
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });

        AlertDialog alert = builder.create();
        Log.v(TAG, "Advance option given to user!");
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent main2Activity = new Intent(this, Main2Activity.class);
        main2Activity.putExtra("Score", score);
        startActivity(main2Activity);
    }

}