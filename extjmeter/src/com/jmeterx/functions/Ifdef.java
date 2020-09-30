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


public class Ifdef extends AbstractFunction {
    private static final List<String> desc;
    private static final String KEY = "__ifdef";

    static {
        desc = new LinkedList();
        desc.add("Var name");
        desc.add("Result if Var is define");
        desc.add("Result if Var is undefine");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private Object[] values;
    public Ifdef() {
    }

    @Override
    public synchronized String execute(SampleResult previousResult,
                                       Sampler currentSampler) throws InvalidVariableException {
        String result = null;
        String varname = getParameter(0).trim();
        String defined = getParameter(1).trim();
        String undefined = getParameter(2).trim();
        JMeterVariables vars = getVariables();

        System.out.println("varname " + varname);
        System.out.println("defined " + defined);
        System.out.println("undefined " + undefined);
        System.out.println("var " + vars.get(varname));

        if(vars.getObject(varname) != null) {
            result = defined;
        } else {
            result = undefined;
        }

        if (vars != null && values.length > 3) {
            String varName = getParameter(3).trim();
            vars.put(varName, result);
        }

        return result;
    }

    @Override
    public synchronized void setParameters(
            Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 3);
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