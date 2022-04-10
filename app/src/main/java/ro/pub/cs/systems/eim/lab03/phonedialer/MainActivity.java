package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private int ids[] = {
            R.id.number_0,
            R.id.number_1,
            R.id.number_2,
            R.id.number_3,
            R.id.number_4,
            R.id.number_5,
            R.id.number_6,
            R.id.number_7,
            R.id.number_8,
            R.id.number_9,
            R.id.star,
            R.id.pound
    };
    private ButtonTapListener buttonTapListener = new ButtonTapListener();
    private DeleteChar deleteChar = new DeleteChar();
    private Call call = new Call();
    private Hangup hangup = new Hangup();
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        phoneNumber = findViewById(R.id.phone_number);
        findViewById(R.id.backspace).setOnClickListener(deleteChar);
        findViewById(R.id.call).setOnClickListener(call);
        findViewById(R.id.hangup).setOnClickListener(hangup);
        for (int i = 0; i < 12; i++) {
            Button button = findViewById(ids[i]);
            button.setOnClickListener(buttonTapListener);
        }
    }

    private class ButtonTapListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumber.setText(phoneNumber.getText().toString() + ((Button)view).getText().toString());
        }
    }

    private class DeleteChar implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            String text = phoneNumber.getText().toString();
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
                phoneNumber.setText(text);
            }
        }
    }

    private class Hangup implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class Call implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
                startActivity(intent);
            }
        }
    }
}