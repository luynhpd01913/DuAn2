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

public class LienChieuActivity extends AppCompatActivity {
    Toolbar toolbarLC;
    ListView lvLC;
    LienchieuAdapter lienchieuAdapter;
    ArrayList<Phongtro> mangptLienChieu;

    int idLC = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitadata = false;

    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_chieu);
        Anhxa();
        getIDloaiPhongTro();
        ActionToolbar();
        GetData(page);
        LoadMoreData();
    }

    private void LoadMoreData() {
        lvLC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietPhongActivity.class);
                intent.putExtra("thongtinphongtro",mangptLienChieu.get(i));
                startActivity(intent);
            }
        });
        lvLC.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        String duongdan= Server.DuongdanLienChieu+String.valueOf(Page);
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
                    lvLC.removeFooterView(footerview);
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

                            mangptLienChieu.add(new Phongtro(id,Tenpt,Giapt,Hinhanhpt,Mota,Diachipt,Sdtpt,Idkvpt));
                            lienchieuAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitadata = true;
                    lvLC.removeFooterView(footerview);
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
                param.put("idsanpham",String.valueOf(idLC));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarLC);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLC.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIDloaiPhongTro() {
        idLC = getIntent().getIntExtra("idphongtro",-1);
        Log.d("giatriloaiphongtro",idLC+"");
    }

    private void Anhxa() {
        toolbarLC = (Toolbar) findViewById(R.id.toolbarLienChieu);
        lvLC = (ListView) findViewById(R.id.listviewLienChieu);
        mangptLienChieu = new ArrayList<>();
        lienchieuAdapter = new LienchieuAdapter(getApplicationContext(),mangptLienChieu);
        lvLC.setAdapter(lienchieuAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    public  class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvLC.addFooterView(footerview);
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
