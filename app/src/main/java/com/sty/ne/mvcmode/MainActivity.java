package com.sty.ne.mvcmode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sty.ne.mvcmode.bean.ImageBean;

public class MainActivity extends AppCompatActivity implements Callback {
    private static final String PATH = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1226659835,1551636097&fm=26&gp=0.jpg";
    private Button btnGetImage;
    private ImageView ivImage;
    private ImageBean imageBean;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case ImageDownloader.SUCCESS: //成功
                    ivImage.setImageBitmap((Bitmap) msg.obj);
                    break;
                case ImageDownloader.ERROR: //失败
                    Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //返回时导致Activity不能销毁 --> 内存泄漏
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(50000);
            }
        }).start();
    }

    private void initView() {
        btnGetImage = findViewById(R.id.btn_get_image);
        ivImage = findViewById(R.id.iv_image);
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnGetImageClicked();
            }
        });
    }

    private void onBtnGetImageClicked() {
        imageBean = new ImageBean();
        imageBean.setRequestPath(PATH);
        new ImageDownloader().down(this, imageBean);
    }

    @Override
    public void callback(int resultCode, ImageBean imageBean) {
        Message message = handler.obtainMessage(resultCode);
        message.obj = imageBean.getBitmap();
        handler.sendMessageDelayed(message, 500);
    }
}
