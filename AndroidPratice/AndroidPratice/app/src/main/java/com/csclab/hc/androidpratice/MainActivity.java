package com.csclab.hc.androidpratice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Runnable {
    /**
     * Init Variable for Page 1
     **/
    Thread thread;
    private Socket socket;
    private PrintWriter writer;
    private String IP = "";

    EditText inputNumTxt1;
    EditText inputNumTxt2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    TextView getIP;
    TextView text;
    Button okbtn;
    /**
     * Init Variable for Page 2
     **/
    TextView textResult;

    Button return_button;

    /**
     * Init Variable
     **/
    String oper = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Func() for setup page 1 **/
        jumpToStartLayout();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void jumpToStartLayout(){
        setContentView(R.layout.get_ipaddr);

        thread = new Thread(this);

        okbtn = (Button)findViewById(R.id.okbutton);
        getIP = (EditText)findViewById(R.id.input);
        text = (TextView) findViewById(R.id.text);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IP = getIP.getText().toString();
                Log.v("debug", "IP " + IP);
                connect();
                //thread.start();
                jumpToMainLayout();
            }
        });
    }

    /**
     * Function for page 1 setup
     */
    public void jumpToMainLayout() {
        //TODO: Change layout to activity_main
        // HINT: setContentView()
        setContentView(R.layout.activity_main);

        //TODO: Find and bind all elements(4 buttons 2 EditTexts)
        // inputNumTxt1, inputNumTxt2
        // btnAdd, btnSub, btnMult, btnDiv
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        inputNumTxt1 = (EditText) findViewById(R.id.etNum1);
        inputNumTxt2 = (EditText) findViewById(R.id.etNum2);


        //TODO: Set 4 buttons' listener
        // HINT: myButton.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    /**
     * Function for onclick() implement
     */
    @Override
    public void onClick(View v) {
        float num1 = 0; // Store input num 1
        float num2 = 0; // Store input num 2
        float result = 0; // Store result after calculating

        // check if the fields are empty
        if (TextUtils.isEmpty(inputNumTxt1.getText().toString())
                || TextUtils.isEmpty(inputNumTxt2.getText().toString())) {
            return;
        }

        // read EditText and fill variables with numbers
        num1 = Float.parseFloat(inputNumTxt1.getText().toString());
        num2 = Float.parseFloat(inputNumTxt2.getText().toString());

        // defines the button that has been clicked and performs the corresponding operation
        // write operation into oper, we will use it later for output
        //TODO: caculate result
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
            default:
                break;
        }
        // HINT:Using log.d to check your answer is correct before implement page turning
        Log.v("debug", "ANS " + result);
        //TODO: Pass the result String to jumpToResultLayout() and show the result at Result view
        jumpToResultLayout(String.valueOf(num1), String.valueOf(num2), oper, String.valueOf(result));
    }

    /**
     * Function for page 2 (result page) setup
     */
    public void jumpToResultLayout(final String num1, final String num2, final String oper, final String resultStr) {
        setContentView(R.layout.result_page);

        //TODO: Bind return_button and textResult form result view
        // HINT: findViewById()
        // HINT: Remember to change type
        return_button = (Button)  findViewById(R.id.Return);
        textResult = (TextView) findViewById(R.id.TextResult);

        if (textResult != null) {
            //TODO: Set the result text

            textResult.setText(resultStr);
        }

        if (return_button != null) {
            //TODO: prepare button listener for return button
            // HINT:
            return_button.setOnClickListener(new View.OnClickListener(){
                 public void onClick(View v) {
                     writer.println(num1);
                     writer.flush();
                     writer.println(num2);
                     writer.flush();
                     writer.println(oper);
                     writer.flush();
                     writer.println(resultStr);
                     writer.flush();
                     jumpToMainLayout();
                 }
             });

        }
    }

    @Override
    public void run() {
        try {
            socket = new Socket(IP, 8000);
        } catch (IOException e) {
            System.out.println("Fail to connect with server!");
            //e.printStackTrace();
        }

        /*try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public void connect() {//負責與server連線
        ClientThreat clientThreat = new ClientThreat();
        clientThreat.start();

    }

    class ClientThreat extends Thread{//thread 負責接收server的訊息並做出相對應的反應

        public ClientThreat() {
            // TODO Auto-generated constructor stub

        }

        @SuppressWarnings("deprecation")
        public void run(){

            try {
                socket = new Socket(IP, 8000);
                Log.v("debug", "Success " + "Success to connect with server!");
                Log.v("debug", "Success " + "Success to connect with server!");
                Log.v("debug", "Success " + "Success to connect with server!");
            } catch (IOException e) {
                Log.v("debug", "Fail " + "Fail to connect with server!");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {//將reader,writer與socket連線
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                Log.v("debug", "Success " + "Success to connect writer!");
                Log.v("debug", "Success " + "Success to connect writer!");
                Log.v("debug", "Success " + "Success to connect writer!");
            } catch (IOException e) {
                Log.v("debug", "Fail " + "Fail to connect writer!");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }

    }
}
