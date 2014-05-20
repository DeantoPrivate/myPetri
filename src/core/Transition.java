package core;

import com.sun.corba.se.spi.activation._ActivatorImplBase;
import net.dynamic.statistic.CountActionListener;
import net.dynamic.statistic.transactionStat;
import net.staticNet.UIActionListener;

import java.util.ArrayList;

/**
 * Created by Денис on 17.03.14.
 */
public class Transition {

    public static String INCOMING_TOKEN_REMOVE = "INCOMING_TOKEN_REMOVE";
    public static String INCOMING_TOKEN_KEEP = "INCOMING_TOKEN_KEEP";

    private ArrayList<IncomingTransitionRule> _incomingTransitionRules;
    private ArrayList<OutgoingTransitionRule> _outgoingTransitionRules;

    // для анализа нам нужно доставать правило по строке состояния
    public TransitionRule getRule(String rule){
        for (IncomingTransitionRule i : _incomingTransitionRules)
            if (i.toString().equals(rule))
                return i;

        for (OutgoingTransitionRule o : _outgoingTransitionRules)
            if (o.toString().equals(rule))
                return o;

        return null;
    }

    // для анализа нужно иметь возможность прекратить проверки.
    private boolean stoppedByAnalyzis = false;
    public void StopByAnalyze(){
        stoppedByAnalyzis = true;
    }
    public void AllowByAnalyze(){
        stoppedByAnalyzis = false;
    }

    public boolean canBeActivated(){

        if (stoppedByAnalyzis) return false;

        // workaround
        if (stepswait!=-1) return true;
        if (sleepSteps == -10) return false;

        for (IncomingTransitionRule a : _incomingTransitionRules){
            if (!a.canStart())
                return false;
        }

        return true;
    }

    private CountActionListener _statElement;
    public void AssignTransactionStat(CountActionListener statElement){
        _statElement = statElement;
    }


    public ArrayList<IncomingTransitionRule> get_incomingTransitionRules() {return  _incomingTransitionRules;}
    public ArrayList<OutgoingTransitionRule> get_outgoingTransitionRules() {return _outgoingTransitionRules;}


    private String _name;
    public void SetName(String name){
        _name = name;
    }
    public String getName(){return _name;}

    public Transition(){

        _incomingTransitionRules = new ArrayList<IncomingTransitionRule>();
        _outgoingTransitionRules = new ArrayList<OutgoingTransitionRule>();
    }

    public void AddIncomingTransitionRule(IncomingTransitionRule rule){
        _incomingTransitionRules.add(rule);
    }
    public void AddOutgoingTransitionRule(OutgoingTransitionRule rule){
        _outgoingTransitionRules.add(rule);
    }

    private UIActionListener _listener;
    public void addUIChangeActionListener(UIActionListener listener){
        _listener = listener;
    }

    // задержка. 0 - нет задержки. -9 : сработать только один раз
    private int sleepSteps = 0;
    public void SetSleepSteps(int sleep){
        sleepSteps = sleep;
        stepswait = -1;
    }
    public int getSleepSteps(){return sleepSteps;}

    private boolean _active = false;
    public void Activate(){
        _active = true;
    }
    public void Deactivate(){
        _active = false;
    }
    public boolean isActive(){
        return _active;
    }

    // indicates transaction was started
    private boolean _wasStarted = false;

    private int stepswait = -1;

    // process transaction
    public boolean Exec(){

        if (stepswait>0){
            stepswait -- ;
            return true;
        }
        _wasStarted = true;

        // additional make sure we can start
        if (!canBeActivated()) return false;

        // make sure transaction was activated
        if (!isActive()) return false;

        // process rule(s)
        if (stepswait==-1){

        for (IncomingTransitionRule rule : _incomingTransitionRules)
            rule.Process();
            if (_listener!=null){
                _listener.FireUIChangedEvent();
            }
        }
        // вот тут нужно подождать еще несколько шагов...
        if (stepswait == -1 && sleepSteps!=0 && sleepSteps!=-9){
            // задерживаем выполнение.
            stepswait = sleepSteps-1;
        }
        else{

        for (OutgoingTransitionRule rule : _outgoingTransitionRules)
            rule.Process();

        if (_listener!=null){
            _listener.FireUIChangedEvent();
        }
        if (_statElement!=null)
            _statElement.FireCountEventFromElement();

        _active = false;
        _wasStarted = false;
        stepswait = -1;
            if (sleepSteps==-9){
                sleepSteps = -10;
            }

        }
        return true;
    }

}
