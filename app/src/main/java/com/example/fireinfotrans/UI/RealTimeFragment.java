package com.example.fireinfotrans.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fireinfotrans.MyApplication;
import com.example.fireinfotrans.PowerActivity;
import com.example.fireinfotrans.PressureActivity;
import com.example.fireinfotrans.R;
import com.example.fireinfotrans.SwitchActivity;
import com.example.fireinfotrans.db.FireInfoTransDB;
import com.example.fireinfotrans.model.CabinetNode;
import com.example.fireinfotrans.model.PowerNode;
import com.example.fireinfotrans.model.PressureNode;
import com.example.fireinfotrans.model.RealCabinet;
import com.example.fireinfotrans.model.RealPower;
import com.example.fireinfotrans.model.RealPressure;
import com.example.fireinfotrans.model.RealSwitch;
import com.example.fireinfotrans.model.SwitchNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * Created by charlesyoung on 2016/4/22.
 */
public class RealTimeFragment extends Fragment implements View.OnClickListener{

    private Button mButtonPressureNode;
    private Button mButtonPowerNode;
    private Button mButtonSwitchNode;
    private Button mButtonCabinetData;

    private TextView text;

    Thread serverThread = null;
    private ServerSocket mServerSocket;
    private static final int PORT = 6000;
    Handler updateConnversationHandler;
    private FireInfoTransDB mFireInfoTransDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateConnversationHandler = new Handler();
        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();
        //建立数据库
        mFireInfoTransDB = FireInfoTransDB.getInstance(getActivity());
        mFireInfoTransDB.saveNodeInfo(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            mServerSocket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab01,container,false);

        mButtonPressureNode = (Button) v.findViewById(R.id.button_pressure_node);
        mButtonPowerNode = (Button) v.findViewById(R.id.button_power_node);
        mButtonSwitchNode = (Button) v.findViewById(R.id.button_switch_node);
        mButtonCabinetData = (Button) v.findViewById(R.id.button_cabinet_data);

        text = (TextView) v.findViewById(R.id.text);

        mButtonPressureNode.setOnClickListener(this);
        mButtonPowerNode.setOnClickListener(this);
        mButtonSwitchNode.setOnClickListener(this);
        mButtonCabinetData.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_pressure_node:
                Intent intent1 = new Intent(getActivity(), PressureActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_power_node:
                Intent intent2 = new Intent(getActivity(), PowerActivity.class);
                startActivity(intent2);
                break;
            case R.id.button_switch_node:
                Intent intent3 = new Intent(getActivity(), SwitchActivity.class);
                startActivity(intent3);
                break;
            case R.id.button_cabinet_data:
                Toast.makeText(getActivity(), "无详情", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //服务器客户端
    public class ClientThread implements Runnable{

        private static final String SERVER_IP = "222.244.147.133";
        private static final int SERVER_PORT = 19888;
        private Socket mSocket;
        private String mContent;
        public ClientThread(String content) {
            mContent = content;
        }

        @Override
        public void run() {
            try {
                mSocket = new Socket(SERVER_IP, SERVER_PORT);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        mSocket.getOutputStream()));
                writer.write(mContent);
                writer.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    //节点服务器端
    public class ServerThread implements Runnable {

        @Override
        public void run() {

            Socket socket = null;
            try {
                mServerSocket = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    socket = mServerSocket.accept();//开启监听
                    CommunicationThread communicationThread =
                            new CommunicationThread(socket);
                    new Thread(communicationThread).start();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                InputStream recvStream;
                recvStream = this.clientSocket.getInputStream();
                this.input = new BufferedReader(new InputStreamReader(recvStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {
                    //处理服务器收到的数据
                    // String content = input.readLine();

                    //StringBuilder stringBuilder = new StringBuilder();
                   // stringBuilder.append("");
                    String line = input.readLine();
                   // while (line != null) {
                    //    stringBuilder.append(line);
                    //}

                   // String content = stringBuilder.toString();

                    String content = line;
                    updateConnversationHandler.post(new updateUIThread(content));

                    //Thread clientThread = new Thread(new ClientThread(content));
                    //clientThread.start();

                    //Log.d("content",content);
                    if(content != null){
                        checkReadData(content);
                    }
                    //stringBuilder.delete(0,stringBuilder.length());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public class updateUIThread implements Runnable{
        private String msg;

        public updateUIThread(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            text.setText(text.getText().toString()+"Client Says: "
                    + msg +"\n");
        }
    }
    public void checkReadData(String content) {

        int array_start = 0,
                array_end = 0;
        char[] recvArray = content.toCharArray();
        char[] processArray = new char[62];
        int length = recvArray.length;
        for (int i = 0; i < length; i++) {
            if (((recvArray[i] == 'a') && (recvArray[i + 1] == 'a') && (recvArray[i + 2] == 'a')) || ((recvArray[i] == '4') && (recvArray[i + 1] == '0'))) {
                array_start = i;
            }
            if ((recvArray[i] == '0') && (recvArray[i + 1] == '0') && (recvArray[i + 2] == '2') && (recvArray[i + 3] == '3')) {
                array_end = i;

                if ((array_end - array_start) == 58) {
                    for (int j = 0; j < 62; j++) {
                        processArray[j] = recvArray[array_start + j];
                    }
                    dataprocess(processArray);
                }
            }
        }
    }

    public void dataprocess(char[] dataArray) {

        int pr_id = 0;
        double pr_data = 0;
        int pr_type = 0;
        int pr_state = 1;
        //开关量数据
        int s_id, s_state, s_type = 0;
        //电源数据
        int temp;
        int w_id1, w_a1, w_b1, w_c1, w_state1 = 0;
        int w_id2, w_a2, w_b2, w_c2, w_state2 = 0;
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sDateFormat.format(new java.util.Date());
        switch (dataArray[3]) {
            case '1':               //压力节点信息
                //数据解析
                pr_id = 10010000 + 100 * ((dataArray[5] - 48) * 10 + (dataArray[7] - 48)) + (dataArray[11] - 48) * 10 + dataArray[13] - 48;
                pr_data = dataArray[17] - 48 + (dataArray[19] - 48) * 0.1 + (dataArray[21] - 48) * 0.01 + (dataArray[23] - 48) * 0.001;
                pr_type = 0;
                //数据判断(若需要根据不同标准判断，则需要建立数组以表示不同节点的type，并建立不同type的不同标准)
                if (pr_data < 1.5) {
                    pr_state = 1;  //数据正常
                } else {
                    pr_state = 0;  //数据异常
                }
                //数据存入历史表
                PressureNode pressureNode = new PressureNode();
                pressureNode.setPressureId(pr_id);
                pressureNode.setPressureData(pr_data);
                pressureNode.setPressureType(pr_type);
                pressureNode.setPressureDatetime(time);
                pressureNode.setPressureState(pr_state);
                mFireInfoTransDB.savePressureNode(pressureNode);
                //数据存入实时表
                RealPressure realPressure = new RealPressure();
                realPressure.setPressureId(pr_id);
                realPressure.setPressureData(pr_data);
                realPressure.setPressureType(pr_type);
                realPressure.setPressureDatetime(time);
                realPressure.setPressureState(pr_state);
                mFireInfoTransDB.updatePressureNode(realPressure);


                break;
            case '2':              //开关量节点信息（暂时还没有）
                //数据解析
                s_id = 30010000 + 100 * ((dataArray[5] - 48) * 10 + (dataArray[7] - 48)) + (dataArray[11] - 48) * 10 + dataArray[13] - 48;
                s_state = (dataArray[17] - 48) * 100 + (dataArray[19] - 48) * 10 + (dataArray[21] - 48);
                s_type = 0;
                //数据存入历史表
                SwitchNode switchNode = new SwitchNode();
                switchNode.setSwitchId(s_id);
                switchNode.setSwitchState(s_state);
                switchNode.setSwitchType(s_type);
                switchNode.setSwitchDatetime(time);
                mFireInfoTransDB.saveSwitchNode(switchNode);
                //数据存入实时表
                RealSwitch realSwitch = new RealSwitch();
                realSwitch.setSwitchId(s_id);
                realSwitch.setSwitchState(s_state);
                realSwitch.setSwitchType(s_type);
                realSwitch.setSwitchDatetime(time);
                mFireInfoTransDB.updateSwitchNode(realSwitch);
                break;
            case '3':            //电源节点信息
                //数据解析
                temp = 40010000 + 100 * ((dataArray[5] - 48) * 10 + (dataArray[7] - 48));
                w_id1 = temp + (dataArray[11] - 48) * 10 + dataArray[13] - 48;
                w_id2 = temp + (dataArray[15] - 48) * 10 + dataArray[17] - 48;
                w_a1 = (dataArray[21] - 48) * 100 + (dataArray[23] - 48) * 10 + (dataArray[25] - 48);
                w_b1 = (dataArray[27] - 48) * 100 + (dataArray[29] - 48) * 10 + (dataArray[31] - 48);
                w_c1 = (dataArray[33] - 48) * 100 + (dataArray[35] - 48) * 10 + (dataArray[37] - 48);
                w_a2 = (dataArray[39] - 48) * 100 + (dataArray[41] - 48) * 10 + (dataArray[43] - 48);
                w_b2 = (dataArray[45] - 48) * 100 + (dataArray[47] - 48) * 10 + (dataArray[49] - 48);
                w_c2 = (dataArray[51] - 48) * 100 + (dataArray[53] - 48) * 10 + (dataArray[55] - 48);
                //数据判断，判断标准 220正负30
                if ((Math.abs(w_a1 - 220) < 30) && (Math.abs(w_b1 - 220) < 30) && (Math.abs(w_c1 - 220) < 30)) {
                    w_state1 = 1;        //电压1正常
                } else {
                    w_state1 = 0;       //电压1异常
                }

                if ((Math.abs(w_a2 - 220) < 30) && (Math.abs(w_b2 - 220) < 30) && (Math.abs(w_c2 - 220) < 30)) {
                    w_state2 = 1;        //电压2正常
                } else {
                    w_state2 = 0;       //电压2异常
                }
                //数据存入历史表
                PowerNode powerNode = new PowerNode();
                powerNode.setPowerId(w_id1);
                powerNode.setPowerA(w_a1);
                powerNode.setPowerB(w_b1);
                powerNode.setPowerC(w_c1);
                powerNode.setPowerDateTime(time);
                powerNode.setPowerState(w_state1);
                mFireInfoTransDB.savePowerNode(powerNode);

                powerNode.setPowerId(w_id2);
                powerNode.setPowerA(w_a2);
                powerNode.setPowerB(w_b2);
                powerNode.setPowerC(w_c2);
                powerNode.setPowerDateTime(time);
                powerNode.setPowerState(w_state2);
                mFireInfoTransDB.savePowerNode(powerNode);
                Log.d("db",powerNode.getPowerDateTime());


                //数据存入实时表
                RealPower realPower = new RealPower();
                realPower.setPowerId(w_id1);
                realPower.setPowerA(w_a1);
                realPower.setPowerB(w_b1);
                realPower.setPowerC(w_c1);
                realPower.setPowerState(w_state1);
                realPower.setPowerDateTime(time);
                mFireInfoTransDB.updatePowerNode(realPower);
                Log.d("dbr",realPower.getPowerDateTime());

                realPower.setPowerId(w_id2);
                realPower.setPowerA(w_a2);
                realPower.setPowerB(w_b2);
                realPower.setPowerC(w_c2);
                realPower.setPowerState(w_state2);
                realPower.setPowerDateTime(time);
                mFireInfoTransDB.updatePowerNode(realPower);

                break;
            default:
                break;
        }

        //处理消防控制柜数据，历史数据表存历史数据；实时表存数据，复位时进行清除
        if ((dataArray[0] == '4') && (dataArray[1] == '0')) {
            int c_id = 20100000 + ((dataArray[11] - 48) * 10 + (dataArray[13] - 48)) * 1000 + (dataArray[17] - 48) * 100 + (dataArray[19] - 48) * 10 + dataArray[21] - 48;

            int type = (dataArray[2] - 48) * 10 + dataArray[3] - 48;
            int bus_type = (dataArray[5] - 48) * 10 + dataArray[7] - 48;
            String c_type = "",
                    c_bus_type = "";

            switch (type) {
                case 42:
                    c_type = "Fault";
                    break;
                case 44:
                    c_type = "Activate";
                    break;
                case 46:
                    c_type = "Alert";
                    break;
                case 62:
                    c_type = "Fault Removed";
                    break;
                default:
                    break;
            }
            if (bus_type == 00) {
                c_bus_type = "CAN";
            } else if (bus_type == 16) {
                c_bus_type = "Switch";
            }

            //数据历史表存储
            CabinetNode cabinetNode = new CabinetNode();
            cabinetNode.setCabinetId(c_id);
            cabinetNode.setCabinetBusType(c_bus_type);
            cabinetNode.setCabinetDatetime(time);
            cabinetNode.setCabinetType(c_type);
            mFireInfoTransDB.saveCabinetNode(cabinetNode);

            //数据实时表存储(复位即清除)
            RealCabinet realCabinet = new RealCabinet();
            realCabinet.setCabinetId(c_id);
            realCabinet.setCabinetBusType(c_bus_type);
            realCabinet.setCabinetType(c_type);
            realCabinet.setCabinetDatetime(time);
            mFireInfoTransDB.updateCabinetNode(realCabinet);

        }

    }

}
