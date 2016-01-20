package cn.plu.testhotfix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView= (TextView) findViewById(R.id.textview);
        textView.setText("result 11111 is "+add(1,1));
        // dd(1,1));
       // textView.setText("result 111111 is "+add(1,1));
    }

    private int add(int num1,int num2){
        return num1+num2;
    }


}
