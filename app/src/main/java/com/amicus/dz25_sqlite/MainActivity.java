package com.amicus.dz25_sqlite;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    List<Item> itemsBooks;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerAdapter.OnItemClickListener itemClickListener;
    // ArrayAdapter<>
    AppDatabase db;
    BookDao bookDao;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // активируем toolbar
        db =AppDatabase.getInstance(this);
        bookDao = db.bookDao();
        itemsBooks = new ArrayList<>();
        recyclerView= findViewById(R.id.books_list);

        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemClickListener = new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item book, int position, View itemView) {
                itemSelected(book, position,itemView);
            }
        };
        recyclerAdapter = new RecyclerAdapter (itemsBooks,itemClickListener);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.post(()->{  // установил постоянную высоту recyclerview, т.к. он в android-14 вёл себя странно
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            float heightScreen = metrics.heightPixels;
            ViewGroup.LayoutParams p = recyclerView.getLayoutParams();
            p.height = (int)heightScreen-500;
            recyclerView.setLayoutParams(p);
        });
        fillItemsBooks();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    List<View>listView = new ArrayList<>();  // вспомогательный список
    void itemSelected(Item book, int position,View itemView){  // устанавливаем флаг выбора в элементе и меняем цвет фона
        book.setChoice(!book.isChoice());                      // наверно нужно переделать по правильному позже
        if(book.isChoice()){
            itemView.setBackgroundColor(R.drawable.ic_launcher_background);
            listView.add(itemView);
        }
        else {
            itemView.setBackgroundColor(500003);
            listView.remove(itemView);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    void fillItemsBooks(){          // заполняем список книг данными  из БД
        Executors.newSingleThreadExecutor().execute(()-> {
           // bookDao.insert(new Book(R.drawable.book,"book","author"));
            for(Book b:bookDao.getAllBooks())
                itemsBooks.add(new Item(b.id,b.imageResId,b.name,b.author));
            toolbar.setTitle("Записей: "+itemsBooks.size());

        });

    }


    ////////////toolBar//////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear,menu);
        getMenuInflater().inflate(R.menu.add_menu,menu);
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(MainActivity.this,"Settings choice",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_delete:   // - удаление элементов
                deleteBook();
                return true;
            case R.id.action_add:  //  + добавление элементов
                 addBook();
                return true;
            case R.id.action_clear:  //  -очистка ДБ
                Executors.newSingleThreadExecutor().execute(()->{
                    bookDao.clearTable();
                });
                itemsBooks.clear();  // удаляем элементы
                recyclerAdapter.notifyDataSetChanged();
                toolbar.setTitle("Записей: "+itemsBooks.size());

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void addBook(){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivityForResult(intent,100);
    }
    //////////////////////////////////////////////////////
    void deleteBook(){
        if(itemsBooks.stream().filter(Item::isChoice).findAny().isEmpty()){
        Toast.makeText(MainActivity.this,"" +
                "Выберите элемент для удаления из списка",Toast.LENGTH_LONG).show();
    }else{
        createDialog(); // показываем диалог подтверждения удаления выбранных элементов
    }}

    public void createDialog(){              // диалог удаления элементов списка
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Удалить выбранные элементы");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<Item> collect = itemsBooks.parallelStream() //получаем коллекцию выбранных елементов для удаления
                        .filter(Item::isChoice)
                        .collect(Collectors
                                .toCollection(ArrayList::new));
                itemsBooks.removeAll(collect);
                Executors.newSingleThreadExecutor().execute(()->{// удаляем элементы
                    for(Item b:collect) {
                    bookDao.deleteByNameAndAuthor(b.getName(),b.getAuthor());
                    }
                });
                toolbar.setTitle("Записей: "+itemsBooks.size());
                recyclerAdapter.notifyDataSetChanged();
                listView.forEach(v->{v.setBackgroundColor(500003);}); //крайне корявый способ т.к. пока не нашёл как получить по другому доступ к вьюжкам:(

            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint({"ResourceType", "NotifyDataSetChanged"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==101 &&data!=null){
            var name = data.getStringExtra("name");
            var author =data.getStringExtra("author");


            Executors.newSingleThreadExecutor().execute(()->{// добавляем элемент
                bookDao.insert(new Book(R.drawable.book,name,author));
                for(Book book:bookDao.getAllBooks()) {
                    boolean find=false;
                  for(Item item:itemsBooks)
                      if(book.id==item.getId())find=true;
                  if(!find)itemsBooks.add(new Item(book.id, book.imageResId, book.name, book.author));
                  if(itemsBooks.isEmpty())
                    itemsBooks.add(new Item(book.id, book.imageResId, book.name, book.author));
                }
                toolbar.setTitle("Записей: "+itemsBooks.size());

            });

            recyclerAdapter.notifyDataSetChanged();

        }
    }
}