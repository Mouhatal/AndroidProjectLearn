package gaaynako.transpay.androidretrofit.config;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .allowWritesOnUiThread(true)
                        .build();
        Realm.setDefaultConfiguration(config);
    }
}
