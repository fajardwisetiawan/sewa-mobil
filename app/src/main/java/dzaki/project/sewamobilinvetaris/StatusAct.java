package dzaki.project.sewamobilinvetaris;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dzaki.project.sewamobilinvetaris.adapter.ApproveAdapter;
import dzaki.project.sewamobilinvetaris.adapter.StatusAdapter;
import dzaki.project.sewamobilinvetaris.app.AppController;
import dzaki.project.sewamobilinvetaris.model.ApproveModel;
import dzaki.project.sewamobilinvetaris.util.Server;

public class StatusAct extends AppCompatActivity {

    public static final String EXTRA_USERNAMES = "username";
    public static final String EXTRA_PEMESANAN_IDS = "pemesanan_id";
    public static final String EXTRA_ID_USERS = "id";
    public static final String EXTRA_TANGGAL_PESANS = "tanggal_pesan";
    public static final String EXTRA_JENIS_KEPERLUANS = "keperluan";
    public static final String EXTRA_JENIS_PEMESANANS = "jenis_pemesanan";
    public static final String EXTRA_KAWASANS = "kawasan";
    public static final String EXTRA_WITELS = "witel";
    public static final String EXTRA_AREA_TUJUANS = "area_tujuan";
    public static final String EXTRA_AREA_POOLS= "pool";
    public static final String EXTRA_ALAMAT_KEBERANGKATANS = "alamat_keberangkatan";
    public static final String EXTRA_ALAMAT_TUJUANS = "alamat_tujuan";
    public static final String EXTRA_JARAKS = "jarak";
    public static final String EXTRA_DURASI_PERJALANANS = "durasi_perjalanan";
    public static final String EXTRA_PERKIRAAN_BBMS = "perkiraan_biaya_bbm";
    public static final String EXTRA_HARI_BERANGKATS = "hari_berangkat";
    public static final String EXTRA_JAM_BERANGKATS = "jam_berangkat";
    public static final String EXTRA_HARI_PULANGS = "hari_pulang";
    public static final String EXTRA_JAM_PULANGS = "jam_pulang";
    public static final String EXTRA_NO_TELP_KANTORS = "no_telp_kantor";
    public static final String EXTRA_NAMA_PENUMPANGS = "nama_penumpang";
    public static final String EXTRA_JUMLAH_PENUMPANGS = "jumlah_penumpang";
    public static final String EXTRA_NAMA_ATASANS = "nama_atasan";
    public static final String EXTRA_KETERANGANS = "keterangan";
    public static final String EXTRA_STATUSS = "status";

    private String url = Server.URL + "tampil_status.php";

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    List<ApproveModel> mItems;

    public final static String TAG_LEVEL = "level";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username, level, status;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    int success;
    private static final String TAG = StatusAct.class.getSimpleName();
    private String urli = Server.URL + "status1.php";
    private String urlii = Server.URL + "status2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String pemesanan_id;

    ImageView backStatusAct;
    private CardView cvSewa, cvStatus, cvApprove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        pd = new ProgressDialog(StatusAct.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_dataStat);
        mItems = new ArrayList<>();

        cvSewa = findViewById(R.id.cvSewa);
        cvStatus = findViewById(R.id.cvStatus);
        cvApprove = findViewById(R.id.cvApprove);
        backStatusAct = (ImageView) findViewById(R.id.backStatusAct);

//      mAdapter.setOnItemClickListener(TroubleShootingAct.this);

        sharedpreferences = getSharedPreferences(HomeAct.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        level = getIntent().getStringExtra(TAG_LEVEL);

        backStatusAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.equals("admin")){
                    Intent intent = new Intent(StatusAct.this, HomeAct.class);
                    intent.putExtra(TAG_ID, id);
                    intent.putExtra(TAG_USERNAME, username);
                    intent.putExtra(TAG_LEVEL, level);
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(StatusAct.this, HomeAct.class);
                    intent.putExtra(TAG_ID, id);
                    intent.putExtra(TAG_USERNAME, username);
                    intent.putExtra(TAG_LEVEL, level);
                    finish();
                    startActivity(intent);
                }
            }
        });

        loadjson(id);
    }




private void loadjson(String id){
        pd.setMessage("Mengambil Data..");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url+"?id="+id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley", "response : " + response.toString());
                for (int i=0; i < response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        ApproveModel md = new ApproveModel();
                        md.setUsername(data.getString("username"));
                        md.setHari_berangkat(data.getString("hari_berangkat"));
                        md.setHari_pulang(data.getString("hari_pulang"));
                        md.setPemesanan_id(data.getString("pemesanan_id"));
                        md.setTanggal_pesan(data.getString("tanggal_pesan"));
                        md.setJenis_pemesanan(data.getString("jenis_pemesanan"));
                        md.setJenis_keperluan(data.getString("keperluan"));
                        md.setKawasan(data.getString("kawasan"));
                        md.setWitel(data.getString("witel"));
                        md.setArea_pool(data.getString("pool"));
                        md.setArea_tujuan(data.getString("area_tujuan"));
                        md.setAlamat_keberangkatan(data.getString("alamat_keberangkatan"));
                        md.setAlamat_tujuan(data.getString("alamat_tujuan"));
                        md.setJarak(data.getString("jarak"));
                        md.setDurasi_perjalanan(data.getString("durasi_perjalanan"));
                        md.setPerkiraan_biaya_bbm(data.getString("perkiraan_biaya_bbm"));
                        md.setJam_berangkat(data.getString("jam_berangkat"));
                        md.setJam_pulang(data.getString("jam_pulang"));
                        md.setNo_telp_kantor(data.getString("no_telp_kantor"));
                        md.setNama_penumpang(data.getString("nama_penumpang"));
                        md.setJumlah_penumpang(data.getString("jumlah_penumpang"));
                        md.setNama_atasan(data.getString("nama_atasan"));
                        md.setKeterangan(data.getString("keterangan"));
                        md.setStatus(data.getString("status"));// memanggil nama array yang kita buat
                        mItems.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                mManager = new LinearLayoutManager(StatusAct.this, LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(dividerItemDecoration);
                mAdapter = new StatusAdapter(StatusAct.this, mItems);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

}
