package comp.example.admin.duan2_timphongtrodanang.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import comp.example.admin.duan2_timphongtrodanang.R;
import comp.example.admin.duan2_timphongtrodanang.model.Phongtro;

public class ChiTietPhongActivity extends AppCompatActivity {
    Toolbar toolbarPhongChitiet;
    Button btnLuu;
    ImageView imgViewHinhanhPhongChitiet;
    TextView tvGiachitiet, tvDiachiChitiet, tvMotaChitiet, tvTenchuChitiet, tvSdtLienheChitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phong);
        Anhxa();
        ActionToolbar();
        GetInformation();

        tvSdtLienheChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = tvSdtLienheChitiet.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
    }

    private void GetInformation() {
        int id = 0;
        String TenChitiet = "";
        int GiaChitiet = 0;
        String HinhanhChitiet = "";
        String MotaChitiet = "";
        String DiachiChitiet = "";
        String SdtChitiet = "";
        int Idkvpt = 0;
        Phongtro phongtro = (Phongtro) getIntent().getSerializableExtra("thongtinphongtro");
        id = phongtro.getID();
        TenChitiet = phongtro.getTenphongtro();
        GiaChitiet = phongtro.getGiaphongtro();
        HinhanhChitiet = phongtro.getHinhanhphongtro();
        MotaChitiet = phongtro.getMotaphongtro();
        DiachiChitiet = phongtro.getDiachiphongtro();
        SdtChitiet = phongtro.getSdtphongtrong();
        Idkvpt = phongtro.getIDPhongtro();

        tvTenchuChitiet.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiachitiet.setText("" + decimalFormat.format(GiaChitiet) + " Đ");
        tvSdtLienheChitiet.setText(SdtChitiet);
        tvDiachiChitiet.setText(DiachiChitiet);
        tvMotaChitiet.setText(MotaChitiet);
        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgViewHinhanhPhongChitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarPhongChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPhongChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarPhongChitiet = findViewById(R.id.toolbarChitiet);
        btnLuu = findViewById(R.id.btnLuu);
        imgViewHinhanhPhongChitiet = findViewById(R.id.imgViewChiTietPhong);
        tvDiachiChitiet = findViewById(R.id.tvDiaChiphongChiTiet);
        tvGiachitiet = findViewById(R.id.tvGiaphongChiTiet);
        tvMotaChitiet = findViewById(R.id.tvMotaChitiet);
        tvTenchuChitiet = findViewById(R.id.tvTenphongChiTiet);
        tvSdtLienheChitiet = findViewById(R.id.tvSdtphongChiTiet);
    }
}
