package ru.gigabyte_artur.blackjack_ai.xo;

import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.gaming.TwoPlayersGame;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;
import java.util.HashMap;
import java.util.Map;

import static ru.gigabyte_artur.blackjack_ai.xo.XoBoard.getEMPTY_VALUE;

public class XoPlayer extends Player
{

    private int side;           // На какой стороне играет игрок (X или O).

    // Конструктор.
    public XoPlayer()
    {
        NeuroNet neuronet1 = GameXO.GenerateModel();
        this.SetNeuroNet(neuronet1);
        this.side = XoBoard.getX_VALUE();
    }

    public XoPlayer(Player player_in)
    {
        this.SetNeuroNetCopyFrom(player_in.GetNeuroNet());
    }

    public XoPlayer copy()
    {
        return new XoPlayer(this);
    }

    // Устанавливает для входного нейрона в сети net_chng с номером neuron_number_in значение, если в доске board_in
    // в ячейке row_in и col_in совпадает со значением value_in.
    private void SetInputSignal(XoBoard board_in, int row_in, int col_in, int value_in, int neuron_number_in, NeuroNet net_chng)
    {
        if (board_in.GetCellValue(row_in, col_in) == value_in)
        {
            net_chng.SetInputSignal(neuron_number_in, 1);
        }
        else
        {
            net_chng.SetInputSignal(neuron_number_in, 0);
        }
    }

    // Выставляет входные сигналы для текущего игрока из игры game_in.
    private void InputSignals(GameXO game_in)
    {
        NeuroNet net1;
        int EMPTY_VALUE = XoBoard.getEMPTY_VALUE();
        int X_VALUE = XoBoard.getX_VALUE();
        int O_VALUE = XoBoard.getO_VALUE();
        net1 = super.GetNeuroNet();
        XoBoard curr_board = game_in.getBoard();
        SetInputSignal(curr_board, 0, 0, EMPTY_VALUE, 0, net1);
        SetInputSignal(curr_board, 0, 1, EMPTY_VALUE, 1, net1);
        SetInputSignal(curr_board, 0, 2, EMPTY_VALUE, 2, net1);

        SetInputSignal(curr_board, 1, 0, EMPTY_VALUE, 3, net1);
        SetInputSignal(curr_board, 1, 1, EMPTY_VALUE, 4, net1);
        SetInputSignal(curr_board, 1, 2, EMPTY_VALUE, 5, net1);

        SetInputSignal(curr_board, 2, 0, EMPTY_VALUE, 6, net1);
        SetInputSignal(curr_board, 2, 1, EMPTY_VALUE, 7, net1);
        SetInputSignal(curr_board, 2, 2, EMPTY_VALUE, 8, net1);

        SetInputSignal(curr_board, 0, 0, X_VALUE, 9, net1);
        SetInputSignal(curr_board, 0, 1, X_VALUE, 10, net1);
        SetInputSignal(curr_board, 0, 2, X_VALUE, 11, net1);

        SetInputSignal(curr_board, 1, 0, X_VALUE, 12, net1);
        SetInputSignal(curr_board, 1, 1, X_VALUE, 13, net1);
        SetInputSignal(curr_board, 1, 2, X_VALUE, 14, net1);

        SetInputSignal(curr_board, 2, 0, X_VALUE, 15, net1);
        SetInputSignal(curr_board, 2, 1, X_VALUE, 16, net1);
        SetInputSignal(curr_board, 2, 2, X_VALUE, 17, net1);

        SetInputSignal(curr_board, 0, 0, O_VALUE, 18, net1);
        SetInputSignal(curr_board, 0, 1, O_VALUE, 19, net1);
        SetInputSignal(curr_board, 0, 2, O_VALUE, 20, net1);

        SetInputSignal(curr_board, 1, 0, O_VALUE, 21, net1);
        SetInputSignal(curr_board, 1, 1, O_VALUE, 22, net1);
        SetInputSignal(curr_board, 1, 2, O_VALUE, 23, net1);

        SetInputSignal(curr_board, 2, 0, O_VALUE, 24, net1);
        SetInputSignal(curr_board, 2, 1, O_VALUE, 25, net1);
        SetInputSignal(curr_board, 2, 2, O_VALUE, 26, net1);

        if (this.getSide() == X_VALUE)
        {
            net1.SetInputSignal(27, 1);
        }
        else
        {
            net1.SetInputSignal(27, 0);
        }
        super.SetNeuroNet(net1);
    }

    // Принимает решение о следующем ходе. Возвращает номер поля на доске, куда поставить значение
    private int Decide(XoBoard board_in)
    {
        int rez = 0, curr_cell, row_number, col_number;
        double curr_value,  max_value = -999;
        this.neuro_net.CalcSignals();
        HashMap<Integer,Double> output_signals = this.neuro_net.GetArrayOutputSignals();
        for (Map.Entry<Integer, Double> current_signal:output_signals.entrySet())
        {
            curr_value = current_signal.getValue();
            if (curr_value > max_value)
            {
                curr_cell = current_signal.getKey();
                row_number = XoBoard.GetRowByNumberCell(curr_cell);
                col_number = XoBoard.GetColByNumberCell(curr_cell);
                if (board_in.IsEmptyCell(row_number, col_number))
                {
                    max_value = curr_value;
                    rez = curr_cell;
                }
                else
                {
                    // Поле занято. Нельзя поставить значение.
                }
            }
            else
            {
                // Выполняем поиск далее.
            }
        }
        return rez;
    }

    // Осуществляет ход текущего игрока.
    private void Turn(GameXO game_in, boolean silent_in)
    {
        int row_number, col_number;
        if (game_in instanceof GameXO)
        {
            int X_VALUE = XoBoard.getX_VALUE();
            int O_VALUE = XoBoard.getO_VALUE();
            XoBoard board1 = game_in.getBoard();
            this.InputSignals(game_in);
            int desicion = this.Decide(board1);
            row_number = XoBoard.GetRowByNumberCell(desicion);
            col_number = XoBoard.GetColByNumberCell(desicion);
            if (this.getSide() == X_VALUE)
            {
                board1.SetXToCell(row_number, col_number);
            }
            else
            {
                board1.SetOToCell(row_number, col_number);
            }
        }
    }

    // Играет текущим игроком игру game_in. Возвращает итоговую сумму очков. Параметр silent_in определяет, будут
    // ли выводиться данные в консоль.
    @Override
    public int Play(TwoPlayersGame game_in, boolean silent_in)
    {
        int rez;
        rez = 0;
        if (game_in instanceof GameXO)
        {
            this.Turn((GameXO)game_in, silent_in);
        }
        else
        {
            rez = 0;
        }
        return rez;
    }

    // Устанавливает игроку сторону.
    public void setSide(int side_in)
    {
        this.side = side_in;
    }

    // Возвращает сторону игрока.
    public int getSide()
    {
        return this.side;
    }
}
