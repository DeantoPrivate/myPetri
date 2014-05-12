package fileSavers;

import com.sun.xml.internal.bind.v2.TODO;
import core.State;
import core.Token;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by deanto on 18.03.14.
 *
 * save state to file
 */
public class FSRState {

    public static String FileState = "State File";

    // save information about state and "links" to tokens(name and id)
    static void SaveStateToFile(State state,String filename){
        try{

            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder sb = new StringBuilder();
            sb.append(FileState+System.getProperty("line.separator"));
            //TODO во всех функциях сохранения в файл использовать такую конструкцию

            sb.append(state.GetName()+System.getProperty("line.separator"));
            sb.append(state.GetID()+System.getProperty("line.separator"));

            ArrayList<Token> state_tokens = state.GetTokens();
            for (int i=0;i<state_tokens.size();i++){
                sb.append(state_tokens.get(i).GetName()+System.getProperty("line.separator"));
                sb.append(state_tokens.get(i).GetID()+System.getProperty("line.separator"));
            }

            bw.write(sb.toString());
            bw.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Запись в файл "+filename+" не удалась!");
        }

    }
    static void SaveStateToFile(State state){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить состояние " + state.GetName());
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        String filename = file.getAbsolutePath();

        if (filename!=null)
            SaveStateToFile(state,filename);
    }


}
