package dzaki.project.sewamobilinvetaris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dzaki.project.sewamobilinvetaris.app.AppController;
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

public class DetailApproveAct extends AppCompatActivity {

    Button btnTerima, btnTolak;
    int success;

    private static final String TAG = DetailApproveAct.class.getSimpleName();
    private String url = Server.URL + "status1.php";
    private String urli = Server.URL + "status2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String pemesanan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_approve);

        Intent intent = getIntent();

        if (intent.getExtras() == null){
            finish();
        }else {
            TextView tvUsernameDA = findViewById(R.id.tvUsernameDA);
            TextView tvTanggalPesanDA = findViewById(R.id.tvTanggalPesanDA);
            TextView tvJenisKeperluanDA = findViewById(R.id.tvJenisKeperluanDA);
            TextView tvJenisPemesananDA = findViewById(R.id.tvJenisPemesananDA);
            TextView tvKawasanDA = findViewById(R.id.tvKawasanDA);
            TextView tvWitelDA = findViewById(R.id.tvWitelDA);
            TextView tvAreaPoolDA = findViewById(R.id.tvAreaPoolDA);
            TextView tvAreaTujuanDA = findViewById(R.id.tvAreaTujuanDA);
            TextView tvAlamatKeberangkatanDA = findViewById(R.id.tvAlamatKeberangkatanDA);
            TextView tvAlamatTujuanDA = findViewById(R.id.tvAlamatTujuanDA);
            TextView tvJarakTempuhDA = findViewById(R.id.tvJarakTempuhDA);
            TextView tvDurasiPerjalananDA = findViewById(R.id.tvDurasiPerjalananDA);
            TextView tvPerkiraanBBMDA = findViewById(R.id.tvPerkiraanBBMDA);
            TextView tvHariKeberangkatanDA = findViewById(R.id.tvHariKeberangkatanDA);
            TextView tvJamKeberangkatanDA = findViewById(R.id.tvJamKeberangkatanDA);
            TextView tvHariKepulanganDA = findViewById(R.id.tvHariKepulanganDA);
            TextView tvJamKepulanganDA = findViewById(R.id.tvJamKepulanganDA);
            TextView tvNoTelpKantorDA = findViewById(R.id.tvNoTelpKantorDA);
            TextView tvNamaPenumpangDA = findViewById(R.id.tvNamaPenumpangDA);
            TextView tvJumlahPenumpangDA = findViewById(R.id.tvJumlahPenumpangDA);
            TextView tvNamaAtasanDA = findViewById(R.id.tvNamaAtasanDA);
            TextView tvKeteranganDA = findViewById(R.id.tvKeteranganDA);


            String username = intent.getStringExtra(EXTRA_USERNAME);
            String id_user = intent.getStringExtra(EXTRA_ID_USER);
            String tanggal_pesan = intent.getStringExtra(EXTRA_TANGGAL_PESAN);
            String jenis_keperluan = intent.getStringExtra(EXTRA_JENIS_KEPERLUAN);
            String jenis_pemesanan = intent.getStringExtra(EXTRA_JENIS_PEMESANAN);
            String kawasan = intent.getStringExtra(EXTRA_KAWASAN);
            String witel = intent.getStringExtra(EXTRA_WITEL);
            String area_tujuan = intent.getStringExtra(EXTRA_AREA_TUJUAN);
            String area_pool = intent.getStringExtra(EXTRA_AREA_POOL);
            String alamat_keberangkatan = intent.getStringExtra(EXTRA_ALAMAT_KEBERANGKATAN);
            String alamat_tujuan = intent.getStringExtra(EXTRA_ALAMAT_TUJUAN);
            String jarak = intent.getStringExtra(EXTRA_JARAK);
            String durasi_perjalanan = intent.getStringExtra(EXTRA_DURASI_PERJALANAN);
            String perkiraan_biaya_bbm = intent.getStringExtra(EXTRA_PERKIRAAN_BBM);
            String hari_berangkat = intent.getStringExtra(EXTRA_HARI_BERANGKAT);
            String jam_berangkat = intent.getStringExtra(EXTRA_JAM_BERANGKAT);
            String hari_pulang = intent.getStringExtra(EXTRA_HARI_PULANG);
            String jam_pulang = intent.getStringExtra(EXTRA_JAM_PULANG);
            String no_telp_kantor = intent.getStringExtra(EXTRA_NO_TELP_KANTOR);
            String nama_penumpang = intent.getStringExtra(EXTRA_NAMA_PENUMPANG);

            String jumlah_penumpang = intent.getStringExtra(EXTRA_JUMLAH_PENUMPANG);
            String nama_atasan = intent.getStringExtra(EXTRA_NAMA_ATASAN);
            String keterangan = intent.getStringExtra(EXTRA_KETERANGAN);
            pemesanan_id = intent.getStringExtra(EXTRA_PEMESANAN_ID);
//        String pemesanan_id = intent.getStringExtra("pemesanan_id");

            tvUsernameDA.setText(username);
            tvTanggalPesanDA.setText(tanggal_pesan);
            tvJenisKeperluanDA.setText(jenis_keperluan);
            tvJenisPemesananDA.setText(jenis_pemesanan);
            tvKawasanDA.setText(kawasan);
            tvWitelDA.setText(witel);
            tvAreaPoolDA.setText(area_pool);
            tvAreaTujuanDA.setText(area_tujuan);
            tvAlamatKeberangkatanDA.setText(alamat_keberangkatan);
            tvAlamatTujuanDA.setText(alamat_tujuan);
            tvJarakTempuhDA.setText(jarak);
            tvDurasiPerjalananDA.setText(durasi_perjalanan);
            tvPerkiraanBBMDA.setText(perkiraan_biaya_bbm);
            tvHariKeberangkatanDA.setText(hari_berangkat);
            tvJamKeberangkatanDA.setText(jam_berangkat);
            tvHariKepulanganDA.setText(hari_pulang);
            tvJamKepulanganDA.setText(jam_pulang);
            tvNoTelpKantorDA.setText(no_telp_kantor);

            String nama1 = nama_penumpang.replace("[","");
            String nama2 = nama1.replace("]","");
            List nama_penum = Arrays.asList(nama2.split(","));
            String penumpang = "";
            for (int i = 0; i<nama_penum.size();i++){
                penumpang += String.valueOf(i+1)+". "+nama_penum.get(i).toString()+"\n";
            }
            tvNamaPenumpangDA.setText(penumpang);
            tvJumlahPenumpangDA.setText(jumlah_penumpang);
            tvNamaAtasanDA.setText(nama_atasan);
            tvKeteranganDA.setText(keterangan);
        }

        btnTerima = (Button) findViewById(R.id.btnTerima);
        btnTolak = (Button) findViewById(R.id.btnTolak);

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailApproveAct.this);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Apa Anda yakin untuk menerima penyewaan ini?");

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        terima(pemesanan_id);
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

    btnTolak.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailApproveAct.this);

            builder.setTitle("Konfirmasi");
            builder.setMessage("Apa Anda yakin untuk menolak penyewaan ini?");

            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    tolak(pemesanan_id);
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

    private void terima(final String pemesanan_id){
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        Toast.makeText(DetailApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(DetailApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DetailApproveAct.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pemesanan_id", pemesanan_id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void tolak(final String pemesanan_id){
        StringRequest strReq = new StringRequest(Request.Method.POST, urli, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());

                        Toast.makeText(DetailApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(DetailApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DetailApproveAct.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pemesanan_id", pemesanan_id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
