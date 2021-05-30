package ru.gigabyte_artur.blackjack_ai;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException
    {
        String filename = "D:\\cars_last.xml";
        Generation generation1 = new Generation();
////        if (new File(filename).isFile())
////            generation1.LoadFromFile(filename);
////        else
//            generation1.InitRandom(100);
//        Generation generation2 = new Generation();
//        Selection selection1 = new Selection();
//        for (int i = 0; i < 100000; i++)
//        {
//            System.out.print(i + ": ");
//            generation1.SetGamesInSeries(10);
//            generation1.Play();
//            generation1.ShowStatic();
//            generation2 = selection1.MakeSelection(generation1);
//            generation1 = generation2;
//            if (i % 100 == 0)
//                generation2.SaveToFile(filename);
//        }
        generation1.LoadFromFile(filename);

    }
}
