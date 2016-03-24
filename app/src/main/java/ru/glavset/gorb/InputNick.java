package ru.glavset.gorb;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Roman on 25.01.2016.
 */
public class InputNick extends Activity{
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_nick);


        editText = (EditText)findViewById(R.id.editText);
        editText.setHint(GORB.NickNameDefault);
        final Button okButton;
        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v){
                                           switch (v.getId()){
                                               case R.id.ok_button:
                                                if ((editText.getText().toString().length() != 0) & (editText.getText().toString() != GORB.NickNameDefault)){
                                                    GORB.NickNameString = editText.getText().toString();
                                                    GORB.textView.setText(GORB.NickNameString);
                                                    GORB.textView.setTextColor(Color.BLUE);

                                                    SharedPreferences.Editor editor = GORB.mSettings.edit();
                                                    editor.putString(GORB.APP_PREFERENCES_nickName, GORB.NickNameString);
                                                    editor.apply();
                                                    finish();
                                                }
                                               default:
                                                   break;
                                           }

                                           }
                                       }
        );

    }
}
