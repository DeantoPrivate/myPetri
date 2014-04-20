package fileSavers;

import core.Property;
import core.Token;

import javax.swing.*;
import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Денис on 12.03.14.
 *
 * save & read from file
 */
public class FSRToken {
    public static String FileToken = "Token File";
    public static void SaveToFile(Token token,File file){

        StringBuilder sFile = new StringBuilder();
        sFile.append(FileToken+'\n');
        sFile.append(token.GetName()+'\n');

        ArrayList<Property> token_properties = token.get_properties();
        for (int i=0;i<token_properties.size();i++)
            sFile.append(FSRProperty.SaveProperty(token_properties.get(i)));

        String filename = file.getAbsolutePath();
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            fw.write(sFile.toString());
            fw.close();
        }catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Запись в файл "+filename+" не удалась!");
        }
    }
    public static void SaveToFile(Token token){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить токен "+token.GetName());
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();

        SaveToFile(token,file);

    }

    public static Token ReadFromFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Открыть файл с токеном");
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        return ReadFromFile(file);
    }
    public static Token ReadFromFile(File file){
        String filename = "";
        if (file!=null)
        try{
        filename = file.getAbsolutePath();

        StringBuffer fileBuffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(filename));


        String buf = reader.readLine();
        String isToken = buf;
        if (!isToken.contains(FileToken)) throw new ExecutionException("не токен",new Throwable("!"));

        String token_name = reader.readLine();

        ArrayList<String> strings = new ArrayList<String>();
        String nextString = "";
        while((nextString = (reader.readLine())) != null){
            strings.add(nextString);
        }

        ArrayList<Property> token_properties = new ArrayList<Property>();
        while (strings.size()!=0)
        {
            token_properties.add(FSRProperty.ReadProperty(strings));
        }

        Token token = new Token();
        token.ChangeName(token_name);
        token.ChangeProperties(token_properties);

        reader.close();

        return token;

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"не получилось прочитать файл "+filename);
        }

        return null;
    }
}
