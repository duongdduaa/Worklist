package com.example.a;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import javax.xml.transform.OutputKeys;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<Work> worklist;
    private Database database;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        database = new Database(this);

        recyclerView.setAdapter(adapter);
        worklist = database.getAll();

        adapter.setData(worklist);

        recyclerView.smoothScrollToPosition(worklist.size());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                Work work = worklist.get(position);
                worklist.remove(position);
                database.delete(work.getId());
                worklist = database.getAll();
                adapter.setData(worklist);
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return super.getMovementFlags(recyclerView, viewHolder);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnClickEvent(new Adapter.OnClickEvent() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Work work = worklist.get(position);
                intent.putExtra("data", work);
                startActivityForResult(intent, 2);
            }

            @Override
            public void OnItemDelete(int position) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intent, 1);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            worklist = database.getAll();
            adapter.setData(worklist);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Work work = (Work) data.getSerializableExtra("back");
            database.update(work);
            worklist = database.getAll();
            adapter.setData(worklist);
        }
    }
}
