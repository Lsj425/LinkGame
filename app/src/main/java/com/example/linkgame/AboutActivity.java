package com.example.linkgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;
import me.drakeet.support.about.Contributor;
import me.drakeet.support.about.License;

public class AboutActivity extends AbsAboutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent it = getIntent();
        String theme = it.getStringExtra("theme");
        switch (theme) {
            case "blue":
                setTheme(R.style.AppTheme);
                break;
            case "gold":
                setTheme(R.style.AppTheme_GoldNoActionBar);
                break;
            case "brown":
                setTheme(R.style.AppTheme_BrownNoActionBar);
                break;
            case "green":
                setTheme(R.style.AppTheme_GreenNoActionBar);
                break;
            case "black":
                setTheme(R.style.AppTheme_BlackNoActionBar);
                break;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.drawable.ic_launcher);
        slogan.setText("响指连连看");
        version.setText("v " + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("应用简介"));
        items.add(new Card(getString(R.string.about_introduce)));

        items.add(new Category("开发者"));
        items.add(new Contributor(R.drawable.owl, "三金", "Developer & Designer", "https://github.com/Lsj425"));

        items.add(new Category("开源库引用"));
        items.add(new License("CardStackView", "loopeer", License.APACHE_2, "https://github.com/loopeer/CardStackView"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        items.add(new License("About-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
        items.add(new License("NewbieGuide", "huburt-Hu", License.APACHE_2, "https://github.com/huburt-Hu/NewbieGuide"));
        items.add(new License("ExplosionField", "tyrantgit", License.APACHE_2, "https://github.com/tyrantgit/ExplosionField"));
        items.add(new License("Glide", "bumptech", License.APACHE_2, "com.github.bumptech.glide:glide:4.8.0"));
        items.add(new License("Greenrobot", "greenrobot", License.APACHE_2, "org.greenrobot:greendao:3.2.2"));
        items.add(new License("Dialogs", "afollestad", License.APACHE_2, "com.afollestad.material-dialogs:core:0.9.6.0"));
    }
}
