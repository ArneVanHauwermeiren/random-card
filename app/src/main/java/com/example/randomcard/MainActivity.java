package com.example.randomcard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private Deck deck;
    private ImageButton button;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();

        button = findViewById(R.id.card_button);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SettingsActivity.SettingsFragment.setAppTheme(preferences.getString("app_theme", "System Default"));

        createDeck();
        createCardButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                createDeck();
                button.setImageResource(getCardImageId(new Card(Card.STARTING_CARD_ID)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.refresh_button);
    }

    private void createDeck(){
        if( preferences.getBoolean("game_has_jokers", true)){
            deck = Deck.createDeckWithJokers(Integer.parseInt(preferences.getString("number_of_jokers","0")));
        }else{
            deck = Deck.createStandardDeck();
        }
    }
    private void createCardButton(){
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setImageResource(playCard());
            }
        });
    }

    private int playCard(){
        Card card = deck.getRandomCard();

        if(card.id == Card.STARTING_CARD_ID){
            createDeck();
        }

        if(preferences.getBoolean("play_each_card_once",false)){
            deck.removeCard(card);
        }
        return getCardImageId(card);
    }

    private int getCardImageId(Card card){
        return getResources().getIdentifier("card"+card.id,"drawable", getPackageName());
    }
}
