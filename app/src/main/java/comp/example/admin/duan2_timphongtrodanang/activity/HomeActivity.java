package comp.example.admin.duan2_timphongtrodanang.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import comp.example.admin.duan2_timphongtrodanang.R;
import comp.example.admin.duan2_timphongtrodanang.adapter.KhuvucAdapter;
import comp.example.admin.duan2_timphongtrodanang.adapter.PhongtroAdapter;
import comp.example.admin.duan2_timphongtrodanang.model.Khuvuc;
import comp.example.admin.duan2_timphongtrodanang.model.Phongtro;
import comp.example.admin.duan2_timphongtrodanang.ultil.CheckConnection;
import comp.example.admin.duan2_timphongtrodanang.ultil.Server;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    //khuvuc
    ArrayList<Khuvuc> mangkhuvuc;
    KhuvucAdapter loaispAdater;
    //khuvuc
    int id = 0;
    String tenkhuvuc = "";
    String hinhanhkhuvuc = "";
    //getPT moi nhat
    ArrayList<Phongtro> mangphongtro;
    PhongtroAdapter phongtroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Anhxa ();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuPTMoiNhat();
            CatchOnItemListView();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối" );
            finish();
        }

    }

    private void GetDuLieuPTMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanphongtromoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    int ID = 0;
                    String Tenphongtro ="";
                    Integer Giaphongtro = 0;
                    String Hinhanhphongtro = "";
                    String Motaphongtro = "";
                    String Diachiphongtro = "";
                    String Sdtphongtro = "";
                    int IDphongtro = 0;
                    for (int i = 0 ; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tenphongtro = jsonObject.getString("tenpt");
                            Giaphongtro = jsonObject.getInt("giapt");
                            Hinhanhphongtro = jsonObject.getString("hinhanhpt");
                            Motaphongtro = jsonObject.getString("motapt");
                            Diachiphongtro = jsonObject.getString("diachipt");
                            Sdtphongtro = jsonObject.getString("sdtpt");
                            IDphongtro = jsonObject.getInt("idpt");
                            mangphongtro.add(new Phongtro(ID,Tenphongtro,Giaphongtro,Hinhanhphongtro,Motaphongtro,Diachiphongtro,Sdtphongtro,IDphongtro));
                            phongtroAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.idgiohang:
//                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
//                startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,LienChieuActivity.class);
                            intent.putExtra("idphongtro",mangkhuvuc.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,ThanhKheActivity.class);
                            intent.putExtra("idphongtro",mangkhuvuc.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,CamLeActivity.class);
                            intent.putExtra("idphongtro",mangkhuvuc.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,HaiChauActivity.class);
                            intent.putExtra("idphongtro",mangkhuvuc.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        //thêm sk nút cho navigation
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,SonTraActivity.class);
                            intent.putExtra("idphongtro",mangkhuvuc.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        //thêm sk nút cho navigation
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,NguHanhSonActivity.class);
                            intent.putExtra("idphongtro",mangkhuvuc.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        //thêm sk nút cho navigation
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        //thêm sk nút cho navigation
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 8:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        //thêm sk nút cho navigation
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 9:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        //thêm sk nút cho navigation
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Server.Duongdankhuvuc, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0; i < response.length();i++ ){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenkhuvuc = jsonObject.getString("tenkhuvuc");
                            hinhanhkhuvuc = jsonObject.getString("hinhanhkhuvuc");
                            mangkhuvuc.add(new Khuvuc(id,tenkhuvuc,hinhanhkhuvuc));
                            loaispAdater.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangkhuvuc.add(7,new Khuvuc(0,"Liên hệ","https://hongngochospital.vn/wp-content/themes/flexible/assets/images/icon_phone_01.png"));
                    mangkhuvuc.add(8,new Khuvuc(0,"Thông tin","http://locbinh.com.vn/www/images/icons/iconho-tro-khach-hang.png"));
                    mangkhuvuc.add(9,new Khuvuc(0,"Đăng xuất","https://us.123rf.com/450wm/tmricons/tmricons1707/tmricons170700785/81207443-logout-sign-icon-sign-out-button-export-document-concept.jpg?ver=6"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://3.bp.blogspot.com/-hUAx6m-MKOQ/Vjwdyfd8JnI/AAAAAAAADPY/1sdz_wpyKBs/s1600/thumb745x510.260232.jpg");
        mangquangcao.add("http://www.nhatrosinhvien.com/wp-content/uploads/2016/08/Untitled-3.jpg");
        mangquangcao.add("https://media.chothue247.com/thumb_x624x476/upload/photo/1526998764.jpg");
        mangquangcao.add("https://img2.dothi.net/dothi_svr_1_resize_624_476_3.20180312094048-3143_wm.jpg");
        for (int i = 0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_ngc = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide);
        viewFlipper.setOutAnimation(animation_slide_ngc);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mangkhuvuc = new ArrayList<>();
        mangkhuvuc.add(0,new Khuvuc(0,"Trang chính","http://bietthubiennhatrang.net/wp-content/uploads/2016/03/icon-biet-thu.gif"));
        loaispAdater = new KhuvucAdapter(mangkhuvuc,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdater);

        mangphongtro = new ArrayList<>();
        phongtroAdapter = new PhongtroAdapter(getApplicationContext(),mangphongtro);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(phongtroAdapter);
    }
}
