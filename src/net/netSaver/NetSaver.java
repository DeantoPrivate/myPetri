package net.netSaver;

import base.TokensBase;
import components.Constructor.GraphPanel;
import components.GraphicalElements.StateElement;
import components.GraphicalElements.TransactionRuleElement;
import components.TokenManager.Dialog;
import core.IncomingTransitionRule;
import core.OutgoingTransitionRule;
import core.State;
import core.Token;
import fileSavers.FSRStateWrap;
import fileSavers.FSRTransactionWrap;
import net.staticNet.StateWrap;
import net.staticNet.TransactionRuleWrap;
import net.staticNet.TransactionWrap;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by deanto on 16.05.14.
 */
public class NetSaver {

    public static State getState(String stateName){
        return _states.get(stateName);
    }

    public static Token getToken(String tokenName){
        return (_tokens.get(tokenName)).Clone();
    }

    private static Hashtable<String,Token> _tokens;
    private static Hashtable<String,State> _states;

    public static void SaveNet(netStaticImpl net){
        try{
            FileWriter fw = new FileWriter(ChooseFile());
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder sb = new StringBuilder();



            String n = System.getProperty("line.separator");
            // сохраниение

                // сохранить базу обязательно сначала
                Dialog.Save();

                // сохраним список всех состояний


                sb.append("Состояния:"+n);
                sb.append(net.getStates().size()+n);
                for (StateWrap sw : net.getStates()){
                    sb.append(FSRStateWrap.Save(sw));
                }

                sb.append("Переходы:"+n);
                sb.append(net.getTransactions().size()+n);
                for (TransactionWrap tw : net.getTransactions()){
                    sb.append(FSRTransactionWrap.Save(tw));
                }

            bw.write(sb.toString());
            bw.close();
            fw.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Сохранение сети не удалось!");
        }
    }

    private static String ChooseFile(){

        String filename = null;

        while(filename==null){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить сеть");
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        filename = file.getAbsolutePath();

        }

        return filename;
    }

    public static netStaticImpl ReadNet(){

        netStaticImpl newNet = netStaticImpl.newNet();
        Dialog.ShowDialog();

        try{

            // сначала загрузим базу.
            Dialog.Load();

            _states = new Hashtable<String, State>();
            _tokens = new Hashtable<String, Token>();

            // загрузим табличку с токенами
            for (Token token : TokensBase.GetTokenBase().GetTokens()){
                _tokens.put(token.GetName(),token);
            }

        JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Открыть файл с сетью");
            fileChooser.showOpenDialog(null);
            File file = fileChooser.getSelectedFile();

            String filename = file.getAbsolutePath();

            StringBuffer fileBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(filename));


            ArrayList<String> strings = new ArrayList<String>();
            String nextString = "";
            while((nextString = (reader.readLine())) != null){
                strings.add(nextString);
            }

        // читаем состояния
            strings.remove(0);
            int statesCount = new Integer(strings.get(0));
            strings.remove(0);

            ArrayList<StateWrap> states = new ArrayList<StateWrap>();

            for (int i=0;i<statesCount;i++){
                StateWrap sw = FSRStateWrap.Read(strings);
                states.add(sw);
                _states.put(sw.get_state().GetName(),sw.get_state());
            }

        // читаем переходы
            strings.remove(0);
            int transactionCount = new Integer(strings.get(0));
            strings.remove(0);

            ArrayList<TransactionWrap> transactions = new ArrayList<TransactionWrap>();

            for (int i=0;i<transactionCount;i++)
                transactions.add(FSRTransactionWrap.Read(strings));


        // теперь все нужно положить в сеть


            newNet.AddStates(states);
            newNet.AddTransactions(transactions);


            ArrayList<TransactionRuleWrap> transactionRuleWraps = new ArrayList<TransactionRuleWrap>();

            for (TransactionWrap tw : transactions){

                for (IncomingTransitionRule itr : tw.get_transaction().get_incomingTransitionRules()){

                    TransactionRuleElement tre = new TransactionRuleElement(GraphPanel.getJPanelForElements());

                    StateElement se = null;

                    for (StateWrap sw : states){
                        if (sw.get_state().GetName().equals(itr.getState()))
                            se = (StateElement)sw.get_element();
                    }

                    tre.setStates(se,tw.get_element());

                    TransactionRuleWrap trw = new TransactionRuleWrap(itr,tre);
                    transactionRuleWraps.add(trw);
                }


                for (OutgoingTransitionRule otr : tw.get_transaction().get_outgoingTransitionRules()){

                    TransactionRuleElement tre = new TransactionRuleElement(GraphPanel.getJPanelForElements());

                    StateElement se = null;

                    for (StateWrap sw : states){
                        if (sw.get_state().GetName().equals(otr.getState()))
                            se = (StateElement)sw.get_element();
                    }

                    tre.setStates(tw.get_element(),se);

                    TransactionRuleWrap trw = new TransactionRuleWrap(otr,tre);
                    transactionRuleWraps.add(trw);
                }

            }

            newNet.AddTransactionRules(transactionRuleWraps);
            reader.close();
            newNet.SyncGElements();
            return newNet;


        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Чтение сети не удалось!");
            return null;
        }


    }
}
