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

        statPanel.ShowPanel();
        statusPanel.Clear();

        // установим параметры в сеть



        // начнем выполнение.


    }

    private netStaticImpl currentNet = null;

    public Analyzer(){

        // зарузим данные. все должно быть сконфигурено...

        netV0 = netStaticImpl.getNet();
        statusPanel = statPanel.getPanel();
        changePanel = net.dynamic.changes.changePanel.getPanel();


        // загрузим возможные изменения параметров

        _statesChanges = changePanel.get_statesChanges();
        _transitionsChanges = changePanel.get_transitionsChanges();

        // потеря токенов отслеживается в процессе - каждый прогон сети. в зависимости от параметров.
        // изменения переходов(задержка,) и изменения правил (количество токенов) указываются для сети в начале прогона
        // изменениия в работе переходов (не работаем какие-то шаги) - отслеживаем в процессе в зависимости отслеживаем
        // ли мы это на текущем шаге
        // нужна специальная структура изменений которая будет давать конфигурацию на следующий шаг -
        // какие параметры указать сети в начале прогона и за какими событиями следить в процессе и включать\выключать
        // переходы и добавлять\удалять токены из состояний между шагами

        // процесс: заружаем сеть. вся статистика сбрасывается. берем первый вариант конфигурации и запускаем сеть.
        // закончили прогон - сохранили статистику и вариант изменений,
        // перезагрузили сеть - все опять сбросилось. взяли следующий вариант и так до конца.

        // потом уже отдельно можно смотреть результаты.


    }

    // изменения для состояний / там все данные достанем и соберем последовательные сеты данных для запуска шагов...
    private ArrayList<changeStat> _statesChanges;

    // изменения для переходов
    private ArrayList<changeTransaction> _transitionsChanges;


    // списки возможных изменений. указаны конкретные параметры изменения
    private ArrayList<ChangeOneRule> _ruleChanges;
    private ArrayList<ChangeOneState> _stateChanges;
    private ArrayList<ChangeOneTransitionDelay> _tDelayChanges;
    private ArrayList<ChangeOneTransitionWorking> _tWorkChanges;


    // представление комбинации для тестирования.тут каждому объекту указано что нужно изменить.


}
