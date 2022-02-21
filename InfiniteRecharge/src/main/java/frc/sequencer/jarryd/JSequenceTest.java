package frc.sequencer.jarryd;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.sequencer.Sequence;
import frc.sequencer.Sequencer;

public class JSequenceTest {
    // sequence lib
    public static synchronized List<Sequence> getSequences()
    {
        if (theSequences == null)
        {
            theSequences = new LinkedList<Sequence>();
            theSequences.add(createshoot());
            theSequences.add(createSquare());
            theSequences.add(createCurveShoot());
            theSequences.add(createFullShooter());
            theSequences.add(create1b());
            theSequences.add(create1bCut());
            theSequences.add(create2b());
            theSequences.add(create2bCut());
            theSequences.add(create3b());
            theSequences.add(create3bCut());
            theSequences.add(create4b());
            theSequences.add(create4bCut());
            theSequences.add(createAccTest());
            theSequences.add(createRevAccTest());
            theSequences.add(create5Ball());
            theSequences.add(create4redcover());
            theSequences.add(createCollision());

        }
        return Collections.unmodifiableList(theSequences);

    }


    private static Sequence createCollision(){
        jCollisionDrive cd1 = new jCollisionDrive();
        jdrive d1 = new jdrive();
        d1.setDist(-4);
        d1.setSpeed(0.35);
        d1.setAngle(0);

        jintake intake = new jintake();
        jshooter shoot = new jshooter();

        cd1.setNextSteps(intake);
        d1.setNextSteps(shoot);
        
        Sequence seq = new Sequence("Collision Detection", 0);
        seq.setInitialTransitions(cd1, d1);
        seq.setInitialSteps(d1);
        return seq;
    }
  

    /*
    NOT TESTED!! (with Phoenix or rapid react)
        -robot still turning when stops
            -drivetrain?
            -code?
    to refine as this is just basic code
        -need to make accurate
            -specific angles
            -specific distances
                -should be accurate for mid section
            -speed?
        -time
            -make faster
            -reduce time in timers
            -shooter speed trans?
    */
    private static Sequence create5Ball()
    {
       jshooter shoot = new jshooter();
       jIndexer index = new jIndexer();
       jintake intake = new jintake();

       jBallShooter ball = new jBallShooter();
       ball.setNumBalls(2);

       jBallShooter ball2 = new jBallShooter();
       ball2.setNumBalls(3);
       
        Jturn t1 = new Jturn();
        t1.setAngle(-86.5);

        jdrive d1 = new jdrive();
        d1.setAngle(-86.5);
        d1.setDist(-1.25);
        d1.setSpeed(0.3);

        Jturn t2 = new Jturn();
        t2.setAngle(-75);

        jdrive d2 = new jdrive();
        d2.setAngle(-75);
        d2.setDist(0.2);
       
        Jturn t3 = new Jturn();
        t3.setAngle(20);

        jdrive d3 = new jdrive();
        d3.setAngle(20);
        d3.setDist(-2.5);
        d3.setSpeed(0.4);
        d3.setAccFwdLimit(0.15);
        d3.setAccRevLimit(0.25);

        Jturn t4 = new Jturn();
        t4.setAngle(0);

        jdrive d4 = new jdrive();
        d4.setAngle(0);
        d4.setDist(-3.65);
        d4.setSpeed(0.45);
        d4.setAccFwdLimit(0.15);
        d4.setAccRevLimit(0.25);
        d4.setDistGain(1.7);

        Jturn t5 = new Jturn();
        t5.setAngle(-45);

        jdrive d5 = new jdrive();
        d5.setAngle(-45);
        d5.setDist(-0.95);
        d5.setSpeed(0.3);

        jtimer T1 = new jtimer();
        T1.setDelay(0.35);

        jcurve c1 = new jcurve();
        c1.setRadius(0.5);
        c1.setAngle(10);
        c1.setSpeed(0.3);

        jtimer T2 = new jtimer();
        T2.setDelay(3);
        
        t1.setNextTrans(d1);
        t1.setNextSteps(d1, shoot, intake);
        d1.setNextTrans(t2);
        d1.setNextSteps(t2, shoot, intake);
        t2.setNextTrans(shoot);
        t2.setNextSteps(shoot, intake);
        shoot.setNextTrans(T2);
        shoot.setNextSteps(shoot, intake, index);
        T2.setNextTrans(d2);
        T2.setNextSteps(d2);
        d2.setNextTrans(t3);
        d2.setNextSteps(t3, intake);
        t3.setNextTrans(d3);
        t3.setNextSteps(d3, intake);
        d3.setNextTrans(t4);
        d3.setNextSteps(t4);

        t4.setNextTrans(d4);
        t4.setNextSteps(d4);

        // d4.setNextTrans(t5);
        // d4.setNextSteps(t5, shoot, intake);
        // t5.setNextTrans(d5);
        // t5.setNextSteps(d5, shoot, intake);
        // d5.setNextTrans(T1);
        // d5.setNextSteps(shoot, intake);
        
        // T1.setNextTrans(c1);
        // T1.setNextSteps(c1, shoot, intake);
        // c1.setNextTrans(ball2);
        // c1.setNextSteps(shoot, intake, index);

        Sequence seq = new Sequence("5 Ball Auto", 1);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, shoot, intake);
        return seq;
    }

    private static Sequence create4redcover()
    {//ball spit code? toggle-able rpm to spit out enemy team balls
        jshooter shoot = new jshooter();
        jshooter shoot2 = new jshooter();
        jIndexer index = new jIndexer();
        jintake intake = new jintake();

        Jturn t01 = new Jturn();
        t01.setAngle(27);
        
        jBallShooter ball = new jBallShooter();
        ball.setNumBalls(1);

        Jturn t1 = new Jturn();
        t1.setAngle(116);

        jdrive d1 = new jdrive();
        d1.setAngle(116);
        d1.setDist(-2.9);
        d1.setSpeed(0.2);

        jtimer T1 = new jtimer();
        T1.setDelay(0.3);

        jdrive d2 = new jdrive();
        d2.setAngle(116);
        d2.setDist(0.2);
        d2.setSpeed(0.2);

        Jturn t2 = new Jturn();
        t2.setAngle(175);

        jdrive d3 = new jdrive();
        d3.setAngle(175);
        d3.setDist(1.3);
        d3.setSpeed(0.2);

        Jturn t3 = new Jturn();
        t3.setAngle(65);

        jBallShooter ball2 = new jBallShooter();
        ball2.setNumBalls(1);

        Jturn t4 = new Jturn();
        t4.setAngle(180);


        t01.setNextTrans(shoot);
        t01.setNextSteps(shoot);
        shoot.setNextTrans(t1);//ball
        shoot.setNextSteps(t1);//shoot, index
      
        ball.setNextTrans(t1);
        ball.setNextSteps(t1);
      
        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(T1);
        d1.setNextSteps(intake);
        T1.setNextTrans(d2);
        T1.setNextSteps(d2, intake);
        d2.setNextTrans(t2);
        d2.setNextSteps(t2, intake);
        t2.setNextTrans(d3);
        t2.setNextSteps(d3, intake);
        d3.setNextTrans(t3);
        d3.setNextSteps(t3, shoot);
        t3.setNextTrans(shoot2);
        t3.setNextSteps(shoot2);
        shoot2.setNextTrans(t4);
        shoot2.setNextSteps(t4, shoot, index);
        ball2.setNextTrans(t4);
        ball2.setNextSteps(t4);


        Sequence seq = new Sequence("4 Red Cover", 4);
        seq.setInitialTransitions(t01);
        seq.setInitialSteps(t01, shoot);
        return seq;
    }















    private static Sequence createAccTest()
    {
        jdrive drive1 = new jdrive();
        drive1.setDist(2);
        drive1.setAngle(0);
        drive1.setSpeed(0.5);
        drive1.setAccFwdLimit(0.3);
        drive1.setAccRevLimit(0.15);

        Sequence seq = new Sequence("Acceleration Test", 0);
        seq.setInitialTransitions(drive1);
        seq.setInitialSteps(drive1);
        return seq;
    }    

    private static Sequence createRevAccTest()
    {
        jdrive drive1 = new jdrive();
        drive1.setDist(-2);
        drive1.setAngle(0);
        drive1.setSpeed(0.5);
        drive1.setAccFwdLimit(0.1);
        drive1.setAccRevLimit(0.05);
  
        Sequence seq = new Sequence ("Reverse Acc Test", 0);
        seq.setInitialTransitions(drive1);
        seq.setInitialSteps(drive1);
        return seq;
    }





    //auto start pos code
    //test/improve
    private static Sequence create1b()
    {
        jshooter step1C1 = new jshooter();
        jintake step1C2 = new jintake();
        jIndexer step1C3 = new jIndexer();

        Jturn step1C4 = new Jturn();
        step1C4.setAngle(-86.5);

        jdrive step1C5 = new jdrive();
        step1C5.setAngle(-86.5);
        step1C5.setDist(-1.25);
        step1C5.setSpeed(0.3);

        Jturn step1C6 = new Jturn();
        step1C6.setAngle(-79.5);

        jtimer timer1C1 = new jtimer();
        timer1C1.setDelay(10);
        
        step1C4.setNextTrans(step1C5);
        step1C4.setNextSteps(step1C5, step1C1);//, step1C2
        step1C5.setNextTrans(step1C6);
        step1C5.setNextSteps(step1C6, step1C1, step1C2);
        step1C6.setNextTrans(timer1C1);
        step1C6.setNextSteps(step1C1, step1C2, step1C3);

        Sequence seq = new Sequence("1B start pos", 1);
        seq.setInitialTransitions(step1C4);
        seq.setInitialSteps(step1C4, step1C1);
        return seq;
    }



    private static Sequence create1bCut()
    {
        jshooter step1C1 = new jshooter();
        jintake step1C2 = new jintake();
        jIndexer step1C3 = new jIndexer();

        Jturn step1C4 = new Jturn();
        step1C4.setAngle(-86.5);

        jdrive step1C5 = new jdrive();
        step1C5.setAngle(-86.5);
        step1C5.setDist(-1.25);
        step1C5.setSpeed(0.3);

        Jturn step1C6 = new Jturn();
        step1C6.setAngle(-79.5);

        jtimer timer1C1 = new jtimer();
        timer1C1.setDelay(10);
        
        step1C4.setNextTrans(step1C5);
        step1C4.setNextSteps(step1C5, step1C1);//, step1C2
        step1C5.setNextTrans(step1C6);
        step1C5.setNextSteps(step1C6, step1C1, step1C2);
        step1C6.setNextTrans(timer1C1);
        step1C6.setNextSteps(step1C1, step1C2, step1C3);

        Sequence seq = new Sequence("1B start pos cut", 1);
        seq.setInitialTransitions(step1C4);
        seq.setInitialSteps(step1C4, step1C1);
        return seq;
    }

    private static Sequence create2b()
    {
        jtimer timer2B1 =new jtimer();
        timer2B1.setDelay(2);

        jtimer timer2B2 = new jtimer();
        timer2B2.setDelay(0.5);

        jtimer timer2B3 = new jtimer();
        timer2B3.setDelay(5);

        jcurve step2B1 = new jcurve();
        step2B1.setRadius(1.58);
        step2B1.setAngle(-50);
        step2B1.setSpeed(-0.3);

        jdrive step2B2 = new jdrive();
        step2B2.setDist(-0.2);
        step2B2.setAngle(-11.5);
        step2B2.setSpeed(0.3);

        Jturn step2B3 = new Jturn();
        step2B3.setAngle(-38.5);

        Jturn step2B8 = new Jturn();
        step2B8.setAngle(-6.5);

        jdrive step2B9 = new jdrive();
        step2B9.setAngle(-6.5);
        step2B9.setSpeed(0.3);
        step2B9.setDist(-3.5);

        Jturn step2B10 = new Jturn();
        step2B10.setAngle(-45);

        jdrive step2B11 = new jdrive();
        step2B11.setAngle(-45);
        step2B11.setDist(-0.8);
        step2B11.setSpeed(0.3);

        jcurve step2B12 = new jcurve();
        step2B12.setRadius(0.5);
        step2B12.setAngle(10);

        Jturn step2B13 = new Jturn();
        step2B13.setAngle(-21.5);

        jdrive step2B14 = new jdrive();
        step2B14.setAngle(-21.5);
        step2B14.setDist(-1.5);
        step2B14.setSpeed(0.3);

        jshooter step2B4 = new jshooter();
        jIndexer step2B5 = new jIndexer();
        jintake step2B6 = new jintake();
        jUnindexer step2B7 = new jUnindexer();

        step2B13.setNextTrans(step2B14);
        step2B13.setNextSteps(step2B14, step2B7);//, step2B6
       
        step2B14.setNextTrans(step2B3);
        step2B14.setNextSteps(step2B3, step2B4);
        step2B3.setNextTrans(timer2B1);
        step2B3.setNextSteps(step2B5, step2B4);
        timer2B1.setNextTrans(step2B8);
        timer2B1.setNextSteps(step2B8);
        step2B8.setNextTrans(step2B9);
        step2B8.setNextSteps(step2B9);
        step2B9.setNextTrans(step2B10);
        step2B9.setNextSteps(step2B10);
        step2B10.setNextTrans(step2B11);
        step2B10.setNextSteps(step2B11);
        step2B11.setNextTrans(timer2B2);
        step2B11.setNextSteps(step2B7, step2B6);
        timer2B2.setNextTrans(step2B12);
        timer2B2.setNextSteps(step2B12, step2B4);//, step2B6
        step2B12.setNextTrans(timer2B3);
        step2B12.setNextSteps(step2B5, step2B4);//, step2B6

        Sequence seq = new Sequence("2B start pos", 2);
        seq.setInitialTransitions(step2B13);
        seq.setInitialSteps(step2B13, step2B7);//, step2B6
        return seq;
    }

    private static Sequence create2bCut()
    {
        jshooter step2C1 = new jshooter();
        jintake step2C2 = new jintake();
        jIndexer step2C3 = new jIndexer();

        jtimer timer2C1 = new jtimer();
        timer2C1.setDelay(7);

        jcurve step2C5 = new jcurve();
        step2C5.setRadius(1.7);
        step2C5.setAngle(-50);
        step2C5.setSpeed(-0.3);

        jdrive step2C6 = new jdrive();
        step2C6.setDist(-0.2);
        step2C6.setAngle(-11.5);
        step2C6.setSpeed(0.3);

        Jturn step2C7 = new Jturn();
        step2C7.setAngle(-38.5);

        step2C5.setNextTrans(step2C6);
        step2C5.setNextSteps(step2C6, step2C1, step2C2);
        step2C6.setNextTrans(step2C7);
        step2C6.setNextSteps(step2C7, step2C1);
        step2C7.setNextTrans(timer2C1);
        step2C7.setNextSteps(step2C1, step2C3);

        Sequence seq = new Sequence("2B start pos cut", 2);
        seq.setInitialTransitions(step2C5);
        seq.setInitialSteps(step2C5, step2C1);//, step2C2
        return seq;
    }

    private static Sequence create3b()
    {
        jshooter shoot = new jshooter();
        jintake intake = new jintake();
        jIndexer index = new jIndexer();

        Jturn step3B1 = new Jturn();
        step3B1.setAngle(64);

        jdrive step3B2 = new jdrive();
        step3B2.setAngle(64);
        step3B2.setDist(-2.2);
        step3B2.setSpeed(0.3);

        Jturn step3B3 = new Jturn();
        step3B3.setAngle(29);

        jtimer timer3B1 = new jtimer();
        timer3B1.setDelay(7.5);


        step3B1.setNextTrans(step3B2);
        step3B1.setNextSteps(step3B2, shoot,  intake);
        step3B2.setNextTrans(step3B3);
        step3B2.setNextSteps(step3B3, shoot, index, intake);
        step3B3.setNextTrans(timer3B1);
        step3B3.setNextSteps(shoot, index, intake);

        Sequence seq = new Sequence("3B start pos", 3);
        seq.setInitialTransitions(step3B1);
        seq.setInitialSteps(step3B1);
        return seq;
    }  
    
    private static Sequence create3bCut()
    {
        jshooter shoot = new jshooter();
        jIndexer index = new jIndexer();

        Jturn step3B1 = new Jturn();
        step3B1.setAngle(-1.5);
        jdrive step3B2 = new jdrive();
        step3B2.setAngle(-1.5);
        step3B2.setDist(-1.5);
        step3B2.setSpeed(0.3);

        jtimer timer3B1 = new jtimer();
        timer3B1.setDelay(7.5);


        step3B1.setNextTrans(step3B2);
        step3B1.setNextSteps(step3B2, shoot);
        step3B2.setNextTrans(timer3B1);
        step3B2.setNextSteps(shoot, index);


        Sequence seq = new Sequence("3B start pos cut", 3);
        seq.setInitialTransitions(step3B1);
        seq.setInitialSteps(step3B1);
        return seq;
    }

    private static Sequence create4b()
    {
        jshooter shoot = new jshooter();
        jintake intake = new jintake();
        jIndexer index = new jIndexer();

        Jturn step4B1 = new Jturn();
        step4B1.setAngle(36);

        jdrive step4B2 = new jdrive();
        step4B2.setAngle(36);
        step4B2.setDist(-1.5);
        step4B2.setSpeed(0.3);

        jtimer timer4B1 = new jtimer();
        timer4B1.setDelay(5);

        step4B1.setNextTrans(step4B2);
        step4B1.setNextSteps(step4B2, shoot, intake);
        step4B2.setNextTrans(timer4B1);
        step4B2.setNextSteps(shoot, index);
        Sequence seq = new Sequence("4B start pos", 4);
        seq.setInitialTransitions(step4B1);
        seq.setInitialSteps(step4B1, shoot);
        return seq;
    }

    private static Sequence create4bCut()
    {
        jshooter shoot = new jshooter();
        jintake intake = new jintake();
        jIndexer index = new jIndexer();

        Jturn step4B1 = new Jturn();
        step4B1.setAngle(36);

        jdrive step4B2 = new jdrive();
        step4B2.setAngle(36);
        step4B2.setDist(-1.5);
        step4B2.setSpeed(0.3);

        jtimer timer4B1 = new jtimer();
        timer4B1.setDelay(5);

        step4B1.setNextTrans(step4B2);
        step4B1.setNextSteps(step4B2, shoot, intake);
        step4B2.setNextTrans(timer4B1);
        step4B2.setNextSteps(shoot, index);
        Sequence seq = new Sequence("4B start pos", 4);
        seq.setInitialTransitions(step4B1);
        seq.setInitialSteps(step4B1, shoot);
        return seq;
    }





















    private static Sequence createSquare()
    {
        // jtimer timer1 =new jtimer();
        // timer1.setDelay(3);
        jdrive step1=new jdrive();
        step1.setDist(3);
        jdrive timer1 = step1;

        // jtimer timer2 =new jtimer();
        // timer2.setDelay(3);
        Jturn step2=new Jturn();
        step2.setAngle(90);
        Jturn timer2 = step2;

        // jtimer timer3 =new jtimer();
        // timer3.setDelay(3);
        jdrive step3=new jdrive();
        step3.setDist(3);
        step3.setAngle(90);
        jdrive timer3 = step3;

        // jtimer timer4 =new jtimer();
        // timer4.setDelay(3);
        Jturn step4=new Jturn();
        step4.setAngle(180);
        Jturn timer4 = step4;

        // jtimer timer5 =new jtimer();
        // timer5.setDelay(3);
        jdrive step5=new jdrive();
        step5.setDist(3);
        step5.setAngle(180);
        jdrive timer5 = step5;

        // jtimer timer6 =new jtimer();
        // timer6.setDelay(3);
        Jturn step6=new Jturn();
        step6.setAngle(270);
        Jturn timer6 = step6;

        // jtimer timer7 =new jtimer();
        // timer7.setDelay(3);
        jdrive step7=new jdrive();
        step7.setDist(3);
        step7.setAngle(270);
        jdrive timer7 = step7;

        // jtimer timer8 =new jtimer();
        // timer8.setDelay(3);
        Jturn step8=new Jturn();
        step8.setAngle(0);
        Jturn timer8 = step8;

        timer1.setNextTrans(timer2);
        timer1.setNextSteps(step2);
        timer2.setNextTrans(timer3);
        timer2.setNextSteps(step3);
        timer3.setNextTrans(timer4);
        timer3.setNextSteps(step4);
        timer4.setNextTrans(timer5);
        timer4.setNextSteps(step5);
        timer5.setNextTrans(timer6);
        timer5.setNextSteps(step6);
        timer6.setNextTrans(timer7);
        timer6.setNextSteps(step7);
        timer7.setNextTrans(timer8);
        timer7.setNextSteps(step8);
        timer8.setNextTrans(timer1);
        timer8.setNextSteps(step1);

        Sequence seq = new Sequence("Jarryd Square", 0);
        seq.setInitialSteps(step1);
        seq.setInitialTransitions(timer1);
        return seq;

    }

    private static Sequence createCurveShoot()
    {
        jcurve stepc1 = new jcurve();
        stepc1.setRadius(1);
        stepc1.setAngle(-90);
      
        jcurve stepc2 = new jcurve();
        stepc2.setRadius(1);
        stepc2.setAngle(-90);
        stepc2.setSpeed(-0.3);
        jshooter stepc2a = new jshooter();
    
        jdrive stepc3 = new jdrive();
        stepc3.setAngle(0);
        stepc3.setDist(-0.50);
        stepc3.setSpeed(0.3);
        jIndexer stepc3a = new jIndexer();
        
        stepc1.setNextTrans(stepc2);
        stepc1.setNextSteps(stepc2, stepc2a);
        stepc2.setNextTrans(stepc3);
        stepc2.setNextSteps(stepc3, stepc3a, stepc2a);

        Sequence seq = new Sequence("Jarryd Curve Shoot", 0);
        seq.setInitialSteps(stepc1);
        seq.setInitialTransitions(stepc1);
        return seq;
    }

private static Sequence createFullShooter()
{
    jintake stepf1 = new jintake();
    jshooter stepf2 = new jshooter();
    jIndexer stepf3 = new jIndexer();
    jUnindexer stepf4 = new jUnindexer(); 

    jtimer timerf1 = new jtimer();
    timerf1.setDelay(5);
    jtimer timerf2 = new jtimer();
    timerf2.setDelay(5);
    jtimer timerf3 = new jtimer();
    timerf3.setDelay(5);
    jtimer timerf4 = new jtimer();
    timerf4.setDelay(5);

    timerf1.setNextTrans(timerf2);
    timerf1.setNextSteps(stepf2);
    timerf2.setNextTrans(timerf3);
    timerf2.setNextSteps(stepf3, stepf2);


        Sequence seq = new Sequence("Jarryd Full Shooter", 0);
        seq.setInitialSteps(stepf1, stepf4);
        seq.setInitialTransitions(timerf1);
        return seq;
}
private static Sequence createshoot()
{
    jshooter shoot = new jshooter();
    jIndexer index = new jIndexer();
    jBallShooter ball = new jBallShooter();
    ball.setNumBalls(3);
    jBallShooter ball2 = new jBallShooter();
    ball2.setNumBalls(2);
    jUnindexer unindex = new jUnindexer();
    jtimer t1 = new jtimer();
    t1.setDelay(2);
    jtimer t2 = new jtimer();
    t2.setDelay(5);

    jtimer t3 = new jtimer();
    t3.setDelay(3);
    
    Jturn d2 = new Jturn();
    d2.setAngle(90);
   
    jdrive d1 = new jdrive();
    d1.setAngle(0);
    d1.setDist(1);
    d1.setSpeed(0.3);

    shoot.setNextTrans(ball);
    shoot.setNextSteps(shoot, index);
    ball.setNextTrans(t1);
    ball.setNextSteps(unindex);

    Sequence seq = new Sequence("shoot", 0);
    seq.setInitialTransitions(shoot);
    seq.setInitialSteps(shoot);
    return seq;
}



    static LinkedList<Sequence> theSequences = null;
}