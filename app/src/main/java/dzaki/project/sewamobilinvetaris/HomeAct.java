package dzaki.project.sewamobilinvetaris;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeAct extends AppCompatActivity {

    String id, username, level;
    SharedPreferences sharedpreferences;

    public static final String my_shared_preferences = "my_shared_preferences";

    private static final String TAG = HomeAct.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_LEVEL = "level";

    private RelativeLayout rvPesan,rvApprove, rvStatus, rvAbout, rvLogout;
    private CardView cvSewa, cvStatus, cvApprove, cvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rvPesan = findViewById(R.id.rvPesan);
        rvApprove = findViewById(R.id.rvApprove);
        rvStatus = findViewById(R.id.rvStatus);
        rvAbout = findViewById(R.id.rvAbout);
        rvLogout = findViewById(R.id.rvLogout);
        cvSewa = findViewById(R.id.cvSewa);
        cvStatus = findViewById(R.id.cvStatus);
        cvApprove = findViewById(R.id.cvApprove);
        cvAbout = findViewById(R.id.cvAbout);

        sharedpreferences = getSharedPreferences(LoginAct.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        level = getIntent().getStringExtra(TAG_LEVEL);

        if(level.equals("admin")){
            cvSewa.setVisibility(View.GONE);
            cvStatus.setVisibility(View.GONE);
            cvAbout.setVisibility(View.GONE);
            cvApprove.setVisibility(View.VISIBLE);
        }
        else{
            cvSewa.setVisibility(View.VISIBLE);
            cvStatus.setVisibility(View.VISIBLE);
            cvAbout.setVisibility(View.VISIBLE);
            cvApprove.setVisibility(View.GONE);
        }

        rvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginAct.session_status, true);
                editor.putString(TAG_ID, id);
                editor.putString(TAG_USERNAME, username);
                editor.putString(TAG_LEVEL, level);
                editor.commit();

                Intent intent = new Intent(HomeAct.this, StatusAct.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_LEVEL, level);
                finish();
                startActivity(intent);
            }
        });

        rvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAct.this,AboutAct.class));
            }
        });

        rvPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginAct.session_status, true);
                editor.putString(TAG_ID, id);
                editor.putString(TAG_USERNAME, username);
                editor.putString(TAG_LEVEL, level);
                editor.commit();

                Intent intent = new Intent(HomeAct.this, PesanMobil.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_LEVEL, level);
                finish();
                startActivity(intent);
            }
        });

        rvApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAct.this,ApproveAct.class));
            }
        });

        rvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginAct.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                Toast.makeText(HomeAct.this, "Berhasil logout", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeAct.this, LoginAct.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
