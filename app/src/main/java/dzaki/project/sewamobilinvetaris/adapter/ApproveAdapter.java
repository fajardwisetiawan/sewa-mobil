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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dzaki.project.sewamobilinvetaris.DetailApproveAct;
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
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_PEMESANAN_ID;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_PERKIRAAN_BBM;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_TANGGAL_PESAN;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_USERNAME;
import static dzaki.project.sewamobilinvetaris.ApproveAct.EXTRA_WITEL;

public class ApproveAdapter extends RecyclerView.Adapter<ApproveAdapter.ViewProcessHolder> {

    KetikaTerimaCallback ketikaTerimaCallback;
    KetikaTolakCallback ketikaTolakCallback;
    Context context;
    private List<ApproveModel> item = new ArrayList<>(); //memanggil modelData



    public ApproveAdapter(Context context, List<ApproveModel> item, KetikaTerimaCallback ketikaTerimaCallback, KetikaTolakCallback ketikaTolakCallback) {
        this.context = context;
        this.item = item;
        this.ketikaTolakCallback = ketikaTolakCallback;
        this.ketikaTerimaCallback =ketikaTerimaCallback;
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_approve, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(ViewProcessHolder holder, int position) {

        final ApproveModel data = item.get(position);

        holder.tvUsername.setText(data.getUsername());
        holder.tvTanggalPinjam.setText(data.getHari_berangkat());
        holder.tvTanggalKembali.setText(data.getHari_pulang());
        holder.linlayAprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailApproveAct.class);
                //TroubleShootingModel clickedItem = mItems.get(position);

                detailIntent.putExtra(EXTRA_USERNAME, data.getUsername());
                detailIntent.putExtra(EXTRA_ID_USER, data.getId_user());
                detailIntent.putExtra(EXTRA_TANGGAL_PESAN, data.getTanggal_pesan().toString());
                detailIntent.putExtra(EXTRA_JENIS_KEPERLUAN, data.getJenis_keperluan());
                detailIntent.putExtra(EXTRA_JENIS_PEMESANAN, data.getJenis_pemesanan());
                detailIntent.putExtra(EXTRA_KAWASAN, data.getKawasan());
                detailIntent.putExtra(EXTRA_WITEL, data.getWitel());
                detailIntent.putExtra(EXTRA_AREA_TUJUAN, data.getArea_tujuan());
                detailIntent.putExtra(EXTRA_AREA_POOL, data.getArea_pool());
                detailIntent.putExtra(EXTRA_ALAMAT_KEBERANGKATAN, data.getAlamat_keberangkatan());
                detailIntent.putExtra(EXTRA_ALAMAT_TUJUAN, data.getAlamat_tujuan());
                detailIntent.putExtra(EXTRA_JARAK, data.getJarak());
                detailIntent.putExtra(EXTRA_DURASI_PERJALANAN, data.getDurasi_perjalanan());
                detailIntent.putExtra(EXTRA_PERKIRAAN_BBM, data.getPerkiraan_biaya_bbm());
                detailIntent.putExtra(EXTRA_HARI_BERANGKAT, data.getHari_berangkat());
                detailIntent.putExtra(EXTRA_JAM_BERANGKAT, data.getJam_berangkat());
                detailIntent.putExtra(EXTRA_HARI_PULANG, data.getHari_pulang());
                detailIntent.putExtra(EXTRA_JAM_PULANG, data.getJam_pulang());
                detailIntent.putExtra(EXTRA_NO_TELP_KANTOR, data.getNo_telp_kantor());
                detailIntent.putExtra(EXTRA_NAMA_PENUMPANG, data.getNama_penumpang());
                detailIntent.putExtra(EXTRA_JUMLAH_PENUMPANG, data.getJumlah_penumpang());
                detailIntent.putExtra(EXTRA_NAMA_ATASAN, data.getNama_atasan());
                detailIntent.putExtra(EXTRA_KETERANGAN, data.getKeterangan());
//                detailIntent.putExtra(EXTRA_PEMESANAN_ID, data.getPemesanan_id());
                detailIntent.putExtra("pemesanan_id", data.getPemesanan_id());
                context.startActivity(detailIntent);
            }
        });

        holder.btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Apa Anda yakin untuk menerima penyewaan ini?");

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        ketikaTerimaCallback.TerimaClicked(data.getPemesanan_id());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Apa Anda yakin untuk menolak penyewaan ini?");

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        ketikaTolakCallback.TolakClicked(data.getPemesanan_id());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {
        LinearLayout linlayAprove, linlayStatusDiproses, linlayStatusDiterima, linlayStatusDitolak;
        TextView tvUsername, tvTanggalPinjam, tvTanggalKembali;
        Button btnYa, btnTidak;

        public ViewProcessHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvTanggalPinjam = (TextView) itemView.findViewById(R.id.tvTanggalPinjam);
            tvTanggalKembali = (TextView) itemView.findViewById(R.id.tvTanggalKembali);
            linlayAprove = (LinearLayout)itemView.findViewById(R.id.linlayAprove);
            btnYa = (Button) itemView.findViewById(R.id.btnYa);
            btnTidak = (Button) itemView.findViewById(R.id.btnTidak);

            linlayStatusDiproses = (LinearLayout)itemView.findViewById(R.id.linlayStatusDiproses);
            linlayStatusDiterima = (LinearLayout)itemView.findViewById(R.id.linlayStatusDiterima);
            linlayStatusDitolak = (LinearLayout)itemView.findViewById(R.id.linlayStatusDitolak);
        }
    }

    public interface KetikaTerimaCallback {
        public void TerimaClicked(String url);
    }

    public interface KetikaTolakCallback {
        public void TolakClicked(String url);
    }
}
