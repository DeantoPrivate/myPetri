package components.TokenManager;

import base.TokensBase;
import core.Token;
import fileSavers.FSRToken;
import internalComponents.WWToken;
import org.w3c.dom.css.RGBColor;
import views.VToken;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.FileNameMap;
import java.util.ArrayList;

/**
 * Created by Денис on 14.03.14.
 */
public class Dialog extends JDialog implements ActionListener, ListSelectionListener {

    public static String ButtonSaveBase = "saveBase";
    public static String ButtonLoadBase = "loadBase";
    public static String ButtonNewToken = "newToken";
    public static String ButtonSaveToken = "saveToken";
    public static String ButtonDeleteToken = "deleteToken";
    public static String ButtonCopyToken = "copyToken";
    public static String ComponentTokenManagerTitle = "Token Manager";

    private JPanel Panel,PanelButtons,PanelToken_base,PanelToken_view,Status;
    private JButton saveBase,loadBase, newToken,saveToken,deleteToken,copyToken;

    private String _status = "";

    public void Show(){
        Init();
        setVisible(true);
    }

    private void Init(){
        setTitle(ComponentTokenManagerTitle);

        Panel = new JPanel();
        Panel.setLayout(null);

        PanelButtons = new JPanel();
        PanelButtons.setLayout(null);
        PanelButtons.setBounds(0,0,200,20);

            saveBase = new JButton(ButtonSaveBase);
            loadBase = new JButton(ButtonLoadBase);
            loadBase.setBounds(0, 0, 100, 20);
            saveBase.setBounds(100, 0, 100, 20);
            loadBase.addActionListener(this);
            saveBase.addActionListener(this);
            PanelButtons.add(saveBase);
            PanelButtons.add(loadBase);

            PanelToken_base = new JPanel();
            PanelToken_base.setBounds(0,20,200,580);
            PanelToken_base.setBackground(Color.LIGHT_GRAY);
            //PanelToken_base.setBorder(new TitledBorder("base"));


            PanelToken_view = new JPanel();
            PanelToken_view.setBounds(200,0,400,580);
            PanelToken_view.setBackground(Color.LIGHT_GRAY);
            PanelToken_view.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            //PanelToken_view.setBorder(new TitledBorder("view"));

            newToken = new JButton(ButtonNewToken);
                newToken.setBounds(200,580,100,20);
                newToken.addActionListener(this);
            saveToken = new JButton(ButtonSaveToken);
                saveToken.setBounds(300,580,100,20);
            deleteToken = new JButton(ButtonDeleteToken);
                deleteToken.setBounds(400,580,100,20);
            copyToken = new JButton(ButtonCopyToken);
                copyToken.setBounds(500,580,100,20);


            Status = new JPanel();
            Status.setBackground(Color.GRAY);
            Status.setBounds(0, 600, 600, 20);


        Panel.add(PanelButtons);
        Panel.add(PanelToken_base);
        Panel.add(PanelToken_view);
        Panel.add(newToken);
        Panel.add(saveToken);
        Panel.add(deleteToken);
        Panel.add(copyToken);
        Panel.add(Status);

        add(Panel);

        updateStatus("initialized");

        setBounds(0, 0, 605, 645);
        setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==loadBase)
            LoadBase();
        if (e.getSource()==newToken)
            newToken();
        if (e.getSource()==saveBase)
            SaveBase();

    }

    void SaveBase(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите папку, куда сохранить базу");
        fileChooser.showDialog(null, "SelectThisFolder");

        File directory = fileChooser.getCurrentDirectory();

        for (Token token : TokensBase.GetTokenBase().GetTokens()){

            String tokenFileName = token.GetName()+".txt";

            File exists = null;
            File files[] = directory.listFiles();
            for (int i=0;i<files.length;i++)
                if (files[i].isFile())
                    if (files[i].getName().equals(tokenFileName))
                        exists = files[i];

            if (exists!=null)
                exists.delete();

            File file = new File(directory.getAbsolutePath()+"/"+tokenFileName);
            FSRToken.SaveToFile(token,file);


        }


    }

    void newToken(){
        WWToken newOne = new WWToken();
        newOne.ConstructToken();

        TokensBase.GetTokenBase().AddToken(newOne.GetToken());
        updatePanelToken_base();
    }

    void LoadBase(){
    updateStatus("loading base");

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Выберите папку с базой токенов");
    fileChooser.showDialog(null, "SelectThisFolder");

    File file = fileChooser.getCurrentDirectory();
    updateStatus("processing directory: "+file.getAbsolutePath());

    TokensBase.GetTokenBase().Clear();

    ProcessDirectory(file);



    //TokensBase.GetTokenBase().ShowTokensInSystem();

    }
    void ProcessDirectory(File directory){

        File files[] = directory.listFiles();
        for (int i=0;i<files.length;i++)
            if (files[i].isDirectory())
                ProcessDirectory(files[i]);
            else
            if (files[i].getName().contains(".txt")){
                updateStatus("processing file: "+directory.getAbsolutePath());
                Token newone;
                newone = FSRToken.ReadFromFile(files[i]);
                if (newone!=null)
                    TokensBase.GetTokenBase().AddToken(newone);
            }
        updatePanelToken_base();
    }

    void updateStatus(String newStatus){
        _status = newStatus;
        Status.removeAll();
        Status.add(new JLabel(_status));
    }

    JList token_list;

    void updatePanelToken_base(){
        String token_names[] = new String[TokensBase.GetTokenBase().GetTokens().size()];
        for (int i=0;i<token_names.length;i++)
            token_names[i] = TokensBase.GetTokenBase().GetTokens().get(i).GetName();

        token_list = new JList(token_names);
        token_list.setLayoutOrientation(JList.VERTICAL);
        token_list.addListSelectionListener(this);

        PanelToken_base.removeAll();
        PanelToken_base.add(token_list);
        PanelToken_base.updateUI();

        PanelToken_view.removeAll();
        PanelToken_view.updateUI();

    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        int pos = token_list.getSelectedIndex();
        VToken vToken = new VToken(TokensBase.GetTokenBase().GetTokens().get(pos));

        PanelToken_view.removeAll();
        PanelToken_view.add(vToken);
        PanelToken_view.updateUI();


    }
}
