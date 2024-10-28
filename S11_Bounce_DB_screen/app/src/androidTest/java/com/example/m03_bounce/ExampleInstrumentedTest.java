package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Color;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.m03_bounce", appContext.getPackageName());
    }

    @Test
    public void addOneRemoveOneBall() {
        BallsList ballsList = new BallsList(appContext);
         Ball ball = new Ball(Color.RED, 5, 5, 5, 5);
         ballsList.addBall(ball);
        try(DBClass db = new DBClass(appContext)) {
            assertEquals(1, ballsList.getBalls().size());
            assertEquals(1, db.findAll().size());
            Ball testBall = ballsList.getBalls().get(0);
            ballsList.removeBall(testBall);
            assertEquals(0, ballsList.getBalls().size());
            assertEquals(0, db.findAll().size());
        }
    }

    @Test
    public void addTwoRemoveOneBall() {
        BallsList ballsList = new BallsList(appContext);
        Ball ball1 = new Ball(Color.RED, 5, 5, 5, 5);
        Ball ball2 = new Ball(Color.RED, 5, 5, 5, 5);
        ballsList.addBall(ball1);
        ballsList.addBall(ball2);
        try(DBClass db = new DBClass(appContext)) {
            assertEquals(2, ballsList.getBalls().size());
            assertEquals(2, db.findAll().size());
            Ball testBall = ballsList.getBalls().get(0);
            ballsList.removeBall(testBall);
            assertEquals(1, ballsList.getBalls().size());
            assertEquals(1, db.findAll().size());
            ballsList.clear();
        }
    }

    @Test
    public void addTwoAndClear() {
        BallsList ballsList = new BallsList(appContext);
        Ball ball1 = new Ball(Color.RED, 5, 5, 5, 5);
        Ball ball2 = new Ball(Color.RED, 5, 5, 5, 5);
        ballsList.addBall(ball1);
        ballsList.addBall(ball2);
        try(DBClass db = new DBClass(appContext)) {
            assertEquals(2, ballsList.getBalls().size());
            assertEquals(2, db.findAll().size());
            ballsList.clear();
            assertEquals(0, ballsList.getBalls().size());
            assertEquals(0, db.findAll().size());
        }
    }
}