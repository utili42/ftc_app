package org.firstinspires.ftc.team8923_2017;

import android.annotation.TargetApi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Competition Blue Pos 1", group = "Swerve")
/**
 * Runable shell for Master Autonomous code
 */
//@Disabled
@TargetApi(21)
public class AutonomousCompetitionBluePos1 extends MasterAutonomous
{
    //Declare variables here

    @Override
    public void runOpMode() throws InterruptedException
    {
        //ChooseOptions();

        InitAuto();//Initializes Hardware and sets position based on alliance
        initVuforia();//Initializes Vuforia

        waitForStart();

        moveGG(1500);
        DropJJ();
        sleep(1000);
        GetVumark();
        GetLeftJewelColor();
        double referenceAngle =  imu.getAngularOrientation().firstAngle;

        if (isLeftJewelRed == true)
        {
            IMUPivot(referenceAngle, 20, 0.5, 0.015);
            RetrieveJJ();
            IMUPivot(referenceAngle, 0, 0.5, 0.015);
        }
        else
        {
            IMUPivot(referenceAngle, -20, 0.5, 0.015);
            RetrieveJJ();
            IMUPivot(referenceAngle, 0, 0.5, 0.015);
        }
        MoveIMU(referenceAngle, 50.0, 0.0, 0.015, 0.5, 3.0);//Go towards parking spot
        IMUPivot(referenceAngle, -90, 0.5, 0.015);
        referenceAngle -= 90.0;
        referenceAngle = adjustAngles(referenceAngle);
        MoveIMU(referenceAngle, -50.0, 0.0, 0.015, 0.5, 0.8);
        moveGG(-1500);
        while (opModeIsActive())
        {
            //Run();
            idle();
        }

    }
}
