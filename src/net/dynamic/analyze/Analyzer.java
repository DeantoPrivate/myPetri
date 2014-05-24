package net.dynamic.analyze;

import base.TokensBase;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by deanto on 19.05.14.
 */
public class Analyzer {

    // анализ начинается с текущего положения сети и панелей статистики и настроек
    private int currentAnalyzeStep = 0;

    // панельки для обновления и сбора информации
    private statPanel statusPanel;
    private changePanel changePanel;



    private void nextStepPreparing(){

        // скачаем новую сеть и загрузим ее/ она в статической переменной. все обращение через нее
        NetSaver.ReadNet(false);


        LiveNet.setNextAnalyzeStep();
        currentAnalyzeStep++;

        //statPanel.ShowPanel();
        statPanel.getPanel().Clear();

        //JOptionPane.showMessageDialog(null,"обновили сесть.");
        // все готово для применения следующего набора параметров

    }

    public Analyzer(){

        // зарузим данные. все должно быть сконфигурено...

        statusPanel = statPanel.getPanel();
        changePanel = net.dynamic.changes.changePanel.getPanel();


        // загрузим возможные изменения параметров

        _statesChanges = changePanel.get_statesChanges();
        _transitionsChanges = changePanel.get_transitionsChanges();


        _stateChanges = new ArrayList<ChangeOneState>();
        ArrayList<ChangeOneState> tmp;
        for(changeStat cS : _statesChanges){
            tmp = cS.getStateChanges();
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

    private void selectFile(){

        while(filename==null) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить сеть");
            fileChooser.showSaveDialog(null);
            File file = fileChooser.getSelectedFile();
            filename = file.getAbsolutePath();
        }
    }

    private String filename = null;

    private void saveInformation(StringBuilder GeneralChanges, StringBuilder currentChanges, StringBuilder statistic){
        try {

            if (filename == null)
                selectFile();

            String sp = filename+currentAnalyzeStep+".txt";
            FileWriter fw = new FileWriter(sp);
            BufferedWriter bw = new BufferedWriter(fw);

            String n = System.getProperty("line.separator");

            StringBuffer s = new StringBuffer();
            s.append("__________Общие изменения:" + n + GeneralChanges.toString() + n);
            s.append(n+"__________Примененные изменения: "+ n + currentChanges.toString() + n);
            s.append("__________Собранная статистика" + n + statistic);
            bw.write(s.toString());
            bw.close();
            fw.close();

        }catch (Exception e){

        }
    }

    public void Analyze(){



        // у нас есть набор комбинаций. посчитаем сколько всего сочетаний - это n!.
        // пойдем до этого числа от 0 - будем стоить бинарную маску 01010111100 - какие берем правила какие не берем
        // нужно только следить чтоб правила не противоречили друг другу. это касается диапазонов для какого-то параметра
        // они должны идти по очереди. находим такие и сколько их. и применяем только битовые маски 0001 0010 0100 1000
        // или просто пропускаем генерацию битовых масок именно в этой части с этими правилами. и потом дальше.


        int allCount = _ruleChanges.size() + _stateChanges.size()+_tDelayChanges.size()+_tWorkChanges.size();
        int currentVar = 1;

        initRules();

        // не будем факториал считать. просто увидим, когда размер массив двоичных масок превысит это число.
        ArrayList<Boolean> mask = new ArrayList<Boolean>();
        while (mask.size()<=allCount){

            nextStepPreparing();

            // будем по очереди брать маски от счетчика и применять их к нашим правилам))

            mask = Combination(currentVar);
            if (mask.size() > allCount) return;

            // в этом массиве нули и еденицы поочереди. это соответственно признаки применяем или не применяем правило в нашем порядке.
            // правила упорядочены в функции getRule(int pos)

            StringBuilder actualchanges = new StringBuilder();
            String n = System.getProperty("line.separator");


            for (int i =0;i<mask.size();i++){
                if (mask.get(i)){
                    // если следующее правило надо применить. todo реализовать непересечение несовместимых условий
                    ChangeOne c = getRule(i);
                    CheckAndApplyChangeOne(c,true);
                    actualchanges.append(c.getString()+n);
                }
            }

            // правила применились. запускаем сеть. (сколько шагов то емае...?)
            for (int w=0;w<100;w++){

                currentAnalyzingStep = w;

                for (int i =0;i<mask.size();i++){
                    if (mask.get(i)){
                        // если следующее правило надо применить. todo реализовать непересечение несовместимых условий
                        ChangeOne c = getRule(i);
                        CheckAndApplyChangeOne(c, false);
                    }
                }

                LiveNet.GetInstance().NextStep();
            }

            StringBuilder staticstic = statPanel.getPanel().GetStatictics();
            StringBuilder DefaultChanges = changePanel.GetChanges();

            saveInformation(DefaultChanges,actualchanges,staticstic);

           // JOptionPane.showMessageDialog(null, "один цикл анализа прошел. данные сохранились");

            currentVar ++;
        }

    }

    private ArrayList<ChangeOne> _rules;
    private void initRules(){
        _rules = new ArrayList<ChangeOne>();
        for (ChangeOneState a : _stateChanges)
            _rules.add(a);

        for (ChangeOneTransitionWorking a : _tWorkChanges)
            _rules.add(a);

        for (ChangeOneTransitionDelay a : _tDelayChanges)
            _rules.add(a);

        for (ChangeOneRule a : _ruleChanges)
            _rules.add(a);
    }
    public ChangeOne getRule(int i){
        return _rules.get(i);
    }

    private int currentAnalyzingStep = 0;

    private void CheckAndApplyChangeOne(ChangeOne c, boolean init){
        if (c instanceof ChangeOneState)
            CheckAndApplyChangeOneState((ChangeOneState)c);
        if (c instanceof ChangeOneTransitionWorking)
            CheckAndApplyChangeOneTransitionWorking((ChangeOneTransitionWorking)c);
        if (c instanceof ChangeOneTransitionDelay && init)
            ApplyChangeOneTransitionDelay((ChangeOneTransitionDelay)c);
        if (c instanceof ChangeOneRule && init)
            ApplyChangeOneRule((ChangeOneRule)c);
    }

    // возвращает значит массив нулей и 1 - берем или не берем правило. если короткий - то значит дальше ничего не берем
    private ArrayList<Boolean> Combination(int i){
        String s = Integer.toBinaryString(i);
        ArrayList<Boolean> answer = new ArrayList<Boolean>();
        for (int t=0;t<s.length();t++)
            if (s.charAt(t) == '0')
                answer.add(false);
            else answer.add(true);
        return answer;
    }

    private void CheckAndApplyChangeOneState(ChangeOneState state){
        // применить изменение к текущей сети если условие выполняется
        int ost = currentAnalyzingStep % state.step; // остаток от деления
        if (ost == 0 && currentAnalyzingStep!=0){
            // самое время потерять\приобрести токен состоянию
            State s = netStaticImpl.getNet().getState(state.State);
            if (state.loose){
                for (Token t : s.GetTokens())
                    if (t.GetName().equals(state.Token)){
                        s.TokenGone(t);
                        return;
                    }
            }else if (state.appearance){
                Token t = TokensBase.GetTokenBase().getToken(state.Token);
                s.LocateToken(t);
            }

        }
    }

    private void CheckAndApplyChangeOneTransitionWorking(ChangeOneTransitionWorking working){
        // применить изменение к текущей сети если условие выполняется
        if (working.notWork){

                if (working.stepL==currentAnalyzingStep){
                Transition transition = netStaticImpl.getNet().getTransition(working.TransitionName);
                transition.StopByAnalyze();
            }
            else if(working.stepR==currentAnalyzingStep){
                Transition transition = netStaticImpl.getNet().getTransition(working.TransitionName);
                transition.AllowByAnalyze();
            }

        }
        if (!working.notWork){
            Transition transition = netStaticImpl.getNet().getTransition(working.TransitionName);
            transition.AllowByAnalyze();
        }
    }

    private void ApplyChangeOneTransitionDelay(ChangeOneTransitionDelay delay){
        // применить изменение к текущей сети
        Transition transition = netStaticImpl.getNet().getTransition(delay.TransitionName);
        transition.SetSleepSteps(delay.delay);
    }

    private void ApplyChangeOneRule(ChangeOneRule rule){
        // применить изменение к текущей сети
        TransitionRule tRule = netStaticImpl.getNet().getTransition(rule.TransitionName).getRule(rule.RuleString);
        try{tRule.setCount(rule.param);}catch (Exception e){
            int t =0;
        }
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
