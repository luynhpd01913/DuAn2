package comp.example.admin.duan2_timphongtrodanang.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import comp.example.admin.duan2_timphongtrodanang.R;
import comp.example.admin.duan2_timphongtrodanang.adapter.LienchieuAdapter;
import comp.example.admin.duan2_timphongtrodanang.model.Phongtro;
import comp.example.admin.duan2_timphongtrodanang.ultil.CheckConnection;
import comp.example.admin.duan2_timphongtrodanang.ultil.Server;

public class ThanhKheActivity extends AppCompatActivity {
    Toolbar toolbarThanhKhe;
    ListView lvThanhKhe;

    LienchieuAdapter thanhkheAdapter;
    ArrayList<Phongtro> mangptThanhKhe;


    int idTK = 0;
    int page = 2;

    View footerview;
    boolean isLoading = false;
    boolean limitadata = false;

    mHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_khe);
        Anhxa();
        getIDloaiPhongTro();
        ActionToolbar();
        GetData(page);
        LoadMoreData();
    }

    private void LoadMoreData() {
        lvThanhKhe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietPhongActivity.class);
                intent.putExtra("thongtinphongtro",mangptThanhKhe.get(i));
                startActivity(intent);
            }
        });
        lvThanhKhe.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem +VisibleItem ==  TotalItem && TotalItem != 0 && isLoading == false && limitadata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.DuongdanThanhKhe+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenpt = "";
                int Giapt = 0;
                String Hinhanhpt = "";
                String Mota = "";
                String Diachipt = "";
                String Sdtpt = "";
                int Idkvpt = 0;
                if(response != null && response.length() != 2){
                    lvThanhKhe.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenpt = jsonObject.getString("tenpt");
                            Giapt = jsonObject.getInt("giapt");
                            Hinhanhpt = jsonObject.getString("hinhanhpt");
                            Mota = jsonObject.getString("motapt");
                            Diachipt = jsonObject.getString("diachipt");
                            Sdtpt = jsonObject.getString("sdtpt");
                            Idkvpt = jsonObject.getInt("idpt");

                            mangptThanhKhe.add(new Phongtro(id,Tenpt,Giapt,Hinhanhpt,Mota,Diachipt,Sdtpt,Idkvpt));
                            thanhkheAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitadata = true;
                    lvThanhKhe.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idTK));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarThanhKhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThanhKhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIDloaiPhongTro() {
        idTK = getIntent().getIntExtra("idphongtro",-1);
        Log.d("giatriloaiphongtro",idTK+"");
    }

    private void Anhxa() {
        toolbarThanhKhe = findViewById(R.id.toolbarThanhKhe);
        lvThanhKhe = findViewById(R.id.listviewThanhKhe);

        mangptThanhKhe = new ArrayList<>();
        thanhkheAdapter = new LienchieuAdapter(getApplicationContext(),mangptThanhKhe);
        lvThanhKhe.setAdapter(thanhkheAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public  class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvThanhKhe.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
