package net.dynamic.analyze;

import net.dynamic.changes.changePanel;
import net.dynamic.statistic.statPanel;
import net.dynamic.statistic.stateStat;
import net.liveNet.LiveNet;
import net.netSaver.NetSaver;
import net.staticNet.netStaticImpl;

/**
 * Created by deanto on 19.05.14.
 */
public class Analyzer {

    // анализ начинается с текущего положения сети и панелей статистики и настроек
    private netStaticImpl netV0;
    private int currentAnalyzeStep = 0;

    // панельки для обновления и сбора информации
    private statPanel statusPanel;
    private changePanel changePanel;



    private void nextStep(){

        // скачаем новую сеть и загрузим ее.
        currentNet = NetSaver.ReadNet(false);
        currentNet.SyncGElements();


        LiveNet.setNextAnalyzeStep();
        currentAnalyzeStep++;

        statusPanel.Clear();

        // установим параметры в сеть


        // начнем выполнение.


    }

    private netStaticImpl currentNet = null;

    public Analyzer(){

        // зарузим данные

        netV0 = netStaticImpl.getNet();
        statusPanel = statPanel.getPanel();
        changePanel = net.dynamic.changes.changePanel.getPanel();


    }

    // просчитав сеть загружаем новую и повторяем.



}
