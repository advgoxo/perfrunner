package com.jmeterx.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Min2 extends AbstractFunction {
    private static final List<String> desc;
    private static final String KEY = "__min2";

    static {
        desc = new LinkedList();
        desc.add("Actual value 1");
        desc.add("Actual value 2");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private Object[] values;
    public Min2() {
    }

    @Override
    public synchronized String execute(SampleResult previousResult,
                                       Sampler currentSampler) throws InvalidVariableException {
        int n1 = 0;
        int n2 = 0;
        String var1 = getParameter(0).trim();
        String var2 = getParameter(1).trim();

        try {
            n1 = Integer.valueOf(var1);
        }catch (NumberFormatException e) {
            n1 = 0;
        }

        try {
            n2 = Integer.valueOf(var2);
        } catch (NumberFormatException e) {
            n2 = 0;
        }

        String result = String.valueOf(Math.min(n1, n2));
        JMeterVariables vars = getVariables();
        if (vars != null && values.length > 2) {
            String varName = getParameter(2).trim();
            vars.put(varName, result);
        }

        return result;
    }

    @Override
    public synchronized void setParameters(
            Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 2);
        values = parameters.toArray();
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    private String getParameter(int i) {
        return ((CompoundVariable) values[i]).execute();
    }
}