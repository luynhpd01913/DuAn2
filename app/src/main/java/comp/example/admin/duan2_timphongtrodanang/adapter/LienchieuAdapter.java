package comp.example.admin.duan2_timphongtrodanang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import comp.example.admin.duan2_timphongtrodanang.R;
import comp.example.admin.duan2_timphongtrodanang.model.Phongtro;

/**
 * Created by Admin on 10/25/2018.
 */

public class LienchieuAdapter extends BaseAdapter {
    Context context;
    ArrayList<Phongtro> arraydienthoai;

    public LienchieuAdapter(Context context, ArrayList<Phongtro> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHoler{
        public TextView txttendienthoai,txtgiadienthoai,txtmotadienthoai;
        public ImageView imgdienthoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;
        if(view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_phongtro_lienchieu, null);
            viewHoler.txttendienthoai = (TextView) view.findViewById(R.id.textviewdienthoai);
            viewHoler.txtgiadienthoai = (TextView) view.findViewById(R.id.textviewgiadienthoai);
            viewHoler.txtmotadienthoai = (TextView) view.findViewById(R.id.textviewmotadienthoai);
            viewHoler.imgdienthoai = (ImageView) view.findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) view.getTag();
        }
        Phongtro phongtro = (Phongtro) getItem(i);
        viewHoler.txttendienthoai.setText(phongtro.getTenphongtro());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtgiadienthoai.setText("Giá : " + decimalFormat.format(phongtro.getGiaphongtro())+ "Đ");
        viewHoler.txtmotadienthoai.setMaxLines(2);
        viewHoler.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtmotadienthoai.setText(phongtro.getDiachiphongtro());
        Picasso.with(context).load(phongtro.getHinhanhphongtro())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHoler.imgdienthoai);
        return view;
    }
}
