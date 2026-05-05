package com.example.menus;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText   etName, etEmail, etPhone, etCourse, etAddress, etNotes;
    TextView   tvResult;
    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName    = findViewById(R.id.etName);
        etEmail   = findViewById(R.id.etEmail);
        etPhone   = findViewById(R.id.etPhone);
        etCourse  = findViewById(R.id.etCourse);
        etAddress = findViewById(R.id.etAddress);
        etNotes   = findViewById(R.id.etNotes);
        tvResult  = findViewById(R.id.tvResult);

        Button btnSubmit     = findViewById(R.id.btnSubmit);
        Button btnMore       = findViewById(R.id.btnMore);
        Button btnActionMenu = findViewById(R.id.btnActionMenu);

        // ── Contextual Menu on Address field ──
        registerForContextMenu(etAddress);

        // ── Submit button ──
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "Name: "    + etName.getText()    + "\n" +
                        "Email: "   + etEmail.getText()   + "\n" +
                        "Phone: "   + etPhone.getText()   + "\n" +
                        "Course: "  + etCourse.getText()  + "\n" +
                        "Address: " + etAddress.getText() + "\n" +
                        "Notes: "   + etNotes.getText();
                tvResult.setText(result);
                tvResult.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Form Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        // ── Popup Menu on More Options button ──
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
            }
        });

        // ── Contextual Action Menu on Long Press of Button ──
        btnActionMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                actionMode = startActionMode(cabCallback);
                return true;
            }
        });
    }

    // ── Options Menu (ActionBar ⋮) ──
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
        return true;
    }

    // ── Contextual Menu (Long press Address field) ──
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
        return true;
    }

    // ── Contextual Action Menu (Long press Button) ──
    private final ActionMode.Callback cabCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Toast.makeText(MainActivity.this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
}