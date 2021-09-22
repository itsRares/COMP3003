package sec.api;

import org.python.core.*;
import org.python.util.*;

public class ScriptHandler 
{
    private PythonInterpreter py;

    public ScriptHandler() {
        this.py = new PythonInterpreter();
    }

    public double runScript(String pythonScript)
    {
        return ((PyFloat) py.eval("float(" + pythonScript + ")")).getValue();
    }

    //Method to import java methods into python with jython
    public void importMethod(String packageName, String method) {
        py.exec("from "+packageName+" import " + method);
    }
}