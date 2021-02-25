package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        Generation generation1 = new Generation();
        ArrayList<Player> top_players;
        generation1.InitRandom(100);
        generation1.Play();
        generation1.ShowScores();
        top_players = generation1.GetTopPlayer(0, 20);
//        // Инициализация.
//        int rez;
//        NeuroNet model;
//        Player player1, player2;
//        // Генерация колоды.
//        model = GameBlackJack.GenerateModel();
//        // Добавление игрока1.
//        player1 = new Player();
//        player1.RandomPlayer(model);
//        // Добавление игрока2.
//        player2 = new Player();
//        player2.RandomPlayer(model);
//        // Установка игроков и игра между игроками.
//        GameBlackJack game1 = new GameBlackJack();
//        game1.SetPlayers(player1, player2);
//        rez = game1.Play();
//        System.out.println(rez);
    }
}
