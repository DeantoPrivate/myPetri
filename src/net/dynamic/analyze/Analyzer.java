package net.dynamic.analyze;

import core.State;
import core.Token;
import core.Transition;
import net.dynamic.changes.changePanel;
import net.dynamic.changes.changeStat;
import net.dynamic.changes.changeTransaction;
import net.dynamic.statistic.statPanel;
import net.dynamic.statistic.stateStat;
import net.dynamic.statistic.transactionStat;
import net.liveNet.LiveNet;
import net.netSaver.NetSaver;
import net.staticNet.netStaticImpl;

import java.util.ArrayList;

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


        // загрузим возможные изменения параметров

        _statesChanges = changePanel.get_statesChanges();
        _transitionsChanges = changePanel.get_transitionsChanges();

    }

    // изменения для состояний / там все данные достанем и соберем последовательные сеты данных для запуска шагов...
    private ArrayList<changeStat> _statesChanges;

    // изменения для переходов
    private ArrayList<changeTransaction> _transitionsChanges;


    // просчитав сеть загружаем новую и повторяем.



}
