package ru.gigabyte_artur.blackjack_ai;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Generation
{
    private ArrayList<Player> Players = new ArrayList<>();
    ScoreGen scores = new ScoreGen();

    // Добавляет игрока player_in в список игроков.
    public void AddPlayer(Player player_in)
    {
        this.Players.add(player_in);
    }

    // Инициализирует поколение случайными игроками.
    public void InitRandom(int generation_size_in)
    {
        Player added_player;
        NeuroNet model;
        model = GameBlackJack.GenerateModel();
        this.Players.clear();
        for (int i = 0; i < generation_size_in; i++)
        {
            added_player = new Player();
            added_player.RandomPlayer(model);
            this.Players.add(added_player);
        }
    }

    // Разыгрывает игры между игроками. Помещает результаты в хранилище результатов.
    public void Play()
    {
        GameBlackJack game1;
        int winner_id;
        for (Player player1: this.Players)
        {
            for (Player player2: this.Players)
            {
                if (!player1.equals(player2))
                {
                    game1 = new GameBlackJack();
                    game1.SetPlayers(player1, player2);
                    winner_id = game1.Play();
                    if (winner_id == 1)
                    {
                        this.scores.IncreaseScorePlayer(player1, 1);
                        this.scores.IncreaseScorePlayer(player2, 0);
                    }
                    else if (winner_id == 2)
                    {
                        this.scores.IncreaseScorePlayer(player1, 0);
                        this.scores.IncreaseScorePlayer(player2, 1);
                    }
                    else
                    {
                        // Ничья. Не изменяем результат.
                        this.scores.IncreaseScorePlayer(player1, 0);
                        this.scores.IncreaseScorePlayer(player2, 0);
                    }
                }
            }
        }
    }

    // Отображает результаты игр текущего поколения.
    public void ShowScores()
    {
        this.scores.ShowScores();
    }

    // Вовзращает игроков с результатами от min_in до max_in места.
    public ArrayList<Player> GetTopPlayer(int min_in, int max_in)
    {
        return this.scores.GetTopPlayer(min_in, max_in);
    }

    // Отображает статистику игры.
    public void ShowStatic()
    {
        this.scores.ShowStatic();
    }

    // Сохраняет текущее поколение в файл с именем file_name_in.
    public void SaveToFile(String file_name_in)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Neuron SourceNeuron;
            // Поколение.
            Element root_element = doc.createElement("generation");
            doc.appendChild(root_element);
            // Игроки.
            Element players_element = doc.createElement("players");
            root_element.appendChild(players_element);
            for (Player curr_player:this.Players)
            {
                // Игрок.
                Element player_element = doc.createElement("player");
                players_element.appendChild(player_element);
                // Нейросеть.
                Element neuro_net_element = doc.createElement("neuro");
                player_element.appendChild(neuro_net_element);
                // Слои.
                Element layers_element = doc.createElement("layers");
                neuro_net_element.appendChild(layers_element);
                for (Layer curr_layer:curr_player.GetNeuroNet().GetLayers())
                {
                    // Слой.
                    Element layer_element = doc.createElement("layer");
                    layers_element.appendChild(layer_element);
                    // Идентификатор слоя.
                    Element layer_id_element = doc.createElement("id");
                    layer_element.appendChild(layer_id_element);
                    // Текст идентификатора.
                    Text layer_id_text = doc.createTextNode(curr_layer.GetId());
                    layer_id_element.appendChild(layer_id_text);
                    // Нейроны.
                    Element neurons_element = doc.createElement("neurons");
                    layer_element.appendChild(neurons_element);
                    for (Neuron curr_neuron:curr_layer.GetNeurons())
                    {
                        // Нейрон.
                        Element neuron_element = doc.createElement("neuron");
                        neurons_element.appendChild(neuron_element);
                        // Идентификатор нейрона.
                        Element neuron_id_element = doc.createElement("id");
                        neuron_element.appendChild(neuron_id_element);
                        // Текст идентификатора.
                        Text neuron_id_text = doc.createTextNode(curr_neuron.GetId());
                        neuron_id_element.appendChild(neuron_id_text);
                        // Аксоны.
                        Element axons_element = doc.createElement("axons");
                        neuron_element.appendChild(axons_element);
                        for (Axon curr_axon:curr_neuron.GetAxons())
                        {
                            // Аксоны.
                            Element axon_element = doc.createElement("axon");
                            axons_element.appendChild(axon_element);
                            // id нейрона.
                            Element source_neuron_id_element = doc.createElement("neuron_id");
                            axon_element.appendChild(source_neuron_id_element);
                            // Вес связи.
                            Element weight_element = doc.createElement("weight");
                            axon_element.appendChild(weight_element);
                            // Входной нейрон.
                            SourceNeuron = curr_axon.GetSource();
                            if (SourceNeuron != null)
                            {
                                // Текст идентификатора.
                                Text input_neuron_id_text = doc.createTextNode(SourceNeuron.GetId());
                                source_neuron_id_element.appendChild(input_neuron_id_text);
                                // Вес связи.
                                Text weight_text = doc.createTextNode(String.valueOf(curr_axon.GetWeight()));
                                weight_element.appendChild(weight_text);
                            }
                            else
                            {
                                // Текст идентификатора.
                                Text input_neuron_id_text = doc.createTextNode("");
                                source_neuron_id_element.appendChild(input_neuron_id_text);
                                // Вес связи.
                                Text weight_text = doc.createTextNode("0");
                                weight_element.appendChild(weight_text);
                            }
                        }
                    }
                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file_name_in));
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Загружает поколение из файла с именем file_name_in.
    public Generation LoadFromFile(String file_name_in) throws ParserConfigurationException, IOException, SAXException
    {
        Generation rez = new Generation();
        try
        {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(file_name_in);
            // Получаем корневой элемент
            Node generaion_item = document.getDocumentElement();
            Generation new_generation = new Generation();
            // Игроки.
            NodeList players_list = generaion_item.getChildNodes();
            for (int c_players_list = 0; c_players_list < players_list.getLength(); c_players_list++)
            {
                Node curr_players_item = players_list.item(c_players_list);
                // Игрок.
                NodeList player_list_items = curr_players_item.getChildNodes();
                for (int c_player_list = 0; c_player_list < player_list_items.getLength(); c_player_list++)
                {
                    Node curr_player_item = player_list_items.item(c_player_list);
                    Player new_player = new Player();
                    // Нейросети.
                    NodeList neuro_list_items = curr_player_item.getChildNodes();
                    for (int c_neuro_list = 0; c_neuro_list < neuro_list_items.getLength(); c_neuro_list++)
                    {
                        // Нейросеть.
                        Node curr_neuro_item = neuro_list_items.item(c_neuro_list);
                        NeuroNet new_neuro_net = new NeuroNet();
                        Layer last_layer = new Layer();
                        // Слои.
                        NodeList layers_list_items = curr_neuro_item.getChildNodes();
                        for (int c_layers_list = 0; c_layers_list < layers_list_items.getLength(); c_layers_list++)
                        {
                            Node curr_layers_item = layers_list_items.item(c_layers_list);
                            // Слой.
                            NodeList layer_list_items = curr_layers_item.getChildNodes();
                            for (int c_layer_list = 0; c_layer_list < layer_list_items.getLength(); c_layer_list++)
                            {
                                Node curr_layer_item = layer_list_items.item(c_layer_list);
                                Layer new_layer = new Layer();
                                String Layer_id = "";
                                // Потомки слоя.
                                NodeList layer_attr_list_items = curr_layer_item.getChildNodes();
                                for (int c_layer_attr_list = 0; c_layer_attr_list < layer_list_items.getLength(); c_layer_attr_list++)
                                {
                                    Node curr_layer_attr_item = layer_attr_list_items.item(c_layer_attr_list);
                                    if (curr_layer_attr_item != null)
                                    {
                                        if (curr_layer_attr_item.getNodeName() == "id")
                                        {
                                            Layer_id = curr_layer_attr_item.getTextContent();
                                        }
                                        else if (curr_layer_attr_item.getNodeName() == "neurons")
                                        {
                                            NodeList neurons_list_item = curr_layer_attr_item.getChildNodes();
                                            for (int c_neurons_list_item = 0; c_neurons_list_item < neurons_list_item.getLength(); c_neurons_list_item++)
                                            {
                                                Node curr_neurons_list_item = neurons_list_item.item(c_neurons_list_item);
                                                // Нейрон.
                                                Neuron new_neuron = new Neuron();
                                                String neuron_id = "";
                                                NodeList neuron_list_item = curr_neurons_list_item.getChildNodes();
                                                for (int c_neuron_list_item = 0; c_neuron_list_item < neuron_list_item.getLength(); c_neuron_list_item++)
                                                {
                                                    Node curr_neuron_list_item = neuron_list_item.item(c_neuron_list_item);
                                                    if (curr_neuron_list_item.getNodeName() == "id")
                                                    {
                                                        // Идентификатор нейрона.
                                                        neuron_id = curr_neuron_list_item.getTextContent();
                                                    }
                                                    else if (curr_neuron_list_item.getNodeName() == "axons")
                                                    {
                                                        // Аксоны.
                                                        NodeList axons_list_item = curr_neuron_list_item.getChildNodes();
                                                        for (int c_axons_list_item = 0; c_axons_list_item < axons_list_item.getLength(); c_axons_list_item++)
                                                        {
                                                            Node curr_axon_list_item = axons_list_item.item(c_axons_list_item);
                                                            NodeList axon_child_list_item = curr_axon_list_item.getChildNodes();
                                                            // Аксон.
                                                            Axon new_axon = new Axon();
                                                            double new_weight = 0;
                                                            String last_neuron_id = "";
                                                            for (int c_axon_child_list_item = 0; c_axon_child_list_item < axon_child_list_item.getLength(); c_axon_child_list_item++)
                                                            {
                                                                Node curr_axon_child_item = axon_child_list_item.item(c_axon_child_list_item);
                                                                if (curr_axon_child_item.getNodeName() == "weight")
                                                                {
                                                                    // Вес связи.
                                                                    new_weight = Double.valueOf(curr_axon_child_item.getTextContent());
                                                                }
                                                                else if (curr_axon_child_item.getNodeName() == "neuron_id")
                                                                {
                                                                    // Предыдущий нейрон связи.
                                                                    last_neuron_id = curr_axon_child_item.getTextContent();
                                                                }
                                                            }

                                                            if (last_neuron_id != "")
                                                            {
                                                                Neuron last_neuron = last_layer.FindNeuronById(last_neuron_id);
                                                                new_axon.Set(last_neuron, new_weight);
                                                            }
                                                            else
                                                            {
                                                                //new_axon.Set(last_neuron, new_weight);
                                                            }
                                                            new_neuron.AddAxon(new_axon);
                                                        }
                                                    }
                                                }
                                                new_neuron.SetId(neuron_id);
                                            }
                                        }
                                    }
                                }
                                new_layer.SetId(Layer_id);
                                new_neuro_net.AddLayer(new_layer);
                                last_layer = new_layer;
                            }
                        }
                        new_player.SetNeuroNet(new_neuro_net);
                    }
                    new_generation.AddPlayer(new_player);
                }
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return rez;
    }
}
