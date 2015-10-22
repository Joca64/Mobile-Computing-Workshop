package com.mygdx.game;

import java.util.Random;

public class Operation
{
    int result;
    String operator;
    String[] operators = {"+","-"};

    public Operation()
    {
        operator = operators[new Random().nextInt(2)];
        generateResult();
        System.out.println("Operation: _" +operator +"_=" +result);
    }

    private void generateResult()
    {
        if(operator.equals("+"))
            result = new Random().nextInt(19);
        else
            result = new Random().nextInt(10 + 9) - 9;
    }

    public boolean validateResult(int first, int second)
    {
        if(operator.equals("+") && first + second == result)
            return true;
        else if(operator.equals("-") && first - second == result)
            return true;
        return false;
    }

    public String getOperator()
    {
        return operator;
    }

    public int getResult()
    {
        return result;
    }
}
