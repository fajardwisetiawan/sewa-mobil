package dzaki.project.sewamobilinvetaris.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dzaki.project.sewamobilinvetaris.DetailApproveAct;
import dzaki.project.sewamobilinvetaris.DetailStatusAct;
import dzaki.project.sewamobilinvetaris.R;
import dzaki.project.sewamobilinvetaris.model.ApproveModel;

import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_ALAMAT_KEBERANGKATAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_ALAMAT_TUJUAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_AREA_POOL;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_AREA_TUJUAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_DURASI_PERJALANAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_HARI_BERANGKAT;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_HARI_PULANG;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_ID_USER;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_JAM_BERANGKAT;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_JAM_PULANG;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_JARAK;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_JENIS_KEPERLUAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_JENIS_PEMESANAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_JUMLAH_PENUMPANG;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_KAWASAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_KETERANGAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_NAMA_ATASAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_NAMA_PENUMPANG;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_NO_TELP_KANTOR;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_PERKIRAAN_BBM;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_TANGGAL_PESAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_USERNAME;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_WITEL;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_ALAMAT_KEBERANGKATANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_ALAMAT_TUJUANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_AREA_POOLS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_AREA_TUJUANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_DURASI_PERJALANANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_HARI_BERANGKATS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_HARI_PULANGS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_ID_USERS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_JAM_BERANGKATS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_JAM_PULANGS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_JARAKS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_JENIS_KEPERLUANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_JENIS_PEMESANANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_JUMLAH_PENUMPANGS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_KAWASANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_KETERANGANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_NAMA_ATASANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_NAMA_PENUMPANGS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_NO_TELP_KANTORS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_PERKIRAAN_BBMS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_STATUSS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_TANGGAL_PESANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_USERNAMES;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_WITELS;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewProcessHolder> {

    Context context;
    private List<ApproveModel> item = new ArrayList<>(); //memanggil modelData



    public StatusAdapter(Context context, List<ApproveModel> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public StatusAdapter.ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_status, parent, false); //memanggil layout list recyclerview
        StatusAdapter.ViewProcessHolder processHolder = new StatusAdapter.ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(StatusAdapter.ViewProcessHolder holder, int position) {

        final ApproveModel data = item.get(position);

        holder.tvUsernameStat.setText(data.getUsername());
        holder.tvTanggalPinjamStat.setText(data.getHari_berangkat());
        holder.tvTanggalKembaliStat.setText(data.getHari_pulang());
        holder.linlayStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailStatusAct.class);
                //TroubleShootingModel clickedItem = mItems.get(position);

                detailIntent.putExtra(EXTRA_USERNAMES, data.getUsername());
                detailIntent.putExtra(EXTRA_ID_USERS, data.getId_user());
                detailIntent.putExtra(EXTRA_TANGGAL_PESANS, data.getTanggal_pesan().toString());
                detailIntent.putExtra(EXTRA_JENIS_KEPERLUANS, data.getJenis_keperluan());
                detailIntent.putExtra(EXTRA_JENIS_PEMESANANS, data.getJenis_pemesanan());
                detailIntent.putExtra(EXTRA_KAWASANS, data.getKawasan());
                detailIntent.putExtra(EXTRA_WITELS, data.getWitel());
                detailIntent.putExtra(EXTRA_AREA_TUJUANS, data.getArea_tujuan());
                detailIntent.putExtra(EXTRA_AREA_POOLS, data.getArea_pool());
                detailIntent.putExtra(EXTRA_ALAMAT_KEBERANGKATANS, data.getAlamat_keberangkatan());
                detailIntent.putExtra(EXTRA_ALAMAT_TUJUANS, data.getAlamat_tujuan());
                detailIntent.putExtra(EXTRA_JARAKS, data.getJarak());
                detailIntent.putExtra(EXTRA_DURASI_PERJALANANS, data.getDurasi_perjalanan());
                detailIntent.putExtra(EXTRA_PERKIRAAN_BBMS, data.getPerkiraan_biaya_bbm());
                detailIntent.putExtra(EXTRA_HARI_BERANGKATS, data.getHari_berangkat());
                detailIntent.putExtra(EXTRA_JAM_BERANGKATS, data.getJam_berangkat());
                detailIntent.putExtra(EXTRA_HARI_PULANGS, data.getHari_pulang());
                detailIntent.putExtra(EXTRA_JAM_PULANGS, data.getJam_pulang());
                detailIntent.putExtra(EXTRA_NO_TELP_KANTORS, data.getNo_telp_kantor());
                detailIntent.putExtra(EXTRA_NAMA_PENUMPANGS, data.getNama_penumpang());
                detailIntent.putExtra(EXTRA_JUMLAH_PENUMPANGS, data.getJumlah_penumpang());
                detailIntent.putExtra(EXTRA_NAMA_ATASANS, data.getNama_atasan());
                detailIntent.putExtra(EXTRA_KETERANGANS, data.getKeterangan());
                detailIntent.putExtra(EXTRA_STATUSS, data.getStatus());
//                detailIntent.putExtra(EXTRA_PEMESANAN_ID, data.getPemesanan_id());
                detailIntent.putExtra("pemesanan_id", data.getPemesanan_id());
                context.startActivity(detailIntent);
            }
        });
        if(data.getStatus().equals("1")){
                holder.linlayDitol.setVisibility(View.GONE);
                holder.linlayDiter.setVisibility(View.GONE);
                holder.linlayDipros.setVisibility(View.VISIBLE);
            }else if(data.getStatus().equals("2")){
                holder.linlayDitol.setVisibility(View.GONE);
                holder.linlayDipros.setVisibility(View.GONE);
                holder.linlayDiter.setVisibility(View.VISIBLE);
            }else{
                holder.linlayDipros.setVisibility(View.GONE);
                holder.linlayDiter.setVisibility(View.GONE);
                holder.linlayDitol.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {
        LinearLayout linlayStatus;
        TextView tvUsernameStat, tvTanggalPinjamStat, tvTanggalKembaliStat;
        LinearLayout linlayDipros, linlayDiter, linlayDitol;

        public ViewProcessHolder(View itemView) {
            super(itemView);
            tvUsernameStat = (TextView) itemView.findViewById(R.id.tvUsernameStat);
            tvTanggalPinjamStat = (TextView) itemView.findViewById(R.id.tvTanggalPinjamStat);
            tvTanggalKembaliStat = (TextView) itemView.findViewById(R.id.tvTanggalKembaliStat);
            linlayStatus = (LinearLayout)itemView.findViewById(R.id.linlayStatus);

            linlayDipros = (LinearLayout) itemView.findViewById(R.id.linlayDipros);
            linlayDiter = (LinearLayout) itemView.findViewById(R.id.linlayDiter);
            linlayDitol = (LinearLayout) itemView.findViewById(R.id.linlayDitol);
//

        }
    }

}
