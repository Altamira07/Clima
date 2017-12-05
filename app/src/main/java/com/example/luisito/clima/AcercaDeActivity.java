package com.example.luisito.clima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcercaDeActivity extends AppCompatActivity {
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtTel)
    TextView txtTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        ButterKnife.bind(this);
        Linkify.addLinks(txtEmail,Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(txtTel,Linkify.PHONE_NUMBERS);

    }
}
