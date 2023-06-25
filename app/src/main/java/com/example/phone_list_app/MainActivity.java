package com.example.phone_list_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ElementListAdapter.OnItemClickListener {
    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;
    private FloatingActionButton FabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //usunięcie jednego elementu przy użyciu itemTouchHelper
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {//  ItemTouchHelper.LEFT || ItemTouchHelper.RIGHT
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //viewHolder.getAdapterPosition();
                ElementRepository elementRepository = new ElementRepository(getApplication());//.deleteElement(viewHolder.)

                //moja bledna metoda:
                //elementRepository.deleteElementById(viewHolder.getAdapterPosition()+1);
                //viewHolder.

//////////////////////////////////////
                int position = viewHolder.getAdapterPosition();
                Element deletedElement = mAdapter.getElementList().get(position);
                // Delete element from Room database
                mElementViewModel.deleteElement(deletedElement);

                // Delete element from RecyclerView
                mAdapter.deleteElement(position);
                //////////////////////////////////////////////////////

                Toast.makeText(MainActivity.this, "SWIPED:", Toast.LENGTH_SHORT).show();
                Log.d("TouchHelper.edo","Adapter position:"+viewHolder.getAdapterPosition());
                Log.d("TouchHelper.edo","--------------------------------------------");
                /*
                    Trzeba usunąć element o id w bazie danych równemu [viewHolder.getAdapterPosition()+1]
                    Bo numeracja w liście jest od zera do ..N, a w bazie jest numeracja od 1 do ..N-1

                    !najlepiej byłoby mieć obiekt elementu który jest na wybranej pozycji
                */

                mAdapter.notifyDataSetChanged();
            }
        };

        //ustawienie recyclera
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ElementListAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mElementViewModel = new ViewModelProvider(this).get(ElementViewModel.class);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);//podłączenie itemTouchHelper

        //Observer::onChanged -> lambda
        mElementViewModel.getmAllElements().observe(this, elements -> {
            mAdapter.setElementList(elements);
        });

        // Obsługa FAB (Floating Action Button)
        FabButton = findViewById(R.id.fabPlus);
        FabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneEditorActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data", Toast.LENGTH_SHORT).show();
            mElementViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(Element element) {
        Intent intent = new Intent(MainActivity.this, PhoneEditorActivity.class);
        String id = String.valueOf(element.getElement_id());
        //intent.putExtra("element", element);
        intent.putExtra("id",id);
        intent.putExtra("manufacturer",element.getManufacturer());
        intent.putExtra("model",element.getModel());
        String and_ver = String.valueOf(element.getAndroid_version());
        intent.putExtra("android_version",and_ver);
        intent.putExtra("website",element.getWebsite());

        startActivity(intent);
    }





}
