package com.mahdi20.realm;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mahdi20.realm.adapter.RecyclerViewAdapter;
import com.mahdi20.realm.model.PhoneBook;
import com.mahdi20.realm.model.RealmTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    private Realm realm;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        realm = Realm.getDefaultInstance();
        Toast.makeText(this, "Hi, Wlc, This is a sample!", Toast.LENGTH_LONG).show();

        RefreshRcy();

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertRecord();
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRecord();
            }
        });

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateRecord();
            }
        });


        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchRecord();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "mahdi.asodeh@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {


            String url = "https://t.me/MahdiAsodeh";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void RefreshRcy() {

        try {
            List<PhoneBook> data = new ArrayList<>();
            RealmResults<RealmTable> results = realm.where(RealmTable.class).findAll();
            for (RealmTable c : results) {
                data.add(new PhoneBook(c.getName(), c.getTell()));
            }

            recyclerView = (RecyclerView) findViewById(R.id.rcy);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.notifyDataSetChanged();
        } catch (Exception e) {

        }

    }

    private void deleteRecord() {


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Delete");
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtTell = (EditText) dialog.findViewById(R.id.edtTell);
        Button btnDialog = (Button) dialog.findViewById(R.id.btnDialog);
        edtName.setVisibility(View.GONE);
        final EditText edtTellOld = (EditText) dialog.findViewById(R.id.edtTellOld);
        edtTellOld.setVisibility(View.GONE);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    realm.beginTransaction();
//                RealmTable realmResults = realm.where(RealmTable.class).findAll();
                    RealmTable realmTable = realm.where(RealmTable.class).equalTo
                            ("tell", edtTell.getText().toString()).findFirst();
                    realmTable.removeFromRealm();
                    realm.commitTransaction();
                } catch (Exception e) {

                }
                RefreshRcy();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void insertRecord() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Add");
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtTell = (EditText) dialog.findViewById(R.id.edtTell);
        Button btnDialog = (Button) dialog.findViewById(R.id.btnDialog);
        final EditText edtTellOld = (EditText) dialog.findViewById(R.id.edtTellOld);
        edtTellOld.setVisibility(View.GONE);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtName.getText().toString().equals("") || edtTell.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Fill in the blanks!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    realm.beginTransaction();
                    RealmTable realmTable = realm.createObject(RealmTable.class);
                    realmTable.setId(String.valueOf(System.currentTimeMillis()));
                    realmTable.setName(edtName.getText().toString().trim());
                    realmTable.setTell(edtTell.getText().toString().trim());
                    realm.commitTransaction();
                    edtName.setText("");
                    edtTell.setText("");
                    Toast.makeText(MainActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "error!", Toast.LENGTH_SHORT).show();
                }


                RefreshRcy();
                dialog.dismiss();

            }
        });
        dialog.show();


    }

    public void updateRecord() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Update");
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtTell = (EditText) dialog.findViewById(R.id.edtTell);
        final EditText edtTellOld = (EditText) dialog.findViewById(R.id.edtTellOld);
//        edtTellOld.setVisibility(View.GONE);
        Button btnDialog = (Button) dialog.findViewById(R.id.btnDialog);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edtName.getText().toString().equals("") || edtTell.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Fill in the blanks", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    realm.beginTransaction();
                    RealmTable realmTable = realm.where(RealmTable.class).equalTo
                            ("tell", edtTellOld.getText().toString()).findFirst();

                    realmTable.setName(edtName.getText().toString());
                    realmTable.setTell(edtTell.getText().toString());
                    realm.commitTransaction();


                    edtName.setText("");
                    edtTell.setText("");
                    Toast.makeText(MainActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "error!", Toast.LENGTH_SHORT).show();
                }

                RefreshRcy();
                dialog.dismiss();

            }
        });
        dialog.show();


    }

    public void searchRecord() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Search");

        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtTell = (EditText) dialog.findViewById(R.id.edtTell);
        final EditText edtTellOld = (EditText) dialog.findViewById(R.id.edtTellOld);

        edtTellOld.setVisibility(View.GONE);
        edtName.setVisibility(View.GONE);

        Button btnDialog = (Button) dialog.findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtTell.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Fill in the blanks", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    List<PhoneBook> data = new ArrayList<>();
                    RealmResults<RealmTable> results = realm.where(RealmTable.class).findAll();
                    for (RealmTable c : results) {
                        if (c.getTell().equals(edtTell.getText().toString())) {
                            data.add(new PhoneBook(c.getName(), c.getTell()));
                        }
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.rcy);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplication());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "error!", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();

            }
        });
        dialog.show();


    }
}
