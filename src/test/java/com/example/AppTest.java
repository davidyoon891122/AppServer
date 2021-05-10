package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.nettool.TestQuery;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        //assertTrue( true );
        
        String testId = "test";
        String testPwd = "1234";
        TestQuery testQuery = new TestQuery();
        testQuery.set_userId(testId);
        testQuery.set_userPwd(testPwd);
        testQuery.willBuildDataBytes();
        byte[] resultBytes = testQuery.getBytes();
        
        System.out.println("test Bytes : " + resultBytes.length);
        assertEquals(28, resultBytes.length);    

        System.out.println(testQuery.toString());

       
    }
}
