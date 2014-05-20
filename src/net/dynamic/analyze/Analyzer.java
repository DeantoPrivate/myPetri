package net.dynamic.analyze;

import core.State;
import core.Token;
import core.Transition;
import core.TransitionRule;
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



    private void nextStepPreparing(){

        // скачаем новую сеть и загрузим ее.
        currentNet = NetSaver.ReadNet(false);
        currentNet.SyncGElements();

        LiveNet.setNextAnalyzeStep();
        currentAnalyzeStep++;

        statPanel.ShowPanel();
        statusPanel.Clear();

        // все готово для применения следующего набора параметров

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


        _stateChanges = new ArrayList<ChangeOneState>();
        ArrayList<ChangeOneState> tmp;
        for(changeStat cS : _statesChanges){
            tmp = new ArrayList<ChangeOneState>();
            for (ChangeOneState s : tmp)
                _stateChanges.add(s);
        }

        _ruleChanges = new ArrayList<ChangeOneRule>();
            ArrayList<ChangeOneRule> tmp2 = new ArrayList<ChangeOneRule>();
        _tDelayChanges = new ArrayList<ChangeOneTransitionDelay>();
            ArrayList<ChangeOneTransitionDelay> tmp3 = new ArrayList<ChangeOneTransitionDelay>();
        _tWorkChanges = new ArrayList<ChangeOneTransitionWorking>();
            ArrayList<ChangeOneTransitionWorking> tmp4 = new ArrayList<ChangeOneTransitionWorking>();

        for (changeTransaction tc : _transitionsChanges){
            tmp2 = tc.getChangeOneRules();
            tmp3 = tc.getChangeOneTransitionDelays();
            tmp4 = tc.getChangeOneTransitionWorkings();

            for (ChangeOneRule c:tmp2)
                _ruleChanges.add(c);
            for (ChangeOneTransitionDelay c:tmp3)
                _tDelayChanges.add(c);
            for (ChangeOneTransitionWorking c:tmp4)
                _tWorkChanges.add(c);

        }


        // теперь правила загружены. каждое правило можно применить или не применить.
        // можно перебрать все комбинации - это и будет прогоном всех вариантов для этой сети


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


    private void Analyze(){

        // тут реализуется прогон. просто берем все комбинации по вариантам. for(for(for(for)))))
        // так просмотрим все. когда находимся внутри. то применяем все что есть к сети - и запускаем сеть.
        // в процессе смотрим опять же только те условия и применяем которые у нас в цикле сейчас.
// TODO так получится пробежать только по отдельности все комбинации. а например чтоб состояние теряло и 1 и 2 токен??
// хер с ним. пусть будут не все варианты. всеравно все не сделать.. только комбинации по одному условию пока

        // todo изменить логику.. сейчас тестируется только один вариант от каждого состояния, перехода и правила.
        for (ChangeOneState stateChange : _stateChanges)
            for (ChangeOneTransitionWorking transitionWorkingChange : _tWorkChanges)
                for (ChangeOneTransitionDelay transitionDelayChange : _tDelayChanges)
                    for (ChangeOneRule ruleChange : _ruleChanges){


                        // к этому моменту у нас есть уже некая комбинация
                        // что-то из этого - статическое изменение - нужно применить к сети
//                      // что-то из этого - динамическое изменение - за ним надо следить в процессе выполнения
                        // TODO надо продумать отключение графического интерфейса чтоб он не обновлялся и быстрее работал

                        // TODO применить изменения

                        // TODO запускать шаги и если наступает условие изменений - применять их

                        // TODO закончили выполнение - сохранить статистику и текущий набор условий.

                        // TODO подготовка к следующему шагу

                    }

    }

    private int currentAnalyzingStep = 0;

    private void CheckAndApplyChangeOneState(ChangeOneState state){
        // применить изменение к текущей сети если условие выполняется
        
    }

    private void CheckAndApplyChangeOneTransitionWorking(ChangeOneTransitionWorking working){
        // применить изменение к текущей сети если условие выполняется
        if (working.notWork){

                if (working.stepL==currentAnalyzeStep){
                Transition transition = currentNet.getTransition(working.TransitionName);
                transition.StopByAnalyze();
            }
            else if(working.stepR==currentAnalyzeStep){
                Transition transition = currentNet.getTransition(working.TransitionName);
                transition.AllowByAnalyze();
            }

        }
    }

    private void ApplyChangeOneTransitionDelay(ChangeOneTransitionDelay delay){
        // применить изменение к текущей сети
        Transition transition = currentNet.getTransition(delay.TransitionName);
        transition.SetSleepSteps(delay.delay);
    }

    private void ApplyChangeOneRule(ChangeOneRule rule){
        // применить изменение к текущей сети
        TransitionRule tRule = currentNet.getTransition(rule.TransitionName).getRule(rule.RuleString);
        tRule.setCount(rule.param);
    }


    // изменения для состояний / там все данные достанем и соберем последовательные сеты данных для запуска шагов...
    private ArrayList<changeStat> _statesChanges;

    // изменения для переходов
    private ArrayList<changeTransaction> _transitionsChanges;


    // списки возможных изменений. указаны конкретные параметры изменения todo возможно тут прийдется разбить на списки списков от каждого объекта. чтоб тестировать вместе хотяб по варианту но от каждого
    private ArrayList<ChangeOneRule> _ruleChanges;
    private ArrayList<ChangeOneState> _stateChanges;
    private ArrayList<ChangeOneTransitionDelay> _tDelayChanges;
    private ArrayList<ChangeOneTransitionWorking> _tWorkChanges;


    // представление комбинации для тестирования.тут каждому объекту указано что нужно изменить.


}
