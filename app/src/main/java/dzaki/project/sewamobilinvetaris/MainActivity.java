package dzaki.project.sewamobilinvetaris;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView logo;
    private static int splashTimeOut=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=findViewById(R.id.logo);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this,LoginAct.class);
                    startActivity(i);
                    finish();
                }
            },splashTimeOut);

            Animation myanim = AnimationUtils.loadAnimation(this,R.anim.anim);
            logo.startAnimation(myanim);
    }
}
