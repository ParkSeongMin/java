package com.seongmin.test.script;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;


/**
 * script api interfaces
 * 	Bindings
 * 	Compilable
 * 	Invocable
 * 	ScriptContext
 * 	ScriptEngine
 * 	ScriptEngineFactory
 * 
 * script api classes
 * 	AbstractScriptEngine
 * 	CompiledScript
 * 	ScriptEngineManager
 * 	SimpleBindings
 * 	SimpleScriptContext
 * 	ScriptException
 * 
 * @author Park SeongMin
 *
 */
public class ScriptEngineTest {

	
	
	@Test
	public void testGetScriptEngine() {

		// http://btsweet.blogspot.kr/2014/09/java-scripting-api-1.html
		
		 // Create a ScriptEngineManager that discovers all script engine
        // factories (and their associated script engines) that are visible to
        // the current thread's classloader.

        ScriptEngineManager manager = new ScriptEngineManager();

        // Obtain a list of all discovered factories as ScriptEngineFactories.
        List<ScriptEngineFactory> factories = manager.getEngineFactories();

        // Display each script engine factory's metadata and create the script engine.
        for (ScriptEngineFactory factory : factories) {
            System.out.println("Full name = " + factory.getEngineName());
            System.out.println("Version = " + factory.getEngineVersion());
            System.out.println("Extensions");
            List<String> extensions = factory.getExtensions();
            for (String extension : extensions) {
                System.out.println("   " + extension);
            }
            System.out.println("Language name = " + factory.getLanguageName());
            System.out.println("Language version = " + factory.getLanguageVersion());
            System.out.println("MIME Types");
            List<String> mimetypes = factory.getMimeTypes();
            for (String mimetype : mimetypes) {
                System.out.println("   " + mimetype);
            }
            System.out.println("Short Names");
            List<String> shortnames = factory.getNames();
            for (String shortname : shortnames) {
                System.out.println("   " + shortname);
            }
            String[] params = {
                        ScriptEngine.ENGINE,
                        ScriptEngine.ENGINE_VERSION,
                        ScriptEngine.LANGUAGE,
                        ScriptEngine.LANGUAGE_VERSION,
                        ScriptEngine.NAME,
                        "THREADING"
                    };
            for (String param : params) {
                System.out.printf("Parameter %s = %s", param, factory.getParameter(param));
                System.out.println();
            }

            ScriptEngine engine = factory.getScriptEngine();
            System.out.println(engine);
            System.out.println();
        }
		
	}
	
	@Test
	public void testEval() throws ScriptException {
		
		// Create a ScriptEngineManager that discovers all script engine
        // factories (and their associated script engines) that are visible to
        // the current thread's classloader.

        ScriptEngineManager manager = new ScriptEngineManager();

        // Obtain a ScriptEngine that supports the JavaScript short name.
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // Evaluate a script that does not return an object.
        Object o = engine.eval("print ('Hello, World')");
        System.out.println();
        System.out.println("Object value: " + o);

        // Evaluate a second script that returns an object.
        o = engine.eval("Math.cos (Math.PI)");
        System.out.println("Object value: " + o);
        System.out.println("Object is Double: " + (o instanceof Double));
	
	}
	
	@Test
	public void testScriptWithFile() throws FileNotFoundException, ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		Object o = engine.eval(new FileReader("script.js"));
	}
	
	@Test
	public void testGetVariable() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();

		// Obtain a ScriptEngine that supports the JavaScript short name.
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		// Initialize the age script variable to 85 (an Integer).
		engine.put("age", 85);

		// Evaluate a script that determines if a person can obtain an amount to
		// deduct from their taxable income based on age.
		engine.eval("var ageAmount = 0.0; if (age >= 65) ageAmount += 6000;");

		// Retrieve the ageAmount script variable (a Double) and output its value.
		System.out.println("ageAmount = " + engine.get("ageAmount"));
	}
	
	@Test
	public void testBindings() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();

		// Obtain a ScriptEngine that supports the JavaScript short name.
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		// Initialize the color and shape script variables.
		engine.put("color", "red");
		engine.put("shape", "rectangle");

		// Evaluate a script that outputs the values of these variables.
		engine.eval("println (color); println (shape);");

		// Save the current bindings object.
		Bindings oldBindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

		// Replace the bindings with a new bindings that overrides color and
		// shape.
		Bindings newBindings = engine.createBindings();
		newBindings.put("color", "blue");
		engine.setBindings(newBindings, ScriptContext.ENGINE_SCOPE);
		engine.put("shape", "triangle");

		// Evaluate the script.
		engine.eval("println (color); println (shape);");

		// Restore the original bindings.
		engine.setBindings(oldBindings, ScriptContext.ENGINE_SCOPE);

		// Evaluate the script.
        engine.eval("println (color); println (shape);");
	}
	
	@Test	
	public void testInputOutput() throws ScriptException {
		
		// script engine 과의 입출력
		
		ScriptEngineManager manager = new ScriptEngineManager();

		// Obtain a ScriptEngine that supports the JavaScript short name.
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		// Redirect the engine's standard output to a StringWriter instance.
		
		// reader and writer
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer, true);
		engine.getContext().setWriter(pw);

		// Evaluate a script that outputs some text.
		engine.eval("print ('This output will go to the string writer.');");

		// Obtain the string buffer's contents and output these contents.
		StringBuffer sb = writer.getBuffer();
		System.out.println("StringBuffer contains: " + sb);
	}
	
	@Test
	public void testCompilable() throws ScriptException {
		
		// compiled script 성능 비교
		
		ScriptEngineManager manager = new ScriptEngineManager();

        // Obtain a ScriptEngine that supports the JavaScript short name.
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // Specify a simple script to demonstrate compilation improvement.
        String script = "function sum (x)"
                + "{"
                + "   return x*(x+1)/2;"
                + "};";

        // Time script parsing and intermediate code execution.
        long now = System.currentTimeMillis();
        int ITER_MAX = 10000;
		for (int i = 0; i < ITER_MAX ; i++) {
            engine.eval(script);
        }
        System.out.println(System.currentTimeMillis() - now);

        Compilable compilable = null;
        if (engine instanceof Compilable) {
            compilable = (Compilable) engine;
            CompiledScript cs = compilable.compile(script);

          // Time intermediate code execution.
            now = System.currentTimeMillis();
            for (int i = 0; i < ITER_MAX; i++) {
                cs.eval();
            }
            System.out.println(System.currentTimeMillis() - now);
        }
	}
	
	@Test
	public void testInvocable() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();

		// Obtain a ScriptEngine that supports the JavaScript short name.
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		// Evaluate a script that defines a global function and an object.
        String script = "function makeEmployee(name, salary)"
	                + "{"
	                + "    var obj = new Object();"
	                + "    obj.name = name;"
	                + "    obj.salary = salary;"
	                + "    obj.print = function()"
	                + "    {"
	                + "        print (\"name = \"+obj.name);"
	                + "        print (\"salary = \"+obj.salary);"
	                + "    };"
	                + "    return obj;"
	                + "}";

		engine.eval(script);

		Invocable invocable = null;
		if (engine instanceof Invocable) {
			invocable = (Invocable) engine;

			// Invoke the makeEmployee() function to make an object.
			Object emp = invocable.invokeFunction("makeEmployee", "John Doe", 40000.0);

			// Print the employee via the object's print() member function.
			invocable.invokeMethod(emp, "print");
		}
		
	}
	
}
