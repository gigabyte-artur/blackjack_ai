package ru.gigabyte_artur.blackjack_ai.xo;

public class XoBoard
{
    private int[][] cells = new int[3][3];      // Доска.

    private static final int EMPTY_VALUE = 0;              // Представление значения пустой ячейки.
    private static final int X_VALUE = 1;              // Представление значения X.
    private static final int O_VALUE = 2;              // Представление значения O.

    public XoBoard()
    {
        this.Init();
    }

    // Инициализирует доску.
    public void Init()
    {
        this.cells[0][0] = EMPTY_VALUE;
        this.cells[0][1] = EMPTY_VALUE;
        this.cells[0][2] = EMPTY_VALUE;

        this.cells[1][0] = EMPTY_VALUE;
        this.cells[1][1] = EMPTY_VALUE;
        this.cells[1][2] = EMPTY_VALUE;

        this.cells[2][0] = EMPTY_VALUE;
        this.cells[2][1] = EMPTY_VALUE;
        this.cells[2][2] = EMPTY_VALUE;
    }

    // Устанавливает в ячейку row_in и col_in значение X.
    public boolean SetXToCell(int row_in, int col_in)
    {
        boolean rez = false;
        if ((row_in >= 0) && (row_in <= 2) && (col_in >= 0) && (col_in <= 2))
        {
            this.cells[row_in][col_in] = X_VALUE;
            rez = true;
        }
        else
        {
            System.out.println("При установке значения X указан некорректный номер ячейки");
            rez = false;
        }
        return rez;
    }

    // Устанавливает в ячейку row_in и col_in значение O.
    public boolean SetOToCell(int row_in, int col_in)
    {
        boolean rez = false;
        if ((row_in >= 0) && (row_in <= 2) && (col_in >= 0) && (col_in <= 2))
        {
            this.cells[row_in][col_in] = O_VALUE;
            rez = true;
        }
        else
        {
            System.out.println("При установке значения X указан некорректный номер ячейки");
            rez = false;
        }
        return rez;
    }

    // Отображает текущую доску в консоли.
    public void Show()
    {
        System.out.println(" | " + this.cells[0][0] + " | " + this.cells[0][1] + " | " + this.cells[0][2] + " | ");
        System.out.println(" | " + this.cells[1][0] + " | " + this.cells[1][1] + " | " + this.cells[1][2] + " | ");
        System.out.println(" | " + this.cells[2][0] + " | " + this.cells[2][1] + " | " + this.cells[2][2] + " | ");
    }

    // Вовзращает значение в ячейке [row_in; col_in].
    public int GetCellValue(int row_in, int col_in)
    {
        int rez = 0;
        rez = this.cells[row_in][col_in];
        return rez;
    }

    public boolean IsEmptyCell(int row_in, int col_in)
    {
        boolean rez = false;
        int cell_value = GetCellValue(row_in, col_in);
        rez = (cell_value == EMPTY_VALUE);
        return rez;
    }

    // Определяет, что на текущей доске есть свободные ячейки.
    public boolean HaveEmpty()
    {
        boolean rez = false;
        rez = (IsEmptyCell(0, 0) || IsEmptyCell(0, 1) || IsEmptyCell(0, 2) ||
                IsEmptyCell(1, 0) || IsEmptyCell(1, 1) || IsEmptyCell(1, 2) ||
                IsEmptyCell(2, 0) || IsEmptyCell(2, 1) || IsEmptyCell(2, 2));
        return rez;
    }

    public static int getEMPTY_VALUE()
    {
        return EMPTY_VALUE;
    }

    public static int getX_VALUE()
    {
        return X_VALUE;
    }

    public static int getO_VALUE()
    {
        return O_VALUE;
    }

    public static int GetRowByNumberCell(int cell_number_in)
    {
        int rez = 0;
        rez = cell_number_in / 3;
        return rez;
    }

    public static int GetColByNumberCell(int cell_number_in)
    {
        int rez = 0;
        rez = cell_number_in % 3;
        return rez;
    }

    // Определяет, что value_in победил по всем строкам и колонкам.
    private boolean isWinner(int row_low_in, int col_low_in, int row_hi_in, int col_hi_in, int value_in)
    {
        boolean rez = true;
        int curr_value;
        for (int row = row_low_in; row <= row_hi_in; row++)
        {
            for (int col = col_low_in; col <=col_hi_in; col++)
            {
                curr_value = GetCellValue(row, col);
                if (curr_value != value_in)
                {
                    rez = false;
                    break;
                }
                else
                {
                    // Продолжаем поиск.
                }
            }
        }
        return rez;
    }

    // Определяет победителя на текущей доске. -1 - ничья.
    public int ChooseWinner()
    {
        // Инициализация.
        int rez = -1;
        // Строки X.
        boolean win_row0_X = isWinner(0, 0, 0, 2, X_VALUE);
        boolean win_row1_X = isWinner(1, 0, 1, 2, X_VALUE);
        boolean win_row2_X = isWinner(2, 0, 2, 2, X_VALUE);
        // Строки O.
        boolean win_row0_O = isWinner(0, 0, 0, 2, O_VALUE);
        boolean win_row1_O = isWinner(1, 0, 1, 2, O_VALUE);
        boolean win_row2_O = isWinner(2, 0, 2, 2, O_VALUE);
        // Колонки X.
        boolean win_col0_X = isWinner(0, 0, 2, 0, X_VALUE);
        boolean win_col1_X = isWinner(0, 1, 2, 1, X_VALUE);
        boolean win_col2_X = isWinner(0, 2, 2, 2, X_VALUE);
        // Колонки O.
        boolean win_col0_O = isWinner(0, 0, 2, 0, O_VALUE);
        boolean win_col1_O = isWinner(0, 1, 2, 1, O_VALUE);
        boolean win_col2_O = isWinner(0, 2, 2, 2, O_VALUE);
        // Диагонали X.
        boolean win_d1_X = ((GetCellValue(0, 0) == X_VALUE) && (GetCellValue(1, 1) == X_VALUE) && (GetCellValue(2, 2) == X_VALUE));
        boolean win_d2_X = ((GetCellValue(2, 0) == X_VALUE) && (GetCellValue(1, 1) == X_VALUE) && (GetCellValue(2, 0) == X_VALUE));
        // Диагонали O.
        boolean win_d1_O = ((GetCellValue(0, 0) == O_VALUE) && (GetCellValue(1, 1) == O_VALUE) && (GetCellValue(2, 2) == O_VALUE));
        boolean win_d2_O = ((GetCellValue(2, 0) == O_VALUE) && (GetCellValue(1, 1) == O_VALUE) && (GetCellValue(2, 0) == O_VALUE));
        // Выбор X.
        if (win_row0_X || win_row1_X || win_row2_X || win_col0_X || win_col1_X || win_col2_X || win_d1_X || win_d2_X)
        {
            rez = 1;
        }
        // Выбор O.
        if (win_row0_O || win_row1_O || win_row2_O || win_col0_O || win_col1_O || win_col2_O || win_d1_O || win_d2_O)
        {
            rez = 2;
        }
        return rez;
    }
}
