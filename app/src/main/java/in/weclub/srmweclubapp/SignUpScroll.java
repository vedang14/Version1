package in.weclub.srmweclubapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SignUpScroll extends AppCompatActivity {

    private EditText fName, lName, mobNo, email, pass, conpass;
    private Button reg;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_scroll);
        dh = new DatabaseHelper(this);
        fName = (EditText)findViewById(R.id.fName);
        lName = (EditText)findViewById(R.id.lName);
        mobNo = (EditText)findViewById(R.id.moNum);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        conpass = (EditText)findViewById(R.id.conpass);
        reg = (Button)findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().equals(conpass.getText().toString())) {
                    char[] c = new char[11];
                    fName.getText().toString().getChars(0, 2, c, 0);
                    lName.getText().toString().getChars(0, 2, c, 3);
                    StringBuilder b = new StringBuilder(new String(c));
                    int i = Calendar.getInstance().get(Calendar.YEAR);
                    b.append(i);
                    boolean inserted = dh.insertContact(b.toString(), fName.getText().toString(),
                            lName.getText().toString(), email.getText().toString(), mobNo.getText().toString(),
                            pass.getText().toString());
                    if (inserted) {
                        Toast.makeText(SignUpScroll.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(SignUpScroll.this, LoginActivity.class);
                        startActivity(it);
                    }
                    else
                        Toast.makeText(SignUpScroll.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                   /* String str[] = new String[6];
                    str[0] = b.toString();
                    str[1] = mobNo.getText().toString();
                    str[2] = pass.getText().toString();
                    str[3] = fName.getText().toString();
                    str[4] = lName.getText().toString();
                    str[5] = email.getText().toString();
                    File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SRMWEClub/data.dat");
                    Save(f,str);
                    Intent it = new Intent(SignUpScroll.this, Profile.class);
                    startActivity(it);*/
                }
                else
                    Toast.makeText(SignUpScroll.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static void Save(File file, String[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length; i++) {
                    //fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
