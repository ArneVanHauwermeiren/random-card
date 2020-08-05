package com.example.randomcard;

import java.util.ArrayList;
import java.util.Random;

class Deck {
    private ArrayList<Card> cards = new ArrayList<>();

    static Deck createStandardDeck(){
        Deck deck = new Deck();
        for (int i = 1; i <= 52; i++) {
            Card card = new Card(i);
            deck.cards.add(card);
        }
        return deck;
    }

    @org.jetbrains.annotations.NotNull
    static Deck createDeckWithJokers(int numberOfJokers){
        Deck deck = createStandardDeck();
        deck.addJokers(numberOfJokers);
        return deck;
    }

    private void addJokers(int numberOfJokers){
        for (int i = 1; i <= numberOfJokers; i++) {
            Card card = new Card(Card.JOKER_CARD_ID);
            cards.add(card);
        }
    }

    Card getRandomCard(){
        if(cards.size() < 1){
            return new Card(Card.STARTING_CARD_ID);
        }

        final int min = 0;
        final int max = cards.size()-1;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return cards.get(random);
    }

    void removeCard(Card card){
        cards.remove(card);
    }
}
