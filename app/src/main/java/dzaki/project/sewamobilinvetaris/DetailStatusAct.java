package dzaki.project.sewamobilinvetaris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import dzaki.project.sewamobilinvetaris.util.Server;

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
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_PEMESANAN_IDS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_PERKIRAAN_BBMS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_STATUSS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_TANGGAL_PESANS;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_USERNAMES;
import static dzaki.project.sewamobilinvetaris.StatusAct.EXTRA_WITELS;

public class DetailStatusAct extends AppCompatActivity {

    int success;

    private static final String TAG = DetailStatusAct.class.getSimpleName();
    private String url = Server.URL + "status1.php";
    private String urli = Server.URL + "status2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String pemesanan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status);
        Intent intent = getIntent();

        if (intent.getExtras() == null) {
            finish();
        } else {
            TextView tvUsernameD = findViewById(R.id.tvUsernameD);
            TextView tvTanggalPesanD = findViewById(R.id.tvTanggalPesanD);
            TextView tvJenisKeperluanD = findViewById(R.id.tvJenisKeperluanD);
            TextView tvJenisPemesananD = findViewById(R.id.tvJenisPemesananD);
            TextView tvKawasanD = findViewById(R.id.tvKawasanD);
            TextView tvWitelD = findViewById(R.id.tvWitelD);
            TextView tvAreaPoolD = findViewById(R.id.tvAreaPoolD);
            TextView tvAreaTujuanD = findViewById(R.id.tvAreaTujuanD);
            TextView tvAlamatKeberangkatanD = findViewById(R.id.tvAlamatKeberangkatanD);
            TextView tvAlamatTujuanD = findViewById(R.id.tvAlamatTujuanD);
            TextView tvJarakTempuhD = findViewById(R.id.tvJarakTempuhD);
            TextView tvDurasiPerjalananD = findViewById(R.id.tvDurasiPerjalananD);
            TextView tvPerkiraanBBMD = findViewById(R.id.tvPerkiraanBBMD);
            TextView tvHariKeberangkatanD = findViewById(R.id.tvHariKeberangkatanD);
            TextView tvJamKeberangkatanD = findViewById(R.id.tvJamKeberangkatanD);
            TextView tvHariKepulanganD = findViewById(R.id.tvHariKepulanganD);
            TextView tvJamKepulanganD = findViewById(R.id.tvJamKepulanganD);
            TextView tvNoTelpKantorD = findViewById(R.id.tvNoTelpKantorD);
            TextView tvNamaPenumpangD = findViewById(R.id.tvNamaPenumpangD);
            TextView tvJumlahPenumpangD = findViewById(R.id.tvJumlahPenumpangD);
            TextView tvNamaAtasanD = findViewById(R.id.tvNamaAtasanD);
            TextView tvKeteranganD = findViewById(R.id.tvKeteranganD);
            LinearLayout linlayDipros = findViewById(R.id.linlayStatusDiproses);
            LinearLayout linlayDiter = findViewById(R.id.linlayStatusDiterima);
            LinearLayout linlayDitol = findViewById(R.id.linlayStatusDitolak);


            String username = intent.getStringExtra(EXTRA_USERNAMES);
            String id_user = intent.getStringExtra(EXTRA_ID_USERS);
            String tanggal_pesan = intent.getStringExtra(EXTRA_TANGGAL_PESANS);
            String jenis_keperluan = intent.getStringExtra(EXTRA_JENIS_KEPERLUANS);
            String jenis_pemesanan = intent.getStringExtra(EXTRA_JENIS_PEMESANANS);
            String kawasan = intent.getStringExtra(EXTRA_KAWASANS);
            String witel = intent.getStringExtra(EXTRA_WITELS);
            String area_tujuan = intent.getStringExtra(EXTRA_AREA_TUJUANS);
            String area_pool = intent.getStringExtra(EXTRA_AREA_POOLS);
            String alamat_keberangkatan = intent.getStringExtra(EXTRA_ALAMAT_KEBERANGKATANS);
            String alamat_tujuan = intent.getStringExtra(EXTRA_ALAMAT_TUJUANS);
            String jarak = intent.getStringExtra(EXTRA_JARAKS);
            String durasi_perjalanan = intent.getStringExtra(EXTRA_DURASI_PERJALANANS);
            String perkiraan_biaya_bbm = intent.getStringExtra(EXTRA_PERKIRAAN_BBMS);
            String hari_berangkat = intent.getStringExtra(EXTRA_HARI_BERANGKATS);
            String jam_berangkat = intent.getStringExtra(EXTRA_JAM_BERANGKATS);
            String hari_pulang = intent.getStringExtra(EXTRA_HARI_PULANGS);
            String jam_pulang = intent.getStringExtra(EXTRA_JAM_PULANGS);
            String no_telp_kantor = intent.getStringExtra(EXTRA_NO_TELP_KANTORS);
            String nama_penumpang = intent.getStringExtra(EXTRA_NAMA_PENUMPANGS);
            String jumlah_penumpang = intent.getStringExtra(EXTRA_JUMLAH_PENUMPANGS);
            String nama_atasan = intent.getStringExtra(EXTRA_NAMA_ATASANS);
            String keterangan = intent.getStringExtra(EXTRA_KETERANGANS);
            String status = intent.getStringExtra(EXTRA_STATUSS);
            pemesanan_id = intent.getStringExtra(EXTRA_PEMESANAN_IDS);
//        String pemesanan_id = intent.getStringExtra("pemesanan_id");

            tvUsernameD.setText(username);
            tvTanggalPesanD.setText(tanggal_pesan);
            tvJenisKeperluanD.setText(jenis_keperluan);
            tvJenisPemesananD.setText(jenis_pemesanan);
            tvKawasanD.setText(kawasan);
            tvWitelD.setText(witel);
            tvAreaPoolD.setText(area_pool);
            tvAreaTujuanD.setText(area_tujuan);
            tvAlamatKeberangkatanD.setText(alamat_keberangkatan);
            tvAlamatTujuanD.setText(alamat_tujuan);
            tvJarakTempuhD.setText(jarak);
            tvDurasiPerjalananD.setText(durasi_perjalanan);
            tvPerkiraanBBMD.setText(perkiraan_biaya_bbm);
            tvHariKeberangkatanD.setText(hari_berangkat);
            tvJamKeberangkatanD.setText(jam_berangkat);
            tvHariKepulanganD.setText(hari_pulang);
            tvJamKepulanganD.setText(jam_pulang);
            tvNoTelpKantorD.setText(no_telp_kantor);
            tvNamaPenumpangD.setText(nama_penumpang);
            String nama1 = nama_penumpang.replace("[","");
            String nama2 = nama1.replace("]","");
            List nama_penum = Arrays.asList(nama2.split(","));
            String penumpang = "";
            for (int i = 0; i<nama_penum.size();i++){
                penumpang += String.valueOf(i+1)+". "+nama_penum.get(i).toString()+"\n";
            }
            tvNamaPenumpangD.setText(penumpang);
            tvNamaAtasanD.setText(nama_atasan);
            tvKeteranganD.setText(keterangan);

            if(status.equals("1")){
                linlayDitol.setVisibility(View.GONE);
                linlayDiter.setVisibility(View.GONE);
                linlayDipros.setVisibility(View.VISIBLE);
            }else if(status.equals("2")){
                linlayDitol.setVisibility(View.GONE);
                linlayDipros.setVisibility(View.GONE);
                linlayDiter.setVisibility(View.VISIBLE);
            }else{
                linlayDipros.setVisibility(View.GONE);
                linlayDiter.setVisibility(View.GONE);
                linlayDitol.setVisibility(View.VISIBLE);
            }
        }
    }
}
