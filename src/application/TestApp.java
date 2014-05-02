package application;

import components.Constructor.GraphicalConstructor;
import components.TokenManager.Dialog;
import core.State;
import core.Token;
import core.Transition;
import core.TransitionRule;
import internalComponents.WWToken;

/**
 * Created by Денис on 11.03.14.

 */

public class TestApp {



    public static void main(String arg[]){



        GraphicalConstructor b = new GraphicalConstructor();
        b.Show();
/*
        Dialog a = new Dialog();
        a.Show();
/*
        State s1 = new State();
        s1.ChangeName("s1");

        State s2 = new State();
        s2.ChangeName("s2");

        Transition transition = new Transition();
        transition.buildTransition(s1,s2);

        WWToken wt1 = new WWToken();
        wt1.ConstructToken();

        Token t1 = wt1.GetToken();

        s1.LocateToken(t1);

        TransitionRule r1 = new TransitionRule();
        r1.constructRule();

        transition.assignTransitionRule(r1);

        if (transition.canBeActivated()){
            transition.Activate();
            transition.Exec();
            transition.Deactivate();
        }

        int t=0;

        //Dialog d = new Dialog();
        //d.Show();


/*
        //
        // синтаксический конструктор!
        пишем например (и в процессе на экране выбираем контекст того что пишем - состояние переход или токен)

        например

                на \выбрали состояние\ станцию \ выбрали объект или создали объект состояния\ прибывает \ выбрали токен или создали \ поезд \ указали токен\
        * это будет значить что состояние порождает токен. если б написали поезд прибывает на станцию то это бы тоже самое. фиктивные сосотяние в начале сети.

        потом еще можем дальше писать И это специальный символ означает что новый токен в то же состояние

                когда с состоянием определились то дальше пишем переход
                когда указываем токен то токен сам может преобразоваться в полный текст. перечислив все поля. можно оставить просто название.

                переход пишем. из\ от \ и прочие специальные слова при условии что выбран контекст перехода то указываем состояние первое потом второе и потом условие перечисляем.

        чтото типа того. чтоб был тект. с специальными вставками.

              //
*/
    }



}
