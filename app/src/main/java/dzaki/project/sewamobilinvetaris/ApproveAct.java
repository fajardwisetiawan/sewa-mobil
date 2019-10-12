package dzaki.project.sewamobilinvetaris;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dzaki.project.sewamobilinvetaris.adapter.ApproveAdapter;
import dzaki.project.sewamobilinvetaris.app.AppController;
import dzaki.project.sewamobilinvetaris.model.ApproveModel;
import dzaki.project.sewamobilinvetaris.util.Server;

public class ApproveAct extends AppCompatActivity implements ApproveAdapter.KetikaTerimaCallback, ApproveAdapter.KetikaTolakCallback {

    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PEMESANAN_ID = "pemesanan_id";
    public static final String EXTRA_ID_USER = "id";
    public static final String EXTRA_TANGGAL_PESAN = "tanggal_pesan";
    public static final String EXTRA_JENIS_KEPERLUAN = "keperluan";
    public static final String EXTRA_JENIS_PEMESANAN= "jenis_pemesanan";
    public static final String EXTRA_KAWASAN = "kawasan";
    public static final String EXTRA_WITEL = "witel";
    public static final String EXTRA_AREA_TUJUAN = "area_tujuan";
    public static final String EXTRA_AREA_POOL = "pool";
    public static final String EXTRA_ALAMAT_KEBERANGKATAN = "alamat_keberangkatan";
    public static final String EXTRA_ALAMAT_TUJUAN = "alamat_tujuan";
    public static final String EXTRA_JARAK = "jarak";
    public static final String EXTRA_DURASI_PERJALANAN = "durasi_perjalanan";
    public static final String EXTRA_PERKIRAAN_BBM = "perkiraan_biaya_bbm";
    public static final String EXTRA_HARI_BERANGKAT = "hari_berangkat";
    public static final String EXTRA_JAM_BERANGKAT = "jam_berangkat";
    public static final String EXTRA_HARI_PULANG = "hari_pulang";
    public static final String EXTRA_JAM_PULANG = "jam_pulang";
    public static final String EXTRA_NO_TELP_KANTOR = "no_telp_kantor";
    public static final String EXTRA_NAMA_PENUMPANG = "nama_penumpang";
    public static final String EXTRA_JUMLAH_PENUMPANG = "jumlah_penumpang";
    public static final String EXTRA_NAMA_ATASAN = "nama_atasan";
    public static final String EXTRA_KETERANGAN = "keterangan";

    private String url = Server.URL + "tampil_approve.php";

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    List<ApproveModel> mItems;

    int success;
    private static final String TAG = ApproveAct.class.getSimpleName();
    private String urli = Server.URL + "status1.php";
    private String urlii = Server.URL + "status2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String pemesanan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        pd = new ProgressDialog(ApproveAct.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_data);
        mItems = new ArrayList<>();

//      mAdapter.setOnItemClickListener(TroubleShootingAct.this);

        loadjson();
    }

    private void loadjson(){
        pd.setMessage("Mengambil Data..");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
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
                        md.setKeterangan(data.getString("keterangan"));// memanggil nama array yang kita buat
                        mItems.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mManager = new LinearLayoutManager(ApproveAct.this, LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(dividerItemDecoration);
                mAdapter = new ApproveAdapter(ApproveAct.this, mItems,ApproveAct.this,ApproveAct.this);
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

    @Override
    public void TerimaClicked(final String pemesanan_id) {

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

                            Toast.makeText(ApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(ApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ApproveAct.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void TolakClicked(final String pemesanan_id) {

            StringRequest strReq = new StringRequest(Request.Method.POST, urlii, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCESS);

                        // Cek error node pada json
                        if (success == 1) {
                            Log.d("get edit data", jObj.toString());

                            Toast.makeText(ApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(ApproveAct.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ApproveAct.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
