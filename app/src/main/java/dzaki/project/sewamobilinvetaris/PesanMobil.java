package dzaki.project.sewamobilinvetaris;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codesgood.views.JustifiedTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.shuhart.stepview.StepView;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dzaki.project.sewamobilinvetaris.app.AppController;
import dzaki.project.sewamobilinvetaris.util.Server;
import me.gujun.android.taggroup.TagGroup;

public class PesanMobil extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1, PLACE_PICKER_REQUEST2 = 1;
    private JustifiedTextView tvMapBerangkat, tvMapTujuan;
    private RelativeLayout rvBerangkat, rvTujuan;
    private ImageView btnCloseBerangkat, btnCloseTujuan;

    ProgressDialog pDialog;
    Intent intent;

    public boolean isBtnMapTujuans() {
        return btnMapTujuans;
    }

    public void setBtnMapTujuans(boolean btnMapTujuans) {
        this.btnMapTujuans = btnMapTujuans;
    }

    public boolean isBtnMapBerangkats() {
        return btnMapBerangkats;
    }

    public void setBtnMapBerangkats(boolean btnMapBerangkats) {
        this.btnMapBerangkats = btnMapBerangkats;
    }
        private String longitudeTujuan, latitudeTujuan, longitudeBerangkat, latitudeBerangkat;

    public String getLongitudeTujuan() {
        return longitudeTujuan;
    }

    public void setLongitudeTujuan(String longitudeTujuan) {
        this.longitudeTujuan = longitudeTujuan;
    }

    public String getLatitudeTujuan() {
        return latitudeTujuan;
    }

    public void setLatitudeTujuan(String latitudeTujuan) {
        this.latitudeTujuan = latitudeTujuan;
    }

    public String getLongitudeBerangkat() {
        return longitudeBerangkat;
    }

    public void setLongitudeBerangkat(String longitudeBerangkat) {
        this.longitudeBerangkat = longitudeBerangkat;
    }

    public String getLatitudeBerangkat() {
        return latitudeBerangkat;
    }

    public void setLatitudeBerangkat(String latitudeBerangkat) {
        this.latitudeBerangkat = latitudeBerangkat;
    }

    boolean btnMapTujuans, btnMapBerangkats;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePickerDialog2;
    private SimpleDateFormat dateFormatter2;

    private TimePickerDialog timePickerDialog;

    private TimePickerDialog timePickerDialog2;

    private ImageView back;
    private StepView stepView;
    private int currentStep = 0;
    private ScrollView svLainya,svKeberangkatan,svTujuan;
    private Button btnNext;
    private NiceSpinner spinnerJenisKeperluan,spinnerPemesanan,spinnerKawasan,spinnerWitel,spinnerAreaPool,spinnerAreaTujuan;
    List<String> JenisKeperluanList, PemesananList,KawasanList,WitelList,AreaPoolList,AreaTujuanList;
    private String dataJenisKeperluan, dataPemesanan, dataKawasan,dataWitel,dataAreaPool, dataAreaTujuan;
    private Button btnMapBerangkat, btnMapTujuan;
    private TextView tvTanggalBerangkat, tvWaktuBerangkat, tvTanggalKepulangan, tvWaktuKepulangan, tvJarak, tvDurasi, tvPerkiraanBBM;
    private ImageView btnAddTanggalBerangkat, btnAddWaktuBerangkat, btnAddTanggalKepulangan, btnAddWaktuKepulangan;
    private EditText etPhone,etNama,etJumlah,etAtasan,etKeterangan;
    private ImageView btnAdd;
    private TagGroup tagView;
    private List<String> itemTagview = new ArrayList<>();

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "pesan_mobil.php";

    private static final String TAG = PesanMobil.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public final static String TAG_LEVEL = "level";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username, level;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_mobil);

        rvBerangkat = findViewById(R.id.rvBerangkat);
        rvTujuan = findViewById(R.id.rvTujuan);
        btnCloseBerangkat = findViewById(R.id.btnCloseBerangkat);
        btnCloseTujuan = findViewById(R.id.btnCloseTujuan);
        setLatitudeBerangkat("");
        setLongitudeBerangkat("");
        setLongitudeTujuan("");
        setLatitudeBerangkat("");

        initViews();

        initViews2();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        btnMapBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(PesanMobil.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                setBtnMapBerangkats(true);
                setBtnMapTujuans(false);
            }
        });

        btnMapTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(PesanMobil.this), PLACE_PICKER_REQUEST2);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                setBtnMapBerangkats(false);
                setBtnMapTujuans(true);
            }
        });

        dateFormatter = new SimpleDateFormat("EEEE, dd-MMMM-yyyy", Locale.US);

        tvTanggalBerangkat = (TextView) findViewById(R.id.tvTanggalBerangkat);
        btnAddTanggalBerangkat = (ImageView) findViewById(R.id.btnAddTanggalBerangkat);
        btnAddTanggalBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        tvWaktuBerangkat = (TextView) findViewById(R.id.tvWaktuBerangkat);
        btnAddWaktuBerangkat = (ImageView) findViewById(R.id.btnAddWaktuBerangkat);
        btnAddWaktuBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        dateFormatter2 = new SimpleDateFormat("EEEE, dd-MMMM-yyyy", Locale.US);

        tvTanggalKepulangan = (TextView) findViewById(R.id.tvTanggalKepulangan);
        btnAddTanggalKepulangan = (ImageView) findViewById(R.id.btnAddTanggalKepulangan);
        btnAddTanggalKepulangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });

        tvWaktuKepulangan = (TextView) findViewById(R.id.tvWaktuKepulangan);
        btnAddWaktuKepulangan = (ImageView) findViewById(R.id.btnAddWaktuKepulangan);
        btnAddWaktuKepulangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog2();
            }
        });



        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        tvJarak = findViewById(R.id.tvJarak);
        tvDurasi = findViewById(R.id.tvDurasiPerjalanan);
        tvPerkiraanBBM = findViewById(R.id.tvPerkiraanBBM);
        etPhone = findViewById(R.id.etPhone);
        etNama = findViewById(R.id.etNama);
        etJumlah = findViewById(R.id.etJumlah);
        etAtasan = findViewById(R.id.etAtasan);
        etKeterangan = findViewById(R.id.etKeterangan);
        btnAdd = findViewById(R.id.btnAdd);
        back = findViewById(R.id.back);
        btnNext = findViewById(R.id.btnNext);
        spinnerJenisKeperluan = findViewById(R.id.spinnerJenisKeperluan);
        spinnerPemesanan = findViewById(R.id.spinnerPemesanan);
        spinnerKawasan = findViewById(R.id.spinnerKawasan);
        spinnerWitel = findViewById(R.id.spinnerWitel);
        spinnerAreaPool = findViewById(R.id.spinnerAreaPool);
        spinnerAreaTujuan = findViewById(R.id.spinnerAreaTujuan);
        stepView = findViewById(R.id.step_view);
        svLainya = findViewById(R.id.svLainya);
        svKeberangkatan = findViewById(R.id.svKeberangkatan);
        svTujuan = findViewById(R.id.svTujuan);
        svKeberangkatan.setVisibility(View.VISIBLE);
        tagView= findViewById(R.id.text_view_show_more);

        sharedpreferences = getSharedPreferences(HomeAct.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        level = getIntent().getStringExtra(TAG_LEVEL);

        JenisKeperluanList = new LinkedList<>(Arrays.asList("Regular", "Event", "Sosial", "CAM", "Emergency","Penanganan Gangguan","Direksi"));
        PemesananList = new LinkedList<>(Arrays.asList("Mobil dan Sopir", "Mobil Saja", "Sopir Saja"));
        KawasanList = new LinkedList<>(Arrays.asList("JABAR - BANTEN", "JABODETABEK", "JATENG - DIY", "JATIM - BALI - NTT", "KALIMANTAN", "PAMASULA","SUMATERA"));
        WitelList = new LinkedList<>(Arrays.asList("Jateng Barut", "Jateng Barsel", "Jateng Utara", "Jateng Tengah", "Jateng Timur", "Jateng Selatan", "Yogyakarta"));
        AreaPoolList = new LinkedList<>(Arrays.asList("SMG Pahlawan", "SMG Johar"));
        AreaTujuanList = new LinkedList<>(Arrays.asList("JABAR - BANTEN", "JABODETABEK", "JATENG - DIY", "JATIM - BALI - NTT", "KALIMANTAN", "PAMASULA","SUMATERA"));
        btnNext.setEnabled(true);
        spinnerJenisKeperluan.attachDataSource(JenisKeperluanList);
        spinnerPemesanan.attachDataSource(PemesananList);
        spinnerKawasan.attachDataSource(KawasanList);
        spinnerWitel.attachDataSource(WitelList);
        spinnerAreaPool.attachDataSource(AreaPoolList);
        spinnerAreaTujuan.attachDataSource(AreaTujuanList);

        spinnerJenisKeperluan.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                setDataJenisKeperluan(item);
                Toast.makeText(PesanMobil.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        spinnerPemesanan.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                setDataPemesanan(item);
                Toast.makeText(PesanMobil.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        spinnerKawasan.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                setDataKawasan(item);
                Toast.makeText(PesanMobil.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        spinnerWitel.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                setDataWitel(item);
                Toast.makeText(PesanMobil.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        spinnerAreaPool.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                setDataAreaPool(item);
                Toast.makeText(PesanMobil.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        spinnerAreaTujuan.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                setDataAreaTujuan(item);
                Toast.makeText(PesanMobil.this, item, Toast.LENGTH_SHORT).show();
            }
        });



        stepView.getState()
                .steps(new ArrayList<String>() {{
                    add("Pemberangkatan");
                    add("Tujuan");
                    add("Lainnya");
                }})
                .stepsNumber(2)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp_1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp_14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp_16))
                .commit();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    if (currentStep==1){
                        svKeberangkatan.setVisibility(View.GONE);
                        svTujuan.setVisibility(View.VISIBLE);
                        btnNext.setBackgroundColor(Color.parseColor("#D0D0D0"));
                       // btnNext.setEnabled(false);
                    }else if (currentStep==2){
                        svLainya.setVisibility(View.VISIBLE);
                        svTujuan.setVisibility(View.GONE);
                        svKeberangkatan.setVisibility(View.GONE);
                        btnNext.setText("Sewa");
                        stepView.done(true);


                    }

                    stepView.go(currentStep, true);
                } else {
                   // finish();

                    sharedpreferences = getSharedPreferences(HomeAct.my_shared_preferences, Context.MODE_PRIVATE);
                    String id_user = getIntent().getStringExtra(TAG_ID);

                    String jenis_keperluan = spinnerJenisKeperluan.getText().toString();
                    String jenis_pemesanan = spinnerPemesanan.getText().toString();
                    String kawasan = spinnerKawasan.getText().toString();
                    String witel = spinnerWitel.getText().toString();
                    String area_pool = spinnerAreaPool.getText().toString();
                    String area_tujuan = spinnerAreaTujuan.getText().toString();
                    String alamat_keberangkatan = tvMapBerangkat.getText().toString();
                    String alamat_tujuan = tvMapTujuan.getText().toString();
                    String jarak = tvJarak.getText().toString();
                    String durasi = tvDurasi.getText().toString();
                    String perkiraan_bbm = tvPerkiraanBBM.getText().toString();
                    String tanggal_keberangkatan = tvTanggalBerangkat.getText().toString();
                    String jam_keberangkatan = tvWaktuBerangkat.getText().toString();
                    String tanggal_kepulangan = tvTanggalKepulangan.getText().toString();
                    String jam_kepulangan = tvWaktuKepulangan.getText().toString();
                    String no_telp_kantor = etPhone.getText().toString();
                    String [] nama_penumpang = tagView.getTags();
                    String jumlah_penumpang = etJumlah.getText().toString();
                    String nama_atasan = etAtasan.getText().toString();
                    String keterangan = etKeterangan.getText().toString();

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

//                            Toast.makeText(PesanMobil.this,id_user +"\n"
//                                    + jenis_keperluan +"\n"+
//                                    jenis_pemesanan +"\n"+
//                                    kawasan +"\n"+
//                                    witel +"\n"+
//                                    area_pool +"\n"+
//                                    area_tujuan +"\n"+
//                                    alamat_keberangkatan +"\n"+
//                                    alamat_tujuan +"\n"+
//                                    jarak +"\n"+
//                                    durasi +"\n"+
//                                    perkiraan_bbm +"\n"+
//                                    tanggal_keberangkatan +"\n"+
//                                    jam_keberangkatan +"\n"+
//                                    tanggal_kepulangan +"\n"+
//                                    jam_kepulangan +"\n"+
//                                    no_telp_kantor +"\n"+
//                                    Arrays.toString(nama_penumpang) +"\n"+
//                                    jumlah_penumpang +"\n"+
//                                    nama_atasan +"\n"+
//                                    keterangan +"\n", Toast.LENGTH_SHORT).show();


                        checkPesanan(id_user, jenis_keperluan, jenis_pemesanan, kawasan, witel, area_pool, area_tujuan, alamat_keberangkatan, alamat_tujuan, jarak, durasi, perkiraan_bbm, tanggal_keberangkatan, jam_keberangkatan,tanggal_kepulangan, jam_kepulangan, no_telp_kantor, Arrays.toString(nama_penumpang), jumlah_penumpang, nama_atasan, keterangan);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.equals("admin")){
                    Intent intent = new Intent(PesanMobil.this, HomeAct.class);
                    intent.putExtra(TAG_ID, id);
                    intent.putExtra(TAG_USERNAME, username);
                    intent.putExtra(TAG_LEVEL, level);
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(PesanMobil.this, HomeAct.class);
                    intent.putExtra(TAG_ID, id);
                    intent.putExtra(TAG_USERNAME, username);
                    intent.putExtra(TAG_LEVEL, level);
                    finish();
                    startActivity(intent);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String[] tagList = new String[]{};
                itemTagview.add(etNama.getText().toString());
                etNama.setText("");
                tagView.setTags(itemTagview);
            }
        });

        tagView.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                itemTagview.remove(tag);

                tagView.setTags(itemTagview);
            }
        });
        btnCloseBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvBerangkat.setVisibility(View.GONE);
                btnMapBerangkat.setVisibility(View.VISIBLE);
                tvMapBerangkat.setText("");
                setBtnMapBerangkats(false);
                setLatitudeBerangkat("");
                setLongitudeBerangkat("");
                if (!getLatitudeBerangkat().equalsIgnoreCase("") &&
                        !getLongitudeBerangkat().equalsIgnoreCase("")&&
                        !getLongitudeTujuan().equalsIgnoreCase("")&&
                        !getLatitudeTujuan().equalsIgnoreCase("")){
                    tvJarak.setText(String.valueOf(distance(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()))));
                    tvDurasi.setText(String.valueOf(distance(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()))));
                    tvPerkiraanBBM.setText(String.valueOf(perkiraan_bbm(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()))));
                }else {
                    tvJarak.setText("Set map terlebih dahulu");
                    tvDurasi.setText("Set map terlebih dahulu");
                    tvPerkiraanBBM.setText("Set map terlebih dahulu");
                }
            }
        });

        btnCloseTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvTujuan.setVisibility(View.GONE);
                btnMapTujuan.setVisibility(View.VISIBLE);
                tvMapTujuan.setText("");
                setBtnMapTujuans(false);
                setLongitudeTujuan("");
                setLatitudeTujuan("");
                if (!getLatitudeBerangkat().equalsIgnoreCase("") &&
                        !getLongitudeBerangkat().equalsIgnoreCase("")&&
                        !getLongitudeTujuan().equalsIgnoreCase("")&&
                        !getLatitudeTujuan().equalsIgnoreCase("")){
                    tvJarak.setText(String.valueOf(distance(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()))));
                    tvDurasi.setText(String.valueOf(distance(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()))));
                    tvPerkiraanBBM.setText(String.valueOf(perkiraan_bbm(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()))));
                }else {
                    tvJarak.setText("Set map terlebih dahulu");
                    tvDurasi.setText("Set map terlebih dahulu");
                    tvPerkiraanBBM.setText("Set map terlebih dahulu");
                }
            }
        });
    }

    public String getDataJenisKeperluan() {
        return dataJenisKeperluan;
    }

    public void setDataJenisKeperluan(String dataJenisKeperluan) {
        this.dataJenisKeperluan = dataJenisKeperluan;
    }

    public String getDataPemesanan() {
        return dataPemesanan;
    }

    public void setDataPemesanan(String dataPemesanan) {
        this.dataPemesanan = dataPemesanan;
    }

    public String getDataKawasan() {
        return dataKawasan;
    }

    public void setDataKawasan(String dataKawasan) {
        this.dataKawasan = dataKawasan;
    }

    public String getDataWitel() {
        return dataWitel;
    }

    public void setDataWitel(String dataWitel) {
        this.dataWitel = dataWitel;
    }

    public String getDataAreaPool() {
        return dataAreaPool;
    }

    public void setDataAreaPool(String dataAreaPool) {
        this.dataAreaPool = dataAreaPool;
    }

    public String getDataAreaTujuan() {
        return dataAreaTujuan;
    }

    public void setDataAreaTujuan(String dataAreaTujuan) {
        this.dataAreaTujuan = dataAreaTujuan;
    }

    private void checkPesanan(final String id_user, final String jenis_keperluan, final String jenis_pemesanan, final String kawasan, final String witel, final String area_pool, final String area_tujuan, final String alamat_keberangkatan, final String alamat_tujuan, final String jarak, final String durasi, final String perkiraan_bbm, final String tanggal_keberangkatan, final String jam_keberangkatan, final String tanggal_kepulangan, final String jam_kepulangan, final String no_telp_kantor, final String nama_penumpang, final String jumlah_penumpang, final String nama_atasan, final String keterangan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memesan ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Sukses menyewa!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();



                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("jenis_keperluan", jenis_keperluan);
                params.put("jenis_pemesanan", jenis_pemesanan);
                params.put("kawasan", kawasan);
                params.put("witel", witel);
                params.put("area_pool", area_pool);
                params.put("area_tujuan", area_tujuan);
                params.put("alamat_keberangkatan", alamat_keberangkatan);
                params.put("alamat_tujuan", alamat_tujuan);
                params.put("jarak", jarak);
                params.put("durasi_perjalanan", durasi);
                params.put("perkiraan_biaya_bbm", perkiraan_bbm);
                params.put("hari_berangkat", tanggal_keberangkatan);
                params.put("jam_berangkat", jam_keberangkatan);
                params.put("hari_pulang", tanggal_kepulangan);
                params.put("jam_pulang", jam_kepulangan);
                params.put("no_telp_kantor", no_telp_kantor);
                params.put("nama_penumpang", nama_penumpang);
                params.put("jumlah_penumpang", jumlah_penumpang);
                params.put("nama_atasan", nama_atasan);
                params.put("keterangan", keterangan);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);



                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvTanggalBerangkat.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    private void showTimeDialog() {

        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */
                tvWaktuBerangkat.setText(hourOfDay+":"+minute);
            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    private void showDateDialog2(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar2 = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate2 = Calendar.getInstance();
                newDate2.set(year, monthOfYear, dayOfMonth);



                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvTanggalKepulangan.setText(dateFormatter2.format(newDate2.getTime()));
            }

        },newCalendar2.get(Calendar.YEAR), newCalendar2.get(Calendar.MONTH), newCalendar2.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog2.show();
    }

    private void showTimeDialog2() {

        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar2 = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */
        timePickerDialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */
                tvWaktuKepulangan.setText(hourOfDay+":"+minute);
            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar2.get(Calendar.HOUR_OF_DAY), calendar2.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this));

        timePickerDialog2.show();
    }

    private void initViews() {
        btnMapBerangkat = (Button) findViewById(R.id.btnMapBerangkat);
        tvMapBerangkat = (JustifiedTextView) findViewById(R.id.tvMapBerangkat);
    }

    private void initViews2() {
        btnMapTujuan = (Button) findViewById(R.id.btnMapTujuan);
        tvMapTujuan = (JustifiedTextView) findViewById(R.id.tvMapTujuan);
    }
    @Override
    public void onResume(){
        super.onResume();


    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(btnMapBerangkat, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
        Snackbar.make(btnMapTujuan, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
//                stBuilder.append("Name: ");
//                stBuilder.append(placename);
//                stBuilder.append("\n");
//                stBuilder.append("Latitude: ");
//                stBuilder.append(latitude);
//                stBuilder.append("\n");
//                stBuilder.append("Logitude: ");
//                stBuilder.append(longitude);
//                stBuilder.append("\n");
//                stBuilder.append("Address: ");
                stBuilder.append(address);

                if (isBtnMapBerangkats()){
                    tvMapBerangkat.setText(stBuilder.toString());
                    btnMapBerangkat.setVisibility(View.GONE);
                    rvBerangkat.setVisibility(View.VISIBLE);
                    setLongitudeBerangkat(longitude);
                    setLatitudeBerangkat(latitude);
                }

                if (isBtnMapTujuans()){
                    tvMapTujuan.setText(stBuilder.toString());
                    btnMapTujuan.setVisibility(View.GONE);
                    rvTujuan.setVisibility(View.VISIBLE);
                    setLongitudeTujuan(longitude);
                    setLatitudeTujuan(latitude);
                }
                if (!getLatitudeBerangkat().equalsIgnoreCase("") &&
                        !getLongitudeBerangkat().equalsIgnoreCase("")&&
                        !getLongitudeTujuan().equalsIgnoreCase("")&&
                        !getLatitudeTujuan().equalsIgnoreCase("")){
                    tvJarak.setText(String.valueOf(new DecimalFormat("#.##").format(distance(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLongitudeTujuan()))))+" KM");
                    tvDurasi.setText(String.valueOf(new DecimalFormat("#.##").format(durasi(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLongitudeTujuan()))*1.3))+" Jam");
                    tvPerkiraanBBM.setText(String.valueOf(new DecimalFormat("#.##").format(perkiraan_bbm(Double.parseDouble(getLatitudeBerangkat()),Double.parseDouble(getLatitudeTujuan()),Double.parseDouble(getLongitudeBerangkat()),Double.parseDouble(getLongitudeTujuan()))))+" Liter");
                }else {
                    tvJarak.setText("Set map terlebih dahulu");
                    tvDurasi.setText("Set map terlebih dahulu");
                    tvPerkiraanBBM.setText("Set map terlebih dahulu");
                }


            }
        }
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;
    //    double a = ro
        // calculate the result
        return (c * r);
    }

    public static double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

//    private double durasi(double lat1, double lat2, double lon1, double lon2) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }
//
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }
public static double durasi(double lat1, double lat2, double lon1, double lon2) {

    // The math module contains a function
    // named toRadians which converts from
    // degrees to radians.
    lon1 = Math.toRadians(lon1);
    lon2 = Math.toRadians(lon2);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    // Haversine formula
    double dlon = lon2 - lon1;
    double dlat = lat2 - lat1;
    double a = Math.pow(Math.sin(dlat / 2), 2)
            + Math.cos(lat1) * Math.cos(lat2)
            * Math.pow(Math.sin(dlon / 2), 2);

    double c = 2 * Math.asin(Math.sqrt(a));

    // Radius of earth in kilometers. Use 3956
    // for miles
    double r = 6371;

    double k = 60;

    // calculate the result
    return (c * r / k);
}

    public static double perkiraan_bbm(double lat1, double lat2, double lon1, double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        double k = 13;

        // calculate the result
        return (c * r / k);
    }
}
