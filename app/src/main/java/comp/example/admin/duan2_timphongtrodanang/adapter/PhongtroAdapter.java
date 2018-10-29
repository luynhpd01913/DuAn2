package comp.example.admin.duan2_timphongtrodanang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PhongtroAdapter extends RecyclerView.Adapter<PhongtroAdapter.ItemHolder> {
    Context context;
    ArrayList<Phongtro> arrayphongtro;

    public PhongtroAdapter(Context context, ArrayList<Phongtro> arrayphongtro) {
        this.context = context;
        this.arrayphongtro = arrayphongtro;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Phongtro sanpham = arrayphongtro.get(position);
        holder.txttensanpham.setText(sanpham.getTenphongtro());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(sanpham.getGiaphongtro()) + "Đ");
        Picasso.with(context).load(sanpham.getHinhanhphongtro())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);

    }

    @Override
    public int getItemCount() {
        return arrayphongtro.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhsanpham;
        public TextView txttensanpham, txtgiasanpham;

        public ItemHolder(View itemView) {
            super(itemView);
            imghinhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context, ChiTietSanPham.class);
//                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    CheckConnection.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
//                    context.startActivity(intent);
                }
            });

        }
    }
}
