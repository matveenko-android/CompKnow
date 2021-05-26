package com.example.compknow;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compknow.MenuFragmentList;
import com.example.compknow.adapter.DataAdapter;
import com.example.compknow.adapter.ListItem;
import com.example.compknow.adapter.RecOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavItemSelectedListener{
    private RecOnClickListener recOnClickListener;
    private DataAdapter adapter;
    private List<ListItem> listData;
    private RecyclerView rcView;
    private String category = "";
    private SharedPreferences pref;
    private final String WINDOWS = "windows";
    private final String LINUX = "linux";
    private final String macOS = "macOS";
    private final String ANDROID = "android";
    private final String IOS = "ios";
    private final String MAT = "mat";
    private final String PRO = "pro";
    private final String VIDEO = "video";
    private final String CYBERSPORT = "Cybersport";
    private final String PK = "PK";
    private final String SOFT = "soft";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMenu();
        setRecOnClickListener();
        init();
    }
    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragmentList mMenuFragment = (MenuFragmentList) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuFragmentList();
            mMenuFragment.setNavItemSelectedListener(this);
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onNavItemSelectedListener(MenuItem item) {
        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId())
            /*case*/{
            case R.id.id_favorite:
                updateFav();
                break;

            case R.id.ia_win:
                updateMainList(getResources().getStringArray(R.array.Windows), WINDOWS);
                break;

            case R.id.ia_lin:
                updateMainList(getResources().getStringArray(R.array.Linux), LINUX);
                break;

            case R.id.ia_mac:
                updateMainList(getResources().getStringArray(R.array.macOS), macOS);
                break;

            case R.id.ia_and:
                updateMainList(getResources().getStringArray(R.array.android), ANDROID);
                break;

            case R.id.ia_ios:
                updateMainList(getResources().getStringArray(R.array.ios), IOS);
                break;

            case R.id.ia_mat:
                updateMainList(getResources().getStringArray(R.array.mat), MAT);
                break;

            case R.id.ia_pro:
                updateMainList(getResources().getStringArray(R.array.pro), PRO);
                break;

            case R.id.ia_vid:
                updateMainList(getResources().getStringArray(R.array.video), VIDEO);
                break;


            case R.id.id_cybersport:
                updateMainList(getResources().getStringArray(R.array.Cybersport), CYBERSPORT);
                break;

            case R.id.id_pk:
                updateMainList(getResources().getStringArray(R.array.PK), PK);
                break;

            case R.id.id_soft:
                updateMainList(getResources().getStringArray(R.array.soft), SOFT);
                break;
        }
    }
    private void updateMainList(String[] array, String cat)
    {
        category = cat;
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();
        String tempCat = pref.getString(cat, "none");
        if (tempCat != null) {
            if (tempCat.equals("none"))
            {
                for (int i = 0; i < array.length; i++)
                {
                    stringBuilder.append("0");
                }
                saveString(stringBuilder.toString());
            }
            else
            {

            }
        }
        List<ListItem> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
        {
            ListItem item = new ListItem();
            item.setText(array[i]);
            item.setCat(cat);
            item.setPosition(i);
            list.add(item);
        }
        adapter.updateList(list, false);
    }
    private void updateFav()
    {
        List<ListItem> listFav = new ArrayList<>();
        List<String[]> listData = new ArrayList<>();
        listData.add(getResources().getStringArray(R.array.Windows));
        listData.add(getResources().getStringArray(R.array.Linux));
        listData.add(getResources().getStringArray(R.array.macOS));
        listData.add(getResources().getStringArray(R.array.android));
        listData.add(getResources().getStringArray(R.array.ios));
        listData.add(getResources().getStringArray(R.array.mat));
        listData.add(getResources().getStringArray(R.array.pro));
        listData.add(getResources().getStringArray(R.array.video));
        listData.add(getResources().getStringArray(R.array.Cybersport));
        listData.add(getResources().getStringArray(R.array.PK));
        listData.add(getResources().getStringArray(R.array.soft));
        String[] cat_array = {WINDOWS, LINUX, macOS, ANDROID, IOS, MAT, PRO, VIDEO, CYBERSPORT, PK, SOFT};


        for (int i = 0; i < listData.size(); i++)
        {

            for(int p = 0; p < listData.get(i).length; p++)
            {
                String d = pref.getString(cat_array[i],"none");
                if(d != null)if(d.charAt(p) == '1')
                {
                    ListItem item = new ListItem();
                    item.setText(listData.get(i)[p]);
                    item.setPosition(p);
                    item.setCat(cat_array[i]);
                    listFav.add(item);
                }

            }
        }
        adapter.updateList(listFav, true);
    }

    private void init()
    {
        pref = getSharedPreferences("CAT", MODE_PRIVATE);
        rcView = findViewById(R.id.rcView);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        listData = new ArrayList<>();
        String[] windows = getResources().getStringArray(R.array.Windows);
        adapter = new DataAdapter(this,recOnClickListener,listData);
        updateMainList(windows, "Windows");
        rcView.setAdapter(adapter);
    }
    private void setRecOnClickListener(){
        recOnClickListener = new RecOnClickListener() {
            @Override
            public void onItemClick(int pos) {
                String tempCat = pref.getString(category, "none");
                if (tempCat != null) {
                    if (tempCat.charAt(pos) == '0') {
                        saveString(replaceCharAtPosition(pos, '1',tempCat));
                    }
                    else
                    {
                        saveString(replaceCharAtPosition(pos, '0',tempCat));
                    }
                }
            }
        };
    }
    private String replaceCharAtPosition(int position, char ch, String st)
    {
        char[] charArray = st.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }
    private void saveString( String stToSave)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(category,stToSave);
        editor.apply();
    }
}