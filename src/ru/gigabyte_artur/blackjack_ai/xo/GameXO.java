package ru.gigabyte_artur.blackjack_ai.xo;

import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.gaming.TwoPlayersGame;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public class GameXO extends TwoPlayersGame
{

    private XoBoard board = new XoBoard();      // Доска, на которой играем.

    public int Play()
    {
        int rez = -1, counter = 0;
        Player curr_player1, curr_player2;
        int X_VALUE = XoBoard.getX_VALUE();
        int O_VALUE = XoBoard.getO_VALUE();
        board.Init();
        curr_player1 = this.getPlayer1();
        if (curr_player1 instanceof XoPlayer)
        {
            ((XoPlayer)curr_player1).setSide(X_VALUE);
        }
        curr_player2 = this.getPlayer2();
        if (curr_player2 instanceof XoPlayer)
        {
            ((XoPlayer)curr_player2).setSide(O_VALUE);
        }
        while ((board.HaveEmpty()) && (rez == -1) && (counter < 1000))
        {
            curr_player1.Play(this, true);
            rez = board.ChooseWinner();
            if (rez == -1)
            {
                curr_player2.Play(this, true);
                rez = board.ChooseWinner();
            }
            counter = counter + 1;
//            board.Show();
//            System.out.println("");
        }
//        System.out.println("------");
        return rez;
    }

    @Override
    public void Init()
    {

    }

    @Override
    public Player NewPlayer()
    {
        XoPlayer rez = new XoPlayer();
        return rez;
    }

    // Возвращает пустую нейронную сеть для новой игры.
    public static NeuroNet GenerateModel()
    {
        NeuroNet rez = new NeuroNet();
        rez.GenerateAddDenseLayer(28, true, false);
        rez.GenerateAddDenseLayer(28, false, false);
        rez.GenerateAddDenseLayer(28, false, false);
        rez.GenerateAddDenseLayer(28, false, false);
        rez.GenerateAddDenseLayer(9, false, true);
        rez.Compile();
        return rez;
    }

    // Возвращает доску текущей игры.
    public XoBoard getBoard()
    {
        return board;
    }
}
