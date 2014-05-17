package net.netSaver;

import base.TokensBase;
import components.TokenManager.Dialog;
import core.State;
import core.Token;
import fileSavers.FSRStateWrap;
import fileSavers.FSRTransactionWrap;
import net.staticNet.StateWrap;
import net.staticNet.TransactionWrap;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
                for (StateWrap sw : net.getStates()){
                    sb.append(FSRStateWrap.Save(sw));
                }

                sb.append("Переходы:"+n);
                for (TransactionWrap tw : net.getTransactions()){
                    sb.append(FSRTransactionWrap.Save(tw));
                }

            bw.write(sb.toString());
            bw.close();

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
    
}
