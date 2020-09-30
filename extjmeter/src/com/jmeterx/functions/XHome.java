package com.jmeterx.functions;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Properties;

import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.functions.InvalidVariableException;

public class XHome extends AbstractFunction {
    private static final List<String> desc;
    private static final String KEY = "__xhome";

    static {
        desc = new LinkedList();
        desc.add("relative path");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private Object[] values;
    public XHome() {
    }

    @Override
    public synchronized String execute(SampleResult previousResult,
                                       Sampler currentSampler) throws InvalidVariableException {
        String r = getParameter(0).trim();
        Properties props = JMeterContextService.getContext().getProperties();
        String result = new File(props.getProperty(
                "xhome", (new File("")
                ).getAbsolutePath()),
                r).getAbsolutePath();

        JMeterVariables vars = getVariables();
        if (vars != null && values.length > 2) {
            String varName = getParameter(1).trim();
            vars.put(varName, result);
        }

        System.out.println("xhome " + result);
        return result;
    }

    @Override
    public synchronized void setParameters(
            Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 1);
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