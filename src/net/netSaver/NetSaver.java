package net.netSaver;

import base.TokensBase;
import components.TokenManager.Dialog;
import core.State;
import fileSavers.FSRStateWrap;
import net.staticNet.StateWrap;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by deanto on 16.05.14.
 */
public class NetSaver {

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
